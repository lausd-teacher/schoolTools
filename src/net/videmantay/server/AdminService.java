package net.videmantay.server;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.DB;
import net.videmantay.server.entity.Roster;
import net.videmantay.server.entity.RosterAssignment;
import net.videmantay.shared.StuffType;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.QueryKeys;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;


/*
 * Admin service for creating userAccts and appUser(which should mirror userAccts);
 * for every call to a UserAcct return both AppUser and UserAcct Make client class
 * AcctInfo
 */
@SuppressWarnings("serial")
public class AdminService  extends HttpServlet {
	
	private final String ADMIN_PAGE = "/admin";
	private final  String USER_SAVE ="/admin/saveuser";
	private final String USER_DELETE = "/admin/deleteuser"; 
	private final String USER_QUERY = "/admin/queryuser";
	private final String USER_GET = "/admin/getuser";
	private final String USER_LIST = "/admin/listusers";
	private final String USER_PIC_URL = "/admin/getuserpicurl";
	private final String BLOBSTORE_HANDLER = "/admin/handleblobstore";
	private final Logger log = Logger.getLogger("Admin Service");
	
	private Gson gson = new Gson();

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		initRequest(req,res);
	}
	
	@Override 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		log.log(Level.FINE, "doPost called");
		initRequest(req,res);
	}
	
	private void initRequest(HttpServletRequest req, HttpServletResponse res){
		
		
	}
	

		private void getAdminView(HttpServletRequest req, HttpServletResponse res){
			try {
				res.setContentType("text/html");
				res.getWriter().write(TemplateGen.getAdminPage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void createUser(HttpServletRequest req, HttpServletResponse res) throws IOException{
			String checkParam =req.getParameter("user");
			Preconditions.checkNotNull(checkParam, "Must send user data");
			Preconditions.checkState(!checkParam.isEmpty(), "You must have valid user data");
			
			AppUser appUser = gson.fromJson(checkParam, AppUser.class);
			//check is user name is already taken
				try(Connection conn = DB.connect();
					CallableStatement stm = conn.prepareCall("{call createAppUser(?,?,?,?,?,?,?,?)}");
					){
					
					stm.setString(1, appUser.getAcctId());
					stm.setString(2, appUser.getFirstName());
					stm.setString(3, appUser.getLastName());
					stm.setString(4, appUser.getUserStatus().name());
					stm.setString(5, appUser.getEmail());
					stm.setString(6, appUser.getMiddleName());
					stm.setString(7, appUser.getPicUrl());
					stm.setString(8, appUser.getDefaultPicUrl());
					
					stm.registerOutParameter(9,Types.INTEGER );
					
					boolean success = stm.execute();
					
					
					ResultSet rs = stm.getResultSet();
					rs.
					
					
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
		
		private void deleteUser(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
			Preconditions.checkNotNull(req.getParameter("user"), "Must send a valid user");
			Preconditions.checkArgument(!req.getParameter("user").isEmpty(), "Must send valid parameters");
			AppUser user = gson.fromJson(req.getParameter("user"), AppUser.class);
			
			//This is a pretty heavy operation as we have to delete everything about the user including if the
			//user is a student or teacher
			
			Stuff<String> stuff;
			switch(user.getUserStatus()){
			case TEACHER: stuff = deleteTeacherRecords(user);break;
			case STUDENT: stuff = deleteStudentRecords(user);break;
			case AIDE: stuff = deleteAideRecords(user);break;
			case ADMIN: stuff= deleteAdminRecords(user);break;
			
			}
			
		}
		
		private Stuff<String> deleteStudentRecords(AppUser appUser){
			
			
			
			return deleteUserUtil(appUser);
		}
		
		private Stuff<String> deleteTeacherRecords(AppUser appUser){
			Stuff<String> stuff = new Stuff<String>(null);
			QueryKeys<Roster> rosterKeys =DB.db().load().type(Roster.class).filter("teacherId", appUser.getId()).keys();
			//wow real deleting stuff here
			//eventual works
			//for each roster delete all the graded works and student works
			for(Key<Roster> r: rosterKeys){
				//potentially hundreds here hopefully non-blocking
				QueryKeys<RosterAssignment> gradedWorkKeys = DB.db().load().type(RosterAssignment.class).filter("rosterId", r.getId()).keys();
				for(Key<RosterAssignment> ra: gradedWorkKeys){
				      
					DB.db().delete().keys(DB.db().load().ancestor(ra).keys());
				}//inner for rosterAssignements
				
				DB.db().delete().keys(DB.db().load().ancestor(r).keys());
			}
			//for each roster delete all roster students,jobs,goals and 
			stuff.setMessage("user successfully deleted");
			return stuff;
		}
		
		private Stuff<String> deleteAideRecords(AppUser appUser){
			
			return deleteUserUtil(appUser);
		}
		
		private Stuff<String> deleteAdminRecords(AppUser appUser){
			
			
			
			return deleteUserUtil(appUser);
		}
		
		private Stuff<String> deleteUserUtil(AppUser appUser){
			DB.db().delete().entity(appUser);
			Stuff<String> stuff = new Stuff<String>(null);
			stuff.setMessage("user successfully deleted");
			return stuff;
		}
	
	
		
	
}
