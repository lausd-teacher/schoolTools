package net.videmantay.server;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.roster.RosterUrl;
import net.videmantay.server.entity.*;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import net.videmantay.server.user.Roster;
import net.videmantay.server.user.RosterDetail;
import net.videmantay.server.user.RosterSetting;
import net.videmantay.server.user.RosterStudent;
import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.StuffType;
import net.videmantay.shared.SubjectType;
import net.videmantay.shared.UserRoles;
import static net.videmantay.server.GoogleUtils.*;
import static net.videmantay.server.user.DB.*;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Preconditions;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.AclRule.Scope;
import com.google.api.services.calendar.model.Event.ExtendedProperties;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskList;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.client.spreadsheet.CellQuery;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.client.spreadsheet.WorksheetQuery;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.spreadsheet.Cell;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.model.batch.BatchUtils;
import com.google.gdata.util.ServiceException;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;


@SuppressWarnings("serial")
public class RosterService extends AbstractAppEngineAuthorizationCodeServlet  {
	
	private final Logger log = Logger.getLogger(RosterService.class.getCanonicalName());
	private final Gson gson = new Gson();
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		try {
			init(req, res);
		} catch (GeneralSecurityException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		try {
			init(req, res);
		} catch (GeneralSecurityException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void init(HttpServletRequest req, HttpServletResponse res)throws IOException , ServletException, GeneralSecurityException, ServiceException{
		//authorize
		initializeFlow();
		// 3. Route The Path
		String path = req.getRequestURI();
		
		switch(path){
		
	
		case "/teacher":getTeacherView(req,res);break;
		case RosterUrl.LIST_ROSTERS:listRoster(req, res);break;
		case RosterUrl.CREATE_RSOTER:saveRoster(req,res);break;
		case RosterUrl.GET_ROSTER: getRoster(req,res);break;
		
		}
		
		
	}
	
	           /////////////////  OAUTH Mehtods   ///////////////////////////////////////////
	
	@Override
	protected String getRedirectUri(HttpServletRequest req)
			throws ServletException, IOException {
		  GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		    url.setRawPath("/oauth2callback");
		    return url.build();
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
			IOException {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		return GoogleUtils.authFlow(user.getUserId());
	}
	////////////////end oauth ///////////////////////////////////////////////////////
	
	//Teacher view////
	private void getTeacherView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		if(AppValid.roleCheck(UserRoles.TEACHER)){
		res.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8888");
		res.getWriter().write(TemplateGen.getTeacherPage());}
		else{
			res.sendRedirect("/login");
		}
	}
	
	//ROSTER CRUD
	private void saveRoster(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException, GeneralSecurityException, ServiceException{
		if(!AppValid.roleCheck(UserRoles.TEACHER)){
			//send to login
			res.sendRedirect("/login");
		}
		final DB<Roster> rosterDB = new DB<Roster>(Roster.class);
		final Credential cred;
		
		Drive drive;
		Calendar calendar;
		Tasks tasks;
		ContactsService contactService;
		SitesService sitesService;
		SpreadsheetService spreadsheetService;
	
		
		
		RosterSetting settings;
		final Roster roster;
		final User user = UserServiceFactory.getUserService().getCurrentUser();
		
		
		 String rosterCheck = req.getParameter("roster");
		 
		 
		if(rosterCheck != null && !rosterCheck.isEmpty()){
			//if the roster is good then assign it
		roster = gson.fromJson(rosterCheck, Roster.class);
		
		}else{
			//Throw and exception roster can't be null 
			ArrayList<String> message = new ArrayList<>();
			message.add("There was a problem persisting the data. Please ensure all required info  is present");
			
			Stuff<String> stuff = new Stuff<>(message);
			stuff.setType(StuffType.MESSAGE);
			res.getWriter().write(gson.toJson(stuff));
			log.log(Level.WARNING, "Null reference to roster sent check client for errors");
			return;
		}
		
		//if roster has an id then it's an update else look
		//so update it 
		if(roster.getId() != null && roster.getId() != 0){
			//This is an update just save the roster
			//get roster by id then do transfer
			
			Roster rosDB = rosterDB.getById(roster.getId());
			
			//check that user is indeed the owner or an admin
			if(rosDB != null){
				if(user.getEmail().equals(rosDB.getOwnerId())){
					final RosterDetail rosDetail = roster.createDetail();
					
					db().transact(new VoidWork(){

						@Override
						public void vrun() {
							rosterDB.save(roster);
							db().save().entity(rosDetail);
						}});
				

					}else{
				//throw an error here.
			
			//send a confirmation message
			ArrayList<String> message= new ArrayList<String>();
			message.add("Roster update successful");
			Stuff<String> stuff = new Stuff<String>(message);
			stuff.setType(StuffType.MESSAGE);
			String returnThis = gson.toJson(stuff);
			res.getWriter().write(returnThis);
			return;
			
		}
			}// end if roster id Null ///////////END UPDATE ROSTER /////////////////////
			
		/////////FIRST SAVE HERE ///////////////////////////////////////////////////////////////////////////		
		}else{//this is a first save set up docs,calendar,etc
	
		final AppUser appUser = db().load().type(AppUser.class).filter("acctId",user.getEmail()).first().now();
		 cred = this.getCredential();
		 System.out.println( "This is what the cred looks like: " + gson.toJson(cred));
		 //check for roster setting in the db 
		 settings = db().load().type(RosterSetting.class).filter("acctId",user.getEmail()).first().now();
		 if(settings == null){
			 settings = new RosterSetting().defaultSetting();
		 }
		 drive = GoogleUtils.drive(cred);
		 
		 			//first check if main drive folder has been set
		 		if(appUser.getMainDriveFolder()==null || appUser.getMainDriveFolder().isEmpty()){
		 			//assign a main folder
		 			File kimchiFile = GoogleUtils.folder("Kimchi");
		 			kimchiFile = drive.files().insert(kimchiFile).execute();
		 			appUser.setMainDriveFolder(kimchiFile.getId());
		 			db().save().entity(appUser);
		 			
		 		}
				File rosterFolder = GoogleUtils.folder(roster.getTitle());
				rosterFolder.setDescription(roster.getDescription());
				
				//get Kimchi folder as parent of roster;
				ParentReference parentFolder = new ParentReference();
				parentFolder.setId(appUser.getMainDriveFolder());
				List<ParentReference> rosParent = new ArrayList<ParentReference>();
				rosParent.add(parentFolder);
				rosterFolder.setParents(rosParent);
				rosterFolder = drive.files().insert(rosterFolder).execute();
				roster.setRosterFolderId(rosterFolder.getId());
				
				
				//set roster folder as parent for all other folders
				ParentReference rp = new ParentReference();
				rp.setId(rosterFolder.getId());
				List<ParentReference> listOfP = new ArrayList<ParentReference>();
				listOfP.add(rp);
				
			File rollBook = spreadsheet("RollBook");
				rollBook.setParents(listOfP);
				File gradeBook = spreadsheet("GradeBook");
				gradeBook.setParents(listOfP);
			File behaviorReport = spreadsheet("BehaviorReport");
				behaviorReport.setParents(listOfP);
				
				rollBook = drive.files().insert(rollBook).execute();
				gradeBook = drive.files().insert(gradeBook).execute();
				behaviorReport = drive.files().insert(behaviorReport).execute();
				
				roster.setGradeBook(gradeBook.getId());
				System.out.println("gradeBook id is: " + gradeBook.getId());
				roster.setRollBook(rollBook.getId());
				roster.setBehaviorReport(behaviorReport.getId());
				
				//set up the spreadsheet here
				GradeBookSetup setUp = new GradeBookSetup(gradeBook.getId(), cred.getAccessToken());
				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withPayload(setUp));
				
				RollBookSetup rollSet = new RollBookSetup(rollBook.getId(), cred.getAccessToken());
				queue.add(TaskOptions.Builder.withPayload(rollSet));
				
				
				
			/*	//optional folders use settings
				//only if there are any
				
			*/
					
			//Set up Gradedwork Calendar and class events
			calendar = GoogleUtils.calendar(cred);
			com.google.api.services.calendar.model.Calendar cal = new com.google.api.services.calendar.model.Calendar();
			cal.setSummary(roster.getTitle());
			cal.setDescription(roster.getDescription());
			cal = calendar.calendars().insert(cal).execute();
			GoogleService rosterCal = new GoogleService();
			rosterCal.setTitle(cal.getSummary());
			rosterCal.setDescription(cal.getDescription());
			rosterCal.setType("calendar");
			rosterCal.setId(cal.getId());
			
			//adding more calendars happens client side///
			roster.getGoogleCalendars().add(rosterCal);
			
			//Create a roster task list
			tasks = GoogleUtils.task(cred);
			TaskList taskList = new TaskList();
			taskList.setTitle(roster.getTitle());
			taskList = tasks.tasklists().insert(taskList).execute();
			
			GoogleService rosterTask = new GoogleService();
			rosterTask.setId(taskList.getId());
			rosterTask.setTitle(taskList.getTitle());
			rosterTask.setDescription("");
			
			roster.getGoogleTasks().add(rosterTask);
			
			//save the owner on the server side
			roster.setOwnerId(user.getEmail());
			roster.getAccess().add(user.getEmail());
			
			//at this point there is no id so we wait for one
			//to assign to rd
			RosterDetail rd = roster.createDetail();
			
			rd.setId(rosterDB.save(roster).getId());
			db().save().entity(rd);
			// Set up a google sites for Class info
		}//end first save else////////
	

	}	
	
	
	
	private void deleteRoster(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		if(AppValid.userCheck() == false){
			res.sendRedirect("/error");
		}
		
		String rosterCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		Roster roster = gson.fromJson(rosterCheck, Roster.class);
		if(AppValid.rosterCheck(roster.getId()) == false){
			//send error to client
		}
	
		
		
		//delete roster and immediate descendents- students etc
		List<Key<Object>> keys = db().load().ancestor(roster).keys().list();
		db().delete().keys(keys);
		//delete all gradedwork and descendents
		List<Key<GradedWork>> gwKeys = db().load().type(GradedWork.class).filter("rosterId",roster.getId()).keys().list();
		for(Key<GradedWork> key: gwKeys){
		List<Key<Object>> stuff =	db().load().ancestor(key).keys().list();
		db().delete().keys(stuff);
		}
	}
	
	private void listRoster(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
		//this method will only list the roster 
		User user = UserServiceFactory.getUserService().getCurrentUser();
		List<RosterDetail> rosters = db().load().type(RosterDetail.class).filter("acctId", user.getEmail() ).list();
		log.log(Level.INFO, "list of rosters is: " + gson.toJson(rosters) );
		res.getWriter().write(gson.toJson(rosters));
	}
	
	private void getRoster(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		User user = UserServiceFactory.getUserService().getCurrentUser();
		if(!UserServiceFactory.getUserService().isUserLoggedIn()){
			res.sendRedirect("/login");
		}
		String idCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		Long id = Longs.tryParse(idCheck);
		
		 Roster roster = db().load().type(Roster.class).filter("id", id).filter("acctId",user.getEmail()).first().now();
		if(roster == null){
			//send something bad
			res.setStatus(res.SC_UNAUTHORIZED, "Unauthorized request");
			res.flushBuffer();
			return;
		}
		
	}
	
	////////RosterStudent CRUD ///////////////////////////////
	
	private void saveRosterStudent(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException
	{
		Drive drive;
		Calendar calendar;
		Credential cred = this.getCredential();
		
		String studentCheck = Preconditions.checkNotNull(req.getParameter("student"));
		String rosterCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		
		RosterStudent student = gson.fromJson(studentCheck, RosterStudent.class);
		Roster roster = gson.fromJson(rosterCheck, Roster.class);
		
			//first save access to rosterDetail
			RosterDetail rd = db().load().type(RosterDetail.class).id(student.getRoster()).now();
			rd.getAccess().add(student.getStudentGoogleId());	
			db().save().entity(rd);
		
	
		student.getAccess().add(student.getStudentGoogleId());	
		student.setId(new Date().getTime());
		
		drive = drive(cred);
		calendar = calendar(cred);
		//create folder for student
		File studentFolder = folder(student.getId().toString());
		List<ParentReference> parent = parent(roster.getRosterFolderId());
		studentFolder.setParents(parent);
		List<Permission> permissions = new ArrayList<Permission>();
		Permission perm = new Permission();
		perm.setEmailAddress(student.getStudentGoogleId());
		perm.setRole("reader");
		perm.setValue(student.getStudentGoogleId());
		perm.setType("user");
		permissions.add(perm);
		studentFolder.setPermissions(permissions);
		
		
		
		studentFolder = drive.files().insert(studentFolder).execute();
		student.setStudentFolderId(studentFolder.getId());
		
		com.google.api.services.calendar.model.Calendar cal = new com.google.api.services.calendar.model.Calendar();
		cal.setSummary(student.getStudentGoogleId());
		cal.setDescription("Calendar for " + student.getFirstName() +" " + student.getLastName()+ " that includes assignments, events , and incidents.");
		cal = calendar.calendars().insert(cal).execute();
		student.setStudentCalId(cal.getId());
		
		AclRule rule = new AclRule();
		rule.setRole("reader");
		rule.setId(student.getStudentGoogleId());
		rule.setScope(new Scope().setValue(student.getStudentGoogleId()).setType("user"));
		calendar.acl().insert(cal.getId(), rule).execute();
		
	}
	private void updateStudent(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Drive drive;
		Calendar calendar;
		Credential cred = this.getCredential();
		
		String studentCheck = Preconditions.checkNotNull(req.getParameter("student"));
		RosterStudent student = gson.fromJson(studentCheck, RosterStudent.class);
		
		RosterStudent dbCheck = db().load().type(RosterStudent.class).id(student.getId()).now();
		if(dbCheck == null){
			//throw exception
		}
		if(!student.getStudentGoogleId().equals(dbCheck.getStudentGoogleId())){
		drive = drive(cred);
		File folder = drive.files().get(student.getStudentFolderId()).execute();
		folder.setTitle(student.getStudentGoogleId());
		drive.files().update(folder.getId(), folder).execute();
		
		calendar = calendar(cred);
		com.google.api.services.calendar.model.Calendar cal = calendar.calendars().get(student.getStudentCalId()).execute();
		cal.setSummary(student.getStudentGoogleId());
		cal.setDescription("Calendar for " + student.getFirstName() +" " + student.getLastName()+ " that includes assignments, events , and incidents.");
		calendar.calendars().update(cal.getId(), cal).execute();
		
		}
		db().save().entity(student);
		
				
	}
	
	private void deleteRosterStudent(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		String studentCheck = req.getParameter("student");
		Long id = Longs.tryParse(studentCheck);
		
		db().delete().key(Key.create(RosterStudent.class, id));
		//List<Key<StudentWork>> studentWork = db().load().type(StudentWork.class).filter("studentId", id).keys().list();
		//db().delete().keys(studentWork);
	}
	
	//roster student automatically get listed with the roster
	
	//GRADEDWORK CRUD
	
	private void deleteGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException, ServiceException{
		Credential cred = this.getCredential();
		SpreadsheetService sheets = sheets(cred);
		
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignement"));
		String gradebook = req.getParameter("gradebook");
		GradedWork gradedWork = gson.fromJson(gwCheck, GradedWork.class);
		
		//delete row from gradebook///
		
		//Not a DB takes some working to get what you want 
		//so query id col for the id should only be one. 
		//the row number corresponds to the col in gradebook
		//delete the col in gradebook
		WorksheetEntry worksheet = null;
		CellQuery cQuery = new CellQuery(worksheet.getCellFeedUrl());
		cQuery.setRange("A2:A");
		cQuery.setStringCustomParameter("id", gradedWork.getId().toString());
		cQuery.setMaxResults(1);
		CellEntry cellEntry = sheets.getFeed(cQuery, CellFeed.class).getEntries().get(0);
		Cell cell = cellEntry.getCell();
		//now get rid of record in gradedWork	
		ListQuery lQuery = new ListQuery(worksheet.getListFeedUrl());
		lQuery.setStringCustomParameter("id", gradedWork.getId().toString());
		ListEntry entry = sheets.getFeed(lQuery, ListFeed.class).getEntries().get(0);
		//if gradedwork deleted then all related student work should be deleted too.
		
		
		//get the spreadsheet//
		SpreadsheetEntry spreadsheet = sheets.getFeed(spreadsheetURL(), SpreadsheetFeed.class).getEntries().get(0);
		for(String s: gradedWork.getAssignedTo()){
			WorksheetQuery wQuery = new WorksheetQuery(spreadsheet.getWorksheetFeedUrl());
			wQuery.setTitleQuery(s);
			WorksheetEntry studentWorksheet = sheets.getFeed(wQuery, WorksheetFeed.class).getEntries().get(0);
			
			ListQuery slQuery = new ListQuery(studentWorksheet.getListFeedUrl());
			slQuery.setStringCustomParameter("gradedworkId",gradedWork.getId().toString());
			ListEntry sEntry = sheets.getFeed(slQuery, ListFeed.class).getEntries().get(0);
			sEntry.delete();
		}
		
		entry.delete();
		//grive some boolean
		
	}
	
	private void saveGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException, ServiceException{
		Credential cred = this.getCredential();
		SpreadsheetService sheets = sheets(cred);
		Boolean firstSave = false;
		Calendar calendar = GoogleUtils.calendar(cred);
		
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignment"));
		String calId = Preconditions.checkNotNull("calendarId");
		String gradebook = Preconditions.checkNotNull(req.getParameter("gradebook"));
		
		GradedWork gradedWork = gson.fromJson(gwCheck, GradedWork.class);
		Event event = gradedWork.getEvent();
		//if the id is null or zero then this is a first save/////
		if(gradedWork.getId() == null || gradedWork.getId() ==  0){
			//this is a firstSave
			firstSave = true;
			//assign id from uuid
			Long id = new Date().getTime();
			gradedWork.setId(id);
			} //id assigned
		
		//lets stuff json obj in description
		//this way we get list from google and have access to our object
		//essentially using google as the db  ... obviously bad practice
		String descript = event.getDescription()+ "\n\n-* Kimchi-Assignment *-";
		event.setDescription(descript);
	
		Event.ExtendedProperties exProps = new Event.ExtendedProperties();
		Map<String, String> arg = ImmutableMap.of("gradedWork", gradedWork.getId().toString());
		exProps.setPrivate(arg);
		event.setExtendedProperties(exProps);
		if(firstSave){
		event = calendar.events().insert(calId, event).execute();
		}else{
			event = calendar.events().update(calId, event.getId(), event).execute();
		}
		gradedWork.setEventId(event.getId());
		SpreadsheetFeed ssf = sheets.getFeed(spreadsheetURL(), SpreadsheetFeed.class);
		SpreadsheetEntry spreadsheet = ssf.getEntries().get(0);
	
		WorksheetQuery wQ = new WorksheetQuery(spreadsheet.getWorksheetFeedUrl());
		wQ.setTitleExact(true);
		wQ.setTitleQuery("GradedWork");
		WorksheetFeed wsf = sheets.getFeed(wQ,WorksheetFeed.class);
		WorksheetEntry worksheet = wsf.getEntries().get(0);
		
		ListFeed lf = sheets.getFeed(worksheet.getListFeedUrl(), ListFeed.class);
		ListEntry entry = null;
		if(firstSave){
		 entry = new ListEntry();
		}
		else{
			List<ListEntry> rowList = lf.getEntries();
			for(ListEntry row:rowList){
				if(row.getTitle().getPlainText().equalsIgnoreCase(gradedWork.getId().toString())){
					entry = row;
				}
			}
		}
		entry.setTitle(new PlainTextConstruct(gradedWork.getId().toString()));
		CustomElementCollection cols  = entry.getCustomElements();
		cols.setValueLocal("id", gradedWork.getId().toString());
		cols.setValueLocal("title", gradedWork.getTitle());
		cols.setValueLocal("description", gradedWork.getDescription());
		cols.setValueLocal("pointsPossible", gradedWork.getPointsPossible().toString());
		cols.setValueLocal("eventId", gradedWork.getEventId());
		cols.setValueLocal("type", gradedWork.getGradedWorkType().name());
		cols.setValueLocal("finishedGrading", gradedWork.isFinishedGrading().toString());
		cols.setValueLocal("subject", gradedWork.getSubject().name());
		cols.setValueLocal("dateAssigned", gradedWork.getAssignedDate());
		cols.setValueLocal("assignedTo", 
				CharMatcher.anyOf("[]").removeFrom(Iterators.toString(gradedWork.getStandards().iterator())));
		cols.setValueLocal("standards", 
		CharMatcher.anyOf("[]").removeFrom(Iterators.toString(gradedWork.getStandards().iterator())));
		if(firstSave){
		//insert entry
		sheets.insert(worksheet.getListFeedUrl(), entry);
	
		}else{  ////////////UPDATE the GW /////////////////////
			//check that everything is kosher/////
			
			entry.update();
		}
		
		//send the entry and event backacross the wire;
		
		
	}
	
	private void listGradedWorks(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		//return google cal events
		String calId = Preconditions.checkNotNull(req.getParameter("calendar"));
		String pageToken  = Preconditions.checkNotNull("cursor");
		
		
		Credential cred = this.getCredential();
		Calendar cal = GoogleUtils.calendar(cred);
		Events events = cal.events().list(calId).setQ("-* Kimchi-Assignment *-")
		.setFields("summary,description,start,end,attachements").setMaxResults(50).setPageToken(pageToken).execute();
	}
	
	private void listGradedWorksFromSheet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServiceException{
		Credential cred = this.getCredential();
		SpreadsheetService sheets = sheets(cred);
		String spreadsheetId = Preconditions.checkNotNull(req.getParameter("gradebook"));
		String cursor = Preconditions.checkNotNull(req.getParameter("cursor"));
		
		
		
		WorksheetEntry gradedWorkSheet = null;
		
		ListQuery lQuery = new ListQuery(gradedWorkSheet.getListFeedUrl());
		lQuery.setReverse(true);
		lQuery.setStartIndex(Ints.tryParse(cursor));
		
		List<ListEntry> gradedWorkList = sheets.getFeed(lQuery, ListFeed.class).getEntries();
		List<GradedWork> gwList = new ArrayList<GradedWork>();
		for(ListEntry l: gradedWorkList){
			gwList.add(this.recordToGradedWork(l));
		}
		
		//return the gwtList
	}
	
	
	private void getGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException, ServiceException{
		
		//event should include gw id and return this gw and all related student works
		//param id of gradedwork roster Id and current user
		
		String gradebook = Preconditions.checkNotNull(req.getParameter("gradebook"));
		String assignmentId = Preconditions.checkNotNull(req.getParameter("assignment"));
	
		Credential cred = this.getCredential();
		WorksheetEntry gradedWorkSheet = null;
		ListQuery lQuery = new ListQuery(gradedWorkSheet.getListFeedUrl());
		lQuery.setMaxResults(1);
		lQuery.setStringCustomParameter("id", assignmentId);
		ListEntry gradedWork = sheets(cred).getFeed(lQuery, ListFeed.class).getEntries().get(0);
			
	}
	
	private void manageStudentWork(HttpServletRequest req, HttpServletResponse res) throws MalformedURLException, IOException, ServiceException{
		String gradebook = Preconditions.checkNotNull(req.getParameter("gradebook"));
		String assignCheck = Preconditions.checkNotNull(req.getParameter("assign"));
		String unassignCheck = Preconditions.checkNotNull(req.getParameter("unassign"));

		StudentWork[] assign  = gson.fromJson(assignCheck, StudentWork[].class);
		StudentWork[] unassign  = gson.fromJson(unassignCheck, StudentWork[].class);
		
		//update the gradedWork
		Credential cred = this.getCredential();
		SpreadsheetService sheets = sheets(cred);
		
	}
	
	private void unassignStudentWork(StudentWork[] studentWork,String gradebook) throws MalformedURLException, IOException, ServiceException{
	
		Credential cred = this.getCredential();
		SpreadsheetService sheets = sheets(cred);
		SpreadsheetEntry spreadsheet = sheets.getFeed(spreadsheetURL(), SpreadsheetFeed.class).getEntries().get(0);
		WorksheetQuery wQuery = new WorksheetQuery(spreadsheet.getWorksheetFeedUrl());
		for(StudentWork sw : studentWork){
			wQuery.setTitleExact(true);
			wQuery.setTitleQuery(sw.getRosterStudentId().toString());
			WorksheetEntry worksheet = sheets.getFeed(wQuery, WorksheetFeed.class).getEntries().get(0);
			
			ListQuery lQuery = new ListQuery(worksheet.getListFeedUrl());
			lQuery.setStringCustomParameter("id", sw.getId().toString());
			ListEntry entry = sheets.getFeed(lQuery, ListFeed.class).getEntries().get(0);
			//do some checking
			entry.delete();
		}
			
	}
	

		private void assignStudentWork(StudentWork[] studentWork,String gradebook) throws IOException, ServiceException, IllegalArgumentException, IllegalAccessException{
		
			List<StudentWork> updateList = new ArrayList<StudentWork>();
			for(int i = 0; i< studentWork.length; i++){
				if(studentWork[i].getId()!= null && studentWork[i].getId() != 0){
					updateList.add(Arrays.asList(studentWork).remove(i));
				}

			}
			/////////////update studentWowrk/////
			Boolean updateUpdated = false;
			if(updateList.size()> 0){
			updateUpdated = updateStudentWork(updateList,gradebook);
			}
			
			Boolean savedUpdated = false;
			
			if(studentWork.length > 0){
				savedUpdated = insertStudentWork(studentWork, gradebook);
			}
			
			//if both are saved send them back as one array
			if(savedUpdated && updateUpdated){
			updateList.addAll(Arrays.asList(studentWork));
			}else{
				//send an error//
			}
			
			
			
			
		}
		
		private Boolean insertStudentWork(StudentWork[] sw, String sheetId) throws IOException, ServiceException, IllegalArgumentException, IllegalAccessException{
			Credential cred = this.getCredential();
			SpreadsheetService sheets = sheets(cred);
			
		
			URL	url = spreadsheetURL();
			
			SpreadsheetEntry spreadsheet = sheets.getEntry(url, SpreadsheetEntry.class);
			URL wsUrl = spreadsheet.getWorksheetFeedUrl();
			WorksheetQuery wQuery = new WorksheetQuery(wsUrl);
			//iterate the list and get studentwork for all the sheets
			for(StudentWork s: sw){
				s.setId(new Date().getTime());
				wQuery.setTitleQuery(s.getRosterStudentId().toString());
				WorksheetEntry worksheet = sheets.getFeed(wQuery, WorksheetFeed.class).getEntries().get(0);
				ListEntry listEntry = studentWorkToRecord(s);
				sheets.insert(worksheet.getListFeedUrl(), listEntry);
			}	
			
			return true;
		}
		
		private Boolean updateStudentWork(List<StudentWork> studentWork,String sheetId) throws IOException, ServiceException, IllegalArgumentException, IllegalAccessException{
			Credential cred = this.getCredential();
			SpreadsheetService sheets = sheets(cred);
			URL url = spreadsheetURL();
			SpreadsheetEntry spreadsheet = sheets.getEntry(url, SpreadsheetEntry.class);
			URL wsUrl = spreadsheet.getWorksheetFeedUrl();
			WorksheetQuery wQuery = new WorksheetQuery(wsUrl);
			for(StudentWork s: studentWork){
				wQuery.setTitleQuery(s.getRosterStudentId().toString());
				WorksheetEntry worksheet = sheets.getFeed(wQuery, WorksheetFeed.class).getEntries().get(0);
				ListQuery lQuery = new ListQuery(worksheet.getListFeedUrl());
				lQuery.setStringCustomParameter("id", s.getId().toString());
				lQuery.setMaxResults(1);
				ListFeed lFeed = sheets.getFeed(lQuery, ListFeed.class);
				ListEntry entry = lFeed.getEntries().get(0);
					for(Field f:s.getClass().getDeclaredFields()){
					f.setAccessible(true);
					entry.getCustomElements().setValueLocal(f.getName(), f.get(s).toString());
				}//inner for
				entry.update();	
			}//outer for
			
			return true;
		}
		

		
	///STUDENT INCIDENT CRUD
	private void updateStudentIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		String incidentCheck = Preconditions.checkNotNull(req.getParameter("incident"));
		StudentIncident incident = gson.fromJson(incidentCheck, StudentIncident.class);
		StudentIncident dbCheck;
		try{
			dbCheck = db().load().type(StudentIncident.class).id(incident.getId()).now();
			Preconditions.checkNotNull(dbCheck);
			if(!dbCheck.getSummary().equals(incident.getSummary())){
				//update the event
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		
	}
	private void deleteStudentIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		Calendar cal;
		Credential cred = this.getCredential();
		cal = calendar(cred);
		
		String calId = Preconditions.checkNotNull(req.getParameter("calId"));
		String incidentCheck = Preconditions.checkNotNull(req.getParameter("incident"));
		StudentIncident incident = gson.fromJson(incidentCheck, StudentIncident.class);
		cal.events().delete(calId, incident.getEventId()).execute();
		StudentIncident dbCheck = null;
	try{
		dbCheck = db().load().type(StudentIncident.class).id(incident.getId()).now();
		Preconditions.checkNotNull(dbCheck);	
	}catch(NullPointerException e){
		
	}
	
		db().delete().entity(dbCheck);
		
	}
	private void saveStudentIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		String incidentCheck = Preconditions.checkNotNull(req.getParameter("incident"));
		String studentCheck = Preconditions.checkNotNull("student");
		String eventCheck = Preconditions.checkNotNull("event");
		StudentIncident incident = gson.fromJson(incidentCheck, StudentIncident.class);
		RosterStudent student = gson.fromJson(studentCheck, RosterStudent.class);
		Event event = gson.fromJson(eventCheck , Event.class);
		
		incident.setId(new Date().getTime());
		Credential cred = this.getCredential();
		Calendar calendar = calendar(cred);
		ExtendedProperties ext = new ExtendedProperties();
		Map<String, String> incidentMap = new HashMap<String, String>();
		incidentMap.put("incident", incident.getId().toString());
		ext.setPrivate(incidentMap);
		event.setExtendedProperties(ext);
		incident.setEventId(calendar.events().insert(student.getStudentCalId(), event).execute().getId());
		db().save().entity(incident);
		
		//give the incident back;
	}
	private void searchStudentIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listStudentIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		//list by fifty in date order
		String cursor = Preconditions.checkNotNull(req.getParameter("cursor"));
		String filter = Preconditions.checkNotNull(req.getParameter("filter"));
		String value = Preconditions.checkNotNull("value");
		String rosterId = Preconditions.checkNotNull("roster");
		
		List<StudentIncident> incidents = db().load().type(StudentIncident.class)
				.filter("rosterId", rosterId).order("date")
				.limit(50).startAt(Cursor.fromWebSafeString(cursor)).list();
	}
	private void getStudentIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
		
	
	
	private GradedWork recordToGradedWork(ListEntry l){
		
		GradedWork gw = new GradedWork();
		gw.setTitle(l.getCustomElements().getValue("title"));
		gw.setDescription(l.getCustomElements().getValue("description"));
		gw.setEventId(l.getCustomElements().getValue("eventId"));
		gw.setFinishedGrading(Boolean.valueOf(l.getCustomElements().getValue("finishedGrading")));
		gw.setGradedWorkType(GradedWorkType.valueOf(l.getCustomElements().getValue("type").toUpperCase()));
		gw.setId(Long.valueOf(l.getCustomElements().getValue("id")));
		gw.setPointsPossible(Double.valueOf(l.getCustomElements().getValue("pointsPossible")));
		gw.setSubject(SubjectType.valueOf(l.getCustomElements().getValue("subject").toUpperCase()));
		gw.setMediaUrl(l.getCustomElements().getValue("mediaURL"));
		List<String>assignedTo = Splitter.on(',').splitToList(l.getCustomElements().getValue("assignedTo"));
		gw.getAssignedTo().addAll(assignedTo);
		List<String>standards = Splitter.on(',').splitToList(l.getCustomElements().getValue("standards"));
		gw.getStandards().addAll(standards);
		
		return gw;
		
	}
	private ListEntry gradedWorkToRecord(GradedWork gradedWork){
		ListEntry entry = new ListEntry();
		entry.setTitle(new PlainTextConstruct(gradedWork.getId().toString()));
		CustomElementCollection cols  = entry.getCustomElements();
		cols.setValueLocal("id", gradedWork!=null?gradedWork.getId().toString():Long.toString(new Date().getTime()));
		cols.setValueLocal("title", gradedWork.getTitle());
		cols.setValueLocal("description", gradedWork.getDescription());
		cols.setValueLocal("pointsPossible", gradedWork.getPointsPossible().toString());
		cols.setValueLocal("eventId", gradedWork.getEventId());
		cols.setValueLocal("type", gradedWork.getGradedWorkType().name());
		cols.setValueLocal("finishedGrading", gradedWork.isFinishedGrading().toString());
		cols.setValueLocal("subject", gradedWork.getSubject().name());
		cols.setValueLocal("dateAssigned", gradedWork.getAssignedDate());
		cols.setValueLocal("assignedTo", 
		CharMatcher.anyOf("[]").removeFrom(Iterators.toString(gradedWork.getAssignedTo().iterator())));
		cols.setValueLocal("standards", 
		CharMatcher.anyOf("[]").removeFrom(Iterators.toString(gradedWork.getStandards().iterator())));
		return entry;
	}
	
	private StudentWork recordToStudentWork(ListEntry l){
		StudentWork sw = new StudentWork();
		sw.setId(Longs.tryParse(l.getCustomElements().getValue("id")));
		sw.setMediaUrl(l.getCustomElements().getValue("studentUrl"));
		sw.setMessage(l.getCustomElements().getValue("message"));
		sw.setRosterStudentId(Longs.tryParse(l.getCustomElements().getValue("studentId")));
		sw.setDateTaken(l.getCustomElements().getValue("dateTaken"));
		sw.setPointsEarned(Doubles.tryParse(l.getCustomElements().getValue("pointsEarned")));
		sw.setStudentWorkStatus(l.getCustomElements().getValue("status"));
		
		return sw;	
	}
	
	private ListEntry studentWorkToRecord(StudentWork sw) throws IllegalArgumentException, IllegalAccessException{
		ListEntry listEntry = new ListEntry();
		for(Field f: StudentWork.class.getDeclaredFields()){
			f.setAccessible(true);
			listEntry.getCustomElements().setValueLocal(f.getName(), f.get(sw).toString());
		}
		return listEntry;
	}
}
