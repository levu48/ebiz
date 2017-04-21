package com.kicon.ebiz.client.ui;

import com.google.gwt.user.cellview.client.DataGrid;

public interface DataGridResources extends DataGrid.Resources {

	  @Source({ DataGrid.Style.DEFAULT_CSS, "DataGrid.css" })
	  DataGrid.Style dataGridStyle();
};