package net.videmantay.teacher;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarCollapseButton;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelFooter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
/* This is basically the AppController in an MVP Design
 * unlike most MVP we're going to do very hard coupling of ui elements since
 * this is most likely the ui for mobile and desktop 
 * yeah bootstrap
 */
public class Teacher extends Composite implements EntryPoint {
//basic gwt uibinder stull
	private static TeacherUiBinder uiBinder = GWT
			.create(TeacherUiBinder.class);

	interface TeacherUiBinder extends
			UiBinder<Widget, Teacher> {
	}
	
	/// UiFields ////
		@UiField
		Navbar navbar;
		
		@UiField
		AnchorListItem dashboardBtn;
		
		@UiField
		AnchorListItem rosterBtn;
		
		@UiField
		AnchorListItem gradesBtn;
		
		@UiField
		AnchorListItem lessonsBtn;
		
		@UiField
		AnchorListItem calendarBtn;
		
		@UiField
		AnchorListItem  libraryBtn;
		
		@UiField
		AnchorListItem vocabBtn;
		
		@UiField
		AnchorListItem commonCoreBtn;

		@UiField
		NavbarCollapseButton collapseBtn;
		
		@UiField
		PanelBody mainPanel;
		
		@UiField
		Column asideCol;
		
		@UiField
		Column mainCol;
		

		@UiField
		DivElement asideMenu;
		
		
		@UiField
		PanelFooter footerPanel;
		
	
		
		
//Constructor
	public Teacher() {
		initWidget(uiBinder.createAndBindUi(this));
	}




	@Override
	public void onModuleLoad() {
		/*
		 * Will be using GQuery for event system so here is where it is uber important to have things under control
		 * So Dashboard is a list of possible classes to activate/see and an all encompassing calendar.
		 * Along with maybe TODO list; Should look similar to Google classroom landing. 
		 * 
		 * 1. load list of rosters  / on click will go to roster view for that particular roster
		 */
		
		RootPanel.get().add(this);
		$(dashboardBtn).click(new Function(){
			@Override
			public void f()
			{RootPanel.get().clear();
			}
		});
	}
	

	
	
	
}
