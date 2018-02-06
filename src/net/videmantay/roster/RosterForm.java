package net.videmantay.roster;

import static com.google.gwt.query.client.GQuery.*;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.json.RosterInfoJson;


public class RosterForm extends Composite{

	private static RosterFormUiBinder uiBinder = GWT.create(RosterFormUiBinder.class);

	interface RosterFormUiBinder extends UiBinder<Widget, RosterForm> {
	}
	
	
	@UiField
	MaterialCard card;
	
	@UiField
	MaterialRow colorRow;
	
	@UiField
	MaterialTextBox title;
	
	@UiField
	MaterialTextBox description;
	
	@UiField
	MaterialTextBox roomNum;
	
	@UiField
	MaterialDatePicker startDate;
	
	@UiField
	MaterialDatePicker endDate;
	
	
	@UiField
	MaterialAnchorButton submitBtn;
	
	@UiField
	MaterialAnchorButton cancelBtn;
	
	@UiField
	HTMLPanel formContainer;
	
	GQuery $colorBtns;
	
	private boolean isUpdate = false;

	private RosterInfoJson data = RosterInfoJson.createObject().cast();
	private String colorVal = "red darken-2";

	public RosterForm() {
		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("rosterForm");
		//setup color row
		for(String s: RosterColors.colors){
			MaterialColumn col = new MaterialColumn();
			col = new MaterialColumn();
			col.setGrid("s1");
			col.setMargin(2);
			final MaterialButton button = new MaterialButton();
			GQuery $btn = $(button);
			$btn.addClass("colorBtn");
			button.setBackgroundColor(Color.fromStyleName(s));
			button.setSize("20px", "20px");
			button.setPadding(3);
			button.setMarginRight(3);
			$btn.attr("data-button-color", s);
			if(s.equals(Color.RED_DARKEN_2.getCssName())){
				$(button).css($$("border: 2px solid DimGray; padding: 1em"));
			}
			button.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent e){
					$(".colorBtn").css($$("padding:0em; border-width:0px;"));
					$(e.getSource()).css($$("padding:1em; border:2px solid DimGray"));
					colorVal = button.getBackgroundColor().getCssName();
				}
			});
			col.add(button);
			colorRow.add(col);
		}
	}
	
	@Override
	public void onLoad(){
		$("#rosterForm input").blur(validate());
		//init colorBtns
		$colorBtns = $("button.colorBtn");
		
		startDate.setAutoClose(true);
		startDate.addCloseHandler(new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
				// TODO Auto-generated method stub
				if(endDate.getDate() != null && startDate.getDate() != null){
					 
					 Date endDateValue = endDate.getValue();
					 if(endDateValue.before(startDate.getDate())){
						 $("#errorStartDateLabel").show();
						
					 }
				 }
					
			}
		});
		endDate.setAutoClose(true);
		endDate.addCloseHandler(new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
			    if(startDate.getDate() != null && endDate.getDate() != null){
					 Date endDateValue = endDate.getValue();
					 if(endDateValue.before(startDate.getDate())){
						 $("#errorEndDateLabel").show();
						
					 }else{
						 $("#errorEndDateLabel").hide();
						 $("#errorStartDateLabel").hide();
						
					 }
				 }else if(endDate.getDate() != null){
					 $("#errorEndDateLabel").show();
				 }
			}
		});
		
		
		
		
		$(".errorLabel").hide();
		$("#errorEndDateLabel").hide();
		$("#errorStartDateLabel").hide();
		
	}
	
	//Need to find a better way to do this
	public RosterInfoJson formData(){
		
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		
		data.setDescription(description.getValue());
		data.setName(title.getValue());
		data.setEnd(df.format(endDate.getValue()));
		data.setStart(df.format(startDate.getValue()));
		data.setRoomNum(roomNum.getValue());
		data.setColor(colorVal);
		

		return data;
		
	}
	
	private  Function validate(){		
		return new Function(){
			@Override
			public void f(){
				GWT.log("event" + $(this).id());
				if($(this).is(":invalid")){
					$(this).next(".errorLabel").show();
					$(this).addClass("inputError");
					submitBtn.setEnabled(false);
				} else{
					$(this).next(".errorLabel").hide();
					submitBtn.setEnabled(true);
				}
			}
		};
		
	}

	public MaterialAnchorButton getSubmitBtn() {
		return this.submitBtn;
	}

	
	public MaterialAnchorButton getCancelBtn() {
		return this.cancelBtn;
	}

	/* This Promise is handled in RosterMain */
	public Promise submit(){
		console.log("SUBMIT: roster hit the form data is : " + JsonUtils.stringify(formData()));
		String url = isUpdate? "/roster/"+ data.getId(): "/roster/";
		
		if(submitBtn.isEnabled()){
			MaterialLoader.loading(true);
			return Ajax.ajax(Ajax.createSettings()
								.setContentType("application/json")
								.setType("post")
								.setDataType("json")
								.setData(formData())
								.setUrl(url));
			
		}else{
			return null;
		}
	}
	
	
	public void reset(){
		
		$("input").val("");
		data = JavaScriptObject.createObject().cast();
		colorVal = "red darken-2";
		$colorBtns.css($$("padding:0em; border-width:0px;"));
		$colorBtns.filter("[data-button-color='red darken-2']").css($$("padding:1em; border:2px solid DimGray"));
	}
	
	public void setData(RosterInfoJson newData){
			console.log("form set data called and data is: ");
			console.log(newData);
			data = newData;
			console.log("just after assigning data to newData");
			console.log(data);
			title.setValue(newData.getName());
			console.log("set the title");
			description.setValue(newData.getDescription());
			console.log("set Description");
			startDate.setValue(new Date(Date.parse(newData.getStart())));
			console.log("set start date");
			endDate.setValue(new Date(Date.parse(newData.getEnd())));
			console.log("set end date");
			roomNum.setValue(newData.getRoomNum());
			console.log("set room number");
			$colorBtns.css($$("padding:0em; border-width:0px;"));
			$colorBtns.filter("[data-button-color='"+newData.getColor()+"']").css($$("padding:1em; border:2px solid DimGray"));
				if(newData.getId() == null){	
					console.log("checking on id ");
					isUpdate = false;
				}else{
					isUpdate = true;
				}
		console.log("finished running the setData on form");
	}

}
