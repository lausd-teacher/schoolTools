package net.videmantay.admin;

import java.util.ArrayList;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

import net.videmantay.json.AppUser;
import net.videmantay.json.AppUserList;

public class AppUserListProvider extends ListDataProvider<AppUser> {

	private final AppUserListProvider $this = this;
	private final ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
	@Override
	public Long getKey(AppUser item) {
		return item.getId();
	}

}
