package com.kicon.ebiz.client.context;

import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Station;
import com.kicon.ebiz.model.CategoryElement;
import com.kicon.ebiz.client.controller.Controller;
import com.kicon.ebiz.client.RpcServiceAsync;
import com.kicon.ebiz.client.util.Constants;

import com.google.gwt.event.shared.HandlerManager;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractContext implements Context {
	private Context parentContext;
	protected RpcServiceAsync rpcService;
	protected HandlerManager handler;
	final public HashMap<String, Object> map = new HashMap<String, Object>();
	
	public AbstractContext() {}
	
	public AbstractContext(Context parentContext) {
		this.parentContext = parentContext;
	}
	
	public void setParentContext(Context parentContext) {
		this.parentContext = parentContext;
	}
	
	public Context getParentContext() {
		return parentContext;
	}
	
	public void setRpcService(RpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public RpcServiceAsync getRpcService() {
		return rpcService;
	}
	
	public void setHandlerManager(HandlerManager handler) {
		this.handler = handler;
	}
	
	public HandlerManager getHandlerManager() {
		return handler;
	}
	
	public String getAppTitle() { return null; }
	public String getAppSubTitle() { return null; }
	public String getUserName() { return null; }
	public String getUserEmail() { return null; }
	
	public LoginInfo getLoginInfo() { return null; }
	public void setLoginInfo(LoginInfo loginInfo) {}
	
	public boolean isTopLevel() {
		if (parentContext == null) return true;
		return false;
	}
	
	public Context getTopLevel() {
		Context cur = this;
		while (cur != null && !cur.isTopLevel()) {
			cur = cur.getParentContext();
		}
		return cur;
	}
	
	public HashMap<String, Object> getMap() {
		return map;
	}
	
	/// Map management
	
	public void clearAdminCurrentShopState() {
		map.put(Constants.AdminShop, null);
		//...
	}
	
	public ActiveUser getCurrentActiveUser() {
		return (ActiveUser) map.get(Constants.ActiveUser);
	}
	
	public Shop getCurrentAdminShop() {
		return (Shop) map.get(Constants.AdminShop);
	}
	
	public void setCurrentAdminShop(Shop shop) {
		map.put(Constants.AdminShop, shop);
	}
	
	
	
	public void clearAdminCurrentStationState() {
		map.put(Constants.AdminStation, null);
	}
	
	public void setCurrentAdminStation(Station station) {
		map.put(Constants.AdminStation, station);
	}
	
	public Station getCurrentAdminStation() {
		return (Station) map.get(Constants.AdminStation);
	}
	
	
	public void clearAdminCurrentItem() {
		map.put(Constants.AdminItem, null);
	}
	
	public void setCurrentAdminItem(Item item) {
		map.put(Constants.AdminItem, item);
	}
	
	public Item getCurrentAdminItem() {
		return (Item) map.get(Constants.AdminItem);
	}
	
	
	public List<CategoryElement> getCurrentAdminCategoryElements(String categoryName) {
		return (List<CategoryElement>) map.get(Constants.AdminItemCategoryElements);
	}
}
