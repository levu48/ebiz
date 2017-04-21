package com.kicon.ebiz.client.module.admin.item;

import java.util.List;

import com.google.code.p.gwtchismes.client.GWTCWait;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import com.kicon.ebiz.client.App;
import com.kicon.ebiz.client.controller.AbstractController;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.view.View;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.client.event.SelectShopEvent;
import com.kicon.ebiz.client.event.SelectShopEventHandler;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.client.module.admin.TemplateTable;
import com.kicon.ebiz.client.module.admin.TemplateController;

import com.google.gwt.user.client.ui.Button;

public class ItemController extends TemplateController {
	
	public interface Display extends TemplateController.Display {
		public ItemForm getItemForm();
		public void setSelectedItem(Item item);
		public ListDataProvider<Item> getDataProvider();
		public void focusOnFirstTextBox();
	}
	
	Display display;
	SingleSelectionModel<Item> selectionModel;
	
	public ItemController(Context context, Display display) {
		super(context);
		this.display = display;
		selectionModel = display.getTable().getSelectionModel();
		bind();
	}
	
	public void bind() {	
		handler.addHandler(SelectShopEvent.TYPE,
		        new SelectShopEventHandler() {
			          public void onSelectShop(SelectShopEvent event) {
			        	  fetchItems(event.getShop());
			        	  //display.getTable().redraw();
			          }
		        });  
		
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
			        Item item = selectionModel.getSelectedObject();
			        if (item != null) {
			        	App.context.setCurrentAdminItem(item);
			        	display.setSelectedItem(item);
			        }
		      }
		}); 
	    
	    display.getNewButton().addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  selectionModel.setSelected(selectionModel.getSelectedObject(), false);
		    	  App.context.setCurrentAdminItem(null);
		    	  display.setSelectedItem(null);
		    	  display.focusOnFirstTextBox();
		      }
		});
	    
	    display.getDeleteButton().addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  Item item = App.context.getCurrentAdminItem();
		    	  if (!Window.confirm("Bạn có chắc muốn xóa sản phẩm \"" + item.getName() + "\" không?")) {
		    		  return;
		    	  }
		    	  
				  final GWTCWait wait = new GWTCWait();
				  wait.setMessage("Vui lòng đợi ...");
				  wait.show(0);
				  
		    	  rpcService.deleteItem(item, new AsyncCallback<Boolean>() {
		    		  public void onSuccess(Boolean result) {
		    			  App.context.setCurrentAdminItem(null);
		    			  selectionModel.setSelected(selectionModel.getSelectedObject(), false);
		    			  display.setSelectedItem(null);
		    			  fetchItems(App.context.getCurrentAdminShop());
		    			  wait.hide();
		    		  }
		    		  public void onFailure(Throwable caught) {
		    			  wait.hide();
		    			  Window.alert("Error delete item");
		    		  }
		    	  });
		    	  wait.hide();	
		      }
		});
	    
	    display.getSubmitButton().addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    		if (!display.getItemForm().checkEditing()) {
		    			return;
		    		}
		    		
		    	  Item item = display.getItemForm().getEditItem();
		    	  Item currentItem = App.context.getCurrentAdminItem(); 
		    	  
		    	  if (currentItem == null) {
		    		  Item newItem = new Item(App.context.getCurrentAdminShop());
		    		  newItem.copy(item);
		    		  rpcService.createItem(newItem, new AsyncCallback<Item>() {
		    			  public void onSuccess(Item result) {
		    				  display.getTable().redraw();
		    				  Window.alert("newItem succeeded: item id = " + result.getId());
		    				  fetchItems(App.context.getCurrentAdminShop());
		    		    	  display.setSelectedItem(null);
		    			  }
						  public void onFailure(Throwable caught) {
							  Window.alert("Error creating new item.");
						  }
		    		  });
		    		  
		    	  } else {
		    		  currentItem.copy(item);
		    		  
				      rpcService.updateItem(currentItem, new AsyncCallback<Item>() {
				    	  public void onSuccess(Item result) {
				    		  display.getTable().redraw();
				    		  Window.alert("updateItem succeeded: item id = " + result.getId());
				    	  }
						  public void onFailure(Throwable caught) {
							  Window.alert("Error updating item.");
						  }
				      });
		    	  }
		      }
		});
	}
	
	public void fetchItems(Shop shop) {
		if (shop == null) return;
		
		final GWTCWait wait = new GWTCWait();
		wait.setMessage("Vui lòng đợi trong lúc tải thông tin ...");
		wait.show(0);
		
		rpcService.getItemList(shop, new AsyncCallback<List<Item>>() {
			public void onSuccess(List<Item> result) {
				List<Item> itemList = result;   
				display.getDataProvider().setList(itemList);
				display.getDataProvider().refresh();
				display.getTable().setRowData(0, itemList);
				display.getTable().setRowCount(itemList.size(), true);
				

				//display.setItemPopupDataProvider(display.getItemDataProvider());  
				//display.setBusinessEntityPopupDataProvider(display.getBusinessEntityDataProvider());   

				wait.hide();
		    } 
		    public void onFailure(Throwable caught) {
		    	wait.hide();
		        Window.alert("Error getting item list.");
		    }
		});
	}
	
	public void go() {
		  fetchItems(App.context.getCurrentAdminShop());
    	  display.setSelectedItem(null);
	}
	
}