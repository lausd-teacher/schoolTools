package net.videmantay.roster.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.addins.client.richeditor.MaterialRichEditor;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.html.ListItem;
import gwt.material.design.client.ui.html.UnorderedList;
import net.videmantay.roster.json.ProcedureJson;
import net.videmantay.roster.json.RoutineConfigJson;

public class RoutineItem extends Composite {

	private static RoutineItemUiBinder uiBinder = GWT.create(RoutineItemUiBinder.class);

	interface RoutineItemUiBinder extends UiBinder<Widget, RoutineItem> {
	}
	private final RoutineConfigJson config;
	@UiField
	HTMLPanel routineItem;
	
	@UiField
	MaterialTab routineTabs;
	
	@UiField
	UnorderedList procList;
	@UiField
	MaterialAnchorButton procCreateBtn;
	
	@UiField
	MaterialAnchorButton procTabBtn;
	
	@UiField
	MaterialAnchorButton groupTabBtn;
	
	@UiField
	MaterialAnchorButton stationTabBtn;
	
	@UiField
	MaterialAnchorButton layoutTabBtn;
	
	@UiField
	DivElement procContent;
	
	@UiField
	DivElement groupContent;
	
	@UiField
	DivElement stationContent;
	
	@UiField
	DivElement layoutContent;
	
	public RoutineItem(RoutineConfigJson con) {
		config = con;
		initWidget(uiBinder.createAndBindUi(this));
		
		//fix tabs and content to not conflict
		procTabBtn.setHref(procTabBtn.getHref()+ config.getId());
		groupTabBtn.setHref(groupTabBtn.getHref()+ config.getId());
		stationTabBtn.setHref(stationTabBtn.getHref()+ config.getId());
		layoutTabBtn.setHref(layoutTabBtn.getHref()+ config.getId());
		
		procContent.setId("procContent"+ config.getId());
		groupContent.setId("groupContent"+ config.getId());
		stationContent.setId("stationContent"+ config.getId());
		layoutContent.setId("layoutContent"+ config.getId());
	}
	
	@Override
	public void onLoad(){
		for(int i = 0; i< config.getProcedures().length(); i++){
			addEditor(config.getProcedures().get(i), i);
		}
		
		//style the button
		procCreateBtn.getElement().getStyle().setPosition(Position.RELATIVE);
		procCreateBtn.setSize(ButtonSize.LARGE);
		//make the routineItem min-height
		$(routineItem).css("min-height:10em; scroll-x:hidden;position:relative");
		
	}
	
	@UiHandler("procCreateBtn")
	public void addNewItem(ClickEvent e){
		console.log("Proc Create Btn clicked");
		int childCount = procList.getWidgetCount();
		console.log("The child count is " + childCount);
		if(childCount > 0){
		ListItem lItem =  (ListItem) procList.getWidget(childCount -1);
		MaterialRichEditor el = (MaterialRichEditor)$(lItem).find(".editor").widget();
		if(el.getValue()==null ||el.getValue().isEmpty()){
			return;
		}else{
			addEditor((ProcedureJson) ProcedureJson.createObject().cast(), childCount -1);
		}
		}else if(childCount == 0){
			addEditor((ProcedureJson) ProcedureJson.createObject().cast(), childCount-1);
		}
	}
	
	private void addEditor(ProcedureJson proc,  int index){
		 MaterialRichEditor editor = new MaterialRichEditor();
		editor.setAirMode(true);
		editor.setBackgroundColor(Color.WHITE);
		editor.setMargin(10.5);
		
		MaterialAnchorButton deleteEditorBtn = new MaterialAnchorButton();
		 ListItem li = new ListItem();
		 li.getElement().setAttribute("index", index+"");
		deleteEditorBtn.setIconType(IconType.DELETE);
		deleteEditorBtn.setType(ButtonType.FLOATING);
		deleteEditorBtn.setSize(ButtonSize.MEDIUM);
		deleteEditorBtn.getElement().getStyle().setPosition(Position.RELATIVE);
		deleteEditorBtn.setLeft(-5.0);
		deleteEditorBtn.setTop(-5.0);
		deleteEditorBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			ListItem lItem = 	$(event.getNativeEvent().getEventTarget()).closest("li").widget();
			String i = lItem.getElement().getAttribute("index");
			int indx = Integer.parseInt(i);
			config.removeProcedure(indx);
				lItem.removeFromParent();
			}});
		MaterialRow row = new MaterialRow();
		MaterialColumn col1  = new MaterialColumn();
		col1.setGrid("s10");
		MaterialColumn col2 = new MaterialColumn();
		col2.setGrid("s1");
		col1.add(editor);
		col2.add(deleteEditorBtn);
		row.add(col1);
		row.add(col2);
		
		li.add(row);
		li.getElement().getStyle().setPosition(Position.RELATIVE);
		procList.add(li);
		GQuery $editor = $(editor);
		$editor.data("procedure",ProcedureJson.createObject());
		$editor.css("border:1px solid DimGray; border-radius:5px;position:relative");
		$editor.addClass("z-depth-1");
		editor.setFocus(true);
		
	}
	
	BlurHandler procBlur = new BlurHandler(){

		@Override
		public void onBlur(BlurEvent event) {
			MaterialRichEditor editor = $(event.getRelativeElement()).widget();
		ProcedureJson proc = 	$(editor).data("procedure", ProcedureJson.class);
		console.log("onBlur from RoutineItem- this is the procedure json");
		console.log(proc);
		proc.setProcedure(editor.getHTML());
			
		}};
		

}
