package net.videmantay.server;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Splitter;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.model.batch.BatchUtils;
import com.google.gdata.util.ServiceException;
import com.google.gson.Gson;

import net.videmantay.server.entity.GradedWork;

public class GradeBookSetup implements DeferredTask {
	
	public final String id;
	public  final String oauth;
	
	public GradeBookSetup(final String sheetId, final String token){
		this.id = sheetId;
		this.oauth = token;
	}
	@Override
	public void run() {
		GoogleCredential cred = new GoogleCredential();
		cred.setAccessToken(oauth);
		
		SpreadsheetService service = GoogleUtils.sheets(cred);
		SpreadsheetFeed feed = null;
		try {
			feed = service.getFeed(GoogleUtils.spreadsheetURL(), SpreadsheetFeed.class);
		} catch (IOException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpreadsheetEntry spreadsheet = GoogleUtils.spreadsheetById(feed, id);
		
		WorksheetEntry gradebook ;
		
		
		try {
			gradebook = spreadsheet.getDefaultWorksheet();
			gradebook.setTitle(new PlainTextConstruct("Gradebook"));
			//set up student cell
			gradebook.update();
			CellFeed cFeed = service.getFeed(gradebook.getCellFeedUrl(), CellFeed.class);
			CellEntry studentCell = new CellEntry(1,1,"Students");
			cFeed.insert(studentCell);
			
		} catch (ServiceException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WorksheetEntry worksheet;
		worksheet = new WorksheetEntry();
		worksheet.setColCount(GradedWork.class.getFields().length);
		worksheet.setRowCount(1);
		worksheet.setTitle(new PlainTextConstruct("Assignments"));
		try {
			worksheet = service.insert(spreadsheet.getWorksheetFeedUrl(), worksheet);
		} catch (ServiceException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CellFeed cellFeed = null;
		try {
			cellFeed = (CellFeed) worksheet.getService().getFeed(worksheet.getCellFeedUrl(), CellFeed.class);
			Field[] fields = GradedWork.class.getFields();
			CellEntry cellEntry;
			for(int i = 0; i< fields.length; i++){
				int colNum = i+1;
				cellEntry = new CellEntry(1,colNum,fields[i].getName());
				cellFeed.insert(cellEntry);
			}
			
		}catch(Exception e){
			
			
		}
		
	}

	

}
