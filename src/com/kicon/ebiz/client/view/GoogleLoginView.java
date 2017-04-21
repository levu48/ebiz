package com.kicon.ebiz.client.view;

import com.kicon.ebiz.client.context.AppContext;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.controller.GoogleLoginController;
import com.kicon.ebiz.model.LoginInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
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

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RequiresResize;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;

public class GoogleLoginView extends AbstractView implements GoogleLoginController.Display {
	
	public class ResizeGrid extends Grid implements RequiresResize {
		public ResizeGrid(int rows, int columns) { super(rows, columns); }
		public void onResize() {}
	}
	
	private ResizeGrid appPanel;
	private Grid appTop;
	private Panel appBody;
	
	private HTML googleLogin;
	private Label topTitle;
	private Label subTitle;
	
	public GoogleLoginView() {
		super();
		setup();
	}
	
	public GoogleLoginView(Context context) {
		super(context);
		setup();
	}
	
	public void setup() {
		appPanel = new ResizeGrid(2,1);
		initWidget(appPanel);
		
		appTop = getAppTopPanel();
		appBody = getLoginPanel();
		
		appPanel.setWidget(0, 0, appTop);
		appPanel.setWidget(1,0, appBody);
		
	    appPanel.setCellPadding(0);
	    appPanel.setCellSpacing(0);

	    appPanel.getCellFormatter().setStyleName(0, 0, "app-appTop");
	    appPanel.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    appPanel.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
	    
	    appPanel.getCellFormatter().setHeight(1, 0, "100%");
	    appPanel.getCellFormatter().setStyleName(1, 0, "app-loginPanel");
	    appPanel.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    appPanel.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
	    
	    appPanel.setWidth("100%");
	    appPanel.setHeight("100%");
	    appPanel.setStyleName("app-appPanel");
	}
	
	public Grid getAppTopPanel() {
		Grid appTop = new Grid(1,2);
		
		topTitle = new Label(context == null ? "topTitle" : context.getAppTitle());
		topTitle.setStyleName("app-topTitle");
		
		subTitle = new Label(context == null ? "subTitle" : context.getAppSubTitle());
		subTitle.setStyleName("app-subTitle");
		
		AbsolutePanel titlePan = new AbsolutePanel();
		titlePan.setSize("400px", "60px");
		
		titlePan.add(topTitle, 0, 0);
		titlePan.add(subTitle, 17, 32);
		
		HorizontalPanel menuPan = new HorizontalPanel();
		menuPan.setSpacing(5);
				
		menuPan.add(new Image("gfx/pencil.png"));
		menuPan.add(new Hyperlink("ghi danh", "shop.register"));
		menuPan.add(new Image("gfx/help.png"));
		menuPan.add(new Hyperlink("trợ giúp", "shop.help"));
		
		menuPan.setWidth("250px");
		menuPan.setStyleName("app-shop-sysMenu");

		appTop.setWidget(0, 0, titlePan);	
		appTop.setWidget(0,  1, menuPan);
		
		appTop.getCellFormatter().setWidth(0, 0, "100%");
		appTop.getCellFormatter().setHorizontalAlignment(0,  1, HasHorizontalAlignment.ALIGN_RIGHT);
		appTop.getCellFormatter().setVerticalAlignment(0,  1, HasVerticalAlignment.ALIGN_TOP);
		appTop.getCellFormatter().setStyleName(0, 1, "app-sysMenu");
		
		return appTop;
	}
	
	public Panel getLoginPanel() {
		DecoratorPanel decorPanel = new DecoratorPanel();
		
		decorPanel.addStyleName("app-login-decorPanel");
	    decorPanel.setWidth("20em");
	    
	    googleLogin = new HTML("untitled");
	    
	    
		LoginInfo loginInfo = context.getLoginInfo();
		
		if (loginInfo == null) {
			googleLogin.setHTML("login info is not available.");
		
		} else {
		
			if (!loginInfo.isLoggedIn()) {
				googleLogin.setHTML("Vui lòng <a href='" + loginInfo.getLoginUrl() + "'>đăng nhập</a> "
						+ " bằng một tài khoản của email của Google (gmail.com) để sử dụng phần mềm."
						+ "<p>Nếu muốn tạo cửa hàng riêng để tự làm chủ, sau khi đăng nhập, bạn cần phải ghi danh."
						+ "<p>Nếu bạn là nhân viên của một cửa hàng được chỉ định để sử dụng phần mềm này, "
						+ "bạn chỉ cần đăng nhập và đi vào sử dụng."
						);
			} else {
				googleLogin.setHTML("Xin chào " + loginInfo.getNickname() + " (" + loginInfo.getEmailAddress() + ")"
						+ " <a href='#shop.list'>bấm vào đây</a> để bắt đầu xử dụng công cụ Quản lý Bán lẻ, "
						+ ", nếu không phải là bạn, vui lòng bấm vào "
						+ " <a href='" + loginInfo.getLogoutUrl() + "'>nối kết</a> này để đăng nhập dưới một tài khoản khác.");
			}
		}
		

	    FlexTable layout = new FlexTable();
	    layout.addStyleName("app-login-message");
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    layout.setWidget(0, 0, googleLogin);
	    cellFormatter.setColSpan(0, 0, 2);
	    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

	    layout.setWidth("350px");
	    return layout;
	}
	
	public void onResize() {
		appPanel.setHeight("100%");
	}
	
	
	public HTML getGoogleLoginHTML() {
		return googleLogin;
	}
	
	public Label getTitleLabel() {
		return topTitle;
	}
	
	public Label getSubTitleLabel() {
		return subTitle;
	}
	
}
