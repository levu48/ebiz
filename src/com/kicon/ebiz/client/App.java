package com.kicon.ebiz.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.context.AppContext;


import java.net.*;


public class App implements EntryPoint {
	public static final RpcServiceAsync rpcService = GWT.create(RpcService.class);
    public static final HandlerManager handler = new HandlerManager(null);
    public static final Context context = new AppContext(null);
    
    public void onModuleLoad() {
		context.setRpcService(rpcService);
		context.setHandlerManager(handler);
		
		AppController appController = new AppController(context);
		appController.go(RootLayoutPanel.get());  	
    }
}