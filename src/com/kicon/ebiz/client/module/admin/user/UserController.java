package com.kicon.ebiz.client.module.admin.user;

import java.util.List;

import com.google.code.p.gwtchismes.client.GWTCWait;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import com.kicon.ebiz.client.App;
import com.kicon.ebiz.client.controller.AbstractController;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.view.View;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.client.event.SelectShopEvent;
import com.kicon.ebiz.client.event.SelectShopEventHandler;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.client.module.admin.TemplateTable;
import com.kicon.ebiz.client.module.admin.TemplateController;
import com.kicon.ebiz.client.module.admin.item.ItemController.Display;

import com.google.gwt.user.client.ui.Button;

public class UserController extends TemplateController {
	
	public interface Display extends TemplateController.Display {
		/*
		public UserForm getForm();
		public void setSelected(User user);
		public ListDataProvider<User> getDataProvider();
		public void focusOnFirstTextBox();
		*/
	}
	
	Display display;
	SingleSelectionModel<User> selectionModel;

	public UserController(Context context, Display display) {
		super(context);
		this.display = display;
		selectionModel = display.getTable().getSelectionModel();
		bind();
	}

}
