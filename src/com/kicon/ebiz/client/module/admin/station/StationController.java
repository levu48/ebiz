package com.kicon.ebiz.client.module.admin.station;

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
import com.kicon.ebiz.client.module.admin.TemplateController;
import com.kicon.ebiz.client.module.admin.item.ItemForm;
import com.kicon.ebiz.client.view.View;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Station;
import com.kicon.ebiz.model.ActiveUser;

import com.kicon.ebiz.model.ActiveUser;

import com.google.gwt.user.client.ui.Button;

public class StationController extends TemplateController {
	
	public interface Display extends TemplateController.Display {

		public StationForm getStationForm();
		public void setSelectedStation(Station station);
		public ListDataProvider<Station> getDataProvider();
		public void focusOnFirstTextBox();
	}
	
	Display display;
	SingleSelectionModel<Station> selectionModel;
	
	public StationController(Context context, Display display) {
		super(context);
		this.display = display;
		selectionModel = display.getTable().getSelectionModel();
		bind();
	}
	
	public void bind() {		
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
			        Station station = selectionModel.getSelectedObject();
			        if (station != null) {
			        	App.context.clearAdminCurrentStationState();
			        	App.context.setCurrentAdminStation(station);
			        	
			        	display.setSelectedStation(station);
			        }
		      }
		});
	    
	}
	
    ///===========
    /// FETCH DATA
	///===========
	
	private void fetchStations(Shop shop) {
		if (shop == null) return;
		
		final GWTCWait wait = new GWTCWait();
		wait.setMessage("Vui lòng đợi trong lúc tải thông tin ...");
		wait.show(0);
		
		rpcService.getStationList(shop, new AsyncCallback<List<Station>>() {
			public void onSuccess(List<Station> result) {
				List<Station> stationList = result; 
				display.getDataProvider().setList(stationList);
				display.getTable().setRowData(0, stationList);
				display.getTable().setRowCount(stationList.size(), true);
				wait.hide();
		    }
		    public void onFailure(Throwable caught) {
		    	wait.hide();
		        Window.alert("Error getting station list.");
		    }
		});
	}
	
	public void go() {
		  fetchStations(App.context.getCurrentAdminShop());
    	  display.setSelectedStation(null);
	}

}
