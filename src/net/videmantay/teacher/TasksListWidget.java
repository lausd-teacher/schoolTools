package net.videmantay.teacher;

import com.google.api.gwt.services.tasks.shared.model.TaskList;
import com.google.api.gwt.services.tasks.shared.model.TaskLists;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;

public class TasksListWidget extends Composite {

	public static CellList<TaskList> tasksList ;
	private AbstractCell<TaskLists> taskListCell = new AbstractCell<TaskLists>(){

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				TaskLists value, SafeHtmlBuilder sb) {
			// TODO Auto-generated method stub
			
		}};
}
