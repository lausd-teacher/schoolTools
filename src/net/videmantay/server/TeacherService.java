package net.videmantay.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static net.videmantay.server.entity.DB.*;
import net.videmantay.server.entity.*;
import net.videmantay.shared.StuffType;
import net.videmantay.shared.UserRoles;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Preconditions;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskList;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Longs;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.ServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;


@SuppressWarnings("serial")
public class TeacherService extends AbstractAppEngineAuthorizationCodeServlet  {
	
	private final Logger log = Logger.getLogger(TeacherService.class.getCanonicalName());
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
				//set roster folder id
				roster.setRosterFolderId(rosterFolder.getId());
				
				//optional folders use settings
				List<File> childFolders = new ArrayList<File>();
				for(String name : settings.getFolderNames()){
					File f = new File();
					f.setTitle(name);
					
					List<ParentReference> parentList = new ArrayList<ParentReference>();
					ParentReference p = new ParentReference();
					p.setId(rosterFolder.getId());
					f.setParents(parentList);
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
//			SpreadsheetService sheets = sheets(cred);
//			File rollBook = spreadsheet("RollBook");
//			rollBook = drive.files().insert(rollBook).execute();
//			SpreadsheetQuery query = new SpreadsheetQuery(new URL(SheetsScope + "/spreadsheets/" + rollBook.getId()));
//			SpreadsheetFeed feed = sheets.query(query, SpreadsheetFeed.class);
//			SpreadsheetEntry rbSS = feed.getEntries().get(0);
//			WorksheetEntry rbWS = rbSS.getDefaultWorksheet();
//			//add first row to the worksheet
//			rbWS.setColCount(15);
//			rbWS.setRowCount(1);
//			rbWS.update();
//			CellFeed rbCellFeed = sheets.getFeed(rbWS.getCellFeedUrl(), CellFeed.class);
//			List<CellEntry> cells = rbCellFeed.getEntries();
//			for(int i = 0; i< cells.size(); i++){
//				switch(i){
//				case 0:cells.get(i).changeInputValueLocal("id");break;
//				case 1:cells.get(i).changeInputValueLocal("firstName");break;
//				case 2:cells.get(i).changeInputValueLocal("id");break;
//				}
//			}
			
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
	
	
	//GRADEDWORK CRUD
	
	private void deleteGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void saveGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		Credential cred = this.getCredential();
		
		Calendar calendar = GoogleUtils.calendar(cred);
		
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignment"));
		String eventCheck = Preconditions.checkNotNull(req.getParameter("event"));
		String rosterIdCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		Long rosId = Longs.tryParse(rosterIdCheck);
		Preconditions.checkNotNull(rosId, "Must have a ligitimate roster");
		Preconditions.checkArgument(AppValid.rosterCheck(rosId), "Must have ligitimate Roster");
		String userCheck = Preconditions.checkNotNull(req.getParameter("user"));
		AppUser user = gson.fromJson(userCheck, AppUser.class);
		
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
		String descript = event.getDescription();
		descript += "-* Kimchi-Assignment *-";
	
		Event.ExtendedProperties exProps = new Event.ExtendedProperties();
		Map<String, String> arg = ImmutableMap.of("gradedWork", gradedWork.getId().toString());
		exProps.setPrivate(arg);
		event.setExtendedProperties(exProps);
		
		event = calendar.events().insert(gradedWork.getCalendarId(), event).execute();
		gradedWork.setGoogleCalEventId(event.getId());
		gradedWork.getAccess().add(user.getAcctId());
		db().save().entity(gradedWork);
		
		}else{  ////////////UPDATE the GW /////////////////////
			//check that everything is kosher/////
			GradedWork gw = db().load().type(GradedWork.class).filter("id", gradedWork.getId()).filter("rosterId", rosId).first().now();
			if(gw == null){
				//throw an exception
			}else{
				event.getExtendedProperties().getPrivate().replace("gradedWork", gson.toJson(gradedWork));
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
		String gradedWork = Preconditions.checkNotNull(req.getParameter("assignment"));
		
		
		
		
	}
	
	private void manageAssignment(HttpServletRequest req, HttpServletResponse res){
		
	}
	private void unassignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
			//list of student ids to unassign
	}
	
	private void assignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
		//Gradedwork must be explicitly assigned so that takes the guess work out of creating calendar events
		//is single calendar or multiple????
		//first step must be created then assigned or wait in limbo so index on isAssigned
		
		//gradedwork needed
		GradedWork gradedWork = gson.fromJson(req.getParameter("gradedWork"), GradedWork.class);
		Type studentCollection = new TypeToken<Set<Key<RosterStudent>>>(){}.getType();
		
		//assign to these students
		Set<Key<RosterStudent>> students = gson.fromJson(req.getParameter("students"), studentCollection);
		List<StudentWork> studentWorkList = new ArrayList<StudentWork>();
		for(Key<RosterStudent> r: students){
			StudentWork sw = new StudentWork();
		
			sw.setGradedWorkKey(Key.create(GradedWork.class,gradedWork.getId()));
			sw.setRosterStudentId(r.getId());
			studentWorkList.add(sw);
		}
		
		db().save().entities(studentWorkList).now().keySet();
	
	
		String message = "\"message\":\"";
		
		
	}
	
	///LESSON CRUD
	private void createLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	///BEHAVIOR REPORT CRUD
	private void createBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
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
	
	
	//SHOWCASE CRUD
	private void createShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//STUDENT JOB CRUD
	private void createStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void queryStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
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
	
	//SEATING CHART ACTION
	private void createSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void querySeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void searchSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	
}
