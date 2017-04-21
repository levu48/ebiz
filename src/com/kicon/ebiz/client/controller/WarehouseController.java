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
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.client.RpcServiceAsync;
import com.kicon.ebiz.client.event.SelectShopEvent;
import com.kicon.ebiz.client.event.SelectShopEventHandler;
import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.client.util.Constants;

import com.google.code.p.gwtchismes.client.GWTCWait;

public class WarehouseController extends AbstractController {
	
	private List<Shop> shopList;
	
	public interface Display {
		public Button getTestButton();
		public Widget asWidget();
	}
	
	private final Display display;
	
	public WarehouseController(Context context, Display display) {
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
		display.getTestButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("handler click test button thru WarehouseController");
			}
		});

	}


}
