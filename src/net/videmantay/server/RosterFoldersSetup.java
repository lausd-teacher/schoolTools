package net.videmantay.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.common.collect.ImmutableList;
import com.googlecode.objectify.ObjectifyService;

import net.videmantay.server.entity.GoogleService;
import static net.videmantay.server.user.DB.db;
import net.videmantay.server.user.Roster;
import net.videmantay.shared.GoogleServiceType;
import net.videmantay.shared.RosterFolderNames;

public class RosterFoldersSetup implements DeferredTask {

	public final String token;
	
	public final  Roster roster;
	public Logger log = Logger.getAnonymousLogger();
	JsonBatchCallback<File> callback = new JsonBatchCallback<File>(){

		@Override
		public void onSuccess(File file, HttpHeaders arg1) throws IOException {
			GoogleService service = new GoogleService();
			service.setTitle(file.getName());
			service.setDescription(file.getDescription());
			service.setId(file.getId());
			service.setType(GoogleServiceType.DRIVE);
			roster.getGoogleFolders().add(service);
			
			
		}

		@Override
		public void onFailure(GoogleJsonError error, HttpHeaders arg1) throws IOException {
			error.getMessage();
			
		}};
	
	
	public RosterFoldersSetup(final String auth, final Roster ros){
		log.log(Level.INFO, "roster folder set constructor called");
		token = auth;
		roster = ros;
		//roster = ObjectifyService.ofy().load().type(Roster.class).id(rosNum).now();
	}
	
	@Override
	public void run() {
		
		log.log(Level.INFO, "Run method called in roter folder set up");
		GoogleCredential cred = new GoogleCredential();
		cred.setAccessToken(token);
		Drive drive = GoogleUtils.drive(cred);
		
		final List<String> listOfP = ImmutableList.of(roster.getRosterFolderId());
		final List<File> childFolders = new ArrayList<File>();
		for(String name : RosterFolderNames.getNames()){
			File f = new File();
			f.setName(name);
			f.setParents(listOfP);
			childFolders.add(f);
		}
		
		BatchRequest driveBatch = drive.batch();
		for(File f: childFolders){
			try {
				drive.files().create(f).queue(driveBatch, callback);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			driveBatch.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db().save().entity(roster);

	}

}
