package com.kicon.ebiz.client.context;

import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.LoginInfo;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AppContext extends AbstractContext {
	final public static String APP_TITLE = "Quản lý Bán hàng";
	final public static String APP_SUB_TITLE = "POS (Point of Sale)";
	
	private LoginInfo loginInfo;
	
	public AppContext(Context parent) {
		super(parent);
	}
	
	public String getAppTitle() {
		return APP_TITLE;
	}
	
	public String getAppSubTitle() {
		return APP_SUB_TITLE;
	}
	
	public LoginInfo getLoginInfo() { 
		if (isTopLevel()) return loginInfo;
		Context top = getTopLevel();
		return top.getLoginInfo();
	}
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

}
