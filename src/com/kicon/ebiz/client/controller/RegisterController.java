package com.kicon.ebiz.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.controller.GoogleLoginController.Display;
import com.kicon.ebiz.client.view.RegisterView;
import com.kicon.ebiz.client.RpcServiceAsync;
//import com.kicon.ebiz.client.event.RegisterEvent;
//import com.kicon.ebiz.client.event.RegisterEventHandler;
import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.client.util.Constants;

public class RegisterController extends AbstractController {
	
	public interface Display extends AbstractController.Display {
		public Button getRegisterButton();
	}
	
	private Display display;
	
	public RegisterController(Context context, Display display) {
		super(context);
		this.display = display;
		bind();
	}
	
	public void go(final HasWidgets container) {
		this.container = container;
	    container.clear();
	    container.add(display.asWidget());
	}
	
	public void bind() {

		display.getRegisterButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
					final LoginInfo loginInfo = context.getLoginInfo();
		            
	        	  	rpcService.registerUser(loginInfo, new AsyncCallback<Account>() {	

		      			public void onSuccess(Account result) {
		      				if (result != null) {
		      					Account userAccount = result;

		      					context.getMap().put(Constants.RegisterAccount, userAccount);
		      					ActiveUser activeUser = context.getCurrentActiveUser();
		      					if (activeUser != null) {
		      						activeUser.setAccount(userAccount);
		      					}
		      					
		      					History.newItem("shop.register.confirm");

		      				} else {
		      					Window.alert("Authenticate failed.");
		      				}
		      		    }
		      		        
		      		    public void onFailure(Throwable caught) {
		      		       Window.alert("Error calling RPC register()");
		      		    }
	      			});
			}
		});
		
	}

}
