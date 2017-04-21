package com.kicon.ebiz.client.controller;

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
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.client.RpcServiceAsync;
import com.kicon.ebiz.client.event.SelectShopEvent;
import com.kicon.ebiz.client.event.SelectShopEventHandler;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.client.util.Constants;

import com.google.code.p.gwtchismes.client.GWTCWait;

public class FrontController extends AbstractController {
	
	private List<Shop> shopList;
	
	public interface Display {
		void setData(List<String> data);
		HasClickHandlers getList();
	    int getClickedRow(ClickEvent event);
	    Widget asWidget();
	}
	
	private final Display display;
	
	public FrontController(Context context, Display display) {
		super(context);
		this.display = display;
		bind();
	}
	
	public void go(final HasWidgets container) {
		this.container = container;
	    container.clear();
	    container.add(display.asWidget());
	    fetchDataFromServer();
	}
	
	public void bind() {
		display.getList().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);
		        if (selectedRow >= 0) {
		            //String shopId = shopList.get(selectedRow).getId().toString();
		            //context.getHandlerManager().fireEvent(new SelectShopEvent(shopId));
		        	Shop shop = shopList.get(selectedRow);
		            context.getHandlerManager().fireEvent(new SelectShopEvent(shop));
		        }
			}
		});
	}
	
	private void fetchDataFromServer() {
		ActiveUser activeUser = (ActiveUser) context.getMap().get(Constants.ActiveUser);
		if (activeUser == null) return;
		
		wait.setMessage("Vui lòng đợi trong lúc truy lục danh mục cửa hàng ...");
		wait.show(0);
		
		rpcService.getWorkerShopList(activeUser.getEmail(), new AsyncCallback<List<Shop>>() {
		
			public void onSuccess(List<Shop> result) {
				shopList = result;   
				//Window.alert("WorkerShopList " + shopList);
				if (shopList != null) {
					List<String> data = new ArrayList<String>();
					Iterator<Shop> i = shopList.iterator();
					while (i.hasNext()) {
						Shop shop = i.next();
						data.add(shop.getName());
					}
					display.setData(data);
				}
				wait.hide();
		    }
		        
		    public void onFailure(Throwable caught) {
		    	wait.hide();
		    	Window.alert("Error getting shop list.");
		    }
		});
	}

}
