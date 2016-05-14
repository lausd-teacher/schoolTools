package net.videmantay.roster.seatingchart;

import com.google.gwt.user.client.ui.HTMLPanel;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.core.client.JsArray;

import net.videmantay.roster.seatingchart.json.FurnitureJson;

public class FurnitureAddAction extends FurnitureAction {
	
	private final int index;
	private final HTMLPanel furnitureDiv;
	private final JsArray<FurnitureJson>furnitureList;

	public FurnitureAddAction( HTMLPanel furnitureDiv, final JsArray<FurnitureJson> tempFurnitureList){
		this.furnitureDiv = furnitureDiv;
		
		furnitureList = tempFurnitureList;
		FurnitureJson json = FurnitureJson.createObject().cast();
		json.setType((String) $(furnitureDiv).data("type"));
		json.setKind((String) $(furnitureDiv).data("kind"));
		$(furnitureDiv).data("furniture", json);
		tempFurnitureList.push(json);	
		index = tempFurnitureList.length() - 1;
	}
	
	@Override
	public void exec() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		furnitureList.set(index, null);
		$(furnitureDiv).remove();
	}

}
