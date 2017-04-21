package com.kicon.ebiz.client.controller;

import com.kicon.ebiz.client.RpcServiceAsync;
import com.kicon.ebiz.client.controller.AbstractController;
import com.kicon.ebiz.client.controller.GoogleLoginController;
import com.kicon.ebiz.client.controller.RegisterController;
import com.kicon.ebiz.client.controller.FrontController;
import com.kicon.ebiz.client.view.GoogleLoginView;
import com.kicon.ebiz.client.view.RegisterView;
import com.kicon.ebiz.client.view.FrontView;
import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.client.view.View;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.context.AppContext;
import com.kicon.ebiz.client.util.Constants;
import com.kicon.ebiz.client.controller.AdminController;
import com.kicon.ebiz.client.view.AdminView;


import java.util.HashMap;
import java.util.List;

import com.google.code.p.gwtchismes.client.GWTCWait;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController extends AbstractController implements ValueChangeHandler<String> {
	
	public AdminController adminController;
	
	public AppController(Context context) {
		super(context);
		bind();
	}
	
	public void bind() {
		History.addValueChangeHandler(this);
	}
	
	
	public void go(final HasWidgets container) {
		this.container = container;
		
		rpcService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			
		      public void onSuccess(LoginInfo result) {
      			
		        LoginInfo loginInfo = result;
		        context.setLoginInfo(loginInfo);
		        
		        if (!loginInfo.isLoggedIn()) {
		        	Controller controller = new GoogleLoginController(context, new GoogleLoginView(context));
		        	controller.go(container);
		        	
		        } else {		        	
		        	wait.setMessage("Vui lòng đợi trong lúc kiểm tra tài khoản ...");
		        	wait.show(0);
		        	
		        	rpcService.getActiveUserByEmail(loginInfo.getEmailAddress(), new AsyncCallback<ActiveUser>() {
		        		public void onSuccess(ActiveUser result) {
		        			ActiveUser activeUser = result;
		        			Account account = activeUser.getAccount();
		        			List<User> userList = activeUser.getUserList();
		        			
		        			if (userList == null || userList.size() == 0) {
		        			
			        			if (account == null) {
			        				RegisterView view = new RegisterView(context, RegisterView.Mode.NEW_USER);
			        				RegisterController controller = new RegisterController(context, view);
			        				controller.go(container);
			        				wait.hide();
			        				
			        			} else if (account != null && account.isLocked()) {
			        				RegisterView view = new RegisterView(context, RegisterView.Mode.REGISTERED);
			        				RegisterController controller = new RegisterController(context, view);
			        				controller.go(container);
			        				wait.hide();
			        				
			        			} else if (account != null && !account.isLocked()) {
			    		        	context.getMap().put(Constants.Account, account);
			    		        	context.getMap().put(Constants.ActiveUser, activeUser);
			        				wait.hide();
			    		        	
			    		    		if ("".equals(History.getToken())) {
			    		    			History.newItem("shop.list");
			    		    		} else {
			    		    			History.fireCurrentHistoryState();
			    		    		}
			        			}
			        			
		        			} else { // userList != null  => GOTO shop.list
			        			Window.alert("getActiveUserByEmail succeeded 6");
			        			context.getMap().put(Constants.Account,  account);
			        			context.getMap().put(Constants.ActiveUser,  activeUser);
		    		        	wait.hide();
		    		    		if ("".equals(History.getToken())) {
		    		    			History.newItem("shop.list");
		    		    		} else {
		    		    			History.fireCurrentHistoryState();
		    		    		}
		        			}
		        			
		        			wait.hide();
		        		}
		        		
		        		public void onFailure(Throwable error) {
		        			Window.alert("Error getAccountByEmail");
		        			wait.hide();
		        		}
		        		
		        	});
		        }
		      }
		      
		      public void onFailure(Throwable error) {
		    	  Window.alert("Error logging in. " + error);
		      }
		    });
	}
	
	
	
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		
		if (token != null) {
			Controller controller = null;
			
			if (token.equals("shop.list")) {
				controller = new FrontController(context, new FrontView(context));

			} else if (token.equals("shop.register.confirm")) {
				controller = new RegisterController(context, new RegisterView(context, RegisterView.Mode.REGISTERED));
				
			} else if (token.equals("shop.admin")) {
				context.getMap().put(Constants.AdminShop, null);
			    adminController = new AdminController(context, new AdminView(context));
			    controller = adminController;
			    
			}
		
		
			if (controller != null) {
				controller.go(container);
			}
		}
	}
}