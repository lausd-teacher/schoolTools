package net.videmantay.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;

public class GoogleUtils {
	private GoogleUtils() {
	}

	private static Logger LOG = Logger.getAnonymousLogger();

	private final static JacksonFactory jsonFactory = new JacksonFactory();
	private final static UrlFetchTransport transport = new UrlFetchTransport();
	private final static String applicationName = "Schooliki";
	private final static String clientId = "342098051221-vohdgpes1bunbmpkbb2i29k8tpkrcnqg.apps.googleusercontent.com";
	private final static String clientSecret = "5nvXVjQOgBHdwoCwGC1x56Zy";
	public final static AppEngineDataStoreFactory dataStoreFactory = AppEngineDataStoreFactory.getDefaultInstance();

	public final static String PhotoScope = "https://www.googleapis.com/auth/photos";
	public final static String PhotoUploadScope = "https://www.googleapis.com/auth/photos.upload";
	public final static String SitesScope = "https://sites.google.com/feeds/";

	public static GoogleAuthorizationCodeFlow authFlow(String userId) throws IOException {
		ArrayList<String> scopes = new ArrayList<String>();
		scopes.add(DriveScopes.DRIVE);
		scopes.add(CalendarScopes.CALENDAR);
		scopes.add(TasksScopes.TASKS);
		scopes.add(SheetsScopes.SPREADSHEETS);
		scopes.add(PlusScopes.USERINFO_PROFILE);
		// scopes.add(PhotoScope);
		scopes.add(PhotoUploadScope);

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, clientId,
				clientSecret, scopes).setDataStoreFactory(dataStoreFactory)
						.addRefreshListener(new DataStoreCredentialRefreshListener(userId, dataStoreFactory))
						.setAccessType("offline").setApprovalPrompt("auto").build();
		return flow;
	}

	public static Credential cred(String userId) throws IOException {
		final Credential credential = authFlow(userId).loadCredential(userId);
		if (credential == null) {
			LOG.warning("Credential is null user didn't get approval");
			return null;
		}
		if (credential.getExpiresInSeconds() <= 300) {
			credential.refreshToken();
		}
		return credential;
	}

	public static Drive drive(Credential cred) {
		return new Drive.Builder(transport, jsonFactory, cred).setApplicationName(applicationName).build();
	}

	public static Tasks task(Credential cred) {
		return new Tasks.Builder(transport, jsonFactory, cred).setApplicationName(applicationName).build();
	}

	public static Calendar calendar(Credential cred) {
		return new Calendar.Builder(transport, jsonFactory, cred).setApplicationName(applicationName).build();
	}

	public static Sheets sheets(Credential cred) {
		return new Sheets.Builder(transport, jsonFactory, cred).setApplicationName(applicationName).build();

	}

	public static Plus plus(Credential cred) {
		return new Plus.Builder(transport, jsonFactory, cred).setApplicationName(applicationName).build();
	}

	public static File folder(String title) {
		File folder = new File();
		folder.setName(title);
		folder.setMimeType("application/vnd.google-apps.folder");
		return folder;
	}

	public static File spreadsheet(String title) {
		File spreadsheet = new File().setMimeType("application/vnd.google-apps.spreadsheet").setName(title);
		return spreadsheet;
	}

	public static String redirectUri(HttpServletRequest req) {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath("/oauth2callback");
		LOG.info("The url is " + url.build());
		return url.build();
	}

}