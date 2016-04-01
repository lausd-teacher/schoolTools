package net.videmantay.roster.student;

import com.floreysoft.gwt.picker.client.callback.AbstractPickerCallback;
import com.floreysoft.gwt.picker.client.domain.Feature;
import com.floreysoft.gwt.picker.client.domain.Picker;
import com.floreysoft.gwt.picker.client.domain.PickerBuilder;
import com.floreysoft.gwt.picker.client.domain.ViewId;
import com.floreysoft.gwt.picker.client.domain.result.BaseResult;
import com.floreysoft.gwt.picker.client.domain.result.PhotoResult;
import com.floreysoft.gwt.picker.client.domain.result.ViewToken;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.shared.LoginInfo;
import net.videmantay.student.json.RosterStudentJson;


public class CreateStudentForm extends Composite{

	private static CreateStudentFormUiBinder uiBinder = GWT.create(CreateStudentFormUiBinder.class);

	interface CreateStudentFormUiBinder extends UiBinder<Widget, CreateStudentForm> {
	}
	

	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialImage studentImg;
	
	@UiField
	MaterialLabel imgUrl;
	
	@UiField
	MaterialButton pickerButton;
	
	@UiField
	MaterialButton okBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	@UiField
	FormPanel form;
	
	@UiField
	MaterialTextBox firstName;
	
	@UiField
	MaterialTextBox lastName;
	
	@UiField
	MaterialTextBox extName;
	
	@UiField
	MaterialDatePicker DOB;
	
	private RosterStudentJson student;
	
	private DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
	
	private AbstractPickerCallback callback = new AbstractPickerCallback() {
        
        @Override
        public void onCanceled() {
          picker.setVisible(false);
        }

		@Override
		public void onPicked(ViewToken viewToken, BaseResult result) {
			PhotoResult pr = result.cast();
			String url = pr.getDocs().get(0).getUrl();
			studentImg.setUrl(url);
			imgUrl.setText(url);
			picker.setVisible(false);
		}
      };
      
      private ClickHandler handler = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			picker.setVisible(true);
		}};
	
	private ClickHandler okHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			$(window).trigger("studentcreate", getFormData());
			
		}};
	
	private ClickHandler cancelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			form.reset();
			modal.closeModal();
			
		}};
	
	
	private Picker picker;
	private final LoginInfo info = window.getPropertyJSO("loginInfo").cast();
	
///Constructor Here , Almost lost it//////////////////////////////////////////////
	public CreateStudentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//set up the picker
		picker = PickerBuilder.create()
				.addView(ViewId.PHOTO_UPLOAD)
				.addView(ViewId.PHOTOS)
				.setOAuthToken(info.getOAuthToken())
				.enableFeature(Feature.SIMPLE_UPLOAD_ENABLED)
				.addCallback(callback)
				.build();
		picker.setVisible(false);
				
		//picker button clickHandler
		pickerButton.addClickHandler(handler);
		okBtn.addClickHandler(okHandler);
		cancelBtn.addClickHandler(cancelHandler);
		
	}
	///////////////////END CONSTR////////////////////////////////////////////////
	
	public void show(){
		modal.openModal();
	}
	
	public void hide(){
		modal.closeModal();
	}
	
	private RosterStudentJson getFormData(){
		student = RosterStudentJson.createObject().cast();
		student.setFirstName(firstName.getValue());
		student.setLastName(lastName.getValue());
		student.setExtName(extName.getValue());
		student.setDOB(df.format(DOB.getDate()));
		
		return student;
	}
	

}
