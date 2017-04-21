package com.kicon.ebiz.client.module.admin;

import com.google.gwt.user.client.ui.TextBox;
import com.kicon.ebiz.client.view.AbstractView;

public class TemplateForm<T> extends AbstractView {
	
	protected String title = "untitled";
	
	public TemplateForm() {}
	
	public TemplateForm(String title) {
		super();
		setTitle(title);
	}
	
	public TextBox getFirstTextBox() {
		return null;
	}

}
