package net.videmantay.server;

import static net.videmantay.server.user.DB.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.googlecode.objectify.Key;

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;

public class AppService extends HttpServlet {
	
	final Logger log = Logger.getLogger(AppService.class.getSimpleName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		initServlet(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		initServlet(req, res);
	}

	private void initServlet(HttpServletRequest req, HttpServletResponse res){
		final Gson gson = new Gson();
		
		
		
		// set constants
		final String  UPDATE_APPUSER = "/app/updateappuser";
		final String GET_APPUSER = "/app/getappuser";
	
		
		String uri = req.getRequestURI();
		switch(uri){
		case UPDATE_APPUSER: updateAppUser(req, res); break;
		case GET_APPUSER: getAppUser(req, res);break;
		}
		
		
	}
	
	private void updateAppUser(HttpServletRequest req, HttpServletResponse res){
		final Gson gson = new Gson();
		final DB<AppUser> appUserDB =new DB<AppUser>(AppUser.class);
		
		//some security checks here will be nice
		// either you are admin or you are the appUser which means going to the 
		// Account to verify
		// this is better to do in a filter
		
		//1. check to see if request has parameter "appProf" in not then 
		// use attribute appUser
		AppUser appUser;
	   if(req.getParameter("appUser") == null || req.getParameter("appUser").isEmpty()){
		   appUser = (AppUser) req.getAttribute("appUser");  
	   }else{
		   appUser = gson.fromJson(req.getParameter("appUser"), AppUser.class);
	   }
		if(appUser != null && appUser.getAcctId() != null ||!appUser.getAcctId().isEmpty()){
		//	if(isAuthorized(appUser)){
				AppUser checkUser = appUserDB.getById(appUser.getId());
				if(checkUser == null){
					throw new NullPointerException("Sorry No app User here something is awry!!");
				}else{
					checkUser.setFirstName(appUser.getFirstName());
					checkUser.setLastName(appUser.getLastName());
					checkUser.setEmail(appUser.getEmail());
					checkUser.setExtendedName(appUser.getExtendedName());
					checkUser.setMiddleName(appUser.getMiddleName());
					appUserDB.save(checkUser);
					
				}// end else
		//	}//inner if
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