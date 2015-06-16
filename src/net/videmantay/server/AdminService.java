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
	
	
		
	
}
