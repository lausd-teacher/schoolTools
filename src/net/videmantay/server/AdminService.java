package net.videmantay.server;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.HttpStatus;

import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.DB;
import net.videmantay.server.entity.RosterDetail;
import net.videmantay.server.entity.RosterStudent;
import static net.videmantay.server.entity.DB.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.QueryKeys;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;


/*
 * Admin service for creating userAccts and appUser(which should mirror userAccts);
 * for every call to a UserAcct return both AppUser and UserAcct Make client class
 * AcctInfo
 */
@SuppressWarnings("serial")
public class AdminService  extends HttpServlet {
	
	private final String ADMIN_PAGE = "/admin";
	private final  String USER_SAVE ="/admin/saveuser";
	private final String USER_DELETE = "/admin/deleteuser"; 
	private final String USER_GET = "/admin/getuser";
	private final String USER_LIST = "/admin/listusers";
	private final String USER_PIC_URL = "/admin/getuserpicurl";
	private final String BLOBSTORE_HANDLER = "/admin/handleblobstore";
	private final Logger log = Logger.getLogger("Admin Service");
	
	private Gson gson = new Gson();
	

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		initRequest(req,res);
	}
	
	@Override 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		log.log(Level.FINE, "doPost called");
		initRequest(req,res);
	}
	
	private void initRequest(HttpServletRequest req, HttpServletResponse res){
		DB.start();
		res.setContentType("application/json");
		//check request route
		//set up factory using both method and uri
		log.log(Level.FINE, "initMehtod called with req" + req.getRequestURI());
		if(req.getMethod().equalsIgnoreCase("GET")){
			//set up switch for uri
			switch(req.getRequestURI()){
			case ADMIN_PAGE: getAdminView(req, res);break;//get amdin page
			case USER_GET : getUserAcct(req, res);break ;///end first gate
			case USER_LIST: listUserAccts(req, res);break     ;
			case USER_SAVE : saveUserAcct(req, res);break; 
			}
		}
		if(req.getMethod().equalsIgnoreCase("POST")){
			//set up switch and use uri as argument
			switch(req.getRequestURI()){
			case USER_SAVE : saveUserAcct(req, res);break; //excecute appropriate method
			case USER_DELETE : deleteUserAcct(req, res);break ;
			
			}
		}
		
	}
	

		private void getAdminView(HttpServletRequest req, HttpServletResponse res){
			try {
				res.setContentType("text/html");
				res.getWriter().write(TemplateGen.getAdminPage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	private void saveUserAcct(final HttpServletRequest req, final HttpServletResponse res){
		final  DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
		final StringBuilder result = new StringBuilder();
		
		log.log(Level.INFO, "create user called");
		
		try{
		final AppUser acct;
	//if request Params are null or empty then get the Attribute
	if(req.getParameter("user") == null || req.getParameter("user").isEmpty()){
		
			log.log(Level.INFO, "Parameter is null so use the Attribute.");
		acct = (AppUser) req.getAttribute("user");
			log.log(Level.INFO, "The attribute is : " + gson.toJson(acct) );
	}else{
		log.log(Level.INFO, "Paramerter is not null so use the Parameter");
		acct = gson.fromJson(req.getParameter("user"), AppUser.class);
		log.log(Level.INFO, "Paramerter is : " + gson.toJson(acct));
	}

	
	
			//this may be just an update for UserRecord so check for id
			//first see if account with that parameter exists.
			//we must check by id just in case email was modified 
	//if acct doesn't have an id then it need to be created
	if(acct.getId() == null || acct.getId().equals("")){//create the user under these conditions
		
					
					//before we do anything see if user exists
					List<AppUser> check = appUserDB.query("acctId", acct.getAcctId());
					
					if(check != null && check.size() >0 && check.get(0).getAcctId().equalsIgnoreCase(acct.getAcctId())){
						log.log(Level.WARNING, "user account id already in exists");
						res.setContentType("application/json");
						res.getWriter().write("{\"message\":\"User already exists\"}");
						return;
					}
					
					//assign main drive folder
				ofy().transact(new VoidWork(){

					@Override
					public void vrun() {
						
						//Teacher set up will happen when teacher 
						//first use apps
						
					
						//set the pic url here
						//get user key and list as arg for userAcct
						acct.setId(appUserDB.save(acct).getId());
							
					}});
				
				
			}else{// account will be updated
							
			AppUser modify =	db().load().type(AppUser.class).id(acct.getId()).now();
			log.log(Level.INFO, "acct from the DB by id is : " + gson.toJson(modify));
			modify.setAcctId(acct.getAcctId());
			modify.setFirstName(acct.getFirstName());
			modify.setLastName(acct.getLastName());
			modify.setRoles(acct.getRoles());
			modify.setIsFirstLogin(acct.getIsFirstLogin());
			modify.setLastUpdate(acct.getLastUpdate());
			modify.setLoginTimes(acct.getLoginTimes());
			modify.setMainDriveFolder(acct.getMainDriveFolder());
			modify.setAuthToken(acct.getAuthToken());
			modify.setEmail(acct.getEmail());
			modify.setTitle(acct.getTitle());
			
			appUserDB.save(modify);
				
			}//end else account will be update
	
	res.setContentType("application/json");
	try {
		
		res.getWriter().println(gson.toJson(acct));

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			System.out.print("ther request param is " + req.getParameter("user"));
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void deleteUserAcct(HttpServletRequest req, final HttpServletResponse res){
		
		log.log(Level.INFO, "delete user called");
		String userCheck =  req.getParameter("user");
		log.log(Level.INFO, "delete user is : " + userCheck);
		final AppUser acct = gson.fromJson(userCheck, AppUser.class);
		
		
		//would delete drive stuff but actually that is entirely up 
		// to the user to do
	
		db().transact(new VoidWork(){

			@Override
			public void vrun() {
				//grabe the entire graph that pertains to that user.
				//if it is a teacher then delete all of his rosters
				//if it is a student delete all rosterStudent references
				if(acct.getRoles().contains(AppRole.STUDENT) && acct.getRosterDetails().size() > 0){
					
					for(RosterDetail rosD: acct.getRosterDetails()){
						Key<RosterStudent> ancestor = Key.create(RosterStudent.class, rosD.getRosterId());
						QueryKeys<Object> keys = db().load().ancestor(ancestor).keys();
						db().delete().keys(keys);
					}
				}//end roster check
				Key<AppUser> createdKey = Key.create(AppUser.class, acct.getId());
				db().delete().key(createdKey);
				System.out.println("delete user key is : Key " + createdKey.getString());
				System.out.println("Delete user successful");
				try {
					res.setContentType("text/plain");
					res.getWriter().write("true");
					return;
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}});
		
	}
	
	private void listUserAccts(HttpServletRequest req, HttpServletResponse res){
	
		ofy().clear();
		log.log(Level.INFO, "List users is called");
		List<AppUser> userAcctList = db().load().type(AppUser.class).list();
		
	res.setContentType("application/json");
	try {
		
		System.out.println(gson.toJson(userAcctList));
		
		res.getWriter().write(gson.toJson(userAcctList));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	private void getUserAcct(HttpServletRequest req, HttpServletResponse res){
		
		String query = req.getParameter("query");
		AppUser acct = null;
		
		acct = 	db().load().type(AppUser.class).filter("acctId", query).first().now();
		
	
			try {
				if(acct == null){
				res.getWriter().write(" {\"message\":\"No user with that email \n please try again\"} ");
				}else{
				res.getWriter().write(gson.toJson(acct));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}//end if
		
	
	}
	
private void validateAppUser(final HttpServletRequest req, final HttpServletResponse res,final AppUser appUser) throws IOException, ServletException{
	EmailValidator emailV =  EmailValidator.getInstance();
	UrlValidator urlV = UrlValidator.getInstance();
	//one big if to validate the user 
	if(!emailV.isValid(appUser.getAcctId())
		&&!urlV.isValid(appUser.getPicUrl())
		&& Strings.isNullOrEmpty(appUser.getFirstName())
		&& Strings.isNullOrEmpty(appUser.getLastName())){
		//send back to client 
		
			System.out.println("Invalid user");
		
	}// end if valid
	
	//clean what you  have
	appUser.setFirstName( CharMatcher.anyOf(" *%#@+_)(!+_?/';:.,<>&)").removeFrom(appUser.getFirstName()));
	appUser.setLastName(CharMatcher.anyOf(" *%#@+_)(!+_?/';:.,<>&)").removeFrom(appUser.getLastName()));
	appUser.setMiddleName(CharMatcher.anyOf(" *%#@+_)(!+_?/';:.,<>&)").removeFrom(appUser.getMiddleName()));
	
		
}
	
}
