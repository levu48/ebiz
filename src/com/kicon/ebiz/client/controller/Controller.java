package com.kicon.ebiz.client.controller;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

import com.kicon.ebiz.client.RpcServiceAsync;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.view.View;

public interface Controller {
	
	public void bind();
	public void go(final HasWidgets container);
	
	
	public Controller getParent();
	public View getDisplay();
	public RpcServiceAsync getRpcService();
	public HandlerManager getHandler();
	public HasWidgets getContainer();
	
	public Context getContext();
	public void setContext(Context context);
}
