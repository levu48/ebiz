package com.kicon.ebiz.client.module.admin;

import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
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
import com.google.gwt.user.client.ui.RequiresResize;

import com.kicon.ebiz.client.view.View;
import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.client.ui.data.DataForm;
///import com.kicon.ebiz.ui.data.WOrderLinePanel;
///import com.kicon.ebiz.ui.data.WarehouseOrderTable;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.ui.DataGridResources;
import com.kicon.ebiz.model.Shop;

import java.util.ArrayList;
import java.util.List;

public abstract class TemplateView<T> extends AbstractView implements TemplateController.Display {
	
	protected SplitLayoutPanel mainPanel;   
	protected TabLayoutPanel tabs;
	
	protected Image image, image2, image3;
	protected Button resizeButton, newButton, deleteButton;
    
	protected TemplateTable<T> mainTable;
	protected ListDataProvider<T> dataProvider;
	protected List<TemplateForm<T>> forms = new ArrayList<TemplateForm<T>>();
    
	protected SplitLayoutPanel topPan;
	protected SplitLayoutPanel vPan;
	
	protected DataGridResources resources;
	protected boolean isSplitted = true;
    
    public TemplateView(Context context) {
    	super(context);
    	setupStructure();
    	//bind();
    }
    
    public void setupStructure() {
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
	    
	    resources = GWT.create(DataGridResources.class);
	    
	    setupMainTable();
	    if (mainTable != null) {
	    	mainTable.setSize("100%", "100%");
	    }
	    
	    dataProvider = new ListDataProvider<T>();
	    dataProvider.setList(new ArrayList<T>());
	    
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);

	    dataProvider.addDataDisplay(mainTable);
	    pager.setDisplay(mainTable);
	    
	    vPan = new SplitLayoutPanel(0);
	    vPan.addNorth(topPan, 32);
	    vPan.addSouth(pager, 40);
	    vPan.add(mainTable);

	    vPan.setSize("100%",  "100%");
	    
	    // =======

		setupForms();
	    for (TemplateForm<T> form:forms) {
	    	tabs.add(form, form.getTitle());
	    }

    	mainPanel.addWest(vPan,  250);
    	mainPanel.add(tabs);
    }
    
    public void setupMainTable() {
    	mainTable = new TemplateTable<T>(20, resources);
    }
    
    public void setupForms() {
    	// to be filled in by subclasses.
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
 		mainTable.clearTableWidth();
 		mainTable.setColumnWidth(mainTable.getPrimaryColumn(), 200, Unit.PX);
 		
 		mainTable.redraw();
 		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
     }
     
     public void contractView() {
 		image.setUrl("gfx/arrowRight.png");
 		topPan.setWidth("250px");
 		
 		mainTable.clearTableWidth();
 		mainTable.setColumnWidth(mainTable.getPrimaryColumn(), 100, Unit.PX);
 		mainTable.redraw();

 		mainPanel.setWidgetSize(vPan, 250);
     }
 
     
     public SplitLayoutPanel setMainPanel() {
     	return mainPanel;
     }
     
    public void onResize() {
    	mainPanel.setSize("100%", "100%");
    	if (!isSplitted) {
    		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
        	mainTable.setSize("100%", "100%");
    	}
    }
    
    public void setMainTable(TemplateTable<T> mainTable) {
    	this.mainTable = mainTable;
    }
    
    public void setDataProvider(ListDataProvider<T> dataProvider) {
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
    	return null;
    }
    
    public TemplateForm<T> getForm() {
    	return null;
    }
    
    public TemplateTable<T> getTable() {
    	return mainTable;
    }
    
    public ListDataProvider<T> getDataProvider() {
    	return null;
    }
    
	public void setSelected(Object object) {}
   
	public void focusOnFirstTextBox() {
  	  	forms.get(0).getFirstTextBox().setCursorPos(forms.get(0).getFirstTextBox().getText().length());
  	  	forms.get(0).getFirstTextBox().setFocus(true);
	}
}
