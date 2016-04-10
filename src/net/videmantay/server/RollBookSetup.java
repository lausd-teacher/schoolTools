package net.videmantay.server;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class RollBookSetup implements DeferredTask {

	public final String oauth;
	public final String id;
	public RollBookSetup(final String sheetId, final String token ){
		id = sheetId;
		oauth = token;
	}
	@Override
	public void run() {
		//set up attendence sheet
		GoogleCredential cred = new GoogleCredential();
		cred.setAccessToken(oauth);
		SpreadsheetService  service = GoogleUtils.sheets(cred);
		
		try {
			SpreadsheetFeed sFeed = service.getFeed(GoogleUtils.spreadsheetURL(), SpreadsheetFeed.class);
			SpreadsheetEntry rollBook = GoogleUtils.spreadsheetById(sFeed, id);
			
			WorksheetEntry attendenceSheet = rollBook.getDefaultWorksheet();
			
			attendenceSheet.setTitle(new PlainTextConstruct("RollBook"));
			attendenceSheet.setColCount(1);
			attendenceSheet.setRowCount(1);
			attendenceSheet.update();
			
			
			CellFeed cFeed = service.getFeed(attendenceSheet.getCellFeedUrl(), CellFeed.class);
			CellEntry cellEntry = new CellEntry(1,1,"students");
			cFeed.insert(cellEntry);
			
			//setup hw check
			WorksheetEntry hwSheet = new WorksheetEntry();
			hwSheet.setTitle(new PlainTextConstruct("Homework"));
			hwSheet.setColCount(1);
			hwSheet.setRowCount(1);
			
			WorksheetFeed wFeed = service.getFeed(rollBook.getWorksheetFeedUrl(), WorksheetFeed.class);
			hwSheet = wFeed.insert(hwSheet);
			
			cFeed = service.getFeed(hwSheet.getCellFeedUrl(), CellFeed.class);
			cellEntry = new CellEntry(1,1,"students");
			cFeed.insert(cellEntry);
			
			WorksheetEntry logs  = new WorksheetEntry();
			logs.setTitle(new PlainTextConstruct("Logs"));
			logs.setColCount(4);
			logs.setRowCount(1);
			
			CellEntry logDate = new CellEntry(1,1, "date");
			CellEntry logType = new CellEntry(1,2,"type");
			CellEntry logLevel = new CellEntry(1,3,"level");
			CellEntry logMsg = new CellEntry(1,4,"message");
			
			logs = wFeed.insert(logs);
			
			cFeed = service.getFeed(logs.getCellFeedUrl(), CellFeed.class);
			cFeed.insert(logDate);
			cFeed.insert(logType);
			cFeed.insert(logLevel);
			cFeed.insert(logMsg);
			
			
			
		} catch (IOException | ServiceException e) {

			e.printStackTrace();
		}
		
		
		//setup studentGroup sheet
		
		
		//setup studentJob sheet
		
		
		//setup goals
		
		
	

	}

}
