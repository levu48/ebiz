package com.kicon.ebiz.client.view;

import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.util.DataService;
import com.kicon.ebiz.client.controller.FrontController;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.client.context.Context;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class FrontView extends AbstractView implements FrontController.Display {
	private Grid appPanel;
	private Grid appTop;
	private VerticalPanel appBody;

	private FlexTable contentTable;
	private ListBox listBox;
	private VerticalPanel shopsPanel;

	public FrontView(Context context) {
		super(context);
		setup();
	}
	
	public void setup() {
		appPanel = new Grid(2,1);
		initWidget(appPanel);
		
		appTop = getAppTopPanel();
		appBody = new VerticalPanel();
		
		appBody.add(getShopListPanel());
		
		appPanel.setWidget(0, 0, appTop);
		appPanel.setWidget(1, 0, appBody);
		
	    appPanel.setCellPadding(0);
	    appPanel.setCellSpacing(0);
	    
	    appPanel.getCellFormatter().setStyleName(0, 0, "app-appTop");
	    appPanel.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    appPanel.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
	    
	    appPanel.getCellFormatter().setHeight(1, 0, "100%");
	    appPanel.getCellFormatter().setStyleName(1, 0, "app-shopListPanel");
	    appPanel.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    appPanel.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
	    
	    appPanel.setSize("100%", "100%");
	    appPanel.setStyleName("app-appPanel");
	    
	    //resize(Window.getClientWidth(), Window.getClientHeight());
	}
	
	//////
	
	public Grid getAppTopPanel() {
		Grid appTop = new Grid(1,2);
		Label topTitle = new Label(context == null ? "appTitle" : context.getAppTitle());
		topTitle.setStyleName("app-topTitle");
		
		Label subTitle = new Label(context == null ? "subTitle" : context.getAppSubTitle());
		subTitle.setStyleName("app-subTitle");
		
		AbsolutePanel titlePan = new AbsolutePanel();
		titlePan.setSize("400px", "60px");
		
		titlePan.add(topTitle, 0, 0);
		titlePan.add(subTitle, 17, 32);
		
		HorizontalPanel menuPan = new HorizontalPanel();
		menuPan.setSpacing(5);
		
		ActiveUser activeUser = (context == null ? null : context.getCurrentActiveUser());
		menuPan.add(new Image("gfx/user.png"));
		menuPan.add(new Hyperlink(activeUser.getEmail(), "login.account"));
		//menuPan.add(new Image("gfx/shop.png"));
		//menuPan.add(new Hyperlink("trang chủ", "shop.list"));

		if (activeUser.isManagement()) {
			menuPan.add(new Image("gfx/admin.png"));
			menuPan.add(new Hyperlink("quản trị", "shop.admin"));
		} else {
			menuPan.add(new Image("gfx/pencil.png"));
			menuPan.add(new Hyperlink("ghi danh", "shop.register"));
		}
		
		menuPan.add(new Image("gfx/help.png"));
		menuPan.add(new Hyperlink("trợ giúp", "shop.help"));
		menuPan.add(new Image("gfx/signout.png"));
		menuPan.add(new Anchor("thoát", context.getLoginInfo().getLogoutUrl()));
		
		menuPan.setWidth("500px");
		menuPan.setStyleName("app-shop-sysMenu");

		appTop.setWidget(0, 0, titlePan);	
		appTop.setWidget(0,  1, menuPan);
		
		appTop.getCellFormatter().setWidth(0, 0, "100%");
		appTop.getCellFormatter().setHorizontalAlignment(0,  1, HasHorizontalAlignment.ALIGN_RIGHT);
		appTop.getCellFormatter().setVerticalAlignment(0,  1, HasVerticalAlignment.ALIGN_TOP);
		appTop.getCellFormatter().setStyleName(0, 1, "app-sysMenu");
		
		appTop.setHeight("60px");
		return appTop;
	}
	
	public DecoratorPanel getShopListPanel() {
	    DecoratorPanel decor = new DecoratorPanel();
	    
	    decor.setWidth("100%");
	    decor.setWidth("18em");

	    contentTable = new FlexTable();
	    contentTable.setWidth("100%");
	    contentTable.getCellFormatter().addStyleName(0, 0, "app-list-container");
	    contentTable.getCellFormatter().setWidth(0, 0, "100%");
	    contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);
	    
	    HorizontalPanel hPanel = new HorizontalPanel();
	    hPanel.setBorderWidth(0);
	    hPanel.setSpacing(0);
	    hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
	    hPanel.add(new Label("Chọn một cửa hàng sau đây để bán hàng:"));
	    
	    contentTable.getCellFormatter().addStyleName(0, 0, "app-list-shops");
	    contentTable.setWidget(0, 0, hPanel);
	    
	    listBox = new ListBox();
	    listBox.setWidth("400px");
		listBox.setVisibleItemCount(10);

		contentTable.setWidget(1, 0, listBox);
		
		decor.add(contentTable);
		
	    return decor;
	}
	
	
	public void onResize() {
		appTop.setHeight("60px");
		//appBody.setSize("100%",  "100%");
		appPanel.setSize("100%", "100%");
	}

	public int getClickedRow(ClickEvent event) {
		return listBox.getSelectedIndex();
	}
	
	public HasClickHandlers getList() {
		return listBox;
	}
	
	public void setData(List<String> data) {
		listBox.clear();
		Iterator<String> i = data.iterator();
		while (i.hasNext()) {
			listBox.addItem(i.next());
		}
	}

}

