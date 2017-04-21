package com.kicon.ebiz.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

@RemoteServiceRelativePath("rpcService")
public interface RpcService extends RemoteService {
	
	LoginInfo login(String requestUri);
	ActiveUser getActiveUserByEmail(String email);
	Account registerUser(LoginInfo loginInfo);
	
	List<Shop> getWorkerShopList(String email);
	
	Shop createShop(Shop shop);
	Shop updateShop(Shop shop);
	void deleteShopAndRelated(Shop shop);
	
	List<Shop> getManagedShopList(String email);
	
	///
	
	String greetServer(String name);
	Account authenticate(String user, String password);
	Account getAccountByEmail(String email);
	
	List<Shop> getShopList();
	List<Shop> getShopList(String accountName);

	List<Shop> getShopList(Account account);
	Shop getShop(String id);

	Boolean deleteShop(Shop shop);
	
	List<Item> getItemList();
	List<Item> getItemList(Shop shop);
	Item getItem(String id);
	Item updateItem(Item item);
	Item createItem(Item item);
	Boolean deleteItem(Item item);
	int deleteAllItems(Shop shop);
	List<Item> searchItem(Shop shop, String searchString);
	List<Item> searchItemByCategoryElement(Shop shop, CategoryElement element);
	
	List<Category> getCategoryList(Shop shop);
	List<CategoryElement> getCategoryElementList(Shop shop, String name);
	List<CategoryElement> getCategoryElementList(Category category);
	Category createCategory(Category category);
	Category updateCategory(Category category);
	CategoryElement updateCategoryElement(CategoryElement categoryElement);
	List<CategoryElement> deleteCategoryElement(Category category, CategoryElement element);
	List<CategoryElement> addNewCategoryElement(Category category, CategoryElement element);
	
	List<Station> getStationList();
	List<Station> getStationList(Shop shop);
	Station getStation(String id);
	Station updateStation(Station station);
	Station createStation(Station station);
	Boolean deleteStation(Station station);
	int deleteAllStations(Shop shop);
	List<Station> searchStation(Shop shop, String searchString);
	List<Station> searchStationByCategoryElement(Shop shop, CategoryElement element);
	
	List<Order> getOrderList();
	List<Order> getOrderList(Shop shop);
	Order getOrder(String id);
	int deleteAllOrders(Shop shop);
	List<Order> searchOrders(Shop shop, Date frDate, Date toDate);
	
	Order closeOrder(Order order);
	Order createNewOrder();
	Order createNewOrder(Order order);
	Order createOrUpdateOrder(Order order);
	OrderLine createNewOrderLine(OrderLine orderLine);
	
	Warehouse createWarehouse(Warehouse warehouse);
	Warehouse updateWarehouse(Warehouse warehouse);
	List<Warehouse> getWarehouseList(Shop shop);
	Boolean deleteWarehouse(Warehouse warehouse);
	
	WarehouseItem createWarehouseItem(WarehouseItem warehouseItem);
	WarehouseItem updateWarehouseItem(WarehouseItem warehouseItem);
	List<WarehouseItem> getWarehouseItemList(Warehouse warehouse);
	Boolean deleteWarehouseItem(WarehouseItem warehouseItem);
	WarehouseItem getWarehouseItem(Item item);
	
	WarehouseOrder createWarehouseOrder(WarehouseOrder warehouseOrder);
	List<WarehouseOrder> getWarehouseOrderList(Warehouse warehouse);
	
	
	List<Account> getAccountList(Shop shop);

	List<User> getUserList(Shop shop);
	User updateUser(User user);
	User createUser(User user);
	Boolean deleteUser(User user);
	
	List<Shop> getManagerShopsByEmail(String email);
	List<Shop> getSellerShopsByEmail(String email);
	
	BusinessEntity updateBusinessEntity(BusinessEntity entity);
	BusinessEntity createBusinessEntity(BusinessEntity entity);
	List<BusinessEntity> getBusinessEntityList(Shop shop);

}
