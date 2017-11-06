package net.videmantay.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.student.work.StudentWorkView;

import com.google.gwt.query.client.Function;
import static com.google.gwt.query.client.GQuery.*;

public class StudentMain extends Composite {

	@UiField
	MaterialHeader header;

	@UiField
	MaterialRow studentHeaderRow;

	@UiField
	MaterialColumn chartCol;
	
	@UiField
	MaterialOverlay overlay;
	
	@UiField
	MaterialCard studentWorkCard;
	
	private Function onResize = new Function() {
		@Override
		public void f() {
			setNavFixed();
		}
	};

	private RunAsyncCallback workAsync = new RunAsyncCallback() {

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess() {
			
			overlay.setBackgroundColor(Color.RED_DARKEN_2);
			overlay.add(new StudentWorkView());
			
		}
	};

	private RunAsyncCallback goalAsync = new RunAsyncCallback() {

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess() {
			

		}
	};

	private RunAsyncCallback jobAsync = new RunAsyncCallback() {

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub

		}
	};

	private RunAsyncCallback incidentAsync = new RunAsyncCallback() {

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub

		}
	};

	private RunAsyncCallback newsAsync = new RunAsyncCallback() {

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub

		}
	};

	private RunAsyncCallback attendanceAsync = new RunAsyncCallback() {

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub

		}
	};
	private static StudentMainUiBinder uiBinder = GWT.create(StudentMainUiBinder.class);

	interface StudentMainUiBinder extends UiBinder<Widget, StudentMain> {
	}

	public StudentMain() {
		initWidget(uiBinder.createAndBindUi(this));
		overlay.addCloseHandler(new CloseHandler<MaterialOverlay>(){

			@Override
			public void onClose(CloseEvent<MaterialOverlay> event) {
				event.getTarget().clear();
				
			}});
		
		studentWorkCard.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				showAssignments();
				
			}});
	}

	@Override
	public void onLoad() {
		setNavFixed();
		$(window).resize(onResize);
	}

	public void setNavFixed() {
		if (body.getClientWidth() < 620) {
			header.getElement().getStyle().setPosition(Style.Position.FIXED);
			header.getElement().getStyle().setZIndex(10);
			studentHeaderRow.getElement().getStyle().setDisplay(Style.Display.BLOCK);
			studentHeaderRow.getElement().getStyle().setPosition(Style.Position.FIXED);
			studentHeaderRow.getElement().getStyle().setZIndex(10);

			chartCol.getElement().getStyle().setMarginTop(8.5, Unit.EM);

		} else {
			header.getElement().getStyle().setPosition(Position.RELATIVE);
			header.getElement().getStyle().setZIndex(1);
			studentHeaderRow.getElement().getStyle().setDisplay(Style.Display.NONE);
			chartCol.getElement().getStyle().setMarginTop(1.5, Unit.EM);
		}
	}

	public void showAssignments() {
		overlay.open(studentWorkCard);
		GWT.runAsync(workAsync);
	}

}
