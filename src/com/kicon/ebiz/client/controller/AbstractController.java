package com.kicon.ebiz.client.controller;

import com.google.code.p.gwtchismes.client.GWTCWait;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.kicon.ebiz.client.RpcServiceAsync;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.view.View;

import com.google.code.p.gwtchismes.client.GWTCWait;

//variable "display" is not handled in the abstract class, but in the concrete subclasses.

public abstract class AbstractController implements Controller {
	public interface Display {
		public Widget asWidget();
	}

	protected Controller parent = null;
	protected Controller thisController = null;
	
	protected RpcServiceAsync rpcService = null;
	protected HandlerManager handler = null;
	protected HasWidgets container = null;
	
	protected Context context = null;
	
	protected GWTCWait wait = new GWTCWait();
	
	protected Display display;
	
	
	
	public AbstractController() {}
	
	public AbstractController(Context context) {
		this.context = context;
		Context top = context.getTopLevel();
		if (top != null) {
			this.rpcService = top.getRpcService();
			this.handler = top.getHandlerManager();
		}
		this.thisController =  this;
	}
	
	public View getDisplay() {
		return (View) display;
	}
	
	public void bind() {}
	
	public void go(final HasWidgets container) {}
	
	public Controller getParent() {
		return parent;
	}
	
	public RpcServiceAsync getRpcService() {
		return rpcService;
	}
	
	public HandlerManager getHandler() {
		return handler;
	}
	
	public HasWidgets getContainer() {
		return container;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	
}
