package net.videmantay.student.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import net.videmantay.student.json.StudentWorkJson;

public class StudentWorkView extends Composite {

	private static StudentWorkViewUiBinder uiBinder = GWT.create(StudentWorkViewUiBinder.class);

	interface StudentWorkViewUiBinder extends UiBinder<Widget, StudentWorkView> {
	}
	
	@UiField(provided=true)
	MaterialDataTable<StudentWorkJson> table;

	public StudentWorkView() {
		
		//create the datatable
		table = new MaterialDataTable<StudentWorkJson>();
		TextColumn<StudentWorkJson> titleCol = new TextColumn<StudentWorkJson>(){

			@Override
			public String getValue(StudentWorkJson object) {
				return object.getTitle();
			}
			
			@Override
			public boolean isSortable(){
				return true;
			}
			
		};
		TextColumn<StudentWorkJson> gradeCol = new TextColumn<StudentWorkJson>(){

			@Override
			public String getValue(StudentWorkJson object) {
				return object.getLetterGrade();
			}
			
			@Override
			public boolean isSortable(){
				return true;
			}
			
		};
		TextColumn<StudentWorkJson> percentageCol = new TextColumn<StudentWorkJson>(){

			@Override
			public String getValue(StudentWorkJson object) {
			return Math.floor(object.getPercentage() * 100.00) +"%";
			}
			
			@Override
			public boolean isSortable(){
				return true;
			}
			
		};
		TextColumn<StudentWorkJson> subjectCol = new TextColumn<StudentWorkJson>(){

			@Override
			public String getValue(StudentWorkJson object) {
				return object.getSubject();
			}
			
			@Override
			public boolean isSortable(){
				return true;
			}
			
		};
		
		TextColumn<StudentWorkJson> tyoeCol = new TextColumn<StudentWorkJson>(){

			@Override
			public String getValue(StudentWorkJson object) {
				return object.getType();
			}
			
			@Override
			public boolean isSortable(){
				return true;
			}
			
		};
		
		TextColumn<StudentWorkJson> dateCol = new TextColumn<StudentWorkJson>(){

			@Override
			public String getValue(StudentWorkJson object) {
				return DateTimeFormat.getFormat("MMM dd, yyyy")
						.format(DateTimeFormat.getFormat("yyyy-mm-dd")
								.parse(object.getDateTaken()));
				
			}
			
			@Override
			public boolean isSortable(){
				return true;
			}
			
		};
		
		table.addColumn(titleCol,"Title");
		//end create datatable
		initWidget(uiBinder.createAndBindUi(this));
		
	}

}
