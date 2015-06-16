package net.videmantay.server;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.*;

import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.DB;



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
		 DB<AppUser> userAcctDB = new DB<AppUser>(AppUser.class);
	
		
	
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
		
			try{
				AppUser userAcct = userAcctDB.query("email", user.getEmail()).iterator().next();
				
				if(userAcct.getAcctId().equalsIgnoreCase("lee@videmantay.net")){
					res.sendRedirect("/admin");
					return;
				}
				
				req.getSession().setAttribute("userAcct", userAcct);
				String path = "";
				switch(userAcct.getUserStatus()){
				case TEACHER: path = "/teacher";break;
				case STUDENT: path ="/student"; break;
				case ADMIN: path = "/admin" ; break;
				case AIDE: path = "/aide" ; break;
				
				}
				
				 res.sendRedirect(path);
				
			}catch(NullPointerException  | NoSuchElementException e){
				///Error likely cause be account not existing so 
				//send to error page
				try {
					res.sendRedirect("/error.html");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e) {
				//Page not there send to 404 error 
				//or something  or error page
				e.printStackTrace();
			} 
	}

}
