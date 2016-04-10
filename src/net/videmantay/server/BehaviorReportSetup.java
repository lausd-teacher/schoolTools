package net.videmantay.server;

import java.io.IOException;
import java.lang.reflect.Field;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

import net.videmantay.server.entity.Incident;
import net.videmantay.server.entity.StudentIncident;

public class BehaviorReportSetup implements DeferredTask {

	public final String token;
	public final String id;
	
	
	public BehaviorReportSetup(final String sheet, final String oauth){
		token = oauth;
		id = sheet;
	}


	@Override
	public void run() {
		GoogleCredential cred = new GoogleCredential();
		cred.setAccessToken(token);
		
		SpreadsheetService service = GoogleUtils.sheets(cred);
		SpreadsheetFeed feed;
		try {
			feed = service.getFeed(GoogleUtils.spreadsheetURL(), SpreadsheetFeed.class);
			SpreadsheetEntry behaviorReport = GoogleUtils.spreadsheetById(feed, id);
			WorksheetEntry behaviorWorksheet = behaviorReport.getDefaultWorksheet();
			behaviorWorksheet.setTitle(new PlainTextConstruct("Incident Types"));
			behaviorWorksheet.setRowCount(1);
			behaviorWorksheet.setColCount(Incident.class.getFields().length);
			behaviorWorksheet.update();
			
			CellFeed cFeed = service.getFeed(behaviorWorksheet.getCellFeedUrl(), CellFeed.class);
	
			Field[] fields = Incident.class.getFields();
			
			
			for(int i = 1; i <= fields.length; i++){
				CellEntry cell = new CellEntry(1,i, fields[i-1].getName());
				cFeed.insert(cell);
			}
			
		} catch (IOException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
