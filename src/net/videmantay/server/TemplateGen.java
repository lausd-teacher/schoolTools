package net.videmantay.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class TemplateGen{
	
	private static Configuration cfg = new Configuration();
	
	
	private TemplateGen(){
		
		

		// Specify the data source where the template files come from. Here I set a
		// plain directory for it, but non-file-system are possible too:
		try {
			cfg.setDirectoryForTemplateLoading(new File("/WEB-INF/html/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Specify how templates will see the data-model. This is an advanced topic...
		// for now just use this:
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// Set your preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear. Here we assume we are developing HTML pages.
		// For production systems TemplateExceptionHandler.RETHROW_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

		// At least in new projects, specify that you want the fixes that aren't
		// 100% backward compatible too (these are very low-risk changes as far as the
		// 1st and 2nd version number remains):
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));  // FreeMarker 2.3.20
		
	}
	
	public static String getAdminPage(){
		try {
			Template adminPage = cfg.getTemplate("/WEB-INF/html/adminView.html");
			HashMap<String, Object> root = new HashMap<String, Object>();
			
			return adminPage.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return error();
	}
	
	public static Template getTeacherPage(){
		try {
			Template teacherPage = cfg.getTemplate("/WEB-INF/html/teacher.html");
			return teacherPage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String getStudentPage(){
		try {
			Template adminPage = cfg.getTemplate("student.html");
			return adminPage.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return error();
	}
	
	public static String getAidePage(){
		try {
			Template adminPage = cfg.getTemplate("aide.html");
			return adminPage.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return error();
	}
	
   private static String error(){
	   return "<h1>Error</h1><div>There has been an internal error please try refrehing the page</div>";
   }
   
   public static Configuration config(){
	   return cfg;
   }
}
