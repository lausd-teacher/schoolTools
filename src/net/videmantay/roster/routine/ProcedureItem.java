package net.videmantay.roster.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;
import gwt.material.design.addins.client.richeditor.MaterialRichEditor;
import net.videmantay.roster.json.ProcedureJson;

public class ProcedureItem extends Composite {

	private static ProcedureItemUiBinder uiBinder = GWT.create(ProcedureItemUiBinder.class);

	interface ProcedureItemUiBinder extends UiBinder<Widget, ProcedureItem> {
	}
	private final ProcedureJson proc;
	@UiField
	MaterialRichEditor editor;
	public ProcedureItem(ProcedureJson procedure) {
		proc = procedure;
		initWidget(uiBinder.createAndBindUi(this));
		editor.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				if(editor.getHTML() == null || editor.getHTML().isEmpty()){
					$(this).remove();
				}else{
					proc.setProcedure(editor.getHTML());
				}
				
			}});
	}
	
	public ProcedureJson getProcedure(){
		return proc;
	}

}
