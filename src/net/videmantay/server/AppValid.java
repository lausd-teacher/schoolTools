package net.videmantay.server;

import static net.videmantay.server.user.DB.db;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.Roster;
import net.videmantay.shared.UserRoles;

public class AppValid {

	private final static Logger log = Logger.getLogger(AppValid.class.getSimpleName());
	private AppValid(){}
	
	public static boolean userCheck(){
		UserService us = UserServiceFactory.getUserService();
		User curUser = us.getCurrentUser();
		if(!us.isUserLoggedIn()){
			return false;
		}
		AppUser user = db().load().type(AppUser.class).filter("acctId", curUser.getEmail()).first().now();
		if(user == null){
			return false;
		}
		log.log(Level.INFO, "user email is " + curUser.getEmail() + " and appUser acctId is " + user.getAcctId());
		return (curUser.getEmail().equals(user.getAcctId()));
	}
	
	public static boolean roleCheck(UserRoles... roles){
		UserService us = UserServiceFactory.getUserService();
		User curUser = us.getCurrentUser();
		if(!us.isUserLoggedIn()){
			return false;
		}
		AppUser user = db().load().type(AppUser.class).filter("acctId", curUser.getEmail()).first().now();
		if(user == null){
			return false;
		}
		for(UserRoles r: roles){
			log.log(Level.INFO, "User has role " + r.name() + "? " + user.getRoles().contains(r));
			if(!user.getRoles().contains(r)){
				return false;
			}
		}
		
		return true;
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
