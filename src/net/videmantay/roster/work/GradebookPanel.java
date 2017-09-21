package net.videmantay.roster.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

public class GradebookPanel extends Composite {

	private static GradebookPanelUiBinder uiBinder = GWT.create(GradebookPanelUiBinder.class);

	interface GradebookPanelUiBinder extends UiBinder<Widget, GradebookPanel> {
	}

	public GradebookPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@Override
	public void onLoad(){
		
			/*$(window).scroll(new Function() {
				
			@Override
			public void f(){
				$(".assignment-title").css("top",$(this).scrollTop()+"px");
					
			}
			});*/
			
			$("section#gradebookSection").scroll(new Function() {
				
			@Override
			public void f(){
				$(".assignment-title").css("top",$(this).scrollTop()+"px");
					
			}
			});
	}
	

}
