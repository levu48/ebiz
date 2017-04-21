package com.kicon.ebiz.client.module.admin.item;

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
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.ui.DataGridResources;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.client.module.admin.TemplateTable;

import java.util.ArrayList;
import java.util.List;

public class ItemView extends AbstractView implements ItemController.Display {
	
	class MainTable extends TemplateTable<Item> {
		public TextColumn<Item> nameColumn;
		public TextColumn<Item> itemNoColumn;
		public TextColumn<Item> barcodeColumn;
		public TextColumn<Item> categoryColumn;
		public TextColumn<Item> contentColumn;
		public TextColumn<Item> costColumn;
		public TextColumn<Item> priceColumn;
		public TextColumn<Item> discountColumn;
		
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

			nameColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return object.getName();
				}
			};
			addColumn(nameColumn, "Sản phẩm");
			
			itemNoColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return "" + (object.getItemNo() == null ? "" : object.getItemNo());
				}
			};
			
			barcodeColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return "" + (object.getBarcode() == null ? "" : object.getBarcode());
				}
			};
			
			categoryColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return "" + (object.getCategoryElement() == null ? "" : object.getCategoryElement());
				}
			};
			
			contentColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return "" + (object.getContentElement() == null ? "" : object.getContentElement());
				}
			};
			
			costColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return "" + object.getCost();
				}
			};
			
			priceColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return "" + object.getPrice();
				}
			};
			
			discountColumn = new TextColumn<Item>() {
				@Override
				public String getValue(Item object) {
					return "" + object.getDiscountPrice();
				}
			};
			    
		}
	}
	
	
	
	SplitLayoutPanel mainPanel;   
	TabLayoutPanel tabs;
	
    Image image, image2, image3;
    Button resizeButton, newButton, deleteButton;
    
    MainTable itemTable;
	ListDataProvider<Item> dataProvider;
    ItemForm itemForm;
    
    SplitLayoutPanel topPan;
    SplitLayoutPanel vPan;
    
	boolean isSplitted = true;
    
    public ItemView(Context context) {
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
	    
	    itemTable = new MainTable(20, resources);
	    itemTable.setSize("100%", "100%");
	    dataProvider = new ListDataProvider<Item>();
	    dataProvider.setList(new ArrayList<Item>());
	    
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);

	    dataProvider.addDataDisplay(itemTable);
	    pager.setDisplay(itemTable);
	    
	    vPan = new SplitLayoutPanel(0);
	    vPan.addNorth(topPan, 32);
	    vPan.addSouth(pager, 40);
	    vPan.add(itemTable);

	    vPan.setSize("100%",  "100%");
	    
	    // =======

	    itemForm = new ItemForm(null);
	    tabs.add(itemForm, "Chi tiết");
	    tabs.add(new ItemForm(null), "Bổ xung");
	    tabs.add(new ItemForm(null), "Nhà cung cấp");
	    
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
		itemTable.clearTableWidth();
		
		itemTable.insertColumn(1, itemTable.itemNoColumn, "Mã số");
		itemTable.insertColumn(2, itemTable.barcodeColumn, "Mã vạch");
		itemTable.insertColumn(3, itemTable.categoryColumn, "Thể loại");
		itemTable.insertColumn(4, itemTable.contentColumn, "Hàm lượng");
		itemTable.insertColumn(5, itemTable.costColumn, "Giá vốn");
		itemTable.insertColumn(6, itemTable.priceColumn, "Giá bán");
		itemTable.insertColumn(7, itemTable.discountColumn, "Giá giảm");
		
		itemTable.setColumnWidth(itemTable.nameColumn, 250, Unit.PX);
		itemTable.setColumnWidth(itemTable.itemNoColumn,  75, Unit.PX);
		itemTable.setColumnWidth(itemTable.barcodeColumn,  75, Unit.PX);
		itemTable.setColumnWidth(itemTable.categoryColumn,  75, Unit.PX);
		itemTable.setColumnWidth(itemTable.contentColumn,  75, Unit.PX);
		itemTable.setColumnWidth(itemTable.costColumn,  75, Unit.PX);
		itemTable.setColumnWidth(itemTable.priceColumn,  75, Unit.PX);
		itemTable.setColumnWidth(itemTable.discountColumn,  75, Unit.PX);

		itemTable.redraw();
		
		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
    }
    
    public void contractView() {
		image.setUrl("gfx/arrowRight.png");
		topPan.setWidth("250px");
		
		itemTable.removeColumn(7);
		itemTable.removeColumn(6);
		itemTable.removeColumn(5);
		itemTable.removeColumn(4);
		itemTable.removeColumn(3);
		itemTable.removeColumn(2);
		itemTable.removeColumn(1);
		
		itemTable.clearTableWidth();
		itemTable.setColumnWidth(itemTable.nameColumn, 250, Unit.PX);
		itemTable.redraw();

		mainPanel.setWidgetSize(vPan, 250);
    }
   
 
	
    public void onResize() {
    	mainPanel.setSize("100%", "100%");
    	if (!isSplitted) {
    		mainPanel.setWidgetSize(vPan, Window.getClientWidth());
        	itemTable.setSize("100%", "100%");	
    	}
    }
    
    public void setMainTable(MainTable itemTable) {
    	this.itemTable = itemTable;
    }
    
    public void setDataProvider(ListDataProvider<Item> dataProvider) {
    	this.dataProvider = dataProvider;
    }
    
    public SplitLayoutPanel getMainPanel() {
    	return mainPanel;
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
    	return itemForm.getSubmitButton();
    }
    
    public ItemForm getItemForm() {
    	return itemForm;
    }
    
    public TemplateTable getTable() {
    	return itemTable;
    }
    
    public ListDataProvider<Item> getDataProvider() {
    	return dataProvider;
    }
    
	public void setSelectedItem(Item item) {		
		itemForm.setItem(item);
	}
	
	public void focusOnFirstTextBox() {
  	  	itemForm.getNameTextBox().setCursorPos(itemForm.getNameTextBox().getText().length());
	    itemForm.getNameTextBox().setFocus(true);
	}

}
