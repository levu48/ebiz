package com.kicon.ebiz.client.context;

import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Station;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.CategoryElement;
import com.kicon.ebiz.client.App;
import com.kicon.ebiz.client.RpcServiceAsync;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.HashMap;
import java.util.List;

public interface Context {	
	public void setParentContext(Context parentContext);
	public Context getParentContext();
	
	public String getAppTitle();
	public String getAppSubTitle();
	public String getUserName();
	public String getUserEmail();
	
	public void clearAdminCurrentShopState();
	public ActiveUser getCurrentActiveUser();
	
	public Shop getCurrentAdminShop();
	public void setCurrentAdminShop(Shop shop);
	
	public void clearAdminCurrentStationState();
	public void setCurrentAdminStation(Station station);
	public Station getCurrentAdminStation();
	
	public void clearAdminCurrentItem();
	public void setCurrentAdminItem(Item item);
	public Item getCurrentAdminItem();
	
	public List<CategoryElement> getCurrentAdminCategoryElements(String categoryName);
	
	// ====
	
	public LoginInfo getLoginInfo();
	public void setLoginInfo(LoginInfo loginInfo);
	
	public boolean isTopLevel();
	public Context getTopLevel();
	
	public void setRpcService(RpcServiceAsync rpcService);
	public RpcServiceAsync getRpcService();
	
	public void setHandlerManager(HandlerManager handler);
	public HandlerManager getHandlerManager();
	public HashMap<String, Object> getMap();

}
