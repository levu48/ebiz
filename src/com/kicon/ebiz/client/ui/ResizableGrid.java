package com.kicon.ebiz.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RequiresResize;

public class ResizableGrid extends Grid implements RequiresResize {
	public Grid appTop;
	public Grid appBody;
	
	public ResizableGrid(int rows, int columns) {
		super(rows, columns);
	}
	public void setAppTop(Grid appTop) { this.appTop = appTop; }
	public void setAppBody(Grid appBody) { this.appBody = appBody; }
	
	public void onResize() {
		int width = Window.getClientWidth();
		int height = Window.getClientHeight();
		if (appTop != null) { appTop.setHeight(height + "px"); }
		if (appBody != null) { appBody.setHeight((height - 62) + "px"); }
		Window.alert("call onResize of ResizableGrid");
	}
}