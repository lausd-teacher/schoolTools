package net.videmantay.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;

import static net.videmantay.server.MyUtils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.appengine.api.urlfetch.FetchOptions.Builder.*;

import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.DriveScopes;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.api.users.*;
import com.google.common.io.ByteStreams;

public class StudentService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9218769254538994119L;
	private final String DASHBOARD = "/student/getdashboard";
	private final String GRADES = "/student/grades";
	private final String FLUENCY = "/student/fluency";
	private final String SPREADSHEET_URL = SPREADSHEET_SCOPE +"/spreadsheets/private/full";
	private final String GRADEBOOK_KEY = "1icNExFsBYYV0aNsefDOCbgvmhmG_WdqFqw4w4SH-v2s";
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		init(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
		init(req, res);
	}
	
	private void init(HttpServletRequest req, HttpServletResponse res){
		getDashboard(req,res);
	}// end init
	
	private void getDashboard(HttpServletRequest req, HttpServletResponse res){
		


	}

}