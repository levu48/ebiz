package com.kicon.ebiz.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.kicon.ebiz.model.Shop;

public class SelectShopEvent extends GwtEvent<SelectShopEventHandler> {
	
	Shop shop;
	public static Type<SelectShopEventHandler> TYPE = new Type<SelectShopEventHandler>();
	
	public SelectShopEvent(Shop shop) {
		this.shop = shop;
	}
	
	@Override
	public Type<SelectShopEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(SelectShopEventHandler handler) {
		handler.onSelectShop(this);
	}
	
	public Shop getShop() {
		return shop;
	}

}

