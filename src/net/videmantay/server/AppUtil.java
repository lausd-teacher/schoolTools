package net.videmantay.server;

import net.videmantay.server.entity.DB;
import net.videmantay.server.entity.UserAccount;

public class AppUtil {
	
	public static Boolean isValidAcct(String email){
		DB<UserAccount> userAcctDB = new DB<UserAccount>(UserAccount.class);
		
		return !userAcctDB.query("email", email).isEmpty();
	}

}
