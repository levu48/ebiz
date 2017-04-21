package com.kicon.ebiz.client.module.admin.user;

import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RequiresResize;

import com.kicon.ebiz.client.App;
import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.client.ui.data.DataForm;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.ui.DataGridResources;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.client.module.admin.TemplateTable;
import com.kicon.ebiz.client.module.admin.TemplateView;

import java.util.ArrayList;
import java.util.List;

public class UserView extends TemplateView<User> implements UserController.Display {

	class MainTable extends TemplateTable<User> {
		public TextColumn<User> firstNameColumn;
		public TextColumn<User> middleNameColumn;
		public TextColumn<User> lastNameColumn;
		public TextColumn<User> emailColumn;

		public MainTable(int pageSize, DataGrid.Resources resources) { 
			super(pageSize, resources); 
			sinkEvents(Event.ONDBLCLICK);
		}
		
		@Override
		public void onBrowserEvent2(Event event) {
			super.onBrowserEvent2(event);
			if (event.getTypeInt() == Event.ONDBLCLICK) {
				if (!isSplitted) {
					contractView();
					isSplitted = true;
				}
			} 
		}
		
		@Override
		public void setupColumns() {		  
			setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

			lastNameColumn = new TextColumn<User>() {
				@Override
				public String getValue(User object) {
					return object.getLastName();
				}
			};
			addColumn(lastNameColumn, "Họ");
			
			middleNameColumn = new TextColumn<User>() {
				@Override
				public String getValue(User object) {
					return object.getMiddleName();
				}
			};
			
			firstNameColumn = new TextColumn<User>() {
				@Override
				public String getValue(User object) {
					return object.getFirstName();
				}
			};
			addColumn(firstNameColumn, "tên");
			
			emailColumn = new TextColumn<User>() {
				@Override
				public String getValue(User object) {
					return object.getEmail();
				}
			};
		}
	}
    
    public UserView(Context context) {
    	super(context);
    	setup();
    	bind();
    }
    
    @Override    
    public void setupMainTable() {
    	mainTable = new MainTable(20, resources);
    }
    
    @Override
    public void setupForms() {
    	forms.add(new UserForm("Chi tiết"));
    	forms.add(new UserForm("tài khoản"));
    	forms.add(new UserForm("lương bổng"));
    	forms.add(new UserForm("lịch làm việc"));
    	forms.add(new UserForm("tiểu sử"));
    }
    
    public void expandView() {
		image.setUrl("gfx/arrowLeft.png");
		topPan.setWidth("100%");
		mainTable.clearTableWidth();
		
		((MainTable) mainTable).insertColumn(1, ((MainTable) mainTable).middleNameColumn, "tên giữa");
		((MainTable) mainTable).insertColumn(3, ((MainTable) mainTable).emailColumn, "email");
		
		((MainTable) mainTable).setColumnWidth(((MainTable) mainTable).lastNameColumn, 100, Unit.PX);
		((MainTable) mainTable).setColumnWidth(((MainTable) mainTable).middleNameColumn, 100, Unit.PX);
		((MainTable) mainTable).setColumnWidth(((MainTable) mainTable).firstNameColumn, 150, Unit.PX);
		((MainTable) mainTable).setColumnWidth(((MainTable) mainTable).emailColumn, 200, Unit.PX);
		
		mainTable.redraw();
		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
    }
    
    public void contractView() {
		image.setUrl("gfx/arrowRight.png");
		topPan.setWidth("250px");
		
		mainTable.removeColumn(3);
		mainTable.removeColumn(1);
		
		mainTable.clearTableWidth();
		((MainTable) mainTable).setColumnWidth(((MainTable) mainTable).lastNameColumn, 100, Unit.PX);
		((MainTable) mainTable).setColumnWidth(((MainTable) mainTable).firstNameColumn, 150, Unit.PX);
		mainTable.redraw();

		mainPanel.setWidgetSize(vPan, 250);
    }    

    public ListDataProvider<User> getDataProvider() {
    	return dataProvider;
    }

    public SplitLayoutPanel getMainPanel() {
    	return mainPanel;
    }
}
