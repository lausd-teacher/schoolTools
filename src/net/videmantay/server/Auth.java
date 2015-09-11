package net.videmantay.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.entity.AppUser;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.drive.DriveScopes;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

import freemarker.template.TemplateException;

public class Auth extends AbstractAppEngineAuthorizationCodeServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		User user = UserServiceFactory.getUserService().getCurrentUser();
		if(user == null || !UserServiceFactory.getUserService().isUserLoggedIn()){
			UserServiceFactory.getUserService().createLoginURL("/test");
		}
		
		AuthorizationCodeFlow auth =  initializeFlow();
		Credential cred = 	auth.loadCredential(UserServiceFactory.getUserService().getCurrentUser().getUserId());
		if(cred == null || !cred.refreshToken()){
			res.sendRedirect(auth.newAuthorizationUrl().build());
		}
		
		Gson gson = new Gson();
		
		
		AppUser appUser = new AppUser();
		appUser.setAcctId(UserServiceFactory.getUserService().getCurrentUser().getEmail());
		try{
		appUser.setAuthToken(cred.getAccessToken());
		}catch(NullPointerException npe){
			res.sendRedirect(auth.newAuthorizationUrl().build());
		}
		HashMap<String , Object> root =new HashMap<String, Object>();
		root.put("appUser",gson.toJson(appUser) );
		try {
			TemplateGen.config().getTemplate("/WEB-INF/html/roster.html").process(root, res.getWriter());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req)
			throws ServletException, IOException {
		  GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		    url.setRawPath("/oauth2callback");
		    return url.build();
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
			IOException {
		return MyUtils.newFlow();
	}

}
