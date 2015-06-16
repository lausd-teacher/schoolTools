



package net.videmantay.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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

import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.DB;
import net.videmantay.server.entity.RosterAssignment;
import net.videmantay.server.entity.StudentWork;
import static net.videmantay.server.entity.DB.*;
import net.videmantay.server.entity.*;
import net.videmantay.shared.GradeLevel;
import net.videmantay.shared.RosterAssignmentType;
import net.videmantay.shared.StuffType;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Event.Creator;
import com.google.api.services.calendar.model.Event.Gadget;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Preconditions;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.util.ServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.TranslateException;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.VoidWork;


@SuppressWarnings("serial")
public class TeacherService extends AbstractAppEngineAuthorizationCodeServlet  {
	//List Constants
	private final String ROSTER_PARAM = "roster";
	private final String GRADEDWORK_PARAM = "gradedWork";
	private final String LESSON_PARAM="lesson";
	private final Gson gson = new Gson();
	
	
	Logger log = Logger.getLogger(TeacherService.class.getCanonicalName());
	private final String CONTACTS = "https://www.google.com/m8/feeds";
	//private final String SITES = "https://sites.google.com/feeds";
	//private final String SPREADSHEET = "https://spreadsheets.google.com/feeds";
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
		case "/teacher/savegradedwork": updateGradedWork(req,res); break;
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
	private void saveRoster(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException, GeneralSecurityException, ServiceException{
		
		final DB<Roster> rosterDB = new DB<Roster>(Roster.class);
		final AppUser appUser = (AppUser)req.getSession().getAttribute("appUser");
		final GoogleCredential cred;
		
		Drive drive;
		Calendar calendar;
		Tasks tasks;
		
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
			
		 cred = MyUtils.createCredentialForUser(appUser.getAcctId(), DriveScopes.DRIVE_FILE, CalendarScopes.CALENDAR, TasksScopes.TASKS,CONTACTS);
			drive = new Drive.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
			//Make sure a main folder is registered with the user
			if(appUser.getMainDriveFolderId() == null){
				//fist assign an App folder ZoomClassroom
				File mainFolder = drive.files().insert(MyUtils.createFolder("ZoomClassroom")).execute();
				appUser.setMainDriveFolderId(mainFolder.getId());
				DB.db().save().entity(appUser).now();
			}
			//Drive mandatory folders
			
				File mainFolder = drive.files().get(appUser.getMainDriveFolderId()).execute();
				
				File rosterFolder = MyUtils.createFolder(roster.getTitle());
				
				drive.parents().insert(rosterFolder.getId(), new ParentReference().setId(mainFolder.getId()).setSelfLink(mainFolder.getSelfLink()));
				roster.setRosterFolderId(rosterFolder.getId());
				
				//set parent reference to all child folders
				ParentReference parent = new ParentReference();
				parent.setId(rosterFolder.getId());
				parent.setSelfLink(rosterFolder.getSelfLink());
				
				//insert a folder called students , work forms and assignments
				File rosterStudents = MyUtils.createFolder("students");
				rosterStudents = drive.files().insert(rosterStudents).execute();
				drive.parents().insert(rosterStudents.getId(), parent).execute();
				
				File rosterWorkForms = MyUtils.createFolder("work forms");
				rosterWorkForms = drive .files().insert(rosterWorkForms).execute();
				drive.parents().insert(rosterWorkForms.getId(), parent).execute();
				
				File rosterAssignments = MyUtils.createFolder("assignments");
				rosterAssignments = drive.files().insert(rosterAssignments).execute();
				drive.parents().insert(rosterAssignments.getId(), parent).execute();
				
				File rosterImages = MyUtils.createFolder("images");
				rosterImages = drive.files().insert(rosterImages).execute();
				drive.parents().insert(rosterImages.getId(), parent).execute();
				
				roster.getFolders().put("student", rosterStudents.getId());
				roster.getFolders().put("work forms", rosterWorkForms.getId());
				roster.getFolders().put("assignments", rosterAssignments.getId());
				roster.getFolders().put("images", rosterImages.getId());
				
			
				
			
			
			//Set up AssignedWork Calendar and class events
				calendar = new Calendar.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
				
			com.google.api.services.calendar.model.Calendar assignedWorkCal = new com.google.api.services.calendar.model.Calendar();
			assignedWorkCal.setDescription(roster.getTitle())
			.setSummary("Assigned work form " + roster.getTitle());
			assignedWorkCal = calendar.calendars().insert(assignedWorkCal).execute();
			roster.getCalendars().put("assigned work", assignedWorkCal.getId());
			
			//Create a roster task list
			tasks = new Tasks.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
			TaskList todo = new TaskList();
			todo.setTitle("Todo");
			todo = tasks.tasklists().insert(todo).execute();
			roster.getTasks().put("Todo", todo.getId());
			
			//set up a contacts groud for the roster
			ContactsService conService = new ContactsService("ZoomClassroom");
			conService.setOAuth2Credentials(cred);
			
			ContactGroupEntry contacts = new ContactGroupEntry();
			contacts.setTitle( TextConstruct.plainText(roster.getTitle()));
			contacts.setSummary(TextConstruct.plainText("This is the contacts for roster " + roster.getTitle()));
			
			  URL postUrl = new URL("https://www.google.com/m8/feeds/groups/"+appUser.getAcctId() +"/full");
			  contacts = conService.insert(postUrl, contacts);
			  roster.setContacts(contacts.getId());
			// Set up a google sites for Class info
		}//end first save else////////
	

	}	
	
	
	
	private void deleteRoster(HttpServletRequest req, HttpServletResponse res) throws GeneralSecurityException, IOException{
	
		AppUser appUser = (AppUser) req.getSession().getAttribute("appUser");
		GoogleCredential cred = MyUtils.createCredentialForUser(appUser.getAcctId(), DriveScopes.DRIVE, CalendarScopes.CALENDAR, TasksScopes.TASKS, CONTACTS);
		Drive drive = new Drive.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
		Calendar calendar = new Calendar.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
		Tasks tasks = new Tasks.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
		
		Roster roster = gson.fromJson(req.getParameter("roster"), Roster.class);
		//Params check/////////////
		if(roster.getId() == null || roster.getId() == 0){
			Stuff stuff = new Stuff(null);
			stuff.setMessage("There was an error when deleting the roster \n please ensure that you choose an existing roster.");
			res.getWriter().write(gson.toJson(stuff));
			log.log(Level.WARNING, "Roster didn't have proper Id");
			
			return;
		}//end if
		
		//As far as google is concerned we leave it to user to clean up!!!
		//Here is a very expensive clean up
		//search for all rosterAssignments and studentwork then delete them
	  List<RosterAssignment> rosterAssignments =  DB.db().load().type(RosterAssignment.class).filter("rosterId", roster.getId()).list();
	  cleanUpRosterAssignments(rosterAssignments);
		//search for all studentjobs and studentgroups and seatingcharts
		DB.db().delete().keys(DB.db().load().ancestor(roster).keys());
		
	}
	
	private void listRoster(HttpServletRequest req, HttpServletResponse res) throws IOException{
		
		String query = req.getParameter("query");
		AppUser appUser = (AppUser) req.getSession().getAttribute("appUser");
	   String condition = req.getParameter("condition");
	   Stuff<Roster> stuff = new Stuff<Roster>(null);
		
		if(query == null || query.isEmpty() && condition == null || condition.isEmpty()){
			//get all user's rosters
			List<Roster> allRosters = DB.db().load().type(Roster.class).filter("acctId", appUser.getAcctId()).list();
			stuff.setStuff(allRosters);
			stuff.setType(StuffType.LIST);
		
		}else{
			List<Roster> rosterList;
			
			stuff.setType(StuffType.LIST);
			switch (condition){
			case "startDate": 
			case "endDate": 
			case "title": 
			case "gradeLevel": 
					rosterList = DB.db().load().type(Roster.class).filter(condition, query).list();
					stuff.setStuff(rosterList);
					stuff.setType(StuffType.LIST);
					break;
			default: stuff.setType(StuffType.ERROR); stuff.setMessage("you cannot query on parameter " + condition);break;
			}
			
		}//end else
			
		res.getWriter().write(gson.toJson(stuff));
		log.log(Level.INFO, "stuff send is " + gson.toJson(stuff));
		
	}
	////////END CRUD ROSTER///////////////////////////////////////////
	
	//CRUD ROSTER STUDENT///////////
	private void listRosterStudents(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		AppUser user = (AppUser) req.getSession().getAttribute("appUser");
		
		//TODO seems I should be checking for roster association here
		//Is this user an authorized agent for this change???
		String rosterCheck = req.getParameter("rosterId");
		String userAcctCheck = req.getParameter("teacherGoogleId");
		Stuff<RosterStudent> stuff = new Stuff<RosterStudent>(null);
		//TODO: this is pretty rudimentary implement better security with shiro///////////
		if(rosterCheck == null || rosterCheck.isEmpty() || userAcctCheck == null || userAcctCheck.isEmpty() && !userAcctCheck.equals(user.getAcctId())){
			//send and error message
			stuff.setType(StuffType.ERROR);
			stuff.setMessage("You must be authorized to list these students");
		
		}else{
			Long rosterId = Long.valueOf(rosterCheck);
			List<RosterStudent> rosterStudentList = db().load().type(RosterStudent.class).ancestor(Key.create(Roster.class, rosterId)).list();
			stuff.setType(StuffType.LIST);
			stuff.setStuff(rosterStudentList);
		}
		
	}
	
	private void createRosterStudent(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, GeneralSecurityException, ServiceException{
		AppUser user = (AppUser)req.getSession().getAttribute("appUser");
		String rosterCheck = req.getParameter("rosterId");
		String userAcctCheck = req.getParameter("userAcct");
		String studentCheck = req.getParameter("student");
		
		
		Stuff<RosterStudent> stuff = new Stuff<RosterStudent>(null);
		
		try{
			
		final RosterStudent rosterStudent = gson.fromJson(studentCheck, RosterStudent.class);
		final Long rosterId = Long.valueOf(rosterCheck);
		
		if(rosterId == rosterStudent.getRoster() && user.isAuthRoster(rosterId)){
			db().transact(new VoidWork(){

				@Override
				public void vrun() {
					Key<RosterStudent> studentKey = db().save().entity(rosterStudent).now();
					Roster roster = db().load().type(Roster.class).id(rosterId).now();
					roster.getStudents().add(studentKey);
					db().save().entity(roster);
					rosterStudent.setId(studentKey.getId());
					
				}});////end transact
			stuff.setType(StuffType.LIST);
			stuff.setMessage("Student successfully added to roster");
			ArrayList<RosterStudent> rosterStudents = new ArrayList<RosterStudent>();
			rosterStudents.add(rosterStudent);
			stuff.setStuff(rosterStudents);
			
		}
	
		
	
		}catch(NullPointerException | NumberFormatException npe){
			stuff.setType(StuffType.ERROR);
			stuff.setMessage("There was an error when adding the student \n please try again!");
			
			log.log(Level.SEVERE, "One of the parameter sent was null possible cyber attack\n Login User : " + user.getAcctId() +
					"\n userId received:" + userAcctCheck +
					" \n rosterId: " + rosterCheck +"\n studentCheck:" + studentCheck);
			npe.printStackTrace();
			
		}finally{
			
			res.getWriter().write(gson.toJson(stuff, Stuff.class));
		}
		
		
	}
	
	private void updateRosterStudent(HttpServletRequest req, HttpServletResponse res){
		String rsCheck = req.getParameter("student");
		Stuff<RosterStudent> stuff = new Stuff<RosterStudent>(null);
		if(rsCheck != null || rsCheck.isEmpty()){
			RosterStudent rs = gson.fromJson(rsCheck, RosterStudent.class);
			db().save().entity(rs);
			ArrayList<RosterStudent> rsList = new ArrayList<RosterStudent>();
			rsList.add(rs);
			stuff.setStuff(rsList);
			stuff.setMessage("Student successfully updated");
			stuff.setType(StuffType.LIST);
			
		}
		
	}
	
	
	
	private void deleteRosterStudent(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		
			String rsCheck = req.getParameter("student");
			Preconditions.checkNotNull(rsCheck,"Must have a json string to convert");
			Preconditions.checkArgument(!rsCheck.isEmpty(), "Cannot be an empty string");
			RosterStudent rs = gson.fromJson(rsCheck, RosterStudent.class);
			
			Stuff<String> stuff = new Stuff<String>(new ArrayList<String>());
			stuff.setMessage("student successfully deleted.");
			try{
			db().delete().entity(rs);
			}catch(TranslateException te){
				
				stuff.setType(StuffType.ERROR);
				stuff.setMessage("Error deleting the student\n if problem persist please contatct admin");
				
			}finally{
				res.getWriter().write(gson.toJson(stuff, Stuff.class));
			}
			
			
			
		
	}
	
	/////END ROSTER STUDENT CRUD///////////
	
	//CRUD CONTACTINFO//////////////////////////////
	
	public void createContactInfo(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void updateContactInfo(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void deleteContactIfno(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	public void listContactInfo(HttpServletRequest req, HttpServletResponse res){
		
	}
	
	////END CONTACTINFO/////////////////////////////
	
	//GRADEDWORK CRUD
	private void createGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
		final DB<RosterAssignment> gradedWorkDB = new DB<RosterAssignment>(RosterAssignment.class);
		final AppUser appUser = (AppUser)req.getSession().getAttribute("appUser");
		
		
		GoogleCredential cred;
		Calendar calendar;
		
		RosterAssignment rosterAssignment = gson.fromJson(req.getParameter("gradedWork"), RosterAssignment.class);
		if(rosterAssignment == null || rosterAssignment.getTitle() == null){
			//throw an error
			
			Stuff<RosterAssignment> stuff = new Stuff<RosterAssignment>(null);
			stuff.setMessage("assigned work not saved");
			stuff.setType(StuffType.ERROR);
			res.getWriter().write(gson.toJson(stuff));
			
			log.log(Level.WARNING, "unformatted gradedwork sent");
			return; 
		}
		
		String calId = req.getParameter("calId");
		
		Event event = new Event();
		event.setDescription((rosterAssignment.getDescription()!= null)? rosterAssignment.getDescription(): "No Description Available");
		event.setSummary(rosterAssignment.getRosterAssignmentType().toString());
		EventDateTime startTime = new EventDateTime();
		startTime.setDate(DateTime.parseRfc3339(new SimpleDateFormat("yyyy-MM-dd").format(rosterAssignment.getDueDate())));
		event.setStart(startTime);
		GregorianCalendar cal =(GregorianCalendar) GregorianCalendar.getInstance();
		cal.setTime(rosterAssignment.getDueDate());
		cal.add(GregorianCalendar.DATE, 1);
		EventDateTime endTime = new EventDateTime();
		endTime.setDate(DateTime.parseRfc3339(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())));
		event.setEnd(endTime);
		event.setGadget(getGradedWorkGadget(rosterAssignment));
		
		try {
			cred = MyUtils.createCredentialForUser(appUser.getAcctId(), CalendarScopes.CALENDAR);
			calendar = new Calendar.Builder(MyUtils.transport(), MyUtils.jsonFactory(), cred).build();
			 event = calendar.events().insert(calId, event).execute();
			 rosterAssignment.setGoogleCalEventId(event.getId());
			 
			
			 
			Key<RosterAssignment> key =  gradedWorkDB.save(rosterAssignment);
			rosterAssignment.setId(key.getId());
			Set<Long> studentIds = new HashSet<Long>();
			 //assign to specified students if there are any else to all students
			 String studentCheck = req.getParameter("students");
			 if(studentCheck !=null && !studentCheck.isEmpty()){
			       Type studentType = new TypeToken<List<Long>>(){}.getType();
			       List<Long> studentIdArray = gson.fromJson(studentCheck, studentType);
			       studentIds.addAll(studentIdArray);
				 
			 }
			 
			
			assign(rosterAssignment, studentIds);
			
  //todo: use watch on that event
		
		} catch (GeneralSecurityException e) {
		
			e.printStackTrace();
		}
		
		
	}
	
	private void deleteGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		DB<RosterAssignment> gradedWorkDB = new DB<RosterAssignment>(RosterAssignment.class);
		AppUser appUser = (AppUser) req.getSession().getAttribute("appUser");
		if(appUser == null){
			log.log(Level.SEVERE, "No user logged in and he got this far scary!!");
		}
		Calendar calService = null;
		try {
			calService = MyUtils.calendarForUser(appUser.getAcctId());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		String checkGradedWork = req.getParameter("gradedWork");
		String checkCalId = req.getParameter("calId");
		//if it's bad get rid of it and quit
		if(checkGradedWork == null || checkGradedWork.isEmpty() || checkCalId == null || checkCalId.isEmpty()){
			//throw an error
			Stuff<String> stuff = new Stuff<String>(null);
			stuff.setMessage("Sorry , there was an error when trying to delete the work assignement.");
			stuff.setType(StuffType.ERROR);
			res.getWriter().write(gson.toJson(stuff));
			return;
		}// end if/////////
		
		//delete event on calendar
		RosterAssignment rosterAssignment = gson.fromJson(checkGradedWork, RosterAssignment.class);
		calService.events().delete(checkCalId, rosterAssignment.getGoogleCalEventId()).execute();
		HashMap<Long,Long> studentWorks = rosterAssignment.getStudentWorks();
		//delete all associated student work
		DB.db().delete().type(StudentWork.class).ids(studentWorks.values());
		//delete gradedWork
		gradedWorkDB.delete(rosterAssignment);
			
	}
	
	private void updateGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
	}
	
	private void listGradedWorks(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
	}
	
	private void getGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
	}
	
	private void unassignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		
	}
	
	private void assignGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		final Gson gson = new Gson();
		//Gradedwork must be explicitly assigned so that takes the guess work out of creating calendar events
		//is single calendar or multiple????
		//first step must be created then assigned or wait in limbo so index on isAssigned
		
		//gradedwork needed
		RosterAssignment rosterAssignment = gson.fromJson(req.getParameter("gradedWork"), RosterAssignment.class);
		Type studentCollection = new TypeToken<List<Long>>(){}.getType();
		
		//assign to these students
		List<Long> studentIds = gson.fromJson(req.getParameter("students"), studentCollection);
		Set<Long> students = new HashSet<Long>();
		students.addAll(studentIds);
		
		assign(rosterAssignment, students);
		
		
	}
	
	///LESSON CRUD
	private void createLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getLesson(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	///BEHAVIOR REPORT CRUD
	private void createBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getBehaviorReport(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//QUIZ CRUD
	private void createQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getQuiz(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//QUIZ QUESTION CRUD
	private void createQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getQuizQuestion(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//CCSTANDARD CRUD
	private void createCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getCCStandard(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//EDUCATIONAL LINK CRUD
	private void createEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getEducationalLink(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	
	//SHOWCASE CRUD
	private void createShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getShowcase(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//STUDENT JOB CRUD
	private void createStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getStudentJob(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//VOCAB CRUD
	private void createVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getVocab(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//VOCAB LIST CRUD
	private void createVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getVocabList(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	//SEATING CHART ACTION
	private void createSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void deleteSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void saveSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void listSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	private void getSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{}
	
	private Gadget getGradedWorkGadget(RosterAssignment rosterAssignment){
		Gadget gadget = new Gadget();
		gadget.setTitle(rosterAssignment.getTitle());
		
		switch(rosterAssignment.getRosterAssignmentType()){
		case HOMEWORK :gadget.setIconLink("");break;
		case PROJECT :gadget.setIconLink("");break;
		case QUIZ :gadget.setIconLink("");break;
		case TEST :gadget.setIconLink("");break;
		case GROUP :gadget.setIconLink("");break;
		case TASK :gadget.setIconLink("");break;
		case BENCHMARK: gadget.setIconLink("");break;
		default:gadget.setLink("");break;
		
		}
		
		gadget.setLink("");
		gadget.setDisplay("chip");
		
		return gadget;
		
	}
	
	private void cleanUpRosterAssignment(final RosterAssignment ra){
		DB.db().transactNew(new VoidWork(){

			@Override
			public void vrun() {
				DB.db().delete().entities(ra.getStudentWorks());
				DB.db().delete().entity(ra);
				
			}});
	}
	
	private void cleanUpRosterAssignments(final List<RosterAssignment> ras){
		for(RosterAssignment r: ras){
			cleanUpRosterAssignment(r);
		}
	}
	
   private Set<StudentWork> assign(final RosterAssignment ra, final Set<Long> studentIds){
	   //first make sure these are not duplicates
	   Map<Long,Long> curStudentIds = ra.getStudentWorks();
	   
	   
	   
	   
	final Set<StudentWork> studentWorkList = new HashSet<StudentWork>();
	
	db().transact(
	   new VoidWork(){

		@Override
		public void vrun() {
			for(Long id: ra.getStudentWorks().keySet()){
				StudentWork sw = new StudentWork();
			
				sw.setGradedWorkKey(Key.create(RosterAssignment.class,ra.getId()));
				sw.setRosterStudentId(id);
				studentWorkList.add(sw);
				//assign key value slow process as a transaction
			    ra.getStudentWorks().put(id, db().save().entity(sw).now().getId());
				
			}
			
		}});
		
		
	return studentWorkList;
		
   }
}
