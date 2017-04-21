package com.kicon.ebiz.client.view;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;

import com.kicon.ebiz.client.App;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.client.controller.AppController;
import com.kicon.ebiz.client.controller.AdminController;
import com.kicon.ebiz.client.controller.Controller;
import com.kicon.ebiz.client.controller.WarehouseController;
import com.kicon.ebiz.client.module.admin.shop.ShopController;
import com.kicon.ebiz.client.module.admin.shop.ShopView;
import com.kicon.ebiz.client.module.admin.station.StationController;
import com.kicon.ebiz.client.module.admin.station.StationView;

import com.kicon.ebiz.client.module.admin.item.ItemController;
import com.kicon.ebiz.client.module.admin.item.ItemView;

import com.kicon.ebiz.client.module.admin.user.UserController;
import com.kicon.ebiz.client.module.admin.user.UserView;

import com.kicon.ebiz.client.view.GoogleLoginView.ResizeGrid;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Category;
import com.kicon.ebiz.model.CategoryElement;
import com.kicon.ebiz.model.BusinessEntity;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.model.ActiveUser;
//import com.kicon.ebiz.model.UserRole;
import com.kicon.ebiz.model.Account;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.Station;
import com.kicon.ebiz.model.Order;
import com.kicon.ebiz.model.Warehouse;
import com.kicon.ebiz.model.WarehouseItem;
import com.kicon.ebiz.model.WarehouseOrder;
import com.kicon.ebiz.model.WarehouseOrderLine;
//import com.kicon.ebiz.model.Report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.Command;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Event;

/*
import com.kicon.ebiz.ui.data.ShopTable;
import com.kicon.ebiz.ui.data.ItemTable;
import com.kicon.ebiz.ui.data.OrderTable;
import com.kicon.ebiz.ui.data.StationTable;
import com.kicon.ebiz.ui.data.UserTable;
import com.kicon.ebiz.ui.data.BusinessEntityTable;
import com.kicon.ebiz.ui.data.CategoryTable;
import com.kicon.ebiz.ui.data.WarehouseTable;
import com.kicon.ebiz.ui.data.WarehouseItemTable;
import com.kicon.ebiz.ui.data.WarehouseOrderLineTable;
import com.kicon.ebiz.ui.data.WarehouseOrderTable;
import com.kicon.ebiz.ui.data.ReportTable;
import com.kicon.ebiz.ui.data.WOrderLinePanel;

import com.kicon.ebiz.ui.data.ShopForm;
import com.kicon.ebiz.ui.data.ItemForm;
import com.kicon.ebiz.ui.data.StationForm;
import com.kicon.ebiz.ui.data.OrderForm;
import com.kicon.ebiz.ui.data.OrderSummaryForm;
import com.kicon.ebiz.ui.data.UserForm;
import com.kicon.ebiz.ui.data.BusinessEntityForm;
import com.kicon.ebiz.ui.data.CategoryForm;
import com.kicon.ebiz.ui.data.WarehouseForm;
import com.kicon.ebiz.ui.data.WarehouseItemForm;
import com.kicon.ebiz.ui.data.WarehouseReceivingForm;
import com.kicon.ebiz.ui.data.WarehouseShippingForm;

import com.kicon.ebiz.ui.data.DataForm;
import com.kicon.ebiz.ui.DataGridResources;
import com.kicon.ebiz.config.Config;
*/

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.view.client.ListDataProvider;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.i18n.client.DateTimeFormat;
//import com.google.gwt.i18n.shared.DateTimeFormat;
//import com.google.gwt.user.datepicker.client.CalendarUtil;

public class AdminView extends AbstractView implements AdminController.Display {
	
	public class ResizeGrid extends Grid implements RequiresResize {
		public ResizeGrid(int rows, int columns) { super(rows, columns); }
		public void onResize() {}
	}
	
	private ResizeGrid appPanel;
	private Panel appTop;
	private Panel appBody;
	
	private Label topTitle;
	private Label subTitle;

	private TabLayoutPanel tabPanel;
	
	private ShopView shopView;
	private ShopController shopController;
	
	private StationView stationView;
	private StationController stationController;
	
	private ItemView itemView;
	private ItemController itemController;
	
	private UserView userView;
	private UserController userController;

	public AdminView(Context context) {
		super(context);
		setup();
		bind();
	}
	
	public void setup() {
		appPanel = new ResizeGrid(2,1);
		initWidget(appPanel);
		
		appTop = setupAppTop();
		appBody = setupAppBody();

		appPanel.setWidget(0, 0, appTop);
		appPanel.setWidget(1, 0, appBody);

	    appPanel.setCellPadding(0);
	    appPanel.setCellSpacing(0);
	    
	    appPanel.getCellFormatter().setStyleName(0, 0, "app-appTop");
	    appPanel.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    appPanel.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
	    
	    appPanel.getCellFormatter().setHeight(1, 0, "100%");
	    //appPanel.getCellFormatter().setStyleName(1, 0, "app-loginPanel");
	    appPanel.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    appPanel.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
	    
	    appPanel.setWidth("100%");
		appPanel.setHeight("100%");
	    appPanel.setStyleName("app-appPanel");
	}
	
	public Panel setupAppTop() {		
		Grid pan = new Grid(1,2);
		Shop shop = context.getCurrentAdminShop();
		ActiveUser activeUser = context.getCurrentActiveUser();
		
		Label topTitle = new Label(context == null ? "topTitle" : context.getAppTitle());
		topTitle.setStyleName("app-topTitle");
		
		subTitle = new Label("");
		subTitle.setStyleName("app-subTitle");
		
		AbsolutePanel titlePan = new AbsolutePanel();
		titlePan.setSize("400px", "60px");
		
		titlePan.add(topTitle, 0, 0);
		titlePan.add(subTitle, 0, 32);
		
		HorizontalPanel sysMenuPan = new HorizontalPanel();
		sysMenuPan.setSpacing(5);
		
		sysMenuPan.add(new Image("gfx/user.png"));
		sysMenuPan.add(new Hyperlink(activeUser.getEmail(), "login.account"));
		
		sysMenuPan.add(new Image("gfx/shop.png"));
		sysMenuPan.add(new Hyperlink("trang chủ", "shop.list"));
		sysMenuPan.add(new Image("gfx/help.png"));
		sysMenuPan.add(new Hyperlink("trợ giúp", "shop.help"));
		sysMenuPan.add(new Image("gfx/signout.png"));
		sysMenuPan.add(new Anchor("thoát", context.getLoginInfo().getLogoutUrl()));
		
		sysMenuPan.setWidth("500px");
		sysMenuPan.setStyleName("pos-shop-sysMenu");

		pan.setWidget(0, 0, titlePan);	
		pan.setWidget(0,  1, sysMenuPan);
		
		pan.getCellFormatter().setWidth(0, 0, "100%");
		pan.getCellFormatter().setHorizontalAlignment(0,  1, HasHorizontalAlignment.ALIGN_RIGHT);
		pan.getCellFormatter().setVerticalAlignment(0,  1, HasVerticalAlignment.ALIGN_TOP);
		pan.getCellFormatter().setStyleName(0, 1, "app-shop-sysMenu");
		
		return pan;
	}
	
	public Panel setupAppBody() {
	    HorizontalPanel pan = new HorizontalPanel();
		
		tabPanel = setupTopLevelTabs();	
		//tabPanel.setWidth("100%");
		//tabPanel.setHeight("100%");
		tabPanel.setSize("100%",  "100%");

		pan.add(tabPanel);
					
		//pan.setStyleName("pop-admin-body");
		pan.setWidth("100%");
		pan.setHeight("100%");
		return pan;
	}
	
	public TabLayoutPanel setupTopLevelTabs() {
		TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
	    tabPanel.setAnimationDuration(1000);
	    
		shopView = new ShopView(context);
		shopController = new ShopController(context, shopView);
	    tabPanel.add(shopView, "Cửa hàng");
	    
		stationView = new StationView(context);
		stationController = new StationController(context, stationView);
	    tabPanel.add(stationView, "Điểm bán");
	    
		itemView = new ItemView(context);
		itemController = new ItemController(context, itemView);
		tabPanel.add(itemView, "Sản phẩm");
		
		tabPanel.add(new AbsolutePanel(), "Đơn hàng");
		
		userView = new UserView(context);
		userController = new UserController(context, userView);
		tabPanel.add(userView, "Nhân sự");
		
		tabPanel.add(new AbsolutePanel(), "Thể loại");

		WarehouseView warehouseView = new WarehouseView(context);
		Controller controller = new WarehouseController(context, warehouseView);
		tabPanel.add(warehouseView, "Quản kho");
		
		tabPanel.add(new AbsolutePanel(), "Đối tác");

		return tabPanel;
	}
	
	public void bind() {
		tabPanel.addSelectionHandler( new SelectionHandler() {
			public void onSelection(SelectionEvent event) {
				int selectedTab = tabPanel.getSelectedIndex();
				
				if (selectedTab>0 && App.context.getCurrentAdminShop() == null) {
					Window.alert("Vui lòng chọn một cửa hàng để quản lý.");
					tabPanel.selectTab(0);
					return;
				}
				
				itemController.go();
				stationController.go();
			}
		});	
	}
	
	public void onResize() {
		tabPanel.setSize("100%", "100%");
		appPanel.setHeight("100%");
		shopView.onResize();
	}
	
	public void displayShops() {
		shopController.go();
	}
	
	
	
}

