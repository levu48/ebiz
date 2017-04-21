package com.kicon.ebiz.client.view;

import com.kicon.ebiz.client.context.AppContext;
import com.kicon.ebiz.client.controller.Controller;
import com.kicon.ebiz.client.controller.GoogleLoginController;
import com.kicon.ebiz.client.controller.RegisterController;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.client.context.Context;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Anchor;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasWidgets;

public class RegisterView extends AbstractView implements RegisterController.Display {
	
	public class ResizeGrid extends Grid implements RequiresResize {
		public ResizeGrid(int rows, int columns) { super(rows, columns); }
		public void onResize() {}
	}
	
	private ResizeGrid appPanel;
	private Grid appTop;
	private DecoratorPanel appBody;
	
	private TextBox userTextBox;
	private PasswordTextBox passwordTextBox;
	
	private TextBox emailTextBox;
	private TextBox displayNameTextBox;
	private Button registerButton;
	
	private HTML mesg, mesg2;
	private LoginInfo loginInfo;
	
	public enum Mode {NEW_USER, REGISTERED}
	
	private Mode mode = Mode.NEW_USER;
	
	public RegisterView() {
		setup();
	}
	
	public RegisterView(Context context) {
		super(context);
		setup();
	}
	
	public RegisterView(Context context, Mode mode) {
		super(context);
		this.mode = mode;
		setup();
	}

	
	public void setup() {
		appPanel = new ResizeGrid(2,1);
		initWidget(appPanel);
		
		appTop = getAppTopPanel();
		appBody = getLoginPanel();
		
		appPanel.setWidget(0, 0, appTop);
		appPanel.setWidget(1, 0, appBody);
		
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
	
	//////
	
	public Grid getAppTopPanel() {
		Grid appTop = new Grid(1,2);

		Label topTitle = new Label(context == null ? "title" : context.getAppTitle());
		topTitle.setStyleName("app-topTitle");
		
		Label subTitle = new Label(context == null ? "subTitle" : context.getAppSubTitle());
		subTitle.setStyleName("app-subTitle");
		
		//VerticalPanel titlePan = new VerticalPanel();
		AbsolutePanel titlePan = new AbsolutePanel();
		titlePan.setSize("400px", "60px");
		
		titlePan.add(topTitle, 0, 0);
		titlePan.add(subTitle, 17, 32);
		
		HorizontalPanel menuPan = new HorizontalPanel();
		menuPan.setSpacing(5);
		
		//menuPan.add(new Hyperlink("trang chủ", "shop.list"));

		menuPan.add(new Image("gfx/pencil.png"));
		menuPan.add(new Hyperlink("ghi danh", "shop.register"));
		menuPan.add(new Image("gfx/help.png"));
		menuPan.add(new Hyperlink("trợ giúp", "shop.help"));
		//menuPan.add(new Hyperlink("đăng xuất", "shop.logout"));
		
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
	
	public DecoratorPanel getLoginPanel() {
		DecoratorPanel decorPanel = new DecoratorPanel();
		
	    decorPanel.setWidth("30em");
	    
		userTextBox = new TextBox();
		passwordTextBox = new PasswordTextBox();
		
		emailTextBox = new TextBox();
		displayNameTextBox = new TextBox();
		
		registerButton = new Button("ghi danh");
		registerButton.setWidth("120px");
	    
		LoginInfo loginInfo = (context == null ? null : context.getLoginInfo());
		
		if (loginInfo == null) {
			mesg = new HTML("login info is not available.");
		
		} else {
		
			if (mode == Mode.NEW_USER) {
				if (loginInfo.isLoggedIn()) {
					mesg = new HTML("Xin chào <b>" + loginInfo.getNickname() + " (" + loginInfo.getEmailAddress() + ")</b>,"
							+ " nếu muốn tạo cửa hàng, xin bạn ghi danh bằng cách bấm vào nút sau đây.<p>");
					
					mesg2 = new HTML("Nếu bạn không phải là <i>" + loginInfo.getNickname() + " (" + loginInfo.getEmailAddress() + ")</i>, vui lòng "
							+ " <a href='" + loginInfo.getLogoutUrl() + "'>đăng xuất</a> và đăng nhập lại dưới tên tài khoản của chính bạn"
							+ " trước khi có thể tiếp tục. ");
				} else {
					mesg = new HTML("Invalid process flow.");
				}
				
			} else if (mode == Mode.REGISTERED) {
				//Account account = AppController.getCurrentRegisterAccount();
				if (loginInfo.isLoggedIn()) {
					mesg = new HTML("Xin chào <b>" + loginInfo.getNickname() + " (" + loginInfo.getEmailAddress() + ")</b>,"
							+ " bạn đã ghi danh thành công. Kết quả chuẩn thuận sẽ được gởi tới bạn qua email.<p>");
					
					mesg2 = new HTML("Nếu bạn không phải là <i>" + loginInfo.getNickname() + " (" + loginInfo.getEmailAddress() + ")</i>, vui lòng "
							+ " <a href='" + loginInfo.getLogoutUrl() + "'>đăng xuất</a> và đăng nhập lại dưới tên tài khoản của chính bạn"
							+ " trước khi có thể tiếp tục. ");
				} else {
					mesg = new HTML("Invalid process flow.");
				}
			}
		}
			
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(2);
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    layout.setWidget(0, 0, mesg);
	    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    cellFormatter.setColSpan(0, 0, 2);

	    int num = layout.getRowCount();
	    
	    if (mode == Mode.NEW_USER) {
	    	layout.setText(num, 0, "email");
	    	cellFormatter.setHorizontalAlignment(num, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	    	layout.setWidget(num, 1, emailTextBox);
	    	emailTextBox.setText(loginInfo.getEmailAddress());
	    	emailTextBox.setReadOnly(true);
	    	
	    	
	    	layout.setText(num+1, 0, "tên");
	    	cellFormatter.setHorizontalAlignment(num+1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	    	layout.setWidget(num+1, 1, displayNameTextBox);
	    	
	    	layout.setWidget(num+2, 1, registerButton);
	    	//cellFormatter.setHorizontalAlignment(num, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    } 
	    
	    num  = layout.getRowCount();
	    layout.setText(num, 0, "");
	    cellFormatter.setHeight(num, 0, "20px");
	    
	    layout.setWidget(num+1, 0, mesg2);
	    cellFormatter.setHorizontalAlignment(num+1, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    cellFormatter.setColSpan(num+1, 0, 2);
	    
	    decorPanel.setWidget(layout);
	    decorPanel.setStyleName("app-register-mesg");

	    return decorPanel;
	}
	
	public LoginInfo getLoginInfo() {
		String nickName = displayNameTextBox.getText();
		if (nickName != null) {
			loginInfo.setNickname(nickName);
		}
		return loginInfo;
	}
	
	public Button getRegisterButton() {
		return registerButton;
	}
	
	public void onResize() {
		appPanel.setHeight("100%");
	}

}
