package net.videmantay.server;

import static net.videmantay.server.user.DB.db;

import java.util.List;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.Roster;

public class AppValid {

	private AppValid(){}
	
	public static boolean userCheck(AppUser user){
		UserService us = UserServiceFactory.getUserService();
		User curUser = us.getCurrentUser();
		if(!us.isUserLoggedIn()){
			//send user to get logged in
		}
		return (curUser.getEmail().equals(user.getAcctId()));
	}
	
	public static boolean rosterCheck(Long id){
		User curUser = UserServiceFactory.getUserService().getCurrentUser();
		List<Key<Roster>> list = db().load().type(Roster.class).filter("ownerId", curUser.getEmail()).keys().list();
		
		for(Key<Roster> k: list){
			if(k.getId() == id){
				return true;
			}//end if
		}//end for
		
		return false;
	}
}
