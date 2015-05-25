package net.videmantay.server;

public class SpreadsheetData {

	private static String gradeRangeData = "";
	private static String hwRangeData = "";
	private static final String GOALQUERY= "";
	private static final String FLUENCYQUERY = "";
	private final String SPREADSHEET_URL = "";
	
	
	public static String getGradeRangeData(){
		return gradeRangeData;
	}
	
	public static void setGradeRangeData(String data){
		verifyData(data);
		gradeRangeData = data;
	}
	
	public static String getHwRangeData(){
		return hwRangeData;
	}
	
	public static void setHwRangeData(String data){
		verifyData(data);
		hwRangeData = data;
	}
	
	public static String getGrades(String student){
		
		return "" + student;
	}
	
	public static String getFluency(String student){
		 return ""+ student;
	}
	
	public static String getHW(String student){
		return "" + student;
	}
	
	private static void verifyData(String data){
		//some regex to verify the data
		//Pattern is capital letter followed one or more digits 
		//followed by a colon another capital letter and one ore more digits
	}
}
