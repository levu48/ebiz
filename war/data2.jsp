<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.kicon.ebiz.model.Shop" %>
<%@ page import="com.kicon.ebiz.model.Category" %>
<%@ page import="com.kicon.ebiz.model.Item" %>
<%@ page import="com.kicon.ebiz.model.Order" %>
<%@ page import="com.kicon.ebiz.model.Station" %>
<%@ page import="com.kicon.ebiz.model.Account" %>
<%@ page import="javax.persistence.Persistence" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.Query" %>
<%@ page import="java.util.List" %>
<%!
	public static String[] items = new String[] {
		"Chinh Phụ Ngâm Khúc", "Truyện Kiều", "Vân Đài Loại Ngữ",
		"Phủ Biên Tạp Lục", 
		"Binh Thư Yếu Lược", "Bình Ngô Đại Cáo", "Thơ Xuân Quỳnh", 
		"Hàn Mạc Tử", "Xuân Diệu", "Huy Cận", "Lưu Trọng Lư",
		"Truyện ngắn Quỳnh Dao", "Lịch sử Văn hóa Việt Nam", 
		"Alibaba và 40 tên cướp", "Ngìn lẻ một đêm", "Tấm Cám",
		"Thạch Sanh Lý Thông", "Ăn khế trả vàng", 
		"Tây Du Ký", "Tam Quốc Chí", "Thủy Hử"
		};

	public static String[] stations = new String[] {
		"Quầy chính", "Quầy tầng 1", "Quầy tầng 2"
		};
	
	public static String[] stationCategories = new String[] {
		"Tầng trệt", "Tầng 1", "Tầng 2"
		};
	
	public static String[] itemCategories = new String[] {
		"Văn học", "Lịch sử", "Nghiên cứu", "Truyện cỏ tích"
		};
	

	boolean createDB(String shopId) {
		String PERSISTENCE_UNIT_NAME = "ebiz";
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		
		Shop shop = em.find(Shop.class, shopId);
		
		em.getTransaction().begin();
		
		for (int i=0; i < items.length; i++) {	
			Item item = new Item(shop);
			item.setName(items[i]);
			item.setPrice(10000);
			
			em.persist(item);
		}
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		for (int i=0; i < stations.length; i++) {
			Station station = new Station(shop);
			station.setName(stations[i]);
			em.persist(station);
		}
		em.getTransaction().commit();
		
		/*
		em.getTransaction().begin();
		for (int i=0; i < itemCategories.length; i++) {
			Category cat = new Category(shop);
			cat.setType(Category.Type.ITEM);
			cat.setName(itemCategories[i]);
			
			shop.setItemCategory(cat);
			em.persist(cat);
		}
		em.merge(shop);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		for (int i=0; i < stationCategories.length; i++) {
			Category cat = new Category(shop);
			cat.setType(Category.Type.STATION);
			cat.setName(stationCategories[i]);
			
			shop.setStationCategory(cat);
			em.persist(cat);
		}
		em.merge(shop);
		em.getTransaction().commit();
		*/
		
		em.close();
		
		return true;
	}	
%>
<%

	String pId = request.getParameter("id");

	createDB(pId);
	
	response.sendRedirect("/admin.jsp");
%>
