package com.kicon.ebiz.client.controller;

import com.kicon.ebiz.client.RpcServiceAsync;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.view.View;
import com.kicon.ebiz.client.view.GoogleLoginView;
import com.kicon.ebiz.model.LoginInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

public class GoogleLoginController extends AbstractController {
	
	public interface Display extends AbstractController.Display {
		public Label getTitleLabel();
		public Label getSubTitleLabel();
		public HTML getGoogleLoginHTML();
	}
	
	private Display display;
	
	public GoogleLoginController(Context context, Display display) {
		super(context);
		this.display = display;
		bind();
	}
	
	public void go(final HasWidgets container) {
		this.container = container;
	    container.clear();
	    container.add(display.asWidget());
	}
	
	public void bind() {}
}
