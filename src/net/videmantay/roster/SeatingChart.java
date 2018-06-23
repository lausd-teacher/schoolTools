package net.videmantay.roster;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import net.videmantay.roster.seatingchart.json.SeatingChartJson;

import static com.google.gwt.query.client.GQuery.*;

public class SeatingChart implements EntryPoint {

	@Override
	public void onModuleLoad() {
		/*load variable from window
		 * var teacher, course, students,seatingcharts*/
		DashboardPanel sc = new DashboardPanel();
		RootPanel.get().add(sc);
		

	}

}
