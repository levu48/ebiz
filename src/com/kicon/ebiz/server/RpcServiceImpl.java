package com.kicon.ebiz.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.kicon.ebiz.client.RpcService;
import com.kicon.ebiz.model.ActiveUser;
import com.kicon.ebiz.model.LoginInfo;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.model.BusinessEntity;
import com.kicon.ebiz.model.Category;
import com.kicon.ebiz.model.CategoryElement;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Order;
import com.kicon.ebiz.model.OrderLine;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Station;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.Warehouse;
import com.kicon.ebiz.model.WarehouseItem;
import com.kicon.ebiz.model.WarehouseOrder;
import com.kicon.ebiz.util.DataService;

@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {
	//private List<Shop> shopList;
	
	
	public LoginInfo login(String requestUri) {
	    UserService userService = UserServiceFactory.getUserService();
	    com.google.appengine.api.users.User user = userService.getCurrentUser();
	    LoginInfo loginInfo = new LoginInfo();
	
	    if (user != null) {
	      loginInfo.setLoggedIn(true);
	      loginInfo.setEmailAddress(user.getEmail());
	      loginInfo.setNickname(user.getNickname());
	      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
	    } else {
	      loginInfo.setLoggedIn(false);
	      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
	    }
	    return loginInfo;
	}
	
	

	
	public List<Shop> createSampleShopList() {
		List<Shop> list = new ArrayList<Shop>();
		Shop shop1 = new Shop();
		//shop1.setId(new Long(1));
		shop1.setId("1");
		shop1.setName("Test Shop 1");
		list.add(shop1);
		
		Shop shop2 = new Shop();
		//shop2.setId(new Long(2));
		shop2.setId("2");
		shop2.setName("Test shop 2");
		list.add(shop2);
		return list;
	}

	public String greetServer(String input) {
		return "Hello, world";
	}
	
	public Account authenticate(String userName, String password) {		
		Account account = DataService.authenticate(userName, password);
		if (account == null) return null;
		return account;
	}
	
	public boolean authenticate2(String user, String password) {		
		if (user.equals("admin") && password.equals("admin")) {
			return true;
		}
		return false;
	}
	
	
	// ================
	// LOGIN & REGISTER
	// ================
	
	public static String getLoginEmail() {
	    UserService userService = UserServiceFactory.getUserService();
	    com.google.appengine.api.users.User user = userService.getCurrentUser();
	    if (user != null) {
	    	return user.getEmail();
	    }
	    return null;
	}
	
	public Account getAccountByEmail(String email) {
		return DataService.getAccountByEmail(email);
	}
	
	public ActiveUser getActiveUserByEmail(String email) {
		return DataService.getActiveUserByEmail(email);
	}
	
	public Account registerUser(LoginInfo loginInfo) {
		return DataService.registerUser(loginInfo);
	}
	
	// ================
	// SHOP
	// ================
	
	public List<Shop> getShopList() {
		List<Shop> shops = DataService.getShopList();
		return shops;
	}
	
	public List<Shop> getShopList(String accountName) {
		List<Shop> shops = DataService.getShopList(accountName);
		return shops;
	}
	
	public List<Shop> getShopList(Account account) {
		List<Shop> shops = DataService.getShopList(account);
		return shops;
	}
	
	
	public List<Shop> getManagedShopList(String email) {
		List<Shop> shops = DataService.getManagedShopList(email);
		return shops;
	}
	
	public List<Shop> getWorkerShopList(String email) {
		List<Shop> shops = DataService.getWorkerShopList(email);
		return shops;
	}
	
	public Shop getShop(String shopId) {
		return DataService.getShop(shopId);
	}
	
	public Shop getShop2(String shopId) {
		Shop shop1 = new Shop();
		//shop1.setId(new Long(1));
		shop1.setId("1");
		shop1.setName("Test Shop 1");
		return shop1;
	}
	
	public Shop updateShop(Shop shop) {
		return DataService.updateShop(shop);
	}
	
	public Shop createShop(Shop shop) {
		return DataService.createShop(shop);
	}
	
	public Boolean deleteShop(Shop shop) {
		return DataService.deleteShop(shop);
	}
	
	public void deleteShopAndRelated(Shop shop) {
		DataService.deleteShopAndRelated(shop);
	}
	
	public List<Shop> getManagerShopsByEmail(String email) {
		return DataService.getManagerShopsByEmail(email);
	}
	
	public List<Shop> getSellerShopsByEmail(String email) {
		return DataService.getSellerShopsByEmail(email);
	}
	
	// ================
	// ITEM
	// ================
	
	public List<Item> getItemList() {
		List<Item> itemList = DataService.getItemList();
		return itemList;
	}
	
	public List<Item> getItemList(Shop shop) {
		List<Item> itemList = DataService.getItemList(shop);
		return itemList;
	}
	
	public Item getItem(String itemId) {
		return DataService.getItem(itemId);
	}
	
	public Item updateItem(Item item) {
		return DataService.updateItem(item);
	}
	
	public Item createItem(Item item) {
		return DataService.createItem(item);
	}
	
	public Boolean deleteItem(Item item) {
		return DataService.deleteItem(item);
	}
	
	public int deleteAllItems(Shop shop) {
		return DataService.deleteAllItems(shop);
	}
	
	public List<Item> searchItem(Shop shop, String searchString) {
		return DataService.searchItem(shop, searchString);
	}
	
	public List<Item> searchItemByCategoryElement(Shop shop, CategoryElement element) {
		return DataService.searchItemByCategoryElement(shop, element);
	}
	
	// ================
	// STATION
	// ================
	
	public List<Station> getStationList() {
		List<Station> stationList = DataService.getStationList();
		return stationList;
	}
	
	public List<Station> getStationList(Shop shop) {
		List<Station> stationList = DataService.getStationList(shop);
		return stationList;
	}
	
	public Station getStation(String stationId) {
		return DataService.getStation(stationId);
	}
	
	public Station updateStation(Station station) {
		return DataService.updateStation(station);
	}
	
	public Station createStation(Station station) {
		return DataService.createStation(station);
	}
	
	public Boolean deleteStation(Station station) {
		return DataService.deleteStation(station);
	}
	
	public int deleteAllStations(Shop shop) {
		return DataService.deleteAllStations(shop);
	}
	
	public List<Station> searchStation(Shop shop, String searchString) {
		return DataService.searchStation(shop, searchString);
	}
	
	public List<Station> searchStationByCategoryElement(Shop shop, CategoryElement element) {
		return DataService.searchStationByCategoryElement(shop, element);
	}
	
	
	// ================
	// CATEGORY
	// ================	
	
	public Category createCategory(Category category) {
		return DataService.createCategory(category);
	}
	
	public Category updateCategory(Category category) {
		return DataService.updateCategory(category);
	}
	
	public CategoryElement updateCategoryElement(CategoryElement categoryElement) {
		return DataService.updateCategoryElement(categoryElement);
	}
	
	public List<Category> getCategoryList(Shop shop) {
		return DataService.getCategoryList(shop);
	}
	
	
	public List<CategoryElement> getCategoryElementList(Shop shop, String name) {
		return DataService.getCategoryElementList(shop, name);
	}
	
	public List<CategoryElement> getCategoryElementList(Category category) {
		return DataService.getCategoryElementList(category);
	}
	
	public List<CategoryElement> deleteCategoryElement(Category category, CategoryElement element) {
		return DataService.deleteCategoryElement(category, element);
	}
	
	public List<CategoryElement> addNewCategoryElement(Category category, CategoryElement element) {
		return DataService.addNewCategoryElement(category, element);
	}
	
	/*
	public Category addNewCategoryElement(Category category, CategoryElement element) {
		return DataService.addNewCategoryElement(category, element);
	}
	*/
	// ================
	// ORDER
	// ================
	
	public List<Order> getOrderList() {
		List<Order> orderList = DataService.getOrderList();
		return orderList;
	}
	
	public List<Order> getOrderList(Shop shop) {
		List<Order> orderList = DataService.getOrderList(shop);
		return orderList;
	}
	
	public Order getOrder(String orderId) {
		return DataService.getOrder(orderId);
	}
	
	public int deleteAllOrders(Shop shop) {
		return DataService.deleteAllOrders(shop);
	}
	
	public List<Order> searchOrders(Shop shop, Date frDate, Date toDate) {
		return DataService.searchOrders(shop, frDate, toDate);
	}
	
	
	public Order closeOrder(Order order) {
		return DataService.closeOrder(order);
	}
	
	public Order createNewOrder() {
		return DataService.createNewOrder();
	}
	
	public Order createNewOrder(Order order) {
		return DataService.createNewOrder(order);
	}
	
	public Order createOrUpdateOrder(Order order) {
		return DataService.createOrUpdateOrder(order);
	}
	
	public OrderLine createNewOrderLine(OrderLine orderLine) {
		return DataService.createNewOrderLine(orderLine);
	}

	// =================
	// WAREHOUSE
	// =================

	public List<Warehouse> getWarehouseList(Shop shop) {
		return DataService.getWarehouseList(shop);
	}
	
	
	public Warehouse createWarehouse(Warehouse warehouse) {
		return DataService.createWarehouse(warehouse);
	}

	public Warehouse updateWarehouse(Warehouse warehouse) {
		return DataService.updateWarehouse(warehouse);
	}
	
	public Boolean deleteWarehouse(Warehouse warehouse) {
		return DataService.deleteWarehouse(warehouse);
	}
	
	// =================
	// WAREHOUSE ITEM
	// =================
	
	public List<WarehouseItem> getWarehouseItemList(Warehouse warehouse) {
		return DataService.getWarehouseItemList(warehouse);
	}
	
	public WarehouseItem getWarehouseItem(Item item) {
		return DataService.getWarehouseItem(item);
	}
	
	public WarehouseItem createWarehouseItem(WarehouseItem warehouseItem) {
		return DataService.createWarehouseItem(warehouseItem);
	}

	public WarehouseItem updateWarehouseItem(WarehouseItem warehouseItem) {
		return DataService.updateWarehouseItem(warehouseItem);
	}	
	
	public Boolean deleteWarehouseItem(WarehouseItem warehouseItem) {
		return DataService.deleteWarehouseItem(warehouseItem);
	}
	
	public WarehouseOrder createWarehouseOrder(WarehouseOrder warehouseOrder) {
		return DataService.createWarehouseOrder(warehouseOrder);
	}
	
	public List<WarehouseOrder> getWarehouseOrderList(Warehouse warehouse) {
		return DataService.getWarehouseOrderList(warehouse);
	}
	
	// =================
	// BUSINESS ENTITY
	// =================
	
	public List<BusinessEntity> getBusinessEntityList(Shop shop) {
		return DataService.getBusinessEntityList(shop);
	}
	
	public BusinessEntity createBusinessEntity(BusinessEntity entity) {
		return DataService.createBusinessEntity(entity);
	}

	public BusinessEntity updateBusinessEntity(BusinessEntity entity) {
		return DataService.updateBusinessEntity(entity);
	}
	
	// =================
	// ACCOUNT
	// =================
	
	public List<Account> getAccountList(Shop shop) {
		List<Account> accountList = DataService.getAccountList(shop);
		return accountList;
	}
	
	// =================
	// USER
	// =================
	
	public List<User> getUserList(Shop shop) {
		return DataService.getUserList(shop);
	}
	
	public User updateUser(User user) {
		return DataService.updateUser(user);
	}
	
	public User createUser(User user) {
		return DataService.createUser(user);
	}
	
	public Boolean deleteUser(User user) {
		return DataService.deleteUser(user);
	}

}