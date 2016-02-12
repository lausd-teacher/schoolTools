package net.videmantay.admin;

import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;


import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;

import net.videmantay.admin.json.AppUserJson;

public class AppUserAsyncDataProvider extends AsyncDataProvider<AppUserJson> {
	private final ArrayList<AppUserJson> list = new ArrayList<AppUserJson>();
	private static int cursor = 0;
	private boolean previous = false;
	Properties data;
	
	
	@Override
	protected void onRangeChanged(final HasData<AppUserJson> display) {
		
		if(previous){
			cursor -= 50;
		}else{
			cursor += 50;
		}
		
		data = $$("cursor:" + cursor);
		Ajax.get(AdminUrl.USER_LIST).done(new Function(){
			@Override
			public void f(){
				JsArray<AppUserJson> array = JsonUtils.unsafeEval((String)this.arguments(0)).cast();
				 for(int i = 0; i < array.length(); i++){
					 list.add(array.get(i));
				 }//end for
				 display.setRowCount(list.size());
				display.setRowData(0, list); 
			}
		});
	}
	
	@Override
	public Long getKey(AppUserJson item){
		return item.getId();
	}

}
