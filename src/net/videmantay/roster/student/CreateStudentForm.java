package net.videmantay.roster.student;

import com.floreysoft.gwt.picker.client.callback.AbstractPickerCallback;
import com.floreysoft.gwt.picker.client.domain.Picker;
import com.floreysoft.gwt.picker.client.domain.PickerBuilder;
import com.floreysoft.gwt.picker.client.domain.ViewId;
import com.floreysoft.gwt.picker.client.domain.result.BaseResult;
import com.floreysoft.gwt.picker.client.domain.result.PhotoResult;
import com.floreysoft.gwt.picker.client.domain.result.ViewToken;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.RosterEvent;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.LoginInfo;
import net.videmantay.student.json.RosterStudentJson;

import java.util.Date;


public class CreateStudentForm extends Composite{

	private static CreateStudentFormUiBinder uiBinder = GWT.create(CreateStudentFormUiBinder.class);

	interface CreateStudentFormUiBinder extends UiBinder<Widget, CreateStudentForm> {
	}
	
	
	@UiField
	MaterialImage studentImg;
	
	
	
	@UiField
	MaterialButton pickerButton;
	
	@UiField
	MaterialButton okBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	@UiField
	FormPanel form;
	
	@UiField
	MaterialTextBox schoolEmail;
	
	@UiField
	MaterialTextBox firstName;
	
	@UiField
	MaterialTextBox lastName;
	
	@UiField
	MaterialButton inactive;

	@UiField
	MaterialDatePicker DOB;
	
	@UiField
	MaterialTextArea currentSummary;
	
	@UiField
	MaterialCheckBox glasses;
	
	@UiField
	MaterialDatePicker eDate;
	
	@UiField
	MaterialTextBox homeLang;
	
	@UiField
	MaterialTextBox readingLevel;
	
	@UiField
	MaterialTextBox eldLevel;
	
	@UiField
	MaterialCheckBox iep;

	@UiField
	MaterialModal studentFormPanel;
	
	@UiField
	MaterialModalContent modalContent;
	
	@UiField
	MaterialCollection modifications;
	
	private final String IMG_URL="https://image.freepik.com/free-icon/user-image-with-black-background_318-34564.jpg";
	
	private  RosterStudentJson student = RosterStudentJson.createObject().cast();
	private Long rosterId = ((RosterJson)window.getPropertyJSO("roster").cast()).getId();
	
	private DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
	
	private AbstractPickerCallback callback = new AbstractPickerCallback() {
        
        @Override
        public void onCanceled() {
          picker.setVisible(false);
          studentFormPanel.open();
        }

		@Override
		public void onPicked(ViewToken viewToken, BaseResult result) {
			studentFormPanel.open();
			PhotoResult pr = result.cast();
			student.setThumbnails(pr.getDocs().get(0).getThumbnails());
			String url = pr.getDocs().get(0).getThumbnails().get(pr.getDocs().get(0).getThumbnails().length() -1).getUrl();
			studentImg.setUrl(url);
			picker.setVisible(false);
			
		}
      };
      
      private ClickHandler handler = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			studentFormPanel.close();
			picker.setVisible(true);
		}};
		
		private ClickHandler inactiveHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(inactive.getText().equalsIgnoreCase("active")){
					inactive.setText("inactive");
					inactive.setIconType(IconType.PERSON_OUTLINE);
				}else{
					inactive.setText("active");
					inactive.setIconType(IconType.PERSON);
				}
				
			}};
	
	private ClickHandler okHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			event.stopPropagation();
			studentFormPanel.close();
			MaterialLoader.loading(true);
			console.log("here is the student just before ajax");
			console.log(getStudent());
			Ajax.ajax(Ajax.createSettings()
					.setUrl("/roster/"+ rosterId +  "/student/")
					.setContentType("application/json")
					.setType("POST")
					.setData(getStudent())
					.setDataType("json")
					)
			.done(new Function(){
				@Override
				public void f(){
					console.log("create student form done return : " +  this.getArgument(0));
					RosterStudentJson rosStu = JsonUtils.safeEval((String)this.getArgument(0));
					$(body).trigger("studentsave", rosStu);
					MaterialLoader.loading(false);
					reset();
				}
			});
			
		}};
	
	private ClickHandler cancelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			student = JavaScriptObject.createObject().cast();
			reset();
			studentFormPanel.close();
			
		}};
	
	
	private  Picker picker;
	private final LoginInfo info = LoginInfo.create();
	
	
///Constructor Here , Almost lost it//////////////////////////////////////////////
	public CreateStudentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//give form a size
		//set up the picker
		modalContent.setDisplay(Display.BLOCK);
		console.log("just before load picker" );
		picker = PickerBuilder.create()
				.addView(ViewId.PHOTOS)
				.setOAuthToken(info.getAuthToken())
				.setDeveloperKey("AIzaSyBBRsdOOp28mnHr0i6ANJgxrB5yCd0YVU4")
				.setSize(900, 600)
				.addCallback(callback)
				.build();
		picker.setVisible(false);
		
		//handle removemod
		$(body).on("removemod", student, new Function(){
			@Override
			public boolean f(Event e, Object... o){
				int index = (int) o[0];
				
				JsArrayString newMods = JsArrayString.createArray().cast();
				for(int i = 0; i< student.getModifications().length(); i++){
					if(i == index){
						continue;
					}else{
						newMods.push(student.getModifications().get(i));
					}
					student.setModifications(newMods);
				}
				
				return true;
			}
		});
		
				
						
	}
	///////////////////END CONSTR////////////////////////////////////////////////
	
	public void show(){
		studentFormPanel.open();
	}
	
	public void hide(){
		studentFormPanel.close();
		form.reset();
		student = null;
	}
	
	public RosterStudentJson getStudent(){
		
		student.setAcct(schoolEmail.getValue());
		student.setFirstName(firstName.getValue());
		student.setLastName(lastName.getValue());
		student.setDOB(df.format(DOB.getDate()));
		student.setCurrentSummary(currentSummary.getValue());
		student.setEDate(df.format(eDate.getValue()).toString());
		student.setHomeLang(homeLang.getValue());
		student.setAcct(schoolEmail.getValue());
		student.setEldLevel(eldLevel.getValue());
		student.setReadingLevel(readingLevel.getValue());
		student.setInactive(inactive.getText().equalsIgnoreCase("inactive"));
		student.setGlasses(glasses.getValue());
		student.setIEP(iep.getValue());
		//student mods are set when immediately
		
		
		return student;
	}
	
	public void setStudent(RosterStudentJson stud){
		this.student = stud;
		//redraw the fields
		//profilePage
		schoolEmail.setValue(student.getAcct());
		firstName.setValue(student.getFirstName());
		lastName.setValue(student.getLastName());
		String url = student.getThumbnails().get(student.getThumbnails().length() -1).getUrl();
		studentImg.setUrl(url);
		DOB.setValue(df.parse(student.getDOB()));
		//Summary
		currentSummary.setValue(student.getCurrentSummary());
		//Detail
		eDate.setValue(df.parse(student.getEDate()));
		glasses.setValue(student.getGlasses());
		eldLevel.setValue(student.getEldLevel());
		readingLevel.setValue(student.getReadingLevel());
		iep.setValue(student.getIEP());
		if(student.getModifications() != null && student.getModifications().length() >0){
		for(int i = 0; i < student.getModifications().length(); i++){
			modifications.add(new ModificationItem(i, student.getModifications().get(i)));
		}
		}else{
			student.setModifications((JsArrayString)JsArray.createArray().cast());
		}
		
	}
	
	public void reset(){
		form.reset();
		inactive.setText("active");
		inactive.setIconType(IconType.PERSON);
		studentImg.setUrl(IMG_URL);
	}
	
	
	
	@Override
	public void onLoad(){
						
				//picker button clickHandler
				pickerButton.addClickHandler(handler);
				okBtn.addClickHandler(okHandler);
				cancelBtn.addClickHandler(cancelHandler);
				inactive.addClickHandler(inactiveHandler);
				
				//init form
				initForm();
				
	}
	
	private void initForm(){
			schoolEmail.setValue("");
			firstName.setValue("");
			lastName.setValue("");
			studentImg.setUrl("");
			DOB.setValue(new Date());
			//Summary
			currentSummary.setValue("");
			//Detail
			eDate.setValue(new Date());
			glasses.setValue(false);
			eldLevel.setValue("");
			readingLevel.setValue("");
			iep.setValue(false);

	}
	

}
