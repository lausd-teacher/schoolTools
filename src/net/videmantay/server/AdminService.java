package net.videmantay.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.entity.AppResources;
import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.DB;
import net.videmantay.server.entity.UserStatus;
import static net.videmantay.server.entity.DB.*;
import net.videmantay.server.entity.UserAccount;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.drive.*;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;


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
	private final String USER_QUERY = "/admin/queryuser";
	private final String USER_GET = "/admin/getuser";
	private final String USER_LIST = "/admin/listusers";
	private final String USER_PIC_URL = "/admin/getuserpicurl";
	private final String BLOBSTORE_HANDLER = "/admin/handleblobstore";
	private final Logger log = Logger.getLogger("Admin Service");
	

	
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
			case USER_QUERY: queryUserAcct(req, res);break   ;
			case USER_PIC_URL: getUserPicUrl(req,res);break;
			case USER_SAVE : saveUserAcct(req, res);break; 
			}
		}
		if(req.getMethod().equalsIgnoreCase("POST")){
			//set up switch and use uri as argument
			switch(req.getRequestURI()){
			case USER_SAVE : saveUserAcct(req, res);break; //excecute appropriate method
			case USER_DELETE : deleteUserAcct(req, res);break ;
			case BLOBSTORE_HANDLER: handleBlobstore(req,res);break;
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
		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		final DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
		final DB<UserAccount> userAcctDB = new DB<UserAccount>(UserAccount.class);
		final StringBuilder result = new StringBuilder();
		log.log(Level.INFO, "create user called");
		
		try{
		final UserAccount acct;
	//if request Params are null or empty then get the Attribute
	if(req.getParameter("userAccount") == null || req.getParameter("userAccount").isEmpty()){
		
			log.log(Level.INFO, "Parameter is null so use the Attribute.");
		acct = (UserAccount) req.getAttribute("userAccount");
			log.log(Level.INFO, "The attribute is : " + gson.toJson(acct) );
	}else{
		log.log(Level.INFO, "Paramerter is not null so use the Parameter");
		acct = gson.fromJson(req.getParameter("userAccount"), UserAccount.class);
		log.log(Level.INFO, "Paramerter is : " + gson.toJson(acct));
	}

	
	
			//this may be just an update for UserRecord so check for id
			//first see if account with that parameter exists.
			//we must check by id just in case email was modified 
	//if acct doesn't have an id then it need to be created
	if(acct.getId() == null || acct.getId().equals("")){//create the user under these conditions
		log.log(Level.INFO, "acct id number : " + acct.getId());
					//This means that it is new and an id must be assigned
		  acct.setId(UUID.randomUUID() +"");
					
					//before we do anything see if user exists
					List<UserAccount> check = userAcctDB.query("acctId", acct.getAcctId());
					
					if(check != null && check.size() <0 && check.get(0).getAcctId().equalsIgnoreCase(acct.getAcctId())){
						log.log(Level.WARNING, "user account id already in DB");
						res.setContentType("application/json");
						res.getWriter().write("{\"message\":\"User already exists\"}");
						return;
					}
					
					
				ofy().transact(new VoidWork(){

					@Override
					public void vrun() {
						
						//Teacher set up will happen when teacher 
						//first use apps
						
					
						//set the pic url here
						//get user key and list as arg for userAcct
						AppUser appUser = new AppUser();
						appUser.setAcctId(acct.getAcctId());
						appUser.setFirstName(acct.getFirstName());
						appUser.setLastName(acct.getLastName());
						appUser.setDefaultPicUrl(acct.getPic());
						Key<AppUser> userKey = appUserDB.save(appUser);
						acct.setAppUser(userKey);
						userAcctDB.save(acct);
						
						res.setContentType("application/json");
						try {
							res.getWriter().println("{\"message\":\"User Successfully created\"}");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
						
					}});
				
					
				
			}else{// account will be updated
				
			
			
			UserAccount modify =	db().load().type(UserAccount.class).filter("acctId",acct.getAcctId()).first().now();
			log.log(Level.INFO, "acct from the DB by id is : " + gson.toJson(modify));
			modify.setAcctId(acct.getAcctId());
			modify.setFirstName(acct.getFirstName());
			modify.setLastName(acct.getLastName());
			if(acct.getPic() == null || acct.getPic().isEmpty()){}//must do something here empty body can't be good
			else{
			modify.setPic(acct.getPic());
			}//end else setPic
				userAcctDB.save(modify);
				
					res.setContentType("application/json");
					res.getWriter().println("{\"message\":\"User Successfully updated\"}");
				log.log(Level.INFO, "The result of modify : " +result.toString());
				return;
			}//end else account will be update
		
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
	
	
	private void deleteUserAcct(HttpServletRequest req, HttpServletResponse res){
		Gson gson = new Gson();
		
		log.log(Level.INFO, "delete user called");
		final UserAccount acct = gson.fromJson(req.getParameter("user"), UserAccount.class);
		
		
		//would delete drive stuff but actually that is entirely up 
		// to the user to do
	
		db().transact(new VoidWork(){

			@Override
			public void vrun() {
				//db().delete().key(Key.create(acct.getUser()));
				DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
				DB<UserAccount> userAcctDB  = new DB<UserAccount>(UserAccount.class);
				//Create a key from the entity
		
				userAcctDB.delete(acct);
				System.out.println("Delete user successful");
				
			}});
	
	}
	
	private void listUserAccts(HttpServletRequest req, HttpServletResponse res){
	
		final DB<UserAccount> userAcctDB = new DB<UserAccount>(UserAccount.class);
		Gson gson =new Gson();
		ofy().clear();
		log.log(Level.INFO, "List users is called");
		List<UserAccount> userAcctList = userAcctDB.list();
		
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
		final DB<UserAccount> userAcctDB = new DB<UserAccount>(UserAccount.class);
		final Gson gson =new Gson();
		
		String query = req.getParameter("query");
		UserAccount acct = null;
		
		acct = 	userAcctDB.query("email", query).iterator().next();
		
	
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
	
	public List<UserAccount> queryUserAcct(HttpServletRequest req, HttpServletResponse res){
		final DB<UserAccount> userAcctDB = new DB<UserAccount>(UserAccount.class);
		
		String field = req.getParameter("field");
		String search = req.getParameter("search");
		return userAcctDB.search(field, search);
	}
	
	public void handleBlobstore(HttpServletRequest req, HttpServletResponse res){
		log.log(Level.INFO, "Handle blobstore was called");
		//this may have been called from Blobstore so first check to see if 
				//pic url has been set
				 String picUrl = "";		
				 ImagesService is  = ImagesServiceFactory.getImagesService();
					BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
					Map<String, BlobKey> blobKeys = bs.getUploadedBlobs(req);
					//we'll assume one image per request
					log.log(Level.INFO, "The blob keys is: " + blobKeys.isEmpty());
				BlobKey bk= 	blobKeys.get("image");
					if(bk != null){
						log.log(Level.INFO , "blobKey success blobKey is " + bk.getKeyString());
						
					 
					ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(bk);
					
					picUrl = is.getServingUrl(options);
					
					log.log(Level.INFO, "picUrl = " + picUrl);
					}
					
					UserAccount newAcct = new UserAccount();
					//first check to see if id was sent 
					String idCheck = req.getParameter("id");
					if(idCheck != null && !idCheck.isEmpty() && Long.parseLong(idCheck) > 0 ){
						newAcct.setId(idCheck);
						
					}
						String keySet = "";
						int counter = 1;
						for(Object k: req.getParameterMap().keySet()){
							keySet += counter +". " + k + " : " + req.getParameter((String) k) + "\n";
							counter++;
						}
						log.log(Level.INFO, keySet);
						log.log(Level.INFO, "req parameter from blob are as followed : \n"+
					                        "acct id: " + req.getParameter("acctId") + "\n" +
								             "first Name: " + req.getParameter("firstName"));
						newAcct.setAcctId(req.getParameter("acctId"));
						newAcct.setFirstName(req.getParameter("firstName"));
						newAcct.setLastName(req.getParameter("lastName"));
						newAcct.setUserStatus(UserStatus.valueOf(req.getParameter("userStatus").toUpperCase()));
						//this may be just an update for UserRecord so check for id
						if(req.getParameter("id") != null || !req.getParameter("id").isEmpty()){
						newAcct.setId(req.getParameter("id"));
						}
						//check that the pic url is valid to throw away
						if(req.getParameter("picUrl") != null && !req.getParameter("picUrl").isEmpty() ){
							BlobKey blobKey = new BlobKey(req.getParameter("picUrl"));
							is.deleteServingUrl(blobKey);
							bs.delete(blobKey);
						}
						newAcct.setPic(picUrl);
					
						
					req.setAttribute("user",newAcct);
					saveUserAcct(req, res);
					
	}
	
	public void  getUserPicUrl(HttpServletRequest req, HttpServletResponse res){
		log.log(Level.INFO, "get blobstore url called");
		
		BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
		
		String url = 	bs.createUploadUrl("/admin/handleblobstore");
		log.log(Level.INFO ,"the url is :" + url);
		res.setContentType("text/plain");
		try {
			res.getWriter().write(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void teacherSetup(UserAccount userAcct) throws TokenResponseException{
		//check that there is root folder called Kim_LAUSD
		// if one exists there is nothing else to do 
		//but if not create one with minimum requirements
		
		// this can only be done with accounts that you personally manage
		// what about teacher with there own google accounts how do they give you 
		// permission??? while they sign up // in the mean time use your own accounts.
		//What kind of scopes Docs obviously what about tasks??? calendar??? 
		//sync calendars // send tasks
		AppResources appRes = new AppResources();
		appRes.setAppUserId(userAcct.getId());
		
			//get cred for teacher to set up docs, calendar, tasks
		GoogleCredential cred = null;
		try {
			cred = MyUtils.createCredentialForUser(MyUtils.transport(), MyUtils.jsonFactory(), MyUtils.serviceAccountId(), MyUtils.privateKey(), userAcct.getAcctId(), DriveScopes.DRIVE, CalendarScopes.CALENDAR, TasksScopes.TASKS);
		} catch (GeneralSecurityException | IOException e1) {
			// TODO Auto-generated catch block
			log.log(Level.WARNING, "Unable to get creds");
			e1.printStackTrace();
		}
		
		
		try{
		Drive drive = new Drive.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).setApplicationName("Kimchi").build();
		File kimchiFolder = MyUtils.createFolder("Kimchi");
		//kimchiFolder.setThumbnailLink("https://lh5.googleusercontent.com/-f06vW5PUAik/U-kMFfPJuLI/AAAAAAAAJWY/pNrzF3yklxQ/s16-no/chkLogo16.png");
		kimchiFolder.setIconLink("https://lh5.googleusercontent.com/-Ncp95NXgbHM/U-kMcnbOrKI/AAAAAAAAJvw/Sy9AOXjlqrs/s128-no/chkLogo128.png");
		kimchiFolder =  drive.files().insert(kimchiFolder).execute();
		appRes.setKimchiFolder(kimchiFolder.getId());
		//set kimchi as parent folder of the rest
		ParentReference parentRef = new ParentReference();
		parentRef.setId(kimchiFolder.getId());
		parentRef.setParentLink(kimchiFolder.getSelfLink());
		

		File sharedStuff = MyUtils.createFolder("Shared");
		sharedStuff.setParents(Arrays.asList(parentRef));
		sharedStuff = drive.files().insert(sharedStuff).execute();
		appRes.setRootSharedFolder(sharedStuff.getId());
		
		File worksheets = MyUtils.createFolder("Worksheets");
		worksheets.setParents(Arrays.asList(parentRef));
		worksheets = drive.files().insert(worksheets).execute();
		appRes.setRootWorksheetFolder(worksheets.getId());
		
		File showcase = MyUtils.createFolder("Showcase");
		showcase.setParents(Arrays.asList(parentRef));
		showcase = drive.files().insert(showcase).execute();
		appRes.setRootShowcaseFolder(showcase.getId());
		
		File schoolStuff = MyUtils.createFolder("School");
		schoolStuff.setParents(Arrays.asList(parentRef));
		schoolStuff = drive.files().insert(schoolStuff).execute();
		appRes.setRootSchoolFolder(schoolStuff.getId());
		
		
		}catch(TokenResponseException e){
			throw e;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.log(Level.WARNING, "Google Drive Error");
			e.printStackTrace();
		}
		
	
			//set up shared calendar
			//this means the calendar exists and user 
		//are being given permission status
			appRes.setSharedCalendar(MyUtils.getCalendar());
			
			com.google.api.services.tasks.Tasks task = new com.google.api.services.tasks.Tasks.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).setApplicationName("Kimchi").build();
			
			TaskList kimchiTasks = new TaskList();
			kimchiTasks.setTitle("Kimchi Tasks");
			try {
				kimchiTasks = task.tasklists().insert(kimchiTasks).execute();
				Task createRoster = new Task();
				createRoster.setTitle("Create Roster").setNotes("Create an inital Roster to get started");
				task.tasks().insert(kimchiTasks.getId(), createRoster).execute();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DB.db().save().entity(appRes);
	}//end teacherSetup

	
}
