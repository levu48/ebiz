package com.kicon.ebiz.client.view;

import com.kicon.ebiz.client.context.Context;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractView extends ResizeComposite implements View {
	final public static String STYLE  = "kicon-form-Label";
	protected String title;
	
	protected Context context;
	
	public AbstractView() {}
	
	public AbstractView(Context context) {
		setContext(context);
	}
	
	// ==== set & get ====
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	/// ===== resize, widget ====
	
	public void setup() {}
	
	public Widget asWidget() {
		return this;
	}
	
	public void onResize() {}

}
