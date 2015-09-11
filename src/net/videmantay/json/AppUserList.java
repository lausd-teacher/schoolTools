package net.videmantay.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArray;

public class AppUserList extends JsArray<AppUser> {

	protected AppUserList(){}
	public final  List<AppUser> getList(){
		ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
		for(int i = 0 ; i < this.length(); i++){
			appUserList.add(this.get(i));
		}
		
		return appUserList;
	}
}
