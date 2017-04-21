package com.kicon.ebiz.client.view;

import com.kicon.ebiz.client.controller.Controller;
import com.kicon.ebiz.client.context.Context;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasWidgets;

public interface View {
	
	public void setContext(Context context);
	public Context getContext();

	public Widget asWidget();
	
	public void setup();
	
	public String getTitle();

}
