package net.videmantay.server;

import com.google.appengine.api.taskqueue.DeferredTask;

import java.io.IOException;
import java.lang.reflect.Field;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

import net.videmantay.server.entity.Goal;

public class GoalBookSetup implements DeferredTask {

	private final String token;
	private final String sheetId;
	
	public GoalBookSetup(String sheetId, String token){
		this.token = token;
		this.sheetId = sheetId;
	}
	@Override
	public void run() {
		GoogleCredential cred = new GoogleCredential();
		cred.setAccessToken(token);
		
		SpreadsheetService service = GoogleUtils.sheets(cred);
		
		try {
			SpreadsheetFeed feed = service.getFeed(GoogleUtils.spreadsheetURL(), SpreadsheetFeed.class);
			SpreadsheetEntry goalbook = GoogleUtils.spreadsheetById(feed, sheetId);
			
			WorksheetEntry goalsheet = goalbook.getDefaultWorksheet();
			goalsheet.setTitle(new PlainTextConstruct("Goals"));
			goalsheet.setRowCount(1);
			goalsheet.setColCount(Goal.class.getFields().length);
			goalsheet.update();
			CellFeed cFeed = service.getFeed(goalsheet.getCellFeedUrl(), CellFeed.class);
			
			Field[] fields = Goal.class.getFields();
			for(int i = 0; i< fields.length; i++){
				CellEntry cell = new CellEntry(1,i+1, fields[i].getName());
				cFeed.insert(cell);
			}
			

		} catch (IOException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
