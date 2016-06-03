package net.videmantay.server;

import static net.videmantay.server.GoogleUtils.authFlow;
import static net.videmantay.server.GoogleUtils.folder;
import static net.videmantay.server.GoogleUtils.spreadsheet;
import static net.videmantay.server.user.DB.db;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.Preconditions;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskList;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Longs;
import com.google.gson.Gson;
import net.videmantay.server.entity.GoogleService;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.Roster;
import net.videmantay.server.user.RosterSetting;


public class RosterSetup extends HttpServlet {

	final Gson gson = new Gson();
	final Logger log = Logger.getLogger(RosterSetup.class.getCanonicalName());
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		setUpRoster(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		setUpRoster(req, res);
	}
	
	
	
private void setUpRoster(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		
		String userId = Preconditions.checkNotNull(req.getParameter("userId"));
		String acctId =Preconditions.checkNotNull(req.getParameter("acctId"));
		 Credential cred = null;
		 cred = authFlow(userId).loadCredential(userId);
			if(cred.getExpiresInSeconds() < 1800){
				cred.refreshToken();
			}
		
		final AppUser appUser = db().load().type(AppUser.class).filter("acctId",acctId).first().now();
		final Drive drive;
		final Calendar calendar;
		final String rosterCheck = Preconditions.checkNotNull(req.getParameter("rosterId")).trim();
			Long rosterId = Longs.tryParse(rosterCheck);
		final Roster roster = db().load().type(Roster.class).id(rosterId).now();
		final Tasks tasks;
		RosterSetting settings;
		
		// System.out.println( "This is what the cred looks like: " + gson.toJson(cred));
				 //check for roster setting in the db 
				 settings = db().load().type(RosterSetting.class).filter("acctId",acctId).first().now();
				 if(settings == null){
					 log.log(Level.INFO, "settings was null create new settings");
					 settings = new RosterSetting().defaultSetting();
				 }
		
		drive = GoogleUtils.drive(cred);
		 
			//first check if main drive folder has been set
		if(appUser.getMainDriveFolder()==null || appUser.getMainDriveFolder().isEmpty()){
			//assign a main folder
			File kimchiFile = GoogleUtils.folder("Kimchi");
			kimchiFile = drive.files().create(kimchiFile).execute();
			appUser.setMainDriveFolder(kimchiFile.getId());
			db().save().entity(appUser);
			
		}
		try{
			//This is here to test existence of the file
			File kimchiFile = drive.files().get(appUser.getMainDriveFolder()).execute();
		}catch(GoogleJsonResponseException gjre){
			File kimchiFile = GoogleUtils.folder("Kimchi");
			kimchiFile = drive.files().create(kimchiFile).execute();
			appUser.setMainDriveFolder(kimchiFile.getId());
			db().save().entity(appUser);
		}
	File rosterFolder = GoogleUtils.folder(roster.getTitle());
	rosterFolder.setDescription(roster.getDescription());
	
	//Get Kimchi folder as parent for roster////
	rosterFolder.setParents(ImmutableList.of(appUser.getMainDriveFolder()));
	rosterFolder = drive.files().create(rosterFolder).execute();
	roster.setRosterFolderId(rosterFolder.getId());
	
	
	//set roster folder as parent for all other folders
	List<String> listOfP = ImmutableList.of(roster.getRosterFolderId());
File studentFolder = folder("Students");
studentFolder.setParents(listOfP);
roster.setStudentFolderId(drive.files().create(studentFolder).execute().getId());

File rollBook = spreadsheet("RollBook");
	rollBook.setParents(listOfP);
	File gradeBook = spreadsheet("GradeBook");
	gradeBook.setParents(listOfP);
File behaviorReport = spreadsheet("BehaviorReport");
	behaviorReport.setParents(listOfP);
	File goalBook = spreadsheet("GoalBook");
	goalBook.setParents(listOfP);
	
	
	rollBook = drive.files().create(rollBook).execute();
	gradeBook = drive.files().create(gradeBook).execute();
	behaviorReport = drive.files().create(behaviorReport).execute();
	goalBook = drive.files().create(goalBook).execute();
	
	roster.setGradeBook(gradeBook.getId());
	System.out.println("gradeBook id is: " + gradeBook.getId());
	roster.setRollBook(rollBook.getId());
	roster.setBehaviorReport(behaviorReport.getId());
	
	//set up the spreadsheet here
	Queue queue = QueueFactory.getDefaultQueue();
	
	GradeBookSetup setUp = new GradeBookSetup(gradeBook.getId(), cred.getAccessToken());
	queue.add(TaskOptions.Builder.withPayload(setUp));
	
	RollBookSetup rollSet = new RollBookSetup(rollBook.getId(), cred.getAccessToken());
	queue.add(TaskOptions.Builder.withPayload(rollSet));
	
	GoalBookSetup goalSet = new GoalBookSetup(goalBook.getId(), cred.getAccessToken());
	queue.add(TaskOptions.Builder.withPayload(goalSet));
	
	BehaviorReportSetup behaviorSetup = new BehaviorReportSetup(behaviorReport.getId(), cred.getAccessToken());
	queue.add(TaskOptions.Builder.withPayload(behaviorSetup));
	
	
	
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

db().save().entity(roster);
		
	}
}
