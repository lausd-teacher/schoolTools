package net.videmantay.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.client.spreadsheet.WorksheetQuery;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class GoogleUtils {
	private GoogleUtils(){}
	
	private final static GsonFactory jsonFactory = new GsonFactory();
	private final static UrlFetchTransport transport = new UrlFetchTransport();
	private final static String applicationName = "Kimchi";
	private final static String clientId = "";
	private final static String clientSecret = "";
	private final static AppEngineDataStoreFactory dataStoreFactory =new AppEngineDataStoreFactory();
	
	public final static String ContactsScope = "https://www.google.com/m8/feeds";
	public final static String SheetsScope = "https://spreadsheets.google.com/feeds";

	
	
	
	public static GoogleAuthorizationCodeFlow authFlow(String userId) throws IOException{
		ArrayList<String> scopes = new ArrayList<String>();
		scopes.add(DriveScopes.DRIVE);
		scopes.add(DriveScopes.DRIVE_APPDATA);
		scopes.add(CalendarScopes.CALENDAR);
		scopes.add(TasksScopes.TASKS);
		scopes.add(ContactsScope);
		scopes.add(SheetsScope);
		
		GoogleAuthorizationCodeFlow flow = 
				  new GoogleAuthorizationCodeFlow.Builder(transport,jsonFactory,clientId,clientSecret,scopes)
				  .setDataStoreFactory(dataStoreFactory)	
				  .addRefreshListener(new DataStoreCredentialRefreshListener(userId,dataStoreFactory))
				  .build();
		
		flow.loadCredential(userId);
						
		return flow;	
	}
	
	public static Drive drive(Credential cred){
		return new Drive.Builder(transport , jsonFactory, cred).setApplicationName(applicationName).build();
	}
	
	public static Tasks task(Credential cred){
		return new Tasks.Builder(transport , jsonFactory, cred).setApplicationName(applicationName).build();
	}
	
	public static Calendar calendar(Credential cred){
		return new Calendar.Builder(transport , jsonFactory, cred).setApplicationName(applicationName).build();
	}
	
	public static SpreadsheetService sheets(Credential cred){
		SpreadsheetService sheets = new SpreadsheetService(applicationName);
		sheets.setOAuth2Credentials(cred);
		return sheets;
	}
	
	public static ContactsService contacts(Credential cred){
		ContactsService contacts = new ContactsService(applicationName);
		contacts.setOAuth2Credentials(cred);
		return contacts;
	}
	
	public static File folder(String title){
		File folder = new File().setMimeType("application/vnd.google-apps.folder").setTitle(title);
		return folder;
	}
	
	public static List<ParentReference> parent(String parent){
		List<ParentReference> list = new ArrayList<ParentReference>();
		ParentReference parentFolder = new ParentReference();
		parentFolder.setId(parent);
		list.add(parentFolder);
		return list;
	}
	
	public static File spreadsheet(String title){
		File folder = new File().setMimeType("application/vnd.google-apps.spreadsheet").setTitle(title);
		return folder;
	}
	
	public static WorksheetEntry gradedWorkSheet(Credential cred, String id) throws MalformedURLException, IOException, ServiceException{
		return worksheetEntry(cred,id,"gradedWork");
	}
	
	public static WorksheetEntry gradebookSheet(Credential cred, String id) throws MalformedURLException, IOException, ServiceException{
		return worksheetEntry(cred, id, "gradeBook");
	}
	
	public static SpreadsheetEntry spreadsheetEntry(Credential cred, String sheetId) throws MalformedURLException, IOException, ServiceException{
		SpreadsheetService sheets = sheets(cred);
		SpreadsheetEntry spreadsheet = sheets.getFeed(spreadsheetURL(sheetId), SpreadsheetFeed.class).getEntries().get(0);
		return spreadsheet;
	}
	
	public static WorksheetEntry worksheetEntry(Credential cred, String sheetId, String title) throws MalformedURLException, IOException, ServiceException{
		SpreadsheetService sheets = sheets(cred);
		SpreadsheetEntry spreadsheet = spreadsheetEntry(cred, sheetId);
		WorksheetQuery wQuery = new WorksheetQuery(spreadsheet.getWorksheetFeedUrl());
		wQuery.setTitleQuery(title);
		WorksheetEntry worksheet = sheets.getFeed(wQuery, WorksheetFeed.class).getEntries().get(0);
		
		return worksheet;
	}
	
	static URL spreadsheetURL(String id) throws MalformedURLException{
		return new URL(SheetsScope +"/spreadsheets/" + id +"/private/basic");
		
	}
	static String redirectUri(HttpServletRequest req) {
	    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
	    url.setRawPath("/oauth2callback");
	    return url.build();
	}
	
	

}
