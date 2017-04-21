package com.kicon.ebiz.client.module.admin.station;

import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Station;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.Column;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

public class StationTable extends DataGrid<Station> {
	public TextColumn<Station> nameColumn;

	  private static final List<Station> DATA = Arrays.asList();

	  private SingleSelectionModel<Station> selectionModel;

	  public StationTable() {
		  setup();
	  }
	  
	  public StationTable(int pageSize) {
		  super(pageSize);
		  setup();
	  }
	  
	  public StationTable(int pageSize, DataGrid.Resources resources) {
		  super(pageSize, resources);
		  setup();
	  }
	  
	  
	  public void setup() {		  
	    setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

	    // Add a text column to show the name.
	    nameColumn = new TextColumn<Station>() {
	      @Override
	      public String getValue(Station object) {
	        return object.getName();
	      }
	    };
	    addColumn(nameColumn, "Điểm bán");
	    
	    
	    // Add a selection model to handle user selection.
	    selectionModel = new SingleSelectionModel<Station>();
	    setSelectionModel(selectionModel);

	    // Set the total row count. This isn't strictly necessary, but it affects
	    // paging calculations, so its good habit to keep the row count up to date.
	    setRowCount(DATA.size(), true);

	    // Push the data into the widget.
	    setRowData(0, DATA);

	    // Add it to the root panel.
	    //RootPanel.get().add(this);
	    //initWidget(this);
	  }
	  
	  public SingleSelectionModel<Station> getSelectionModel() {
		  return selectionModel;
	  }
	  
	  
}