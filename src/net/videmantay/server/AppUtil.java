package net.videmantay.server;

import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.DB;


public class AppUtil {
	
	public static Boolean isValidAcct(String email){
		DB<AppUser> userAcctDB = new DB<AppUser>(AppUser.class);
		
		return !userAcctDB.query("email", email).isEmpty();
	}

}
