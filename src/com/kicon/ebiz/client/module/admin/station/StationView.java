package com.kicon.ebiz.client.module.admin.station;

import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.client.Event;

import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.client.module.admin.TemplateTable;
import com.kicon.ebiz.client.module.admin.item.ItemForm;
import com.kicon.ebiz.client.module.admin.station.StationView.MainTable;
import com.kicon.ebiz.client.ui.data.DataForm;
///import com.kicon.ebiz.ui.data.WOrderLinePanel;
///import com.kicon.ebiz.ui.data.WarehouseOrderTable;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.ui.DataGridResources;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Station;

import java.util.ArrayList;
import java.util.List;

public class StationView extends AbstractView implements StationController.Display {
	
	class MainTable extends TemplateTable<Station> {
		public TextColumn<Station> nameColumn;

		public MainTable(int pageSize, DataGrid.Resources resources) { 
			super(pageSize, resources); 
			sinkEvents(Event.ONDBLCLICK);
		}
		
		@Override
		public void onBrowserEvent2(Event event) {
			  super.onBrowserEvent2(event);
			  if (event.getTypeInt() == Event.ONDBLCLICK) {
				  if (!isSplitted) {
						contractView();
						isSplitted = true;
				  }
			  } 
		}
		
		@Override
		public void setupColumns() {		  
			setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

			nameColumn = new TextColumn<Station>() {
				@Override
				public String getValue(Station object) {
					return object.getName();
				}
			};
			addColumn(nameColumn, "Điểm bán");
			    
		}
	}
	
	
	class ResizeStationTable extends StationTable implements RequiresResize {
		public ResizeStationTable(int pageSize, DataGrid.Resources resources) { super(pageSize, resources); }
		public void onResize() {
			this.setSize("100%",  "100%");
		}
	}
	
	SplitLayoutPanel mainPanel;   
	TabLayoutPanel tabs;
	
    Image image, image2, image3;
    Button resizeButton, newButton, deleteButton;
    
    MainTable stationTable;
	ListDataProvider<Station> dataProvider;
    StationForm stationForm;
    
    SplitLayoutPanel topPan;
    SplitLayoutPanel vPan;
    
	boolean isSplitted = true;
    
    public StationView(Context context) {
    	super(context);
    	setup();
    	bind();
    }
    
    public void setup() {
    	mainPanel = new SplitLayoutPanel(1);
    	mainPanel.setSize("100%",  "100%");
    	initWidget(mainPanel);
    	tabs = new TabLayoutPanel(2.5, Unit.EM);
    	tabs.setAnimationDuration(1000);
    	
    	// ==========
    	
        image = new Image("gfx/arrowRight.png");
        resizeButton = new Button();
        resizeButton.getElement().appendChild(image.getElement());
        resizeButton.setSize("32px", "32px");
        
        image2 = new Image("gfx/plus.png");
        newButton = new Button();
        newButton.getElement().appendChild(image2.getElement());
        newButton.setSize("32px", "32px");
        
        image3 = new Image("gfx/cross.png");
        deleteButton = new Button();
        deleteButton.getElement().appendChild(image3.getElement());
        deleteButton.setSize("32px", "32px");
	    
        HorizontalPanel hPan = new HorizontalPanel();
        hPan.add(newButton);
        hPan.add(deleteButton);
        hPan.add(resizeButton);
	    topPan = new SplitLayoutPanel(0);
	    topPan.addWest(new TextBox(), 154);
	    topPan.addEast(hPan, 96);
	    topPan.setSize("100%", "100%");
	    
	    DataGridResources resources = GWT.create(DataGridResources.class);
	    
	    stationTable = new MainTable(20, resources);
	    stationTable.setSize("100%", "100%");
	    dataProvider = new ListDataProvider<Station>();
	    dataProvider.setList(new ArrayList<Station>());
	    
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);

	    dataProvider.addDataDisplay(stationTable);
	    pager.setDisplay(stationTable);
	    
	    vPan = new SplitLayoutPanel(0);
	    vPan.addNorth(topPan, 32);
	    vPan.addSouth(pager, 40);
	    vPan.add(stationTable);

	    vPan.setSize("100%",  "100%");
	    
	    // =======

	    stationForm = new StationForm(null);
	    tabs.add(stationForm, "Chi tiết");
	    tabs.add(new StationForm(null), "Bổ xung");
	    
	    // =======
    	mainPanel.addWest(vPan,  250);
    	mainPanel.add(tabs);
    }
    
    public void setup2() {
    	mainPanel = new SplitLayoutPanel(1);
    	mainPanel.setSize("100%",  "100%");
    	initWidget(mainPanel);
    	tabs = new TabLayoutPanel(2.5, Unit.EM);
    	tabs.setAnimationDuration(1000);
    	
    	// ==========
    	
        image = new Image("gfx/arrowRight.png");
        resizeButton = new Button();
        resizeButton.getElement().appendChild(image.getElement());
        resizeButton.setSize("32px", "32px");
        
	    topPan = new SplitLayoutPanel(0);
	    topPan.addWest(new TextBox(), 200);
	    topPan.addEast(resizeButton, 32);
	    topPan.setSize("100%", "100%");
	    
	    DataGridResources resources = GWT.create(DataGridResources.class);
	    
	    stationTable = new MainTable(20, resources);
	    stationTable.setSize("100%", "100%");
	    dataProvider = new ListDataProvider<Station>();
	    dataProvider.setList(new ArrayList<Station>());
	    
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);

	    dataProvider.addDataDisplay(stationTable);
	    pager.setDisplay(stationTable);
	    
	    vPan = new SplitLayoutPanel(0);
	    vPan.addNorth(topPan, 32);
	    vPan.addSouth(pager, 40);
	    vPan.add(stationTable);

	    vPan.setSize("100%",  "100%");
	    
	    // =======

	    stationForm = new StationForm(null);
	    tabs.add(stationForm, "Chi tiết");
	    tabs.add(new StationForm(null), "Bổ xung");
	    
	    // =======
    	mainPanel.addWest(vPan,  250);
    	mainPanel.add(tabs);

    }
    
    public void bind() {
  		resizeButton.addClickHandler(new ClickHandler() {
  	    	public void onClick(ClickEvent event) {
  	    		toggleSplit();
  	    	}
  	    });
  		
      }
      
      public void toggleSplit() {
  		if (isSplitted) {
  			expandView();
  			isSplitted = false;
  		
  		} else {
  			contractView();
  			isSplitted = true;
  		}
      }
      
      public void expandView() {
  		image.setUrl("gfx/arrowLeft.png");
  		topPan.setWidth("100%");
  		stationTable.clearTableWidth();
  		stationTable.setColumnWidth(stationTable.nameColumn, 250, Unit.PX);
  		stationTable.redraw();
  		
  		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
      }
      
      public void contractView() {
  		image.setUrl("gfx/arrowRight.png");
  		topPan.setWidth("250px");
  		stationTable.clearTableWidth();
  		stationTable.setColumnWidth(stationTable.nameColumn, 250, Unit.PX);
  		stationTable.redraw();

  		mainPanel.setWidgetSize(vPan, 250);
      }
     
    
    public void bind2() {
		resizeButton.addClickHandler(new ClickHandler() {
	    	
	    	//boolean isSplitted = true;
	    	public void onClick(ClickEvent event) {
	    		
	    		if (isSplitted) {
	    			image.setUrl("gfx/arrowLeft.png");
	    			topPan.setWidth("100%");
	    			stationTable.clearTableWidth();
		    		stationTable.redraw();
		    		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
		    		
	    			isSplitted = false;
	    		
	    		} else {
	    			image.setUrl("gfx/arrowRight.png");
	    			topPan.setWidth("250px");
	    			stationTable.clearTableWidth();
		    		stationTable.redraw();
		    		mainPanel.setWidgetSize(vPan, 250);

	    			isSplitted = true;
	    		}
	    	}
	    	
	    });
    }
   
 
	
    public void onResize() {
		//Window.alert("isSplitted = " + isSplitted + ", resize, width = " + Window.getClientWidth());
    	mainPanel.setSize("100%", "100%");
    	if (!isSplitted) {
    		//Window.alert("resize, width = " + Window.getClientWidth());
    		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
    		stationTable.setSize("100%", "100%");
    		
    	}
    	
    }
    
    public void setMainTable(MainTable stationTable) {
    	this.stationTable = stationTable;
    }
    
    public void setDataProvider(ListDataProvider<Station> dataProvider) {
    	this.dataProvider = dataProvider;
    }
    
    public Button getResizeButton() {
    	return resizeButton;
    }
    
    public Button getNewButton() {
    	return newButton;
    }
    
    public Button getDeleteButton() {
    	return deleteButton;
    }
    
    public Button getSubmitButton() {
    	return stationForm.getSubmitButton();
    }
    
    public StationForm getStationForm() {
    	return stationForm;
    }
    
    public TemplateTable getTable() {
    	return stationTable;
    }
    
    
    public ListDataProvider<Station> getDataProvider() {
    	return dataProvider;
    }
    
	public void setSelectedStation(Station station) {
		stationForm.setStation(station);
	}
	
	public void focusOnFirstTextBox() {
  	  	stationForm.getNameTextBox().setCursorPos(stationForm.getNameTextBox().getText().length());
	    stationForm.getNameTextBox().setFocus(true);
	}
	    
	public void setProvider(ListDataProvider<Station> dataProvider) {
		this.dataProvider = dataProvider;
	}
	    
	public SplitLayoutPanel getMainPanel() {
		return mainPanel;
	}
}

