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
		"Bánh tráng nướng giòn",
		"Bầu luộc chấm trứng",
		"Đậu hủ chiên giòn",
		"Đậu hủ chiên sả",
		"Đậu phộng rang muối",
		"Khổ qua luộc",
		"Rau luộc thập cẩm kho quẹt",
		"Rau muống xào tỏi",
		"Trứng cút luộc",
		"Bò chiên mắm nhĩ cuốn cả bẹ",
		"Bò lúc lắc",
		"Bò xào cải chua dưa leo",
		"Bò xào hành cần",
		"Cơm chiên hải sản",
		"Cơm gà",
		"Cơm sườn",
		"Cơm thêm",
		"Hủ tiếu mì bò kho",
		"Hủ tiếu mì gà",
		"Hủ tiếu mì hải sản",
		"Hủ tiếu mì xương",
		"Nui xương",
		"Ốp la bò kho",
		"Ốp la bánh mì",
		"Ốp la patê gan",
		"Bún thêm",
		"Cá diêu hồng thêm",
		"Cá kèo thêm",
		"Cháo",
		"Cơm chiên",
		"Đầu cá hồi thêm",
		"Lẩu cá diêu hồng",
		"Lẩu cá kèo",
		"Lẩu đầu cá hồi",
		"Lẩu hải sản",
		"Lẩu lươn",
		"Lẩu thái",
		"Lẩu tôm chua cay",
		"Lươn thêm",
		"Mì xào"
		};

	public static String[] stations = new String[] {
		"Nhà trên - Bàn 01", "Nhà trên - Bàn 02", "Nhà trên - Bàn 03", "Nhà trên - Bàn 04", "Nhà trên - Bàn 05",
		"Nhà trong - Bàn 06", "Nhà trong - Bàn 07", "Nhà trong - Bàn 08", "Nhà trong - Bàn 09", "Nhà trong - Bàn 10",
		"Nhà trong - Bàn 11", "Nhà trong - Bàn trong kẹt", 
		"Lầu 1 - Bàn gần cửa sổ", "Lầu 1 - Bàn ngoài sân",
		"Phòng VIP 1", "Phòng VIP 2", "Phòng VIP 3"
		};
	
	public static String[] stationCategories = new String[] {
		"Khu nhà trên", "Khu ngoài vườn", "Khu sân thượng"
		};
	
	public static String[] itemCategories = new String[] {
		"Khai vị", "Thức ăn", "Thức uống", "Tráng miệng"
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
