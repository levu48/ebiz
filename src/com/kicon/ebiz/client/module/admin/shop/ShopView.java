package com.kicon.ebiz.client.module.admin.shop;

import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
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
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RequiresResize;

import com.kicon.ebiz.client.App;
import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.client.ui.data.DataForm;
///import com.kicon.ebiz.ui.data.WOrderLinePanel;
///import com.kicon.ebiz.ui.data.WarehouseOrderTable;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.ui.DataGridResources;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.client.module.admin.TemplateTable;

import java.util.ArrayList;
import java.util.List;

public class ShopView extends AbstractView implements ShopController.Display {
	
	class MainTable extends TemplateTable<Shop> {
		public TextColumn<Shop> nameColumn;
		public TextColumn<Shop> streetNoColumn;
		public TextColumn<Shop> roadColumn;
		public TextColumn<Shop> cityColumn;
		
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

			nameColumn = new TextColumn<Shop>() {
				@Override
				public String getValue(Shop object) {
					return object.getName();
				}
			};
			addColumn(nameColumn, "Cửa hàng");
			    
			streetNoColumn = new TextColumn<Shop>() {
				@Override
				public String getValue(Shop object) {
					return object.getStreetNo();
				}
			};
			    
			roadColumn = new TextColumn<Shop>() {
				@Override
				public String getValue(Shop object) {
					return object.getRoad();
				}
			};
			    
			cityColumn = new TextColumn<Shop>() {
			     @Override
			     public String getValue(Shop object) {
			    	 return object.getCity();
			     }
			 };
		}
	}
	
	
	
	SplitLayoutPanel mainPanel;   
	TabLayoutPanel tabs;
	
    Image image, image2, image3;
    Button resizeButton, newButton, deleteButton;
    
    MainTable shopTable;
	ListDataProvider<Shop> shopDataProvider;
    ShopForm shopForm;
    
    SplitLayoutPanel topPan;
    SplitLayoutPanel vPan;
    
	boolean isSplitted = true;
    
    public ShopView(Context context) {
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
	    
	    shopTable = new MainTable(20, resources);
	    shopTable.setSize("100%", "100%");
	    shopDataProvider = new ListDataProvider<Shop>();
	    shopDataProvider.setList(new ArrayList<Shop>());
	    
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);

	    shopDataProvider.addDataDisplay(shopTable);
	    pager.setDisplay(shopTable);
	    
	    vPan = new SplitLayoutPanel(0);
	    vPan.addNorth(topPan, 32);
	    vPan.addSouth(pager, 40);
	    vPan.add(shopTable);

	    vPan.setSize("100%",  "100%");
	    
	    // =======

	    shopForm = new ShopForm(null);
	    tabs.add(shopForm, "Chi tiết");
	    tabs.add(new ShopForm(null), "Tài khoản");
	    
	    // =======
    	mainPanel.addWest(vPan,  250);
    	mainPanel.add(tabs);
    }
    
    public void bind() {
		newButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		if (!isSplitted) {
					toggleSplit();
	    		}
	    	}
	    });
		
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
		shopTable.clearTableWidth();

		shopTable.insertColumn(1, shopTable.streetNoColumn, "Số nhà");
		shopTable.insertColumn(2, shopTable.roadColumn, "Đường");
		shopTable.insertColumn(3, shopTable.cityColumn, "Thành phố");
		
		shopTable.setColumnWidth(shopTable.nameColumn, 300, Unit.PX);
		shopTable.setColumnWidth(shopTable.streetNoColumn, 75, Unit.PX);
		shopTable.setColumnWidth(shopTable.roadColumn, 250, Unit.PX);
		shopTable.setColumnWidth(shopTable.cityColumn, 250, Unit.PX);
		shopTable.redraw();
		
		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
    }
    
    public void contractView() {
		image.setUrl("gfx/arrowRight.png");
		topPan.setWidth("250px");

		shopTable.removeColumn(3);
		shopTable.removeColumn(2);
		shopTable.removeColumn(1);
		shopTable.clearTableWidth();
		shopTable.setColumnWidth(shopTable.nameColumn, 250, Unit.PX);
		shopTable.redraw();

		mainPanel.setWidgetSize(vPan, 250);
    }
   
 
	
    public void onResize() {
    	mainPanel.setSize("100%", "100%");
    	if (!isSplitted) {
    		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
        	shopTable.setSize("100%", "100%");	
    	}
    }
    
    public void setMainTable(MainTable shopTable) {
    	this.shopTable = shopTable;
    }
    
    public void setDataProvider(ListDataProvider<Shop> shopDataProvider) {
    	this.shopDataProvider = shopDataProvider;
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
    	return shopForm.getSubmitButton();
    }
    
    public ShopForm getShopForm() {
    	return shopForm;
    }
    
    public TemplateTable getShopTable() {
    	return shopTable;
    }
    
    public ListDataProvider<Shop> getShopDataProvider() {
    	return shopDataProvider;
    }
    
	public void setSelectedShop(Shop shop) {
		/*
		if (shop == null) {
			subTitle.setText("");
		} else {
			String str = shop.getName() + ", " + shop.getRoad() + ", " + shop.getCity();
			subTitle.setText(str);
		}
		*/
		
		shopForm.setShop(shop);
		//shopForm.setVisible(true);
		/*
		stationForm.clearEditFields();
		stationForm.setVisible(false);

		itemForm.clearEditFields();
		itemForm.setVisible(false);
		
		orderForm.setVisible(false);

		userForm.clearEditFields();
		userForm.setVisible(false);
		
		categoryForm.getCategoryElementListBox().clear();
		categoryForm.clearEditFields();
		categoryForm.setVisible(false);
		warehouseForm.setVisible(false);
		*/
	}
	
	public void focusOnFirstTextBox() {
  	  	shopForm.getNameTextBox().setCursorPos(shopForm.getNameTextBox().getText().length());
	    shopForm.getNameTextBox().setFocus(true);
	}
	
    
   

}
