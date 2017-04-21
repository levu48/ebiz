package com.kicon.ebiz.client;

import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.BusinessEntity;
import com.kicon.ebiz.model.Category;
import com.kicon.ebiz.model.CategoryElement;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Order;
import com.kicon.ebiz.model.OrderLine;
import com.kicon.ebiz.model.Station;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.Warehouse;
import com.kicon.ebiz.model.WarehouseItem;
import com.kicon.ebiz.model.WarehouseOrder;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;
import java.util.List;

public interface RpcServiceAsync {
	
	void login(String requestUri, AsyncCallback<LoginInfo> callback);
	void getActiveUserByEmail(String email, AsyncCallback<ActiveUser> callback);
	void registerUser(LoginInfo loginInfo, AsyncCallback<Account> callback);
	
	void getWorkerShopList(String email, AsyncCallback<List<Shop>> callback);
	
	void createShop(Shop shop, AsyncCallback<Shop> callback);
	void updateShop(Shop shop, AsyncCallback<Shop> callback);
	void deleteShopAndRelated(Shop shop, AsyncCallback<Void> callback);
	
	void getManagedShopList(String email, AsyncCallback<List<Shop>> callback);
	
	////
	
	void greetServer(String input, AsyncCallback<String> callback);
	void authenticate(String userName, String password, AsyncCallback<Account> callback);
	void getAccountByEmail(String email, AsyncCallback<Account> callback);
	
	void getShopList(AsyncCallback<List<Shop>> callback);
	void getShopList(String accountName, AsyncCallback<List<Shop>> callback);

	void getShopList(Account account, AsyncCallback<List<Shop>> callback);
	void getShop(String id, AsyncCallback<Shop> callback);

	void deleteShop(Shop shop, AsyncCallback<Boolean> callback);
	
	void getItemList(AsyncCallback<List<Item>> callback);
	void getItemList(Shop shop, AsyncCallback<List<Item>> callback);
	void getItem(String id, AsyncCallback<Item> callback);
	void updateItem(Item item, AsyncCallback<Item> callback);
	void createItem(Item item, AsyncCallback<Item> callback);
	void deleteItem(Item item, AsyncCallback<Boolean> callback);
	void deleteAllItems(Shop shop, AsyncCallback<Integer> callback);
	void searchItem(Shop shop, String searchString, AsyncCallback<List<Item>> callback);
	void searchItemByCategoryElement(Shop shop, CategoryElement element, AsyncCallback<List<Item>> callback);
	
	void getCategoryList(Shop shop, AsyncCallback<List<Category>> callback);
	void getCategoryElementList(Shop shop, String name, AsyncCallback<List<CategoryElement>> callback);
	void getCategoryElementList(Category category, AsyncCallback<List<CategoryElement>> callback);
	void createCategory(Category category, AsyncCallback<Category> callback);
	void updateCategory(Category category, AsyncCallback<Category> callback);
	void updateCategoryElement(CategoryElement categoryElement, AsyncCallback<CategoryElement> callback);
	//void deleteCategoryElement(Category category, CategoryElement element, AsyncCallback<Boolean> callback);
	void deleteCategoryElement(Category category, CategoryElement element, AsyncCallback<List<CategoryElement>> callback);
	//void addNewCategoryElement(Category category, CategoryElement element, AsyncCallback<Category> callback);
	void addNewCategoryElement(Category category, CategoryElement element, AsyncCallback<List<CategoryElement>> callback);
	
	void getStationList(AsyncCallback<List<Station>> callback);
	void getStationList(Shop shop, AsyncCallback<List<Station>> callback);
	void getStation(String id, AsyncCallback<Station> callback);
	void updateStation(Station station, AsyncCallback<Station> callback);
	void createStation(Station station, AsyncCallback<Station> callback);
	void deleteStation(Station station, AsyncCallback<Boolean> callback);
	void deleteAllStations(Shop shop, AsyncCallback<Integer> callback);
	void searchStation(Shop shop, String searchString, AsyncCallback<List<Station>> callback);
	void searchStationByCategoryElement(Shop shop, CategoryElement element, AsyncCallback<List<Station>> callback);

	void getOrderList(AsyncCallback<List<Order>> callback);
	void getOrderList(Shop shop, AsyncCallback<List<Order>> callback);
	void getOrder(String id, AsyncCallback<Order> callback);
	void deleteAllOrders(Shop shop, AsyncCallback<Integer> callback);
	void searchOrders(Shop shop, Date frDate, Date toDate, AsyncCallback<List<Order>> callback);
	
	void closeOrder(Order order, AsyncCallback<Order> callback);
	void createNewOrder(AsyncCallback<Order> callback);
	void createNewOrder(Order order, AsyncCallback<Order> callback);
	void createOrUpdateOrder(Order order, AsyncCallback<Order> callback);
	void createNewOrderLine(OrderLine orderLine, AsyncCallback<OrderLine> callback);
	
	void createWarehouse(Warehouse warehouse, AsyncCallback<Warehouse> callback);
	void updateWarehouse(Warehouse warehouse, AsyncCallback<Warehouse> callback);
	void getWarehouseList(Shop shop, AsyncCallback<List<Warehouse>> callback);
	void deleteWarehouse(Warehouse warehouse, AsyncCallback<Boolean> callback);
	
	void createWarehouseItem(WarehouseItem warehouseItem, AsyncCallback<WarehouseItem> callback);
	void updateWarehouseItem(WarehouseItem warehouseItem, AsyncCallback<WarehouseItem> callback);
	void getWarehouseItemList(Warehouse warehouse, AsyncCallback<List<WarehouseItem>> callback);	
	void deleteWarehouseItem(WarehouseItem warehouseItem, AsyncCallback<Boolean> callback);
	void getWarehouseItem(Item item, AsyncCallback<WarehouseItem> callback);
	
	void createWarehouseOrder(WarehouseOrder warehouseOrder, AsyncCallback<WarehouseOrder> callback);
	void getWarehouseOrderList(Warehouse warehouse, AsyncCallback<List<WarehouseOrder>> callback);
	
	void getAccountList(Shop shop, AsyncCallback<List<Account>> callback);
	
	void getUserList(Shop shop, AsyncCallback<List<User>> callback);
	void updateUser(User user, AsyncCallback<User> callback);
	void createUser(User user, AsyncCallback<User> callback);
	void deleteUser(User user, AsyncCallback<Boolean> callback);
	
	void getManagerShopsByEmail(String email, AsyncCallback<List<Shop>> callback);
	void getSellerShopsByEmail(String email, AsyncCallback<List<Shop>> callback);
	
	void updateBusinessEntity(BusinessEntity entity, AsyncCallback<BusinessEntity> callback);
	void createBusinessEntity(BusinessEntity entity, AsyncCallback<BusinessEntity> callback);
	void getBusinessEntityList(Shop shop, AsyncCallback<List<BusinessEntity>> callback);


}
