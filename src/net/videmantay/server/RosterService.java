package net.videmantay.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.entity.*;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import net.videmantay.server.user.Roster;
import net.videmantay.server.user.RosterDetail;
import net.videmantay.server.user.RosterSetting;
import net.videmantay.server.user.RosterStudent;
import net.videmantay.shared.StuffType;
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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.primitives.Longs;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.client.spreadsheet.WorksheetQuery;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;


@SuppressWarnings("serial")
public class RosterService extends AbstractAppEngineAuthorizationCodeServlet  {
	
	private final Logger log = Logger.getLogger(RosterService.class.getCanonicalName());
	private final Gson gson = new Gson();
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		init(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		init(req, res);
	}
	
	
	private void init(HttpServletRequest req, HttpServletResponse res)throws IOException , ServletException{
		
		//1. First check to see if user and check authorization
		
		//2. Set res content type to JSON
		res.setContentType("application/json");
		
		// 3. Route The Path
		String path = req.getRequestURI();
		
		switch(path){
		
	
		
		case "" :break;
		
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
	
	//ROSTER CRUD
	private void saveRoster(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException, GeneralSecurityException, ServiceException{
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
		
		//make sure the user making the request is the user that is logged in
		String userCheck = req.getParameter("user");
		AppUser user = null;
		if(userCheck != null && !userCheck.isEmpty()){
			user = gson.fromJson(userCheck, AppUser.class);
		}
		if(!AppValid.userCheck(user)){
			//throw an error
		}
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
				if(user.getRoles().contains(UserRoles.ADMIN) || 
						user.getAcctId().equals(rosDB.getOwnerId())){
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
			
			
		 cred =this.getCredential();
		 //check for roster setting in the db 
		 settings = db().load().type(RosterSetting.class).filter("acctId",user.getAcctId()).first().now();
		 if(settings == null){
			 settings = new RosterSetting();
		 }
		 drive = GoogleUtils.drive(cred);
		 
		 			//first check if main drive folder has been set
		 		if(user.getMainDriveFolder()==null || user.getMainDriveFolder().isEmpty()){
		 			//assign a main folder
		 			File kimchiFile = new File();
		 			kimchiFile.setTitle("Kimchi");
		 			kimchiFile = drive.files().insert(kimchiFile).execute();
		 			user.setMainDriveFolder(kimchiFile.getId());
		 			
		 		}
				File rosterFolder = new File();
				rosterFolder.setTitle(roster.getTitle());
				rosterFolder.setDescription(roster.getDescription());
				ParentReference parentFolder = new ParentReference();
				parentFolder.setId(user.getMainDriveFolder());
				
				rosterFolder.getParents().add(parentFolder);
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
				roster.setRollBook(rollBook.getId());
				roster.setBehaviorReport(behaviorReport.getId());
				
				//optional folders use settings
				List<File> childFolders = new ArrayList<File>();
				for(String name : settings.getFolderNames()){
					File f = new File();
					f.setTitle(name);
					f.setParents(listOfP);
					childFolders.add(f);
				}
				
				BatchRequest driveBatch = drive.batch();
				for(File f: childFolders){
					drive.files().insert(f).queue(driveBatch, null);
				}
				
				driveBatch.execute();
			
					
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
			roster.setOwnerId(user.getAcctId());
			roster.getAccess().add(user.getAcctId());
			
			//at this point there is no id so we wait for one
			//to assign to rd
			RosterDetail rd = roster.createDetail();
			
			rd.setId(rosterDB.save(roster).getId());
			db().save().entity(rd);
			// Set up a google sites for Class info
		}//end first save else////////
	

	}	
	
	
	
	private void deleteRoster(HttpServletRequest req, HttpServletResponse res){
		
		String rosterCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		Roster roster = gson.fromJson(rosterCheck, Roster.class);
		String userCheck = Preconditions.checkNotNull(req.getParameter("user"));
		AppUser user = gson.fromJson(userCheck, AppUser.class);
		AppValid.userCheck(user);
		
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
	private void listRoster(HttpServletRequest req, HttpServletResponse res){
		//this method will only list the roster 
		User user = UserServiceFactory.getUserService().getCurrentUser();
		List<Roster> rosters = db().load().type(Roster.class).filter("acctId", user.getEmail() ).list();
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
		AtomicLong generator = new AtomicLong();
		student.setId( generator.incrementAndGet());
		
		drive = drive(cred);
		calendar = calendar(cred);
		//create folder for student
		File studentFolder = folder(student.getStudentGoogleId());
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
	
	private void deleteGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		String gwCheck = req.getParameter("student");
		Long id = Longs.tryParse(gwCheck);
		List<Key<Object>> keys = db().load().ancestor(Key.create(GradedWork.class, id)).keys().list();
		db().delete().keys(keys);
	}
	
	private void saveGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		Credential cred = this.getCredential();
		SpreadsheetService sheets = sheets(cred);
		
		Calendar calendar = GoogleUtils.calendar(cred);
		
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignment"));
		String eventCheck = Preconditions.checkNotNull(req.getParameter("event"));
		String calId = Preconditions.checkNotNull("calendarId");
		String gradebook = Preconditions.checkNotNull(req.getParameter("gradebook"));
		
		GradedWork gradedWork = gson.fromJson(gwCheck, GradedWork.class);
		Event event = gson.fromJson(eventCheck, Event.class);
		//if the id is null or zero then this is a first save/////
		if(gradedWork.getId() == null || gradedWork.getId() ==  0){
			//assign id from uuid
			Long id = new AtomicLong().getAndIncrement();
			gradedWork.setId(id);
		
		//lets stuff json obj in description
		//this way we get list from google and have access to our object
		//essentially using google as the db  ... obviously bad practice
		String descript = event.getDescription()+ "-* Kimchi-Assignment *-";
		event.setDescription(descript);
	
		Event.ExtendedProperties exProps = new Event.ExtendedProperties();
		Map<String, String> arg = ImmutableMap.of("gradedWork", gradedWork.getId().toString());
		exProps.setPrivate(arg);
		event.setExtendedProperties(exProps);
		
		event = calendar.events().insert(calId, event).execute();
		gradedWork.setEventId(event.getId());
		SpreadsheetFeed ssf = sheets.getFeed(new URL(GoogleUtils.SheetsScope +"/spreadsheets/" + gradebook +"/private/basic"), SpreadsheetFeed.class);
		SpreadsheetEntry spreadsheet = ssf.getEntries().get(0);
	
		WorksheetQuery wQ = new WorksheetQuery(spreadsheet.getWorksheetFeedUrl());
		wQ.setTitleExact(true);
		wQ.setTitleQuery("GradedWork");
		WorksheetFeed wsf = sheets.getFeed(wQ,WorksheetFeed.class);
		WorksheetEntry worksheet = wsf.getEntries().get(0);
		
		ListFeed lf = sheets.getFeed(worksheet.getListFeedUrl(), ListFeed.class);
		ListEntry entry = new ListEntry();
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
		cols.setValueLocal("dateAssigned", gradedWork.getAssignedDate().);
		cols.setValueLocal("standards", 
		CharMatcher.anyOf("[]").removeFrom(Iterators.toString(gradedWork.getStandards().iterator())));
	
		}else{  ////////////UPDATE the GW /////////////////////
			//check that everything is kosher/////
			GradedWork gw = db().load().type(GradedWork.class).filter("id", gradedWork.getId()).filter("rosterId", rosId).first().now();
			if(gw == null){
				//throw an exception
			}else{
				event.setDescription(event.getDescription()+"-* Kimchi-Assignment *-");
				event = calendar.events().update(gradedWork.getCalendarId(), event.getId(), event).execute();
				db().save().entity(gradedWork);
			}
		}
		
		
		
		
	}
	
	private void listGradedWorks(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		//return google cal events
		String rosterCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		String calId = Preconditions.checkNotNull(req.getParameter("calendar"));
		Long rosId  = Preconditions.checkNotNull(Longs.tryParse(rosterCheck), "Must have ligitimate roster");
		Preconditions.checkArgument(AppValid.rosterCheck(rosId), "Must have a ligitimate roster");
		
		Credential cred = this.getCredential();
		Calendar cal = GoogleUtils.calendar(cred);
		cal.events().list(calId).setQ("-* Kimchi-Assignment *-").setFields("").execute();
		
		
	}
	
	private void getGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
		//event should include gw id and return this gw and all related student works
		//param id of gradedwork roster Id and current user
		String userCheck = Preconditions.checkNotNull(req.getParameter("user"));
		String rosterCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignment"));
		Long rosterId = Longs.tryParse(rosterCheck);
		Long gwId = Longs.tryParse(gwCheck);
		
		AppUser user = gson.fromJson(userCheck, AppUser.class);
		GradedWork gradedWork = db().load().type(GradedWork.class).id(gwId).now();
		//check if user is on list
		if(gradedWork.getAccess().contains(user.getAcctId())){
			//proceed
			//load the studentwork
			List<StudentWork> studentWorks = ImmutableList.copyOf(db().load().keys(gradedWork.getStudentWorkKeys()).values());
			gradedWork.getStudentWorks().addAll(studentWorks);
			//send it over the wire
		}
		
		
		
	}
	
	private void manageAssignment(HttpServletRequest req, HttpServletResponse res){
		//expect two arrays here one for assignedTo and unAssignedTo
		String assignCheck = Preconditions.checkNotNull(req.getParameter("assign"));
		String unassignCheck = Preconditions.checkNotNull(req.getParameter("unassign"));
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignment"));
		
		RosterStudent[] assign = gson.fromJson(assignCheck, RosterStudent[].class);
		RosterStudent[] unassign = gson.fromJson(unassignCheck, RosterStudent[].class);
		GradedWork gw = gson.fromJson(gwCheck, GradedWork.class);
		req.setAttribute("assign", assign);
		req.setAttribute("assignment", gw);
		req.setAttribute("unassign", unassign);
		try {
			unassignGradedWork(req,res);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assignGradedWork(req,res);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void unassignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
			RosterStudent[] students = (RosterStudent[]) req.getAttribute("unassign");
			GradedWork gradedWork = (GradedWork)req.getAttribute("assignment");
			
	}
	
	private void assignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
		//Gradedwork must be explicitly assigned so that takes the guess work out of creating calendar events
		//is single calendar or multiple????
		//first step must be created then assigned or wait in limbo so index on isAssigned
		
		//gradedwork needed
		GradedWork gradedWork = gson.fromJson(req.getParameter("gradedWork"), GradedWork.class);
		//getting back an array of string
		String studentsCheck = Preconditions.checkNotNull(req.getParameter("students"));
		Long[] studentList = gson.fromJson(studentsCheck, Long[].class);
		List<StudentWork> studentWorkList = new ArrayList<StudentWork>();
		for(Long s:studentList){
			StudentWork sw = new StudentWork();
			sw.setGradedWorkId(gradedWork.getId());
			sw.setRosterStudentId(s);
			studentWorkList.add(sw);
		}
		
		//assign to these students
		gradedWork.setStudentWorkKeys(db().save().entities(studentWorkList).now().keySet());
		db().save().entity(gradedWork);

	}
	
		private void saveStudentWork(HttpServletRequest req, HttpServletResponse res){
			String studentCheck = Preconditions.checkNotNull(req.getParameter("studentWork"));
			StudentWork[] studentWork = gson.fromJson(studentCheck, StudentWork[].class);
			db().save().entities(studentWork);
			
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
		
		incident.setId(new AtomicLong().incrementAndGet());
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
		
	
	///LESSON CRUD
	private void createLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	
	//QUIZ CRUD
	private void createQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//QUIZ QUESTION CRUD
	private void createQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//CCSTANDARD CRUD
	private void createCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//EDUCATIONAL LINK CRUD
	private void createEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//VOCAB CRUD
	private void createVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//VOCAB LIST CRUD
	private void createVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
}
