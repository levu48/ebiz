package com.kicon.ebiz.client.module.admin;

import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.RequiresResize;
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
import com.google.gwt.user.client.Event;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

public class TemplateTable<T> extends DataGrid<T> implements RequiresResize { 
	  public TextColumn<T> primaryColumn;
	  private final List<T> DATA = Arrays.asList();

	  private SingleSelectionModel<T> selectionModel;

	  public TemplateTable() {
		  setup();
	  }
	  
	  public TemplateTable(int pageSize) {
		  super(pageSize);
		  setup();
	  }
	  
	  public TemplateTable(int pageSize, DataGrid.Resources resources) {
		  super(pageSize, resources);
		  setup();
	  }
	  

	  
	  public void setup() {		  
		  setupColumns();
		  setupSelectionModel();
	  }
	  
	  public void setupColumns() {}
	  
	  public TextColumn<T> getPrimaryColumn() {
		  return primaryColumn;
	  }
	  
	  public void setupSelectionModel() {
		    selectionModel = new SingleSelectionModel<T>();
		    setSelectionModel(selectionModel);
		    setRowCount(DATA.size(), true);
		    setRowData(0, DATA);
	  }
	  
	  public SingleSelectionModel<T> getSelectionModel() {
		  return selectionModel;
	  }
	  
	  public void onResize() {
		  this.setSize("100%",  "100%");
	  }
	  
	  
	  
}