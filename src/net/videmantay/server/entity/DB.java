package net.videmantay.server.entity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.appengine.api.utils.SystemProperty;

import net.videmantay.server.Stuff;
import net.videmantay.shared.StuffType;


public class DB {

	
	public static Connection connect() throws SQLException, ClassNotFoundException{
		String url = null;
		if (SystemProperty.environment.value() ==
		    SystemProperty.Environment.Value.Production) {
		  // Connecting from App Engine.
		  // Load the class that provides the "jdbc:google:mysql://"
		  // prefix.
		  Class.forName("com.mysql.jdbc.GoogleDriver");
		  url =
		    "jdbc:google:mysql://zoomclassroom:db?database=school";
		} else {
		 // Connecting from an external network.
		  Class.forName("com.mysql.jdbc.Driver");
		  url = "jdbc:mysql://localhost:3306?database=school";
		}

		//use connect by user status ie teacher student root
		Connection conn = DriverManager.getConnection(url, "teacher", "p422forA11");
		return conn;	
	}
	
	public static Stuff<String> assignGroup(Integer groupId, Integer rosterId,Set<Integer>studentIds) throws SQLException{
		
	 ;
		 Stuff<String> stuff = new Stuff<String>(null);
		try(
				Connection conn = DB.connect();
				CallableStatement call = conn.prepareCall("{call assignGroup(?,?,?);}");
				){
			int rowCount = 0;
			
			for(Integer i: studentIds){
			call.setInt(1, groupId);
			call.setInt(2, rosterId);
			call.setInt(3, i);
		  int num =  call.executeUpdate();
		  rowCount = rowCount + num;
		 
		  stuff.setMessage("Rows effected: " + rowCount);
		 
		  
		}
		}catch(SQLException | ClassNotFoundException e){
		
			stuff.setType(StuffType.ERROR);
			stuff.setMessage("A server error occured please try saving again..");
			e.getStackTrace();
			
		}
		
		return stuff;
	}
	
	public Long createAppUser(final AppUser appUser){
		
		Long appUserId = null;
		try(
				Connection conn = DB.connect();
				CallableStatement call = conn.prepareCall("{call createAppUser(?,?,?,?,?,?,?,?);}");
				){
			call.setString(1, appUser.getAcctId());
			call.setString(2, appUser.getFirstName());
			call.setString(3,appUser.getLastName());
			call.setString(4, appUser.getRole());
			call.setString(5, appUser.getEmail());
			call.setString(6, appUser.getMiddleName());
			call.setString(7, appUser.getPicUrl());
			call.setString(8, appUser.getDefaultPicUrl());
			call.setString(9, appUser.getTitle());
			
			call.registerOutParameter(9, Types.INTEGER);
			call.executeUpdate();
			appUserId = call.getLong(9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return appUserId;
	}
	
	public static Boolean deleteAppUser(Long id){
	
		try(Connection conn = DB.connect();
			CallableStatement call = conn.prepareCall("{call deleteAppUser(?);}");){
			call.setLong(0, id);
			
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
			
		}
		
		return true;
	}
	
	public static Boolean updateAppUser(AppUser appUser){
		
		try(
				Connection conn = DB.connect();
				CallableStatement call = conn.prepareCall("{call updateAppUser(?,?,?,?,?,?,?,?,?,?,?,?,?);}")){
			call.setString(1, appUser.getAcctId());
			call.setString(2, appUser.getFirstName());
			call.setString(3, appUser.getLastName());
			call.setString(4, appUser.getRole());
			call.setString(5, appUser.getEmail());
			call.setString(6, appUser.getMiddleName());
			call.setString(7, appUser.getTitle());
			call.setString(8, appUser.getPicUrl());
			call.setString(9, appUser.getDefaultPicUrl());
			call.setString(10, appUser.getMainDriveFolderId());
			call.setString(11, appUser.getAuthToken());
			call.setTimestamp(12, appUser.getLastLogin());
			call.setLong(13, appUser.getLoginAttempts());
			call.setString(14, appUser.getAuthToken());
			call.setLong(15, appUser.getIdAppUser());
			
			call.executeUpdate();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static List<AppUser> listAppUser(final String criteria){
		List<AppUser> appUserList = null;
	
		try(
				Connection conn = DB.connect();
				CallableStatement call = conn.prepareCall("{call listAppUser(?);}");
				){
				call.setString(0, criteria);
				
				try( ResultSet rs = call.executeQuery();){
					BeanListHandler<AppUser> bh = new BeanListHandler<AppUser>(AppUser.class);
					appUserList = bh.handle(rs);
					}// end rs try handle by catch all
				
				
		} catch (ClassNotFoundException |SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return appUserList;
	}
	
	public static Long createRosterByTeacher(final Roster roster, final String acctId){
		Long id = null;
		//TODO: security check!
		try(Connection conn = DB.connect();
			CallableStatement call = conn.prepareCall("{call createRosterByTeacher()}");){
			call.setString(1, acctId);
			call.setString(2, roster.getTitle());
			call.setDate(3, roster.getStartDate());
			call.setDate(4, roster.getEndDate());
			call.setString(5, roster.getDescription());
			call.setString(6, roster.getMainTeacher());
			call.setString(7, roster.getGradeLevel());
			call.setString(8, roster.getFolderId());
			call.setString(9, roster.getContacts());
			call.registerOutParameter(10, Types.INTEGER);
			
			call.executeUpdate();
			id = call.getLong(10);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return id;
		}
		
		return id;	
	}
	
	public static Boolean deleteRoster(final Long rosterId){
		
		try(Connection conn = DB.connect();
				CallableStatement call = conn.prepareCall("{call deleteRoster(?);}");){
			call.setLong(1, rosterId);
			call.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Boolean updateRosterByTeacher(final Roster roster , final AppUser appUser){
		
		try(Connection conn = DB.connect();
				CallableStatement call = conn.prepareCall("{call updateRoster(?,?,?,?,?,?,?,?,?,?,?);}");){
			call.setLong(1, appUser.getIdAppUser());
			call.setString(2, appUser.getRole());
			call.setLong(3, roster.getId());
			call.setString(4, roster.getTitle());
			call.setDate(5, roster.getStartDate());
			call.setDate(6, roster.getEndDate());
			call.setString(7, roster.getDescription());
			call.setString(8, roster.getMainTeacher());
			call.setString(9, roster.getGradeLevel());
			call.setString(10, roster.getFolderId());
			call.setString(11, roster.getContacts());
			
			call.executeUpdate();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public static List<Roster> listRosterByUser(final Long userId){
		List<Roster> rosterList = null;
		
		try(Connection conn = DB.connect();
				CallableStatement call = conn.prepareCall("{call listRosterByUser(?);}");){
			call.setLong(1, userId);
				try(ResultSet rs = call.executeQuery()){
					while(rs.next()){
						BeanListHandler<Roster> bh = new BeanListHandler<Roster>(Roster.class);
						rosterList = bh.handle(rs);
					}
				}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rosterList;
		}
		
		return rosterList;
	}
	
	
	
	
}
		


