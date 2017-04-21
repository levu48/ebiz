package com.kicon.ebiz.client.view;

import com.kicon.ebiz.client.context.AppContext;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.controller.Controller;
import com.kicon.ebiz.client.controller.WarehouseController;
import com.kicon.ebiz.model.LoginInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasWidgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RequiresResize;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;

public class WarehouseView extends AbstractView implements WarehouseController.Display {
	
	public class ResizeGrid extends Grid implements RequiresResize {
		public ResizeGrid(int rows, int columns) { super(rows, columns); }
		public void onResize() {}
	}
	
	private ResizeGrid appPanel;
	private Grid appTop;
	private Panel appBody;
	
	private TabLayoutPanel tabPanel;
	
	private Button testButton;
	
	public WarehouseView() {
		super();
		setup();
	}
	
	public WarehouseView(Context context) {
		super(context);
		setup();
	}
	
	public void setup() {
		appPanel = new ResizeGrid(1,1);
		initWidget(appPanel);
		
		testButton = new Button("test warehouse view button");
		//appPanel.setWidget(0, 0, testButton);
		
		tabPanel = setupTabs();
		tabPanel.setSize("100%",  "100%");
		appPanel.setWidget(0, 0, tabPanel);
		
	    appPanel.setSize("100%", "100%");
	    appPanel.setStyleName("app-appPanel");
	}
	
	public TabLayoutPanel setupTabs() {
		TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
	    tabPanel.setAnimationDuration(1000);
	    
	    tabPanel.add(new AbsolutePanel(), "Kho hàng");
		tabPanel.add(new AbsolutePanel(), "Kiểm hàng");
		tabPanel.add(new AbsolutePanel(), "Nhập hàng");
		tabPanel.add(new AbsolutePanel(), "Xuất hàng");
		tabPanel.add(new AbsolutePanel(), "Quá trình");
		return tabPanel;
	}
	
	public Button getTestButton() {
		return testButton;
	}
	
	
	public void onResize() {
		tabPanel.setSize("100%",  "100%");
		appPanel.setSize("100%", "100%");
	}
	
	
}
