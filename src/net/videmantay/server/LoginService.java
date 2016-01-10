package net.videmantay.server;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.*;

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;



@SuppressWarnings("serial")
public class LoginService extends HttpServlet {
	
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		login(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		login(req, res);
	}
	
	
	private void  login(HttpServletRequest req , HttpServletResponse res){
		 User user = null;
		
	
			user = UserServiceFactory.getUserService().getCurrentUser();
	
		//first check to see if authenticated
		if(user == null || !UserServiceFactory.getUserService().isUserLoggedIn()){
			
			try {
				res.sendRedirect(UserServiceFactory.getUserService().createLoginURL("/login"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end if
		
		//next load user acct if null
		//account not exist catch error sent to 
		//error page
		AppUser appUser = DB.db().load().type(AppUser.class).filter("acctId", user.getEmail()).first().now();
		if(appUser == null){
			//redirect to error page no account with that user is set
			
			return;
		}
		
		req.getSession().setAttribute("appUser", appUser);
		if(appUser.getRoles().contains(AppRole.ADMIN)){
			
		}else if(appUser.getRoles().contains(AppRole.TEACHER)){
		
		}else if(appUser.getRoles().contains(AppRole.FACULTY)){
			
		}else if(appUser.getRoles().contains(AppRole.STUDENT)){
			
		}
			
				
	}

}
