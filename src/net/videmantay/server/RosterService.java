package net.videmantay.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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
import net.videmantay.server.user.RosterStudent;
import net.videmantay.shared.StuffType;
import net.videmantay.shared.UserRoles;
import static net.videmantay.server.GoogleUtils.*;
import static net.videmantay.server.user.DB.*;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Preconditions;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Event.ExtendedProperties;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Longs;
import com.google.gdata.util.ServiceException;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.ObjectifyService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

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
		} catch (TemplateException e) {
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
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void init(HttpServletRequest req, HttpServletResponse res)throws IOException , ServletException, GeneralSecurityException, ServiceException, TemplateException{
		//authorize
		initializeFlow();
		// 3. Route The Path
		String path = req.getRequestURI();
		log.log(Level.INFO, "the path is " + path);
		switch(path){
		
	
		case "/teacher":getTeacherView(req,res);break;
		
		//route roster
		case RosterUrl.LIST_ROSTERS:listRoster(req, res);break;
		case RosterUrl.CREATE_ROSTER:saveRoster(req,res);break;
		case RosterUrl.GET_ROSTER: getRoster(req,res);break;
		case RosterUrl.DELETE_ROSTER:deleteRoster(req,res);break;
		//need for long process
		
		
		//route student
		case RosterUrl.CREATE_STUDENT:saveRosterStudent(req,res);break;
		case RosterUrl.UPDATE_STUDENT:updateRosterStudent(req,res);break;
		case RosterUrl.DELETE_STUDENT: deleteRosterStudent(req,res);break;
		
		case RosterUrl.CREATE_ASSIGNMENT:saveGradedWork(req,res);break;
		
		case RosterUrl.GET_SEATINGCHART:getSeatingChart(req,res);break;
		case RosterUrl.CREATE_SEATINGCHART:createSeatingChart(req,res);break;
		case RosterUrl.UPDATE_SEATINGCHART:updateSeatingChart(req,res);break;
		
		
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
	private void getTeacherView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, TemplateException{
		if(AppValid.roleCheck(UserRoles.TEACHER)){
			User acct = UserServiceFactory.getUserService().getCurrentUser();
		res.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8888");
		Map<String, Object> data = new HashMap<String, Object>();
		AppUser user = db().load().type(AppUser.class).filter("acctId", acct.getEmail()).first().now();
	if(this.getUserId(req).equals(acct.getUserId())){
		System.out.println("user id are equal");
	
		Credential cred = cred(acct.getUserId());
		user.setAuthToken(cred.getAccessToken());
		
	}
	else{
		System.out.println("User id didn't equal no oauth then");
			//res.sendRedirect("/somewhere else");
		}
		
		//get roster list
		List<RosterDetail> rosters = db().load().type(RosterDetail.class).filter("ownerId", acct.getEmail() ).list();
		log.log(Level.INFO, "roster is length is " + rosters.size());
		String loginInfo = gson.toJson(user);
		String rosterList = gson.toJson(rosters);
		data.put("loginInfo", loginInfo);
		data.put("rosterList", rosterList);
		Template teacherPage = TemplateGen.getTeacherPage();
		teacherPage.process(data, res.getWriter());
		}else{
			res.sendRedirect("/login");
		}
	}
	
	//ROSTER CRUD
	private void saveRoster(final HttpServletRequest req, final HttpServletResponse res)throws IOException,ServletException, GeneralSecurityException, ServiceException{
		if(!AppValid.roleCheck(UserRoles.TEACHER)){
			//send to login
			res.sendRedirect("/login");
		}
		final DB<Roster> rosterDB = new DB<Roster>(Roster.class);
		Credential cred = null;
		
		
		final Roster roster;
		final UserService us =UserServiceFactory.getUserService();
		final User user = us.getCurrentUser();
		if(UserServiceFactory.getUserService().isUserLoggedIn()){
		cred = authFlow(user.getUserId()).loadCredential(user.getUserId());
			if(cred.getExpiresInSeconds() < 1800){
				cred.refreshToken();
			}
		}else{
			res.sendRedirect(us.createLoginURL("/teacher"));
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
				if(user.getEmail().equals(rosDB.getOwnerId())){
					final RosterDetail rosDetail = roster.createDetail();
					
					db().transact(new VoidWork(){

						@Override
						public void vrun() {
							rosterDB.save(roster);
							db().save().entity(rosDetail);
							
						}});
					//send rosterDetail back
					try {
						res.getWriter().write(gson.toJson(rosDetail));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				

					}else{
				//throw an error here.
			
		}
				
			}// end if roster id Null ///////////END UPDATE ROSTER /////////////////////
			
		/////////FIRST SAVE HERE ///////////////////////////////////////////////////////////////////////////		
		}else{//this is a first save set up docs,calendar,etc
				log.log(Level.INFO, "first save callded");
		
			roster.setOwnerId(user.getEmail());
			
			
			//at this point there is no id so we wait for one
			//to assign to rd
			RosterDetail rd = roster.createDetail();
			Key<Roster> rosterKey = rosterDB.save(roster);
			rd.setId(rosterKey.getId());
			db().save().entity(rd);
			
			//set up default class time and seating chart
			SeatingChart seatingChart = new SeatingChart();
			seatingChart.rosterKey = rosterKey;
			ClassTime classTime = new ClassTime();
			classTime.setTitle("Class Time");
			classTime.setDescript("Class Time is a protocol to help student with transitioning from one task to the next.\n For Example 'Carpet Time', 'Reading Groups', etc");
			classTime.setId(db().save().entity(seatingChart).now().getId());
			roster.getClassTimes().add(classTime);
			roster.setId(rosterKey.getId());
			db().save().entity(roster).now();
			
			
			//setup necessary files like parent folder gradebook,goalbook, and the like
			Queue queue = QueueFactory.getDefaultQueue();
			TaskOptions to = TaskOptions.Builder.withUrl("/rostersetup")
					.param("rosterId", rosterKey.getId()+"")
					.param("userId", user.getUserId())
					.param("acctId", user.getEmail());
			queue.add(to);
			try {
				res.getWriter().write(gson.toJson(rd));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		List<RosterDetail> rosters = db().load().type(RosterDetail.class).filter("ownerId", user.getEmail() ).list();
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
		
		 Roster roster = db().load().type(Roster.class).id(id).now();
		if(roster == null|| !roster.getOwnerId().equals(user.getEmail()) ){
			//send something bad
			res.setStatus(res.SC_UNAUTHORIZED, "Unauthorized request");
			res.flushBuffer();
			return;
		}
		
		//load student
		
		roster.getRosterStudents().addAll( db().load().keys(roster.studentKeys).values());
		res.getWriter().write(gson.toJson(roster));
	}
	
	////////RosterStudent CRUD ///////////////////////////////
	
	private void saveRosterStudent(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException
	{
		log.log(Level.INFO,"Save Roster student called");
		Drive drive;
		
		Credential cred = null;
		UserService us =UserServiceFactory.getUserService();
		User user = us.getCurrentUser();
		if(UserServiceFactory.getUserService().isUserLoggedIn()){
		cred = authFlow(user.getUserId()).loadCredential(user.getUserId());
		if(cred.getExpiresInSeconds() <= 1800){
			cred.refreshToken();
		}
		}else{
			res.sendRedirect(us.createLoginURL("/teacher"));
		}
		String studentCheck = Preconditions.checkNotNull(req.getParameter("student"));
		
		
		RosterStudent student = gson.fromJson(studentCheck, RosterStudent.class);
		log.log(Level.INFO, "student acctId is " + student.getAcctId() );
		//check for duplicate//////
		Roster roster = db().load().type(Roster.class).id(student.getRoster()).now();
		if(roster == null || !roster.getOwnerId().equals(user.getEmail())){
			//return with error
			log.log(Level.INFO, "Roster is null");
		}
		
			//first save access to rosterDetail
			Key<Roster> rKey = Key.create(Roster.class, roster.getId());
			Key<RosterDetail> rdKey = Key.create(rKey, RosterDetail.class, roster.getId());
			RosterDetail rd = db().load().key(rdKey).now();
			log.log(Level.INFO,  "rd is null ? " + (rd == null));
			rd.getAccess().add(student.getAcctId());	
			db().save().entity(rd);
		
	
		drive = drive(cred);
		
		//be sure roster has a student folder
		if(roster.getStudentFolderId()==null || roster.getStudentFolderId().isEmpty()){
			log.log(Level.INFO, "roster studentfolderid is either empty or null called");
 			//assign a main folder
 			File rosStudentFolder = GoogleUtils.folder("Students");
 			rosStudentFolder.setParents(ImmutableList.of(roster.getRosterFolderId()));
 			rosStudentFolder = drive.files().create(rosStudentFolder).execute();
 			roster.setStudentFolderId(rosStudentFolder.getId());
 			db().save().entity(roster);
 			
 		}
 		try{
 			//This is here to test existence of the file maybe 
 			//student folders has out-of-date data
 			drive.files().get(roster.getStudentFolderId()).execute();
 		}catch(GoogleJsonResponseException gjre){
 			File rosStudentFolder = GoogleUtils.folder("Students");
 			rosStudentFolder.setParents(ImmutableList.of(roster.getRosterFolderId()));
 			rosStudentFolder = drive.files().create(rosStudentFolder).execute();
 			roster.setStudentFolderId(rosStudentFolder.getId());
 			db().save().entity(roster);
 		}
		//create folder for student
 		log.log(Level.INFO, "creating student folder called");
		File studentFolder = folder(student.acctId);
		
		studentFolder.setParents(ImmutableList.of(roster.getStudentFolderId()));
		studentFolder = drive.files().create(studentFolder).execute();
		
	
		Permission perm = new Permission();
		perm.setEmailAddress(student.getAcctId());
		perm.setRole("reader");
		perm.setType("user");
		
		
		drive.permissions().create(studentFolder.getId(), perm).setFields("id").execute();
		
		student.setStudentFolderId(studentFolder.getId());
		student.getAccess().add(student.getAcctId());	
		Key<RosterStudent> key = db().save().entity(student).now();
		roster.getStudentKeys().add(key);
		db().save().entity(roster);
		
		//populate the gradedwork, goals and spreadsheets with this student 
		//then place a reords sheet in his folder
		
		
		res.getWriter().write(gson.toJson(student));
		res.flushBuffer();
		return;
		
	}
	
	private void updateRosterStudent(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Drive drive;
		Credential cred = this.getCredential();
		
		String studentCheck = Preconditions.checkNotNull(req.getParameter("student"));
		RosterStudent student = gson.fromJson(studentCheck, RosterStudent.class);
		
		RosterStudent dbCheck = db().load().type(RosterStudent.class).id(student.getId()).now();
		if(dbCheck == null){
			//throw exception
		}
		if(!student.getAcctId().equals(dbCheck.getAcctId())){
		drive = drive(cred);
		File folder = drive.files().get(student.getStudentFolderId()).execute();
		folder.setName(student.getAcctId());
		drive.files().update(folder.getId(), folder).execute();
		
		}
		db().save().entity(student);
		res.getWriter().write(gson.toJson(student));
		res.flushBuffer();
		
		return;
				
	}
	
	private void deleteRosterStudent(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		String studentCheck = req.getParameter("student");
		Long id = Longs.tryParse(studentCheck);
		
		db().delete().key(Key.create(RosterStudent.class, id));
		//List<Key<StudentWork>> studentWork = db().load().type(StudentWork.class).filter("studentId", id).keys().list();
		//db().delete().keys(studentWork);
		Stuff<String> stuff = new Stuff<>("Student deleted");
		res.getWriter().write(gson.toJson(stuff));
	}
	
	//roster student automatically get listed with the roster
	
	//GRADEDWORK CRUD
	
	private void deleteGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException, ServiceException{
		
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignement"));
		GradedWork gradedWork = gson.fromJson(gwCheck, GradedWork.class);
		if(!AppValid.rosterCheck(gradedWork.rosterId)){
			//throw exception
		}

		Key<GradedWork> parentKey = Key.create(GradedWork.class,gradedWork.id);
		//delete the record from all students records spreadsheet
		List<Key<Object>> keys = db().load().ancestor(parentKey).keys().list();
		db().delete().keys(keys);
	
		//delete row from gradebook///
		
	//send success message
		return;
		
	}
	
	private void createGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException, ServiceException{
		UserService us = UserServiceFactory.getUserService();
		User user = us.getCurrentUser();
		
		Credential cred = cred(user.getUserId());
		Boolean firstSave = false;
		Calendar calendar = GoogleUtils.calendar(cred);
		
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignment"));
	}
	
	private void saveGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException, ServiceException{
		UserService us = UserServiceFactory.getUserService();
		User user = us.getCurrentUser();
		
		Credential cred = cred(user.getUserId());
		Boolean firstSave = false;
		Calendar calendar = GoogleUtils.calendar(cred);
		
		String gwCheck = Preconditions.checkNotNull(req.getParameter("assignment"));
		String calId = Preconditions.checkNotNull("calendarId");
		
		GradedWork gradedWork = gson.fromJson(gwCheck, GradedWork.class);
		log.log(Level.INFO, "Graded work event to Json is :" + gson.toJson(gradedWork.getEvent()) );
		if(!AppValid.rosterCheck(gradedWork.rosterId)){
			//TODO:throw an error
		}
		
		Event event = new Event();
		//if the id is null or zero then this is a first save/////
		if(gradedWork.getId() == null || gradedWork.getId().isEmpty()){
			//this is a firstSave
			firstSave = true;
			//assign id from uuid
			gradedWork.setId(UUID.randomUUID().toString());
			} //id assigned
		
		//lets stuff json obj in description
		//this way we get list from google and have access to our object
		//essentially using google as the db  ... obviously bad practice
		String descript = gradedWork.getDescription()+ "\n\n-* " + gradedWork.rosterId +"-Assignment *-";
		descript+= " " + gradedWork.type.toString();
		event.setDescription(descript);
	
		Event.ExtendedProperties exProps = new Event.ExtendedProperties();
		Map<String, String> arg = ImmutableMap.of("assignment", gradedWork.getId());
		exProps.setPrivate(arg);
		event.setExtendedProperties(exProps);
		if(firstSave){
		event = calendar.events().insert(calId, event).execute();
		gradedWork.setEventId(event.getId());
		}else{
			event = calendar.events().update(calId, event.getId(), event).execute();
		}
		
		
		//get roster student to make sure you can only assign work to them
		List<RosterStudent> rosterStudents = db().load().type(RosterStudent.class)
				.ancestor(Key.create(Roster.class, gradedWork.rosterId)).list();
		List<Long>studentIds  = new ArrayList<Long>();
		
		for(RosterStudent s: rosterStudents){
			studentIds.add(s.id);
		}
		
		Long[] unassign = gson.fromJson(req.getParameter("unassign"), Long[].class);
		Long[] assign = gson.fromJson(req.getParameter("assign"), Long[].class);
		
		//first make sure that assign and unassign don't have matching ids
		for(Long id: unassign){
			if(Arrays.asList(assign).contains(id)){
				//throw an exception
				log.log(Level.WARNING, "unassign and assign have matching ids : contradiction");
			}
		}
		//now make sure all unassing are students
		for(Long id: unassign){
			if(!studentIds.contains(id)){
				//throw an error
				log.log(Level.WARNING, "Student is not in Roster");
			}
		}
		
		// make studentwork list for assigned 
		ArrayList<StudentWork> studentWorks = new ArrayList<>();
		
		//now make sure all assigned are students
		for(int i= 0; i< assign.length; i++){
			if(!studentIds.contains(assign[i])){
				//throw an error
				log.log(Level.WARNING, "Student is not in Roster");
			}else{
				studentWorks.add(new StudentWork(gradedWork,assign[i] ));
			}
		}
		Key<GradedWork>parentKey = Key.create(GradedWork.class, gradedWork.id);
		//so for unassign cycle through the gradedwork
		for(Long remove: unassign){
			gradedWork.getAssignedTo().remove(remove);
			for(StudentWork stuW:gradedWork.studentWorks){
				if(stuW.rosterStudentId.equals(remove)){
					Key<StudentWork> stuKey = Key.create(parentKey, StudentWork.class, stuW.id);
					db().delete().key(stuKey).now();
					gradedWork.studentWorks.remove(stuW);
					break;
				}//end if
			}//end inner for
		}//end for
		
		
	
		gradedWork.setStudentWorkKeys(db().save().entities(studentWorks).now().keySet());
		db().save().entity(gradedWork);
		res.getWriter().write(gson.toJson(gradedWork));
		res.flushBuffer();
		
	}
	
	private void listGradedWorksFromCal(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		User acct = UserServiceFactory.getUserService().getCurrentUser();
		
	
		
		//return google cal events
		String calId = Preconditions.checkNotNull(req.getParameter("calendar"));
		String rosterId = Preconditions.checkNotNull(req.getParameter("roster"));
		String pageToken  = Preconditions.checkNotNull(req.getParameter("cursor"));
		String criteria = Preconditions.checkNotNull(req.getParameter("criteria"));
		Long roster = Longs.tryParse(rosterId);
		if(!AppValid.rosterCheck(roster)){
			//TODO:throw exception
		}
		
		
		Credential cred = cred(acct.getUserId());
		Calendar cal = GoogleUtils.calendar(cred);
		String Q = "-* " + rosterId+"-Assignment *-";
		if(!criteria.isEmpty()){
			Q+= " "+ criteria;
		}
		Events events = cal.events().list(calId).setQ(Q)
		.setFields("summary,description,start,end,attachements").setMaxResults(50).setPageToken(pageToken).execute();
		res.getWriter().write(gson.toJson(events));
		res.flushBuffer();
	}
	
	private void listGradedWorksFromDB(HttpServletRequest req, HttpServletResponse res) throws IOException, ServiceException{
		
		String rosterCheck = Preconditions.checkNotNull(req.getParameter("roster"));
		Long rosterId = Longs.tryParse(rosterCheck);
		if(!AppValid.rosterCheck(rosterId)){
			
			return;
		}
		String q = req.getParameter("query");

		Query<GradedWork> query = db().load().type(GradedWork.class).filter("rosterId", rosterId).order("dueDate").limit(50);
		if(q != null && !q.isEmpty()){
			
			AppQuery appQuery = gson.fromJson(q, AppQuery.class);
			//scrub name for valid categories
			
			//scrub value for appropriate values
			
			//add filter
			query.filter(appQuery.name, appQuery.value);
		}
		List<GradedWork> gradedWorks;
		String cursor = req.getHeader("cursor");
		if(cursor ==  null || cursor.isEmpty()){
			gradedWorks = query.list();
		}else{
			gradedWorks = query.startAt(Cursor.fromWebSafeString(cursor)).list();
			cursor = query.iterator().getCursor().toWebSafeString();
		}
		
		
		
		
		res.addHeader("Cursor", cursor);
		res.getWriter().write(gson.toJson(gradedWorks));
		res.flushBuffer();
	}
	
	
	private void getGradedWork(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException, ServiceException{
		
		//event should include gw id and return this gw and all related student works
		//param id of gradedwork roster Id and current user
		
		String assignmentCheck = Preconditions.checkNotNull(req.getParameter("assignmentId"));
		String rosterCheck = Preconditions.checkNotNull("rosterId");
		Long rosterId = Longs.tryParse("rosterCheck");
		if(!AppValid.rosterCheck(rosterId)){
			//return error
			
			return;
		}
		
		GradedWork gradedWork = db().load().key(Key.create(GradedWork.class, Longs.tryParse(assignmentCheck))).now();
		if(gradedWork.rosterId != rosterId){
			//throw error
			return;
		}
		
		res.getWriter().write(gson.toJson(gradedWork));
	}
	
	/*
	 * StudentWork must be able to be assigned and unassigned on a whim
	 * so during a selection process in the ui there are two list an unassigned and assign list
	 * students can't be both but can be neither
	 */
	
	
		
		private void updateStudentWork(HttpServletRequest req, HttpServletResponse res) throws IOException, ServiceException, IllegalArgumentException, IllegalAccessException{
			String stuWorkCheck = Preconditions.checkNotNull(req.getParameter("studentWorks"));
			StudentWork[] studentWorks = gson.fromJson(stuWorkCheck, StudentWork[].class);
			
			//do somekind of verification here
			
			//now persist
			db().save().entities(studentWorks);
			
			//send success message
		}
		
	//SEATING CHART CRUD/////////
		private void getSeatingChart(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
			log.log(Level.INFO, "get seating chart called");
			log.log(Level.INFO, "param names are " + req.getParameterNames().nextElement() );
			String classTimeCheck = Preconditions.checkNotNull(req.getParameter("classTime"));
			ClassTime classTime = gson.fromJson(classTimeCheck, ClassTime.class);
			Long rosterId = Longs.tryParse(req.getParameter("roster"));
			
			Roster roster = db().load().type(Roster.class).id(rosterId).now();
			UserService us = UserServiceFactory.getUserService();
			User user = us.getCurrentUser();
			if(user == null || us.isUserLoggedIn()){
				//throw exception
			}
			if(!roster.ownerId.equals(user.getEmail())){
				//throw exception
			}
			Key<SeatingChart> key = Key.create(roster.getKey(),SeatingChart.class,classTime.id);
			SeatingChart seatingChart = db().load().key(key).now();
			
			res.getWriter().write(gson.toJson(seatingChart));
			res.flushBuffer();
		}
		
		private void createSeatingChart(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
			String classTimeCheck = Preconditions.checkNotNull(req.getParameter("classTime"));
			Long rosterId = Longs.tryParse(req.getParameter("roster"));
			
			Roster roster = db().load().key(Key.create(Roster.class, rosterId)).now();
			
			log.log(Level.INFO, "Roster in create class time is null?  " + (roster == null));
			ClassTime classTime = gson.fromJson(classTimeCheck, ClassTime.class);
			SeatingChart seatingChart = new SeatingChart();
			
			User user = UserServiceFactory.getUserService().getCurrentUser();
			if(user == null || ! UserServiceFactory.getUserService().isUserLoggedIn()){
				//throw some error
			}
				
			if(roster == null || !roster.getOwnerId().equals(user.getEmail())){
					//throw exception
				}
			seatingChart.rosterKey = roster.getKey();	
				
			
			
			classTime.setId( db().save().entity(seatingChart).now().getId() );
			if(roster.getClassTimes() == null){
				roster.setClassTimes(new ArrayList<ClassTime>());
			}
			roster.classTimes.add(classTime);
			db().save().entity(roster);
			
			res.getWriter().write(gson.toJson(classTime));
			
		}
		
		private void updateSeatingChart(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
			UserService us = UserServiceFactory.getUserService();
			User user = us.getCurrentUser();
			
			String seatingChartCheck = Preconditions.checkNotNull(req.getParameter("seatingChart"));
			log.log(Level.INFO, "the seating chart is \n " + seatingChartCheck);
			SeatingChart seatingChart = gson.fromJson(seatingChartCheck, SeatingChart.class);
			
			Long rosterId = Longs.tryParse(req.getParameter("roster"));
			Roster roster = db().load().type(Roster.class).id(rosterId).now();
			if(!roster.ownerId.equals(user.getEmail())){
				//throw error
			}
			
			Key<SeatingChart> key = Key.create(roster.getKey(), SeatingChart.class, seatingChart.getId());
			SeatingChart seatingChartDB = db().load().key(key).now();
			if(seatingChartDB == null){
				//throw error
			}
			seatingChart.rosterKey = roster.getKey();
			db().save().entity(seatingChart);
			
			//res.getWriter().write(gson.toJson(seatingChart));
			
		}
		
		private void deleteSeatingChat(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
			
			UserService us = UserServiceFactory.getUserService();
			User user = us.getCurrentUser();
			if(!us.isUserLoggedIn()){
				//do some error
			}
			String classTimeCheck = Preconditions.checkNotNull(req.getParameter("classTime"));
			ClassTime classTime = gson.fromJson(classTimeCheck, ClassTime.class);
			Long rosterId = Longs.tryParse("roster");
			Roster roster = db().load().type(Roster.class).id(rosterId).now();
			log.log(Level.INFO, "classTimes length before remove is " + roster.classTimes.size());
			Arrays.asList(roster.classTimes).remove(classTime);
			log.log(Level.INFO, "classTimes length AFTER remove is " + roster.classTimes.size());

			Key<SeatingChart> key = Key.create(roster.getKey(), SeatingChart.class, classTime.id);
			db().delete().key(key);
			db().save().entity(roster);
			
				res.setStatus(HttpServletResponse.SC_OK);
				res.getWriter().write("Delete Successful");
			
		}
		//CRUD INCIDENTS////
		
		public void saveIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
			
		}
		
		public void deleteIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
			
		}
		public void updateIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
	
		}

		public void listIncident(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
	
		}
	////////////////////////	
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
		
	
	
	
}
