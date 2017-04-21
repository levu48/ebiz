package com.kicon.ebiz.util;

import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.ActiveUser;
//import com.kicon.ebiz.model.UserRole;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Order;
import com.kicon.ebiz.model.OrderLine;
import com.kicon.ebiz.model.OrderStatus;
import com.kicon.ebiz.model.Station;
import com.kicon.ebiz.model.Category;
import com.kicon.ebiz.model.CategoryElement;
import com.kicon.ebiz.model.LoginInfo;
//import com.kicon.pos.config.Config;
import com.kicon.ebiz.model.Warehouse;
import com.kicon.ebiz.model.WarehouseItem;
import com.kicon.ebiz.model.WarehouseOrder;
import com.kicon.ebiz.model.WarehouseOrderLine;
import com.kicon.ebiz.model.BusinessEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;


public class DataService {
	//public static String PERSISTENCE_UNIT_NAME = Config.PERSISTENCE_UNIT_NAME;
	//public static String JDBC_DRIVER = Config.JDBC_DRIVER; 
	
	public static String PERSISTENCE_UNIT_NAME = "ebiz";
	public static String JDBC_DRIVER = "jdbc:google:rdbms://lv-ecom001:ecom001/ebiz";
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	
	// ================
	// CLEAN 
	// ================
	
	public static void clean() {
		String[] dbTables = new String[] {"ORDERLINE", "ORDERMODEL", "STATION", "ITEM", "SHOP", "ACCOUNT" }; // DOES NOT WORK due to foreign keys
		
		for (int i=0; i<dbTables.length; i++) {
			String tableName = dbTables[i];
		
			try {
				Connection c = null;
				c = DriverManager.getConnection(JDBC_DRIVER);
				ResultSet rs = c.createStatement().executeQuery("DROP TABLE " + tableName); 
			} catch (SQLException e) {}
		}
	}
	
	
	// ================
	// USERS
	// ================
	public static Account authenticate(String userName, String password) {	
		if (userName == null || password == null ) {
			return null;
		}

		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM ACCOUNT"
						+ " where name = '" + userName + "'"
						+ " and password = '" + password + "'"); 
	
			if (rs.next()) {
				Account account = new Account();
				account.setName(rs.getString("name"));
				account.setEmail(rs.getString("email"));
				account.setId(new String("id"));
				return account;
			}
		} catch (SQLException e) {}
		
		return null;
	}
	
	public static Account getAccountByEmail(String email) {
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT a FROM Account a WHERE a.email = :email");
		q.setParameter("email", email);
		List<Account> accountList = q.getResultList();	
		em.close();
		if (accountList == null || accountList.size() == 0) return null;
		return accountList.get(0);
	}
	
	public static ActiveUser getActiveUserByEmail(String email) {
		ActiveUser activeUser = new ActiveUser();
		activeUser.setEmail(email);
		
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT a FROM Account a WHERE a.email = :email");
		q.setParameter("email", email);
		List<Account> accountList = q.getResultList();		

		Query q2 = em.createQuery("SELECT a FROM User a WHERE a.email = :email");
		q2.setParameter("email", email);
		List<User> userList = q2.getResultList();	
		
		em.close();
		
		if (accountList != null && accountList.size() > 0) {
			activeUser.setAccount(accountList.get(0));
		}
		activeUser.setUserList(userList);
		return activeUser;
	}
	
	public static Account registerUser(LoginInfo loginInfo) {
		if (loginInfo == null) return null;
		EntityManager em = emf.createEntityManager();
		Account account = new Account();
		account.setEmail(loginInfo.getEmailAddress());
		account.setName(loginInfo.getNickname());
		account.setDisplayName(loginInfo.getNickname());
		account.setLocked(true);
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();
		em.close();
		return account;
	}
	

	public static List<Shop> getManagerShopsByEmail(String email) {
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT s FROM User a, Shop s WHERE a.email = :email"
					+ " and (a.role = :manager or a.role = :owner) and a.shop = s");
		
		q.setParameter("email", email);
		q.setParameter("manager", User.Role.MANAGER);
		q.setParameter("owner", User.Role.OWNER);
		
		List<Shop> list = q.getResultList();	
		em.close();
		if (list == null || list.size() == 0) return null;
		return list;
	}
	
	public static List<Shop> getSellerShopsByEmail(String email) {
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT s FROM User a, Shop s WHERE a.email = :email"
					+ " and (a.role = :worker or a.role = :manager or a.role = :owner) and a.shop = s");
					//+ " and a.shop = s");
		
		q.setParameter("email", email);
		q.setParameter("worker", User.Role.WORKER);
		q.setParameter("manager", User.Role.MANAGER);
		q.setParameter("owner", User.Role.OWNER);
		
		List<Shop> list = q.getResultList();	
		em.close();
		if (list == null || list.size() == 0) return null;
		return list;
	}
	
	// ================
	// SHOP
	// ================
	
	public static Shop getShop(String id) {
		EntityManager em = emf.createEntityManager();
		
		Shop dbShop = em.find(Shop.class, id);			
		em.close();
		return dbShop;
	}
	
	public static List<Shop> getShopList() {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s FROM Shop s order by s.name");
		List<Shop> shopList = q.getResultList();			
		em.close();
		return shopList;
	}
	
	public static List<Shop> getShopList(String accountEmail) {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s FROM Shop s WHERE s.createdBy = :email");
		q.setParameter("email", accountEmail);
		List<Shop> shopList = q.getResultList();			
		em.close();
		return shopList;
	}
	
	public static List<Shop> getManagedShopList(String email) {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s FROM User u, Shop s WHERE u.email = :email and u.shop = s" 
					+ " and (u.role = :owner or u.role = :manager)");
		q.setParameter("email", email);
		q.setParameter("owner", User.Role.OWNER);
		q.setParameter("manager", User.Role.MANAGER);
		List<Shop> shopList = q.getResultList();			
		em.close();
		return shopList;
	}
	
	public static List<Shop> getWorkerShopList(String email) {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s FROM User u, Shop s WHERE u.email = :email and u.shop = s" 
					+ " and (u.role = :owner or u.role = :manager or u.role = :worker)");
		q.setParameter("email", email);
		q.setParameter("owner", User.Role.OWNER);
		q.setParameter("manager", User.Role.MANAGER);
		q.setParameter("worker", User.Role.WORKER);
		List<Shop> shopList = q.getResultList();			
		em.close();
		return shopList;
	}
	
	public static List<Shop> getShopList(Account account) {
		if (account == null) return null;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s FROM Shop s WHERE s.createdBy = :email");
		q.setParameter("email", account.getEmail());
		List<Shop> shopList = q.getResultList();			
		em.close();
		return shopList;
	}
	
	public static Shop updateShop(Shop shop) {
		if (shop== null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Shop dbShop = em.find(Shop.class, shop.getId());
		em.getTransaction().begin();
		dbShop.copy(shop);
		em.getTransaction().commit();
			
		em.close();
		return dbShop;
	}
	
	public static Shop createShop(Shop shop) {
		if (shop == null) return null;
		User user = new User();
		user.setEmail(shop.getCreatedBy());
		user.setShop(shop);
		user.setRole(User.Role.OWNER);
		user.setCriticalInfoEditable(false);
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(shop);
		em.persist(user);
		em.getTransaction().commit();
			
		em.close();
		return shop;
	}
	
	public static Boolean deleteShop(Shop shop) {
		if (shop == null) return new Boolean(false);
		
		EntityManager em = emf.createEntityManager();
		
		Shop dbShop = em.find(Shop.class, shop.getId());
		em.getTransaction().begin();
		em.remove(dbShop);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	
	public static void deleteShopAndRelated(Shop shop) {
		deleteAllOrderLines(shop);
		deleteAllOrders(shop);
		deleteAllItems(shop);
		deleteAllStations(shop);
		deleteAllUsers(shop);
		deleteShop(shop);
	}
	

	
	// ================
	// ITEM
	// ================
	
	private static Item parseItem(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setId(rs.getString("id"));
		item.setName(rs.getString("name"));
		item.setBarcode(rs.getString("barcode"));
		item.setItemNo(rs.getString("itemNo"));
		item.setPrice(Double.parseDouble(rs.getString("price")));
		item.setDiscountPrice(Double.parseDouble(rs.getString("discountPrice")));
		item.setStockProduct(rs.getBoolean("stockProduct"));
		item.setQuantityInStock(Double.parseDouble(rs.getString("quantityInStock")));
		return item;
	}
	
	public static Item getItem(String id) {
		Item item = new Item();	
		if (id == null || id.length()==0 ) {
			return item;
		}

		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM ITEM where id=" + id); 
	
			if (rs.next()) {
				/*
				item.setName(rs.getString("name"));
				item.setPrice(Double.parseDouble(rs.getString("price")));
				item.setId(new String(id));
				*/
				item = parseItem(rs);
			}
		} catch (SQLException e) {}
		
		return item;
	}
	
	public static List<Item> getItemList() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM ITEM"); 
	
			while (rs.next()) {
				/*
				Item item = new Item();
				item.setName(rs.getString("name"));
				item.setPrice(Double.parseDouble(rs.getString("price")));
				item.setId(new String("" + rs.getInt("id")));
				*/
				Item item = parseItem(rs);
				itemList.add(item);
				
			}
		} catch (SQLException e) {
			return null;
		}
		return itemList;
	}
	
	public static List<Item> getItemList2(Shop shop) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM ITEM where shop=" + shop.getId()); 
	
			while (rs.next()) {
				/*
				Item item = new Item();
				item.setName(rs.getString("name"));
				item.setPrice(Double.parseDouble(rs.getString("price")));
				item.setId(new String("" + rs.getInt("id")));
				*/
				Item item = parseItem(rs);
				itemList.add(item);
			}
		} catch (SQLException e) {
			return null;
		}
		return itemList;
	}
	
	public static List<Item> getItemList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM Item o where o.shop = :shop order by o.name");
		query.setParameter("shop", shop);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		List<Item> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static Item updateItem(Item item) {
		if (item == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
			
		em.close();
		return item;
	}
	
	
	public static Item updateItem2(Item item) {
		if (item == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Item dbItem = em.find(Item.class, item.getId());
		em.getTransaction().begin();
		dbItem.copy(item);
		em.getTransaction().commit();
			
		em.close();
		return dbItem;
	}
	
	public static Item createItem(Item item) {
		if (item == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(item);
		em.getTransaction().commit();
			
		em.close();
		return item;
	}
	
	
	public static Boolean deleteItem(Item item) {
		if (item == null) return new Boolean(false);
		
		EntityManager em = emf.createEntityManager();
		
		
		Item dbItem = em.find(Item.class, item.getId());
		em.getTransaction().begin();
		em.remove(dbItem);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	
	public static int deleteAllItems(Shop shop) {
		if (shop == null) return 0;
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("DELETE FROM Item o WHERE o.shop = :shop");
		q.setParameter("shop", shop);
		int num = q.executeUpdate();
		em.getTransaction().commit();
		em.close();
		return num;
	}
	
	public static List<Item> searchItem(Shop shop, String searchString) {
		if (shop == null) return null;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT o FROM Item o WHERE o.shop = :shop and o.name like :searchString");
		q.setParameter("shop", shop);
		q.setParameter("searchString", "%" + searchString + "%");
		List<Item> itemList = q.getResultList();			
		em.close();
		return itemList;
	}
	
	public static List<Item> searchItemByCategoryElement(Shop shop, CategoryElement element) {
		if (shop == null) return null;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT o FROM Item o WHERE o.shop = :shop and o.categoryElement = :element");
		q.setParameter("shop", shop);
		q.setParameter("element", element);
		List<Item> itemList = q.getResultList();			
		em.close();
		return itemList;
	}
	
	// ================
	// STATION
	// ================
	
	public static Station getStation(String id) {
		Station station = new Station();	
		if (id == null || id.length()==0 ) {
			return station;
		}

		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM STATION where id=" + id); 
	
			if (rs.next()) {
				station.setName(rs.getString("name"));
				station.setId(new String(id));
			}
		} catch (SQLException e) {}
		
		return station;
	}
	
	public static List<Station> getStationList() {
		ArrayList<Station> stationList = new ArrayList<Station>();
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM STATION"); 
	
			while (rs.next()) {
				Station station = new Station();
				station.setName(rs.getString("name"));
				station.setId(new String("" + rs.getInt("id")));
				stationList.add(station);
			}
		} catch (SQLException e) {
			return null;
		}
		return stationList;
	}
	
	public static List<Station> getStationList2(Shop shop) {
		ArrayList<Station> stationList = new ArrayList<Station>();
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM STATION WHERE shop=" + shop.getId()); 
	
			while (rs.next()) {
				Station station = new Station();
				station.setName(rs.getString("name"));
				station.setId(new String("" + rs.getInt("id")));
				stationList.add(station);
			}
		} catch (SQLException e) {
			return null;
		}
		return stationList;
	}
	
	public static List<Station> getStationList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM Station o where o.shop = :shop order by o.name");
		query.setParameter("shop", shop);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		List<Station> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static Station updateStation(Station station) {
		if (station == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.merge(station);
		em.getTransaction().commit();
			
		em.close();
		return station;
	}
	
	public static Station updateStation2(Station station) {
		if (station == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Station dbStation = em.find(Station.class, station.getId());
		em.getTransaction().begin();
		dbStation.copy(station);
		em.getTransaction().commit();
			
		em.close();
		return dbStation;
	}
	
	public static Station createStation(Station station) {
		if (station == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(station);
		em.getTransaction().commit();
			
		em.close();
		return station;
	}
	
	public static Boolean deleteStation(Station station) {
		if (station == null) return new Boolean(false);
		
		EntityManager em = emf.createEntityManager();
		
		Station dbStation = em.find(Station.class, station.getId());
		em.getTransaction().begin();
		em.remove(dbStation);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	
	public static int deleteAllStations(Shop shop) {
		if (shop == null) return 0;
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("DELETE FROM Station o WHERE o.shop = :shopId");
		q.setParameter("shopId", shop);
		int num = q.executeUpdate();
		em.getTransaction().commit();
		em.close();
		return num;
	}
	
	public static List<Station> searchStation(Shop shop, String searchString) {
		if (shop == null) return null;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT o FROM Station o WHERE o.shop = :shop and o.name like :searchString");
		q.setParameter("shop", shop);
		q.setParameter("searchString", "%" + searchString + "%");
		List<Station> stationList = q.getResultList();			
		em.close();
		return stationList;
	}
	
	public static List<Station> searchStationByCategoryElement(Shop shop, CategoryElement element) {
		if (shop == null) return null;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT o FROM Station o WHERE o.shop = :shop and o.categoryElement = :element");
		q.setParameter("shop", shop);
		q.setParameter("element", element);
		List<Station> stationList = q.getResultList();			
		em.close();
		return stationList;
	}
	

	// ================
	// CATEGORY
	// ================
	
	public static List<Category> getCategoryList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM Category o where o.shop = :shop order by o.name");
		query.setParameter("shop", shop);
		List<Category> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	
	public static List<CategoryElement> getCategoryElementList(Shop shop, String name) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT e FROM Category o, CategoryElement e where o.shop = :shop"
				+ " and o.name = :name and e.category = o order by e.name");
		query.setParameter("shop", shop);
		query.setParameter("name", name);
		List<CategoryElement> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static List<CategoryElement> getCategoryElementList(Category category) {
		if (category == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM CategoryElement o where o.category = :category"
				+ " order by o.name");
		query.setParameter("category", category);
		List<CategoryElement> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	
	public static Category createCategory(Category category) {
		if (category == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(category);
		em.getTransaction().commit();
			
		em.close();
		return category;
	}
	
	public static Category updateCategory(Category category) {
		if (category == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Category dbCategory = em.find(Category.class, category.getId());
		em.getTransaction().begin();
		dbCategory.copy(category);
		em.getTransaction().commit();
			
		em.close();
		return dbCategory;
	}
	
	public static CategoryElement updateCategoryElement(CategoryElement categoryElement) {
		if (categoryElement == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		//CategoryElement dbCategoryElement = em.find(CategoryElement.class, categoryElement.getId());
		em.getTransaction().begin();
		//dbCategoryElement.copy(categoryElement);
		em.merge(categoryElement);
		em.getTransaction().commit();
			
		em.close();
		//return dbCategoryElement;
		return categoryElement;
	}
	
	public static List<CategoryElement> deleteCategoryElement(Category category, CategoryElement element) {
		if (category == null || element == null) return null;
		
		EntityManager em = emf.createEntityManager();
		CategoryElement dbElement = em.find(CategoryElement.class, element.getId());

		em.getTransaction().begin();
			em.remove(dbElement);
			em.merge(category);
		em.getTransaction().commit();
		
		Query query = em.createQuery("SELECT o FROM CategoryElement o where o.category = :category"
				+ " order by o.name");
		query.setParameter("category", category);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		List<CategoryElement> list = query.getResultList();
		em.close();
		
		return list;
	}
	
	public static Boolean deleteCategoryElement3(Category category, CategoryElement element) {
		if (element == null) return new Boolean(false);
		
		EntityManager em = emf.createEntityManager();
		CategoryElement dbElement = em.find(CategoryElement.class, element.getId());

		em.getTransaction().begin();
			category.removeElement(element);
			em.remove(dbElement);
			em.merge(category);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	
	public static Boolean deleteCategoryElement2(CategoryElement element) {
		if (element == null) return new Boolean(false);
		
		Category category = element.getCategory();
		//category.removeElement(element);
		
		EntityManager em = emf.createEntityManager();
		
		//CategoryElement dbElement = em.find(CategoryElement.class, element.getId());

		em.getTransaction().begin();
			category.removeElement(element);
			//em.remove(dbElement);
			em.merge(category);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	
	public static List<CategoryElement> addNewCategoryElement(Category category, CategoryElement element) {
		if (category == null || element == null) return null;
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(element);
		em.merge(category);
		em.getTransaction().commit();
		
		
		Query query = em.createQuery("SELECT o FROM CategoryElement o where o.category = :category"
				+ " order by o.name");
		query.setParameter("category", category);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		List<CategoryElement> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static Category addNewCategoryElement2(Category category, CategoryElement element) {
		if (category == null || element == null) return null;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
			//category.addElement(element);
			//em.persist(element);
			em.merge(category);
		em.getTransaction().commit();
		em.close();

		//emf.getCache().evictAll(); // NOT EXIStED in EMF, what version is it?
		return category;
	}
	
	
	// ================
	// ORDER
	// ================
	
	public static Order getOrder(String id) {
		Order order = new Order();	
		if (id == null || id.length()==0 ) {
			return order;
		}

		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM ORDERMODEL where id=" + id); 
	
			if (rs.next()) {
				order.setInformalName(rs.getString("informalName"));
				order.setDateCreated(rs.getDate("dateCreated"));
				
				String status = rs.getString("status");
				if ("Completed".equals(status)) {
					order.setStatus(OrderStatus.Completed);
					
				} else if ("Accepted".equals(status)) {
					order.setStatus(OrderStatus.Accepted);
					
				} else if ("Processing".equals(status)) {
					order.setStatus(OrderStatus.Processing);
				
				} else {
					order.setStatus(OrderStatus.New);
				}
				
				order.setId(new String(id));
			}
		} catch (SQLException e) {}
		
		return order;
	}
	
	public static List<Order> getOrderList() {
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM ORDERMODEL"); 
	
			while (rs.next()) {
				Order order = new Order();
				
				order.setInformalName(rs.getString("informalName"));
				order.setDateCreated(rs.getDate("dateCreated"));
				
				String status = rs.getString("status");
				if ("Completed".equals(status)) {
					order.setStatus(OrderStatus.Completed);
					
				} else if ("Accepted".equals(status)) {
					order.setStatus(OrderStatus.Accepted);
					
				} else if ("Processing".equals(status)) {
					order.setStatus(OrderStatus.Processing);
				
				} else {
					order.setStatus(OrderStatus.New);
				}
				
				order.setId(rs.getString("id"));
				
				orderList.add(order);
			}
		} catch (SQLException e) {
			return null;
		}
		return orderList;
	}
	
	
	public static List<Order> getOrderList(Shop shop) {
			if (shop == null) return null;
			
			EntityManager em = emf.createEntityManager();
			
			Query query = em.createQuery("SELECT o FROM Order o where o.shop= :shop order by o.dateCreated");
			query.setParameter("shop", shop);
			List<Order> list = query.getResultList();
				
			em.close();
			return list;
	}
	
	public static List<Order> searchOrders(Shop shop, Date frDate, Date toDate) {
		if (shop == null) return null;	
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM Order o where o.shop= :shop"
				//+ " and o.dateCreated between :startDate and :endDate"
				+ " and o.dateCreated >= :startDate and o.dateCreated <= :endDate"
				+ " order by o.dateCreated desc"
				);
		query.setParameter("shop", shop);
		query.setParameter("startDate", frDate, TemporalType.TIMESTAMP);
		query.setParameter("endDate", toDate, TemporalType.TIMESTAMP);
		List<Order> list = query.getResultList();
			
		em.close();
		return list;
}
	
	public static List<Order> getOrderList2(Shop shop) {
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("SELECT * FROM ORDERMODEL where shop=" + shop.getId()); 
	
			while (rs.next()) {
				Order order = new Order();
				
				order.setInformalName(rs.getString("informalName"));
				//order.setDateCreated(rs.getDate("dateCreated"));
				
				String status = rs.getString("status");
				if ("Completed".equals(status)) {
					order.setStatus(OrderStatus.Completed);
					
				} else if ("Accepted".equals(status)) {
					order.setStatus(OrderStatus.Accepted);
					
				} else if ("Processing".equals(status)) {
					order.setStatus(OrderStatus.Processing);
				
				} else {
					order.setStatus(OrderStatus.New);
				}
				
				order.setId(rs.getString("id"));
				
				orderList.add(order);
			}
		} catch (SQLException e) {
			return null;
		}
		return orderList;
	}
	
	public static int deleteAllOrders2(Shop shop) {
		if (shop == null) return 0;
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			ResultSet rs = c.createStatement().executeQuery("DELETE * FROM ORDERMODEL where shop =" + shop.getId());
			c.commit();
			c.close();
		} catch (SQLException e) {}
		
		return -1;  //TEMP
	}
	
	public static int deleteAllOrders(Shop shop) {
		if (shop == null) return 0;
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("DELETE FROM Order o WHERE o.shop = :shop");
		q.setParameter("shop", shop);
		int num = q.executeUpdate();
		em.getTransaction().commit();
		em.close();
		return num;
	}
	
	public static void deleteAllOrderLines3(Shop shop) {
		if (shop == null) return ;
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT o FROM Order o WHERE o.shop = :shop");
		q.setParameter("shop", shop);
		List<Order> list = q.getResultList();
		Iterator i = list.iterator();
	
		try {
			Connection c = null;
			c = DriverManager.getConnection(JDBC_DRIVER);
			while (i.hasNext()) {
				Order order = (Order) i.next();
				ResultSet rs = c.createStatement().executeQuery("DELETE * FROM ORDERLINE where orderId=" + order.getId());
			}
			c.commit();
			c.close();
		} catch (SQLException e) {}
		
		em.getTransaction().commit();
		em.close();
	}
	
	public static void deleteAllOrderLines(Shop shop) {
		if (shop == null) return ;
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT o FROM Order o WHERE o.shop = :shop");
		q.setParameter("shop", shop);
		List<Order> list = q.getResultList();
		for (Order order:list) {
			List<OrderLine> orderlines = order.getOrderLines();
			for (OrderLine ol:orderlines) {
				//ol.setItem(null);
				em.remove(ol);
			}
		}
		em.getTransaction().commit();
		em.close();
	}
	
	
	// ================
	// WAREHOUSE
	// ================
	
	public static List<Warehouse> getWarehouseList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM Warehouse o where o.shop = :shop order by o.name");
		query.setParameter("shop", shop);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		List<Warehouse> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static Warehouse createWarehouse(Warehouse warehouse) {
		if (warehouse == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(warehouse);
		em.getTransaction().commit();
			
		em.close();
		return warehouse;
	}
	
	public static Warehouse updateWarehouse(Warehouse warehouse) {
		if (warehouse == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.merge(warehouse);
		em.getTransaction().commit();
			
		em.close();
		return warehouse;
	}
	
	public static Boolean deleteWarehouse(Warehouse warehouse) {
		if (warehouse == null) return new Boolean(false);
		
		EntityManager em = emf.createEntityManager();
		
		Warehouse dbWarehouse = em.find(Warehouse.class, warehouse.getId());
		em.getTransaction().begin();
		em.remove(dbWarehouse);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	
	// ================
	// WAREHOUSE ITEM
	// ================
	
	public static List<WarehouseItem> getWarehouseItemList(Warehouse warehouse) {
		if (warehouse == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM WarehouseItem o where o.warehouse = :warehouse");
		query.setParameter("warehouse", warehouse);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		List<WarehouseItem> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static WarehouseItem getWarehouseItem(Item item) {
		if (item == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM WarehouseItem o where o.item = :item");
		query.setParameter("item", item);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		WarehouseItem warehouseItem = (WarehouseItem) query.getSingleResult();
			
		em.close();
		return warehouseItem;
	}
	
	
	public static WarehouseItem createWarehouseItem(WarehouseItem warehouseItem) {
		if (warehouseItem == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(warehouseItem);
		em.getTransaction().commit();
			
		em.close();
		return warehouseItem;
	}
	
	
	public static WarehouseItem updateWarehouseItem(WarehouseItem warehouseItem) {
		if (warehouseItem == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.merge(warehouseItem);
		em.getTransaction().commit();
			
		em.close();
		return warehouseItem;
	}
	
	public static Boolean deleteWarehouseItem(WarehouseItem warehouseItem) {
		if (warehouseItem == null) return new Boolean(false);
		
		EntityManager em = emf.createEntityManager();
		
		WarehouseItem dbWarehouseItem = em.find(WarehouseItem.class, warehouseItem.getId());
		em.getTransaction().begin();
		em.remove(dbWarehouseItem);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	


	// ================
	// WAREHOUSE ORDERS
	// ================
	
	public static List<WarehouseOrder> getWarehouseOrderList(Warehouse warehouse) {
		if (warehouse == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM WarehouseOrder o where o.warehouse = :warehouse order by o.dateCreated");
		query.setParameter("warehouse", warehouse);
		List<WarehouseOrder> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static WarehouseOrder createWarehouseOrder(WarehouseOrder warehouseOrder) {
		if (warehouseOrder == null) return null;
		
		createWarehouseItemsFromOrder(warehouseOrder);
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(warehouseOrder);
		em.getTransaction().commit();
			
		em.close();
		return warehouseOrder;
	}
	
	private static void createWarehouseItemsFromOrder(WarehouseOrder warehouseOrder) {
		if (warehouseOrder == null) return;
		
		EntityManager em = emf.createEntityManager();
		
		Warehouse warehouse = warehouseOrder.getWarehouse();
		
		List<WarehouseOrderLine> lines = warehouseOrder.getWarehouseOrderLines();
		for(WarehouseOrderLine line: lines) {
			Item item = line.getItem();
			WarehouseItem wItem = getWarehouseItemByItem(warehouse, item);
			if (wItem == null) {
				wItem = new WarehouseItem();
				wItem.setWarehouse(warehouse);
				wItem.setItem(item);
			
				if (warehouseOrder.getOrderType() == WarehouseOrder.OrderType.RECEIVING) {
					wItem.setQuantity(line.getQuantity());
				} else if (warehouseOrder.getOrderType() == WarehouseOrder.OrderType.SHIPPING) {
					wItem.setQuantity(0 - line.getQuantity());
				}
				
				em.getTransaction().begin();
				em.persist(wItem);
				em.getTransaction().commit();
			
			} else {
				double val = wItem.getQuantity();
				if (warehouseOrder.getOrderType() == WarehouseOrder.OrderType.RECEIVING) {
					wItem.setQuantity(val + line.getQuantity());
				} else if (warehouseOrder.getOrderType() == WarehouseOrder.OrderType.SHIPPING) {
					wItem.setQuantity(val - line.getQuantity());
				}
				em.getTransaction().begin();
				em.merge(wItem);
				em.getTransaction().commit();
			}
		}
		em.close();
	}
	
	private static WarehouseItem getWarehouseItemByItem(Warehouse warehouse, Item item) {
		if (warehouse == null || item == null) return null;
		WarehouseItem warehouseItem = null;
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("SELECT o FROM WarehouseItem o where o.item = :item and o.warehouse = :warehouse");
			query.setParameter("item", item);
			query.setParameter("warehouse", warehouse);
			warehouseItem = (WarehouseItem) query.getSingleResult();
				
			em.close();
		} catch (javax.persistence.NoResultException e) {
			warehouseItem = null;
		}
		return warehouseItem;
	}
	
	
	// ================
	// BUSINESS ENTITY
	// ================
	
	public static List<BusinessEntity> getBusinessEntityList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM BusinessEntity o where o.shop = :shop");
		query.setParameter("shop", shop);
		List<BusinessEntity> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static BusinessEntity createBusinessEntity(BusinessEntity entity) {
		if (entity == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
			
		em.close();
		return entity;
	}
	
	public static BusinessEntity updateBusinessEntity(BusinessEntity entity) {
		if (entity == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		BusinessEntity dbObj = em.find(BusinessEntity.class, entity.getId());
		em.getTransaction().begin();
		dbObj.copy(entity);
		em.getTransaction().commit();
			
		em.close();
		return dbObj;
	}
	
	// ================
	// ACCOUNT
	// ================
	
	public static List<Account> getAccountList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM Account o order by o.name");
		//query.setParameter("shop", shop);
		List<Account> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	
	// ================
	// USER
	// ================
	
	public static List<User> getUserList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM User o where o.shop = :shop order by o.role");
		query.setParameter("shop", shop);
		List<User> list = query.getResultList();
			
		em.close();
		return list;
	}
	
	public static User createUser(User user) {
		if (user == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
			
		em.close();
		return user;
	}
	
	public static User updateUser(User user) {
		if (user == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		User dbUser = em.find(User.class, user.getId());
		em.getTransaction().begin();
		dbUser.copy(user);
		em.getTransaction().commit();
			
		em.close();
		return dbUser;
	}
	
	public static Boolean deleteUser(User user) {
		if (user == null) return new Boolean(false);
		
		EntityManager em = emf.createEntityManager();
		
		User dbUser = em.find(User.class, user.getId());
		em.getTransaction().begin();
		em.remove(dbUser);
		em.getTransaction().commit();
			
		em.close();
		return new Boolean(true);
	}
	
	public static int deleteAllUsers(Shop shop) {
		if (shop == null) return 0;
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("DELETE FROM User o WHERE o.shop = :shop");
		q.setParameter("shop", shop);
		int num = q.executeUpdate();
		em.getTransaction().commit();
		em.close();
		return num;
	}
	
	// ================
	// USER ROLE (deprecated)
	// ================
	/*
	public static List<UserRole> getRoleList(Shop shop) {
		if (shop == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT o FROM UserRole o where o.shop = :shop order by o.role");
		query.setParameter("shop", shop);
		List<UserRole> list = query.getResultList();
			
		em.close();
		return list;
}
	
	public static UserRole createRole(UserRole role) {
		if (role == null) return null;
		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit();
			
		em.close();
		return role;
	}
	
	public static UserRole updateRole(UserRole role) {
		if (role == null) return null;
		
		EntityManager em = emf.createEntityManager();
		
		UserRole dbRole = em.find(UserRole.class, role.getId());
		em.getTransaction().begin();
		dbRole.copy(role);
		em.getTransaction().commit();
			
		em.close();
		return dbRole;
	}
	*/
	
	// ================
	
	public static Order closeOrder(Order order) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		order.setStatus(OrderStatus.Completed);
		
		em.getTransaction().begin();

		em.persist(order);

		em.getTransaction().commit();
		em.close();
		
		return order;
	}
	
	
	public static Order createNewOrder() {
		Order order = new Order();
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		order.setStatus(OrderStatus.Processing);
		em.merge(order);
		em.getTransaction().commit();
			
		em.close();
		
		return order;
	}
	
	public static Order createNewOrder(Order order) {		
		return createOrUpdateOrder(order);
	}
	
	public static Order createOrUpdateOrder(Order order) {		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		order.setStatus(OrderStatus.Processing);
		em.merge(order);
		em.getTransaction().commit();
			
		em.close();
		
		return order;
	}
	
	public static OrderLine createNewOrderLine(OrderLine orderLine) {		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		em.merge(orderLine);
		em.getTransaction().commit();
			
		em.close();
		
		return orderLine;
	}
	
}
