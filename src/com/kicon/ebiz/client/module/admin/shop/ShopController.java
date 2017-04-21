package com.kicon.ebiz.client.module.admin.shop;

import java.util.List;

import com.google.code.p.gwtchismes.client.GWTCWait;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
import com.kicon.ebiz.client.module.admin.item.ItemController;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.client.module.admin.TemplateTable;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.client.event.SelectShopEvent;
import com.kicon.ebiz.client.event.SelectShopEventHandler;



public class ShopController extends AbstractController {
	
	public interface Display extends AbstractController.Display {
		public Button getResizeButton();
		public Button getNewButton();
		public Button getDeleteButton();
		public Button getSubmitButton();
		public ShopForm getShopForm();
		public TemplateTable getShopTable();
		public void setSelectedShop(Shop shop);
		public ListDataProvider<Shop> getShopDataProvider();
		
		public void focusOnFirstTextBox();

	}
	
	Display display;
	SingleSelectionModel<Shop> shopSelectionModel;
	
	public ShopController(Context context, Display display) {
		super(context);
		this.display = display;
		shopSelectionModel = display.getShopTable().getSelectionModel();
		bind();
	}
	
	public void bind() {		
	    shopSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
			        Shop shop = shopSelectionModel.getSelectedObject();
			        if (shop != null) {
			        	App.context.clearAdminCurrentShopState();
			        	App.context.setCurrentAdminShop(shop);
			        	
			        	display.setSelectedShop(shop);
			        	
			            SelectShopEvent selectShopEvent = new SelectShopEvent(shop);
			            handler.fireEvent(selectShopEvent);
			            
			        	/*
			        	fetchItems();
			        	fetchStations();
			        	fetchOrders();
			        	fetchUsers();
			        	fetchCategories();
			        	fetchCategoryElements();
			        	fetchWarehouses();
			        	fetchBusinessEntities();
			        	*/
			        }
		      }
		});
	    
	    display.getSubmitButton().addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {

	    		if (!display.getShopForm().checkEditing()) {
	    			return;
	    		}
	    		
	    		Shop shop = display.getShopForm().getEditing();

	    		Shop adminShop = App.context.getCurrentAdminShop();
	    		
				final GWTCWait wait = new GWTCWait();
				wait.setMessage("Vui lòng đợi ...");
				wait.show(0);
	    		
		    	if (adminShop == null) {
		    		  //String email = AppController.getCurrentAccount().getEmail();
		    		  String email = App.context.getCurrentActiveUser().getEmail();
		    		  shop.setCreatedBy(email);
		    		  rpcService.createShop(shop, new AsyncCallback<Shop>() {
		    			  public void onSuccess(Shop result) {
		    				  display.getShopTable().redraw();
		    				  //Window.alert("newShop succeeded: shop id = " + result.getId());
		    				  fetchShops();
		    		    	  display.setSelectedShop(null);
		    		    	  wait.hide();
		    			  }
						  public void onFailure(Throwable caught) {
							  wait.hide();
							  Window.alert("Error creating new shop.");
						  }
		    		  });
		    		  
		    	  } else {
		    		  adminShop.copy(shop);
				      rpcService.updateShop(adminShop, new AsyncCallback<Shop>() {
				    	  public void onSuccess(Shop result) {
				    		  display.getShopTable().redraw();
				    		  //Window.alert("updateShop succeeded: shop id = " + result.getId());
				    		  wait.hide();
				    	  }
						  public void onFailure(Throwable caught) {
							  wait.hide();
							  Window.alert("Error updating shop.");
						  }
				      });
		    	  }
		    	wait.hide();
	    	}
	    });
	    
	    display.getNewButton().addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  shopSelectionModel.setSelected(shopSelectionModel.getSelectedObject(), false);
		    	  App.context.setCurrentAdminShop(null);
		    	  display.setSelectedShop(null);
		    	  display.focusOnFirstTextBox();
		      }
		});
	    
	    display.getDeleteButton().addClickHandler(new ClickHandler() {
	    	  

		      public void onClick(ClickEvent event) {
		    	  Shop shop = App.context.getCurrentAdminShop();
		    	  
		    	  if (!Window.confirm("Bạn có chắc muốn xóa cửa hàng \"" + shop.getName() + "\" không?")) {
		    		  return;
		    	  }
		    		  
		    	  
				  final GWTCWait wait = new GWTCWait();
				  wait.setMessage("Vui lòng đợi ...");
				  wait.show(0);
					
		    	  rpcService.deleteShopAndRelated(shop, new AsyncCallback<Void>() {
		    		  public void onSuccess(Void result) {
		    			  App.context.setCurrentAdminShop(null);
		    			  shopSelectionModel.setSelected(shopSelectionModel.getSelectedObject(), false);
		    			  display.setSelectedShop(null);
		    			  fetchShops();
		    			  wait.hide();
		    		  }
		    		  public void onFailure(Throwable caught) {
		    			  wait.hide();
		    			  Window.alert("Error delete shop");
		    		  }
		    	  });
		      }
		});
	}
	
	public void go() {
		fetchShops();
	}
	
    ///===========
    /// FETCH DATA
	///===========
    
	private void fetchShops() {
		ActiveUser activeUser = App.context.getCurrentActiveUser();
		if (activeUser == null) return;
		
		final GWTCWait wait = new GWTCWait();
		wait.setMessage("Vui lòng đợi trong lúc tải thông tin ...");
		wait.show(0);
		
		rpcService.getManagedShopList(activeUser.getEmail(), new AsyncCallback<List<Shop>>() {
		
			public void onSuccess(List<Shop> result) {
				List<Shop> shopList = result;   
				display.getShopDataProvider().setList(shopList);
				display.getShopTable().setRowData(0, shopList);
				display.getShopTable().setRowCount(shopList.size(), true);
				wait.hide();
		    }
		        
		    public void onFailure(Throwable caught) {
		    	wait.hide();
		       Window.alert("Error getting managed shop list.");
		    }
		});
	}
	


}
