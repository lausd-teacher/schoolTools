



package net.videmantay.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static net.videmantay.server.entity.DB.*;
import net.videmantay.server.entity.*;
import net.videmantay.shared.GradeLevel;
import net.videmantay.shared.StuffType;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Event.Creator;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.VoidWork;


@SuppressWarnings("serial")
public class TeacherService extends AbstractAppEngineAuthorizationCodeServlet  {
	
	Logger log = Logger.getLogger(TeacherService.class.getCanonicalName());
	private final String CONTACTS = "https://www.google.com/m8/feeds";
	private final String SITES = "https://sites.google.com/feeds";
	private final String SPREADSHEET = "https://spreadsheets.google.com/feeds";
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		init(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		init(req, res);
	}
	
	
	private void init(HttpServletRequest req, HttpServletResponse res)throws IOException , ServletException{
		
		//1. First check to see if user is legit
		User user = UserServiceFactory.getUserService().getCurrentUser();
		if(user.getEmail() != "lee@videmantay.net"){
			res.sendRedirect("/error");
		}
		
		//2. Set res content type to JSON
		res.setContentType("application/json");
		
		// 3. Route The Path
		String path = req.getRequestURI();
		
		switch(path){
		
	
		
		case "/teacher/creategradedwork": createGradedWork(req,res); break;
		case "/teacher/deletegradedwork": deleteGradedWork(req,res) ; break;
		case "/teacher/savegradedwork": saveGradedWork(req,res); break;
		case "/teacher/querygradedwork": queryGradedWork(req,res) ; break;
		case "/teacher/searchgradedwork": searchGradedWork(req,res) ; break;
		case "/teacher/listgradedworks": listGradedWorks(req,res) ; break;
		case "/teacher/getgradedwork": getGradedWork(req,res) ; break;
		case "/teacher/assigngradedwork": ;break;
	
		
		case "/teacher/createseatingchart": createSeatingChart(req, res);break; 
		case "/teacher/deleteseatingchart": deleteSeatingChart(req, res); break;
		case "/teacher/getseatingchart": getSeatingChart(req, res);  break;
		case "/teacher/listseatingcharts": listSeatingChart(req, res); break;
		
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
		// TODO Auto-generated method stub
		return MyUtils.newFlow();
	}
	////////////////end oauth ///////////////////////////////////////////////////////
	
	//ROSTER CRUD
	private void saveRoster(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException, GeneralSecurityException{
		final Gson gson = new Gson();
		final DB<Roster> rosterDB = new DB<Roster>(Roster.class);
		final AppUser appUser = (AppUser)req.getSession().getAttribute("appUser");
		final GoogleCredential cred;
		
		Drive drive;
		Calendar calendar;
		Tasks tasks;
		ContactsService contactService;
		SitesService sitesService;
		SpreadsheetService spreadsheetService;
		
		
		RosterSetting settings;
		Roster roster = null;
		
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
		//for roster settings 
		if(roster.getId() != null && roster.getId() != 0){
			//This is an update just save the roster
			rosterDB.save(roster);
			//send a confirmation message
			ArrayList<String> message= new ArrayList<String>();
			message.add("Roster update successful");
			Stuff<String> stuff = new Stuff<String>(message);
			stuff.setType(StuffType.MESSAGE);
			String returnThis = gson.toJson(stuff);
			res.getWriter().write(returnThis);
			return;
			
		}else{
			//this is a first save set up docs,calendar,etc
			
		 cred = MyUtils.createCredentialForUser(appUser.getAcctId(), DriveScopes.DRIVE_FILE, CalendarScopes.CALENDAR, TasksScopes.TASKS,SITES);
			drive = new Drive.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
			//Drive mandatory folders
				
			
					//optional folders use settings
			
			
			//Set up Gradedwork Calendar and class events
			
			
			//Create a roster task list
			
			// Set up a google sites for Class info
		}//end first save else////////
	

	}	
	
	
	
	private void deleteRoster(){}
	private void listRoster(){}
	
	
	//GRADEDWORK CRUD
	private void createGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		final Gson gson = new Gson();
		final DB<GradedWork> gradedWorkDB = new DB<GradedWork>(GradedWork.class);
		final AppUser appUser = (AppUser)req.getSession().getAttribute("appUser");
		GoogleCredential cred;
		Calendar calendar;
		
		GradedWork gradedWork = gson.fromJson(req.getParameter("gradedWork"), GradedWork.class);
		//save it to retrieve id 
		 gradedWork = gradedWorkDB.getById(gradedWorkDB.save(gradedWork).getId());
		 Event event = gson.fromJson(req.getParameter("event"), Event.class);
		 
		 String calId = "";
		 
		try {
			cred = MyUtils.createCredentialForUser(appUser.getAcctId(), CalendarScopes.CALENDAR);
			calendar = new Calendar.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
			 event = calendar.events().insert(calId, event).execute();
			 gradedWork.setGoogleCalEventId(event.getId());
			 event.set("gradedWork", gradedWork.getId());
			 gradedWorkDB.save(gradedWork);
			 calendar.events().update(calId, event.getId(), event);
		
		} catch (GeneralSecurityException e) {
		
			e.printStackTrace();
		}
		
		
	}
	
	private void deleteGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void saveGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void queryGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void searchGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void listGradedWorks(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void getGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void unassignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private void assignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		final Gson gson = new Gson();
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
	
	public void teacherSetup(AppUser appUser) throws TokenResponseException{
		//check that there is root folder called Kim_LAUSD
		// if one exists there is nothing else to do 
		//but if not create one with minimum requirements
		
		// this can only be done with accounts that you personally manage
		// what about teacher with there own google accounts how do they give you 
		// permission??? while they sign up // in the mean time use your own accounts.
		//What kind of scopes Docs obviously what about tasks??? calendar??? 
		//sync calendars // send tasks
		
		
			//get cred for teacher to set up docs, calendar, tasks
		GoogleCredential cred = null;
		try {
			cred = MyUtils.createCredentialForUser(userAcct.getAcctId(), DriveScopes.DRIVE, CalendarScopes.CALENDAR, TasksScopes.TASKS);
		} catch (GeneralSecurityException | IOException e1) {
			// TODO Auto-generated catch block
			log.log(Level.WARNING, "Unable to get creds");
			e1.printStackTrace();
		}
		
		
		try{
		Drive drive = new Drive.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).setApplicationName("Kimchi").build();
		File kimchiFolder = MyUtils.createFolder("Kimchi");
		//kimchiFolder.setThumbnailLink("https://lh5.googleusercontent.com/-f06vW5PUAik/U-kMFfPJuLI/AAAAAAAAJWY/pNrzF3yklxQ/s16-no/chkLogo16.png");
		kimchiFolder.setIconLink("https://lh5.googleusercontent.com/-Ncp95NXgbHM/U-kMcnbOrKI/AAAAAAAAJvw/Sy9AOXjlqrs/s128-no/chkLogo128.png");
		kimchiFolder =  drive.files().insert(kimchiFolder).execute();
		appRes.setKimchiFolder(kimchiFolder.getId());
		//set kimchi as parent folder of the rest
		ParentReference parentRef = new ParentReference();
		parentRef.setId(kimchiFolder.getId());
		parentRef.setParentLink(kimchiFolder.getSelfLink());
		

		File sharedStuff = MyUtils.createFolder("Shared");
		sharedStuff.setParents(Arrays.asList(parentRef));
		sharedStuff = drive.files().insert(sharedStuff).execute();
		appRes.setRootSharedFolder(sharedStuff.getId());
		
		File worksheets = MyUtils.createFolder("Worksheets");
		worksheets.setParents(Arrays.asList(parentRef));
		worksheets = drive.files().insert(worksheets).execute();
		appRes.setRootWorksheetFolder(worksheets.getId());
		
		File showcase = MyUtils.createFolder("Showcase");
		showcase.setParents(Arrays.asList(parentRef));
		showcase = drive.files().insert(showcase).execute();
		appRes.setRootShowcaseFolder(showcase.getId());
		
		File schoolStuff = MyUtils.createFolder("School");
		schoolStuff.setParents(Arrays.asList(parentRef));
		schoolStuff = drive.files().insert(schoolStuff).execute();
		appRes.setRootSchoolFolder(schoolStuff.getId());
		
		
		}catch(TokenResponseException e){
			throw e;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.log(Level.WARNING, "Google Drive Error");
			e.printStackTrace();
		}
		
	
			//set up shared calendar
			//this means the calendar exists and user 
		//are being given permission status
			appRes.setSharedCalendar(MyUtils.getCalendar());
			
			com.google.api.services.tasks.Tasks task = new com.google.api.services.tasks.Tasks.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).setApplicationName("Kimchi").build();
			
			TaskList kimchiTasks = new TaskList();
			kimchiTasks.setTitle("Kimchi Tasks");
			try {
				kimchiTasks = task.tasklists().insert(kimchiTasks).execute();
				Task createRoster = new Task();
				createRoster.setTitle("Create Roster").setNotes("Create an inital Roster to get started");
				task.tasks().insert(kimchiTasks.getId(), createRoster).execute();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DB.db().save().entity(appRes);
	}//end teacherSetup
}
