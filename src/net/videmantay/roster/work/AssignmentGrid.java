package net.videmantay.roster.work;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.json.GradedWorkJson;

public class AssignmentGrid extends DataGrid<GradedWorkJson> {

	private TextColumn<GradedWorkJson> titleCol = new TextColumn<GradedWorkJson>(){

		@Override
		public String getValue(GradedWorkJson object) {
			return object.getTitle();
		}};
		
		/*private TextColumn<GradedWorkJson> descriptCol = new TextColumn<GradedWorkJson>(){

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getDescription();
			}};*/
			
		private TextColumn<GradedWorkJson> typeCol = new TextColumn<GradedWorkJson>(){

				@Override
				public String getValue(GradedWorkJson object) {
					return object.getGradedWorkType();
				}};	
				
	private TextColumn<GradedWorkJson> subjectCol = new TextColumn<GradedWorkJson>(){

					@Override
					public String getValue(GradedWorkJson object) {
						return object.getSubject();
					}};	
	private TextColumn<GradedWorkJson> assignedToCol = new TextColumn<GradedWorkJson>(){

						@Override
						public String getValue(GradedWorkJson object) {
							StringBuilder sb = new StringBuilder();
							for(int i = 0; i < object.getAssignedTo().length(); i++){
								sb.append(object.getAssignedTo().get(i));
								if(i != object.getAssignedTo().length() - 1){
									sb.append(", ");
								}
							}
							return sb.toString();
						}};		
/*	private TextColumn<GradedWorkJson> langCol = new TextColumn<GradedWorkJson>(){

							@Override
							public String getValue(GradedWorkJson object) {
								return object.getLang();
							}};	*/					
	private TextColumn<GradedWorkJson> dueDateCol = new TextColumn<GradedWorkJson>(){

					@Override
					public String getValue(GradedWorkJson object) {
					return object.getSubject();
								}};	
								
	private TextColumn<GradedWorkJson> assignedDateCol = new TextColumn<GradedWorkJson>(){

				@Override
				public String getValue(GradedWorkJson object) {
				return object.getSubject();
									}};	
								
	private NumberCell pointsCell = new NumberCell();
	private Column<GradedWorkJson, Number> pointsCol =new Column<GradedWorkJson,Number>(pointsCell){

		@Override
		public Number getValue(GradedWorkJson object) {
			return object.getPointsPossible();
		}};
		
	private SafeHtmlCell isGradedCell = new SafeHtmlCell();
	private Column<GradedWorkJson, SafeHtml> isGradedCol = new Column<GradedWorkJson, SafeHtml>(isGradedCell){

		@Override
		public SafeHtml getValue(GradedWorkJson object) {
			SafeHtml html;
			if(object.isFinishedGrading()){
				html = SafeHtmlUtils.fromString("<i class='material-icons'>done</i>");
			}else{
				html = SafeHtmlUtils.fromString("<i></i>");
			}
			return html;
		}};
	private AbstractCell<GradedWorkJson> actionCell = new AbstractCell<GradedWorkJson>("click"){

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context, GradedWorkJson value, SafeHtmlBuilder sb) {
			final String html = "<div><i class='material-icons gotoGradedWork' style='color:Green'>arrow_right</i></div>";
			sb.appendHtmlConstant(html);
			
		}
		
		@Override
		public void onBrowserEvent(Context context, Element parent, GradedWorkJson value, NativeEvent event,
		        ValueUpdater<GradedWorkJson> valueUpdater){
			if(event.getType().equalsIgnoreCase("click")){
				$(body).trigger("gotoGradedWork",value);
			}
		}
		
		@Override
		public void onEnterKeyDown(Context context, Element parent, GradedWorkJson value, NativeEvent event,
		        ValueUpdater<GradedWorkJson> valueUpdater){
			$(body).trigger("gotoGradedWork",value);
		}
	
	};	
		
	IdentityColumn<GradedWorkJson> actionCol = new IdentityColumn<>(actionCell);	

	public AssignmentGrid(){
		super();
		this.addStyleName("striped responsive-table");
		this.addColumn(titleCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Title</span>"));
		this.addColumn(dueDateCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Due<br/> Date</span>"));
		this.addColumn(typeCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Type</span>"));
		this.addColumn(subjectCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Subject</span>"));
		this.addColumn(pointsCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Points</span>"));
		//this.addColumn(descriptCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Summary</span>"));
		this.addColumn(assignedDateCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Assigned<br/> Date</span>"));
		this.addColumn(assignedToCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Students</span>"));
		this.addColumn(isGradedCol, SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Scored</span>"));
		this.addColumn(actionCol);
		this.setSelectionModel(new SingleSelectionModel<GradedWorkJson>());
		this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		this.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		this.setFocus(true);
		//this.setEmptyTableWidget(new EmptyAssignmentGrid());
		this.setHeight("500px");
		this.setWidth("100%");
	}
}
