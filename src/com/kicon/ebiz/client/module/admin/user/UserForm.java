package com.kicon.ebiz.client.module.admin.user;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.client.App;
import com.kicon.ebiz.model.CategoryElement;
import com.kicon.ebiz.model.Shop;
import com.kicon.ebiz.model.Item;
import com.kicon.ebiz.model.User;
import com.kicon.ebiz.client.module.admin.TemplateForm;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.i18n.client.DateTimeFormat;

public class UserForm extends TemplateForm<User> {
	private FlexTable form;
	private Item item;
	
	private Label itemInfo;
	private Label idLabel;
	private TextBox nameTextBox;
	private ListBox categoryListBox;
	
	private TextBox itemNoTextBox;
	private TextBox barcodeTextBox;
	private TextBox costTextBox;
	private TextBox priceTextBox;
	private TextBox discountPriceTextBox;
	
	private DateBox manufacturingDate;
	private DateBox expirationDate;
	
	private ListBox unitListBox;
	private ListBox contentListBox;
	
	private TextBox taxTextBox;
	private CheckBox isStockedCheckBox;
	private TextBox stockTextBox;
	private TextArea noteTextArea;
	
	private Button submitButton;
	private Button newButton;
	private Button deleteButton;
	
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public UserForm() {
		super();
		this.item = null;
		setup();
	}
	
	public UserForm(String title) {
		super(title);
		this.item = null;
		setup();
	}

	public void setup() {
		form = new FlexTable();
		initWidget(form);
		
		Label nameLabel = new Label("Tên");
		nameLabel.setStyleName(STYLE);
		form.setWidget(0, 0, nameLabel);
		
		nameTextBox = new TextBox();
		form.setWidget(1, 0, nameTextBox);
		nameTextBox.setWidth("334px");
		
		Label itemNoLabel = new Label("Mã số");
		itemNoLabel.setStyleName(STYLE);
		form.setWidget(2, 0, itemNoLabel);
		
		Label barcodeLabel = new Label("Mã vạch");
		barcodeLabel.setStyleName(STYLE);
		form.setWidget(2, 1, barcodeLabel);
		
		Label categoryLabel = new Label("Thể loại");
		categoryLabel.setStyleName(STYLE);
		form.setWidget(2, 2, categoryLabel);
		
		itemNoTextBox = new TextBox();
		form.setWidget(3, 0, itemNoTextBox);
		
		barcodeTextBox = new TextBox();
		form.setWidget(3, 1, barcodeTextBox);
		
		categoryListBox = new ListBox();
		categoryListBox.addItem("");
		categoryListBox.setSelectedIndex(0);
		form.setWidget(3, 2, categoryListBox);
		
		Label costLabel = new Label("Giá vốn");
		costLabel.setStyleName(STYLE);
		form.setWidget(4, 0, costLabel);
		
		Label priceLabel = new Label("Giá bán");
		priceLabel.setStyleName(STYLE);
		form.setWidget(4, 1, priceLabel);
		
		Label discountLabel = new Label("Giá giảm");
		discountLabel.setStyleName(STYLE);
		form.setWidget(4, 2, discountLabel);
		
		costTextBox = new TextBox();
		form.setWidget(5, 0, costTextBox);
		
		priceTextBox = new TextBox();
		form.setWidget(5, 1, priceTextBox);
		
		discountPriceTextBox = new TextBox();
		form.setWidget(5, 2, discountPriceTextBox);
		
		Label manufacturingLabel = new Label("Ngày sản xuất");
		manufacturingLabel.setStyleName(STYLE);
		form.setWidget(6, 0, manufacturingLabel);
		
		Label expirationLabel = new Label("Ngày hết hạn");
		expirationLabel.setStyleName(STYLE);
		form.setWidget(6, 1, expirationLabel);
		
		Label contentLabel = new Label("Hàm lượng");
		contentLabel.setStyleName(STYLE);
		form.setWidget(6, 2, contentLabel);
		
		manufacturingDate = new DateBox();
		form.setWidget(7, 0, manufacturingDate);
		
		expirationDate = new DateBox();
		form.setWidget(7, 1, expirationDate);
		
		contentListBox = new ListBox();
		contentListBox.addItem("");
		contentListBox.setSelectedIndex(0);
		form.setWidget(7, 2, contentListBox);
		
		Label isStockedLabel = new Label("Tồn kho");
		isStockedLabel.setStyleName(STYLE);
		form.setWidget(8, 0, isStockedLabel);
		
		Label stockLabel = new Label("Định mức tồn kho");
		stockLabel.setStyleName(STYLE);
		form.setWidget(8, 1, stockLabel);
		
		Label unitLabel = new Label("Đơn vị");
		unitLabel.setStyleName(STYLE);
		form.setWidget(8, 2, unitLabel);
		
		isStockedCheckBox = new CheckBox();
		form.setWidget(9, 0, isStockedCheckBox);
		
		stockTextBox = new TextBox();
		form.setWidget(9, 1, stockTextBox);
		
		unitListBox = new ListBox();
		unitListBox.addItem("");
		unitListBox.setSelectedIndex(0);
		form.setWidget(9, 2, unitListBox);
		
		Label noteLabel = new Label("Ghi chú");
		noteLabel.setStyleName(STYLE);
		form.setWidget(10, 0, noteLabel);
		
		noteTextArea = new TextArea();
		form.setWidget(11, 0, noteTextArea);
		noteTextArea.setWidth("440px");
		form.setWidget(11, 0, noteTextArea);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		form.setWidget(12, 0, horizontalPanel);
		
		submitButton = new Button("Nạp");
		horizontalPanel.add(submitButton);
		submitButton.setWidth("100px");
		
		form.getFlexCellFormatter().setColSpan(1, 0, 3);
		form.getFlexCellFormatter().setColSpan(11, 0, 3);
	}
	
	public void clearEditFields() {
		//itemInfo.setText("");
		//idLabel.setText("");
		nameTextBox.setText("");
		itemNoTextBox.setText("");
		barcodeTextBox.setText("");
		costTextBox.setText("");
		priceTextBox.setText("");
		discountPriceTextBox.setText("");
		//taxTextBox.setText("" + item.getTaxValue());
		stockTextBox.setText("");
		
		manufacturingDate.setValue(null);
		manufacturingDate.setFormat(new DateBox.DefaultFormat(dateFormat));
		
		expirationDate.setValue(null);
		expirationDate.setFormat(new DateBox.DefaultFormat(dateFormat));
		
		unitListBox.clear();
		contentListBox.clear();
		categoryListBox.clear();
	}
	
	
	public void setUser(User user) {
/*
		this.item = item;
		if (item == null) {
			clearEditFields();
		} else {
			//itemInfo.setText(item.getName());
			//idLabel.setText(item.getId());
			nameTextBox.setText(item.getName());
			itemNoTextBox.setText(item.getItemNo());
			barcodeTextBox.setText(item.getBarcode());
			costTextBox.setText("" + item.getCost());
			priceTextBox.setText("" + item.getPrice());
			discountPriceTextBox.setText("" + item.getDiscountPrice());
			
			manufacturingDate.setValue(item.getManufacturingDate());
			expirationDate.setValue(item.getExpirationDate());
			
			//taxTextBox.setText("" + item.getTaxValue());
			stockTextBox.setText("" + item.getQuantityInStock());
		}
		//setupListBox(categoryListBox, Shop.CAT_ITEM);
		//setupListBox(unitListBox, Shop.CAT_UNIT);
		//setupListBox(contentListBox, Shop.CAT_CONTENT);
		 
		 */
	}
	
	
	public void setupListBox(ListBox listBox, String categoryName) {
		CategoryElement el = null;
		if (item != null && categoryName != null) {
			if (categoryName.equals(Shop.CAT_ITEM)) {
				el = item.getCategoryElement();
				
			} else if (categoryName.equals(Shop.CAT_UNIT)){
				el = item.getUnitElement();
				
			} else if (categoryName.equals(Shop.CAT_CONTENT)){
				el = item.getContentElement();
			}
		}
		
		List<CategoryElement> list = App.context.getCurrentAdminCategoryElements(categoryName);
		
		listBox.clear();
		listBox.addItem("");
		
		int selectedIndex = 0;
		int i = 1;
		for(CategoryElement element: list) {
			String name = element.getName();
			listBox.addItem(name);
			if (el != null && el.getName().equals(name)) {
				selectedIndex = i;
			}
			i++;
		}
		listBox.setSelectedIndex(selectedIndex);
	}
	
	public Button getSubmitButton() {
		return submitButton;
	}
	
	public Button getNewButton() {
		return newButton;
	}
	
	public Button getDeleteButton() {
		return deleteButton;
	}
	
	public boolean checkEditing() {
		String title = nameTextBox.getText();
		if (title == null || title.equals("") || title.length() < 3) {
			Window.alert("Tên sản phẩm phải có tối thiểu 3 chữ cái");
			return false;
		}
		return true;
	}
	
	public Item getEditItem() {
		Item item = new Item();
		item.setName(nameTextBox.getText());
		item.setItemNo(itemNoTextBox.getText());
		item.setBarcode(barcodeTextBox.getText());
		
		item.setManufacturingDate(manufacturingDate.getValue());
		item.setExpirationDate(expirationDate.getValue());
		
		int i = categoryListBox.getSelectedIndex();
		
		i = unitListBox.getSelectedIndex();
		if (i>0) {
			List<CategoryElement> list = new ArrayList<CategoryElement>(); //App.context.getCurrentAdminUnitCategoryElements();
			CategoryElement element = list.get(i-1);
			item.setUnitElement(element);
		}
		
		i = contentListBox.getSelectedIndex();
		if (i>0) {
			List<CategoryElement> list = new ArrayList<CategoryElement>(); //App.context.getCurrentAdminContentCategoryElements();
			CategoryElement element = list.get(i-1);
			item.setContentElement(element);
		}
		
		i = categoryListBox.getSelectedIndex();
		if (i>0) {
			List<CategoryElement> list = new ArrayList<CategoryElement>(); //App.context.getCurrentAdminItemCategoryElements();
			CategoryElement element = list.get(i-1);
			item.setCategoryElement(element);
		}

		
		try {
			item.setCost(Double.parseDouble(costTextBox.getText()));
		} catch (NumberFormatException e) {
			//Window.alert("Giá vốn. NumberFormatException " + e);
			costTextBox.setText("0");
			item.setCost(0);
		}
		
		try {
			item.setPrice(Double.parseDouble(priceTextBox.getText()));
		} catch (NumberFormatException e) {
			//Window.alert("Giá tiền. NumberFormatException " + e);
			priceTextBox.setText("0");
			item.setPrice(0);
		}
		
		try {
			item.setDiscountPrice(Double.parseDouble(discountPriceTextBox.getText()));
		} catch (NumberFormatException e) {
			//Window.alert("Giảm giá. NumberFormatException " + e);
			discountPriceTextBox.setText("0");
			item.setDiscountPrice(0);
		}
		
		try {
			item.setQuantityInStock(Double.parseDouble(stockTextBox.getText()));
		} catch (NumberFormatException e) {
			//Window.alert("Còn trong kho. NumberFormatException " + e);
			stockTextBox.setText("0");
			item.setQuantityInStock(0);
		}
		
		if (isStockedCheckBox.isEnabled()) {
			item.setStockProduct(true);
		} else {
			item.setStockProduct(false);
		}
		
		return item;
	}
	
	public TextBox getNameTextBox() {
		return nameTextBox;
	}
	
	public TextBox getFirstTextBox() {
		return nameTextBox;
	}

}
