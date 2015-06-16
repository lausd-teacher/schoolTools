package net.videmantay.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.StorageHolder.Type;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.Key;

import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.DB;
import net.videmantay.shared.StuffType;
import static net.videmantay.server.entity.DB.*;

public class AppService extends HttpServlet {
	
	final Logger log = Logger.getLogger(AppService.class.getSimpleName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		initServlet(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		initServlet(req, res);
	}

	private void initServlet(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException{
		final Gson gson = new Gson();
		final DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
		
		
		// set constants
		final  String  UPDATE_APPUSER = "/app/updateappuser";
		final  String GET_APPUSER = "/app/getappuser";
		final  String SAVE_APPUSER = "/app/saveappuser";
		final  String DELETE_APPUSER = "/app/deleteuser";
		final  String LIST_APPUSER = "/app/listuser";
	
		
		String uri = req.getRequestURI();
		switch(uri){
		case UPDATE_APPUSER: updateAppUser(req, res); break;
		case GET_APPUSER: getAppUser(req, res);break;
		case SAVE_APPUSER: createAppUser(req, res);break;
		case DELETE_APPUSER: deleteAppUser(req, res);break;
		case LIST_APPUSER:listAppUser(req,res);break;
		}
		
		
	}
	
	private void listAppUser(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		final DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
		final List<AppUser>appUserList;
		String query = req.getParameter("query");
		if(query == null || query.isEmpty() || query.equals("*")){
			//list all
			 appUserList = appUserDB.list();
			
			
		}else{
			//use query as filter for fistName , lastName and email
			
			 appUserList = 
			DB.db().load().type(AppUser.class)
					.filter("email >=", query)
					.filter("firstName >=" ,query)
					.filter("lastName >=", query)
					.distinct(true).limit(150).list();	
		}
		
		Stuff<AppUser> stuff = new Stuff<AppUser>(appUserList);
		stuff.setType(StuffType.LIST);
		res.getWriter().write(gson.toJson(stuff));
		
	}
	
	private void createAppUser(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		final DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);	
		
	String json = req.getParameter("user");
	if(json != null && !json.isEmpty()){
		AppUser user = gson.fromJson(json, AppUser.class);
		//before save make sure id is unique and acctId is unique
		if(appUserDB.getById(user.getId()) != null || appUserDB.query("acctId", user.getAcctId()) != null){
			log.log(Level.INFO, "Tried to save user with duplicate id");
			Stuff stuff = new Stuff(null);
			stuff.setMessage("This user already exist");
		}
		appUserDB.save(user);
		ArrayList<String> message = new ArrayList<String>();
		message.add("User was created successfully!");
		
		Stuff<String> stuff = new Stuff<String>(message);
		res.getWriter().write(gson.toJson(stuff));
	}else{
		log.log(Level.WARNING, "Null or empty sting sent across wire");
		ArrayList<String> message = new ArrayList<String>();
		message.add("User was NOT created! \n Please ensure that you complete all necessary fields!");
		
		Stuff<String> stuff = new Stuff<String>(message);
		res.getWriter().write(gson.toJson(stuff));
		
	}
		
	}

	private void deleteAppUser(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		final DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);	
		
	String json = req.getParameter("user");
	if(json != null && !json.isEmpty()){
		AppUser user = gson.fromJson(json, AppUser.class);
		appUserDB.delete(user);
		
		
		Stuff<String> stuff = new Stuff<String>(null);
		stuff.setMessage("user deleted successfully!");
		res.getWriter().write(gson.toJson(stuff));
	}else{
		log.log(Level.WARNING, "Null or empty sting sent across wire");
		ArrayList<String> message = new ArrayList<String>();
		message.add("User was NOT deleted! \n Please ensure that you complete all necessary fields!");
		message.add("field appears empty!");
		
		Stuff<String> stuff = new Stuff<String>(message);
		stuff.setMessage("There was an error please read the following:");
		res.getWriter().write(gson.toJson(stuff));
		
	}
	
}
	
	private void updateAppUser(HttpServletRequest req, HttpServletResponse res) throws IOException{
		final Gson gson = new Gson();
		final DB<AppUser> appUserDB =new DB<AppUser>(AppUser.class);
		
		//some security checks here will be nice
		// either you are admin or you are the appUser which means going to the 
		// Account to verify
		// this is better to do in a filter
		
		//1. check to see if request has parameter "appUser"  if not then 
		// use attribute appUser
		AppUser appUser;
	   if(req.getParameter("appUser") == null || req.getParameter("appUser").isEmpty()){
		   appUser = (AppUser) req.getAttribute("appUser");  
	   }else{
		   appUser = gson.fromJson(req.getParameter("appUser"), AppUser.class);
	   }
		if(appUser != null && appUser.getAcctId() != null ||!appUser.getAcctId().isEmpty()){
				AppUser checkUser = appUserDB.getById(appUser.getId());
				if(checkUser == null){
					log.log(Level.WARNING, "Tried to update a non-existing user");
					Stuff stuff = new Stuff(null);
					stuff.setMessage("User not registered, update unsuccessful");
					res.getWriter().write(gson.toJson(stuff));
					throw new NullPointerException("Sorry No app User here something is awry!!");
				
				}else{
					checkUser.setFirstName(appUser.getFirstName());
					checkUser.setLastName(appUser.getLastName());
					checkUser.setEmail(appUser.getEmail());
					checkUser.setExtendedName(appUser.getExtendedName());
					checkUser.setMiddleName(appUser.getMiddleName());
					checkUser.setDefaultPicUrl(appUser.getDefaultPicUrl());
					appUserDB.save(checkUser);
					
					Stuff<AppUser> stuff = new Stuff<AppUser>(Arrays.asList(appUser));
					stuff.setMessage("user update successful");
					res.getWriter().write(gson.toJson(stuff));
					
				}// end else
		}//outer if
	}
	
	private void getAppUser(HttpServletRequest req, HttpServletResponse res){
		final Gson gson = new Gson();
		final DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
		
		
		log.log(Level.INFO, "key is" + req.getParameter("key"));
		Key<AppUser> key =  (Key<AppUser>)gson.fromJson(req.getParameter("key"), Key.class);
		log.log(Level.INFO, "key is " + key.toString());
		//must do a check here either you are an admin or you are trying to modify your own app profile
	//make sure key is valid and retireve appUser
		if(key != null ){
		AppUser appUser = appUserDB.getById(key.getId());
		log.log(Level.INFO, appUser.getAcctId());
		//check if user is authorized 
		
		}
		
	}
	
	
	
}