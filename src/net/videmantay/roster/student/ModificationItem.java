package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialTextArea;

public class ModificationItem extends Composite {

	private static ModificationItemUiBinder uiBinder = GWT.create(ModificationItemUiBinder.class);

	interface ModificationItemUiBinder extends UiBinder<Widget, ModificationItem> {
	}

	private final int index;
	private String mod;
	
	@UiField
	MaterialIcon removeIcon;
	
	@UiField
	MaterialTextArea modification;
	
	public ModificationItem(int x, String m) {
		index = x;
		mod = m;
		initWidget(uiBinder.createAndBindUi(this));
		modification.setValue(mod);
		modification.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				mod = modification.getValue();
				
			}});
		removeIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(body).trigger("removemod", index);
				
			}});
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getMod(){
		return this.mod;
	}
	
}
