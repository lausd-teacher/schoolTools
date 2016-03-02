package net.videmantay.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.appengine.api.taskqueue.DeferredTask;

public class RosterFoldersSetup implements DeferredTask {

	public final String token;
	public final List<String> list;
	public final String roster;
	
	
	public RosterFoldersSetup(final String auth, final List<String> folders, final String rosterId){
		token = auth;
		list = folders;
		roster = rosterId;
	}
	
	@Override
	public void run() {
		GoogleCredential cred = new GoogleCredential();
		cred.setAccessToken(token);
		Drive drive = GoogleUtils.drive(cred);
		
		ParentReference rp = new ParentReference();
		rp.setId(roster);
		List<ParentReference> listOfP = new ArrayList<ParentReference>();
		listOfP.add(rp);
		List<File> childFolders = new ArrayList<File>();
		for(String name : list){
			File f = new File();
			f.setTitle(name);
			f.setParents(listOfP);
			childFolders.add(f);
		}
		
		BatchRequest driveBatch = drive.batch();
		for(File f: childFolders){
			try {
				drive.files().insert(f).queue(driveBatch, null);
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

	}

}
