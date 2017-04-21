package com.kicon.ebiz.client.module.admin;

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
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Button;

import com.kicon.ebiz.client.App;
import com.kicon.ebiz.client.controller.AbstractController;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.view.View;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.client.module.admin.TemplateTable;

public abstract class TemplateController<T> extends AbstractController {
	
	public interface Display extends AbstractController.Display {
		public Button getResizeButton();
		public Button getNewButton();
		public Button getDeleteButton();
		public Button getSubmitButton();
		public TemplateTable getTable();
		public SplitLayoutPanel getMainPanel();
	}
	
	public TemplateController(Context context) {
		super(context);
	}
	
	
}
