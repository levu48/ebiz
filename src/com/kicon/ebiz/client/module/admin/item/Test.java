package com.kicon.ebiz.client.module.admin.item;

import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.model.Shop;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class Test extends AbstractView {
	private FlexTable form;
	private Shop shop;
	
	private Label info;
	private Label idLabel;
	private Label createdByLabel;
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
	
	
	
	private TextBox streetNoTextBox;
	private TextBox roadTextBox;
	private TextBox cityTextBox;
	private TextBox phoneNoTextBox;
	private TextBox faxNoTextBox;
	private TextBox emailTextBox;
	private TextBox webTextBox;
	private ListBox taxListBox;
	private TextArea receiptNoteTextArea;
	
	private Button submitButton;
	
	public Test() {
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("252px", "247px");
		
		FlexTable form = new FlexTable();
		absolutePanel.add(form, 0, 0);
		form.setSize("179px", "215px");
		
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
		
		Label unitLabel = new Label("Đơn vị");
		unitLabel.setStyleName(STYLE);
		form.setWidget(8, 0, unitLabel);
		
		Label isStockedLabel = new Label("Tồn kho");
		isStockedLabel.setStyleName(STYLE);
		form.setWidget(8, 1, isStockedLabel);
		
		Label stockLabel = new Label("Định mức tồn kho");
		stockLabel.setStyleName(STYLE);
		form.setWidget(8, 2, stockLabel);
		
		unitListBox = new ListBox();
		unitListBox.addItem("");
		unitListBox.setSelectedIndex(0);
		form.setWidget(9, 0, unitListBox);
		
		isStockedCheckBox = new CheckBox();
		form.setWidget(9, 1, isStockedCheckBox);
		
		stockTextBox = new TextBox();
		form.setWidget(9, 2, stockTextBox);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		form.setWidget(10, 0, horizontalPanel);
		
		submitButton = new Button("Nạp");
		horizontalPanel.add(submitButton);
		submitButton.setWidth("100px");
		
		form.getFlexCellFormatter().setColSpan(1, 0, 3);
	}
	
	public void setup() {}
}
