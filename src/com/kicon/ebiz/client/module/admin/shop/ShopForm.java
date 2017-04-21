package com.kicon.ebiz.client.module.admin.shop;

import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.model.Shop;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ShopForm extends AbstractView {
	private FlexTable form;
	private Shop shop;
	
	private Label info;
	private Label idLabel;
	private Label createdByLabel;
	private TextBox nameTextBox;
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
	//private Button newButton;
	//private Button deleteButton;
	

	
	
	public ShopForm(Context context) {
		super(context);

		form = new FlexTable();
		initWidget(form);
		
		Label nameLabel = new Label("Tên cửa hàng");
		nameLabel.setStyleName(STYLE);
		form.setWidget(0, 0, nameLabel);
		
		nameTextBox = new TextBox();
		form.setWidget(1, 0, nameTextBox);
		nameTextBox.setWidth("334px");
		
		Label streetNumberLabel = new Label("Số nhà");
		streetNumberLabel.setStyleName(STYLE);
		form.setWidget(2, 0, streetNumberLabel);
		
		Label roadLabel = new Label("Đường");
		roadLabel.setStyleName(STYLE);
		form.setWidget(2, 1, roadLabel);
		
		Label cityLabel = new Label("Thành phố");
		cityLabel.setStyleName(STYLE);
		form.setWidget(2, 2, cityLabel);
		
		streetNoTextBox = new TextBox();
		form.setWidget(3, 0, streetNoTextBox);
		
		roadTextBox = new TextBox();
		form.setWidget(3, 1, roadTextBox);
		
		cityTextBox = new TextBox();
		form.setWidget(3, 2, cityTextBox);
		
		Label phoneLabel = new Label("Điện thoại");
		phoneLabel.setStyleName(STYLE);
		form.setWidget(4, 0, phoneLabel);
		
		Label faxLabel = new Label("Fax");
		faxLabel.setStyleName(STYLE);
		form.setWidget(4, 1, faxLabel);
		
		Label emailLabel = new Label("Email");
		emailLabel.setStyleName(STYLE);
		form.setWidget(4, 2, emailLabel);
		
		phoneNoTextBox = new TextBox();
		form.setWidget(5, 0, phoneNoTextBox);
		
		faxNoTextBox = new TextBox();
		form.setWidget(5, 1, faxNoTextBox);
		
		emailTextBox = new TextBox();
		form.setWidget(5, 2, emailTextBox);
		
		Label webLabel = new Label("Web");
		webLabel.setStyleName(STYLE);
		form.setWidget(6, 0, webLabel);
		
		Label taxLabel = new Label("Tax");
		taxLabel.setStyleName(STYLE);
		form.setWidget(6, 2, taxLabel);
		
		webTextBox = new TextBox();
		form.setWidget(7, 0, webTextBox);
		webTextBox.setWidth("334px");
		
		taxListBox = new ListBox();
		form.setWidget(7, 1, taxListBox);
		
		taxListBox.addItem("0");
		taxListBox.addItem("5");
		taxListBox.addItem("10");
		taxListBox.addItem("20");
		
		Label receiptNoteLabel = new Label("Ghi chú");
		receiptNoteLabel.setStyleName(STYLE);
		form.setWidget(8, 0, receiptNoteLabel);
		
		receiptNoteTextArea = new TextArea();
		form.setWidget(9, 0, receiptNoteTextArea);
		receiptNoteTextArea.setWidth("440px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		form.setWidget(10, 0, horizontalPanel);
		
		submitButton = new Button("Nạp");
		horizontalPanel.add(submitButton);
		submitButton.setWidth("100px");
		/*
		newButton = new Button("Mới");
		horizontalPanel.add(newButton);
		newButton.setWidth("50px");
		
		deleteButton = new Button("Xóa");
		horizontalPanel.add(deleteButton);
		deleteButton.setWidth("50px");
		*/
		form.getFlexCellFormatter().setColSpan(1, 0, 3);
		form.getFlexCellFormatter().setColSpan(7, 0, 2);
		form.getFlexCellFormatter().setColSpan(9, 0, 3);
	}
	
	public void setup() {}
	
	public Button getSubmitButton() {
		return submitButton;
	}
	
	/*
	public Button getNewButton() {
		return newButton;
	}
	
	public Button getDeleteButton() {
		return deleteButton;
	}
	*/
	
	public boolean checkEditing() {
		String shopTitle = nameTextBox.getText();
		if (shopTitle == null || shopTitle.equals("") || shopTitle.length() < 3) {
			Window.alert("Tên cửa hàng phải có tối thiểu 3 chữ cái");
			return false;
		}
		
		return true;
	}
	
	public Shop getEditing() {
		Shop shop = new Shop();
		
		shop.setName(nameTextBox.getText());
		shop.setStreetNo(streetNoTextBox.getText());
		shop.setRoad(roadTextBox.getText());
		shop.setCity(cityTextBox.getText());
		shop.setPhoneNo(phoneNoTextBox.getText());
		shop.setFaxNo(faxNoTextBox.getText());
		shop.setEmail(emailTextBox.getText());
		shop.setWeb(webTextBox.getText());
		//taxCheckBox.setValue(false);
		//shop.setTaxRate(taxTextBox.getText());
		shop.setTaxRate(taxListBox.getValue(taxListBox.getSelectedIndex()));
		shop.setReceiptNote(receiptNoteTextArea.getText());
		
		return shop;
	}
	
	public void setShop(Shop shop) {
		this.shop = shop;
		if (shop == null) {
			clearEditFields();
		} else {
			//info.setText(shop.getName());
			//idLabel.setText(shop.getId());
			//createdByLabel.setText(shop.getCreatedBy());
			
			nameTextBox.setText(shop.getName());
			streetNoTextBox.setText(shop.getStreetNo());
			roadTextBox.setText(shop.getRoad());
			cityTextBox.setText(shop.getCity());
			phoneNoTextBox.setText(shop.getPhoneNo());
			faxNoTextBox.setText(shop.getFaxNo());
			emailTextBox.setText(shop.getEmail());
			webTextBox.setText(shop.getWeb());
			
			//taxCheckBox.setValue(false);
			//taxTextBox.setText("" + shop.getTaxRate());
			///taxListBox.setSelectedIndex(getTaxIndexByValue(shop.getTaxRate()));
			
			receiptNoteTextArea.setText(shop.getReceiptNote());
			
			/*
			if (isOwner()) {
				enable();
			} else {
				disable();
			}
			*/
		}
	}
	
	private void clearEditFields() {
		//info.setText("");
		//idLabel.setText("");
		//createdByLabel.setText("");
		nameTextBox.setText("");
		streetNoTextBox.setText("");
		roadTextBox.setText("");
		cityTextBox.setText("");
		phoneNoTextBox.setText("");
		faxNoTextBox.setText("");
		emailTextBox.setText("");
		webTextBox.setText("");
		//taxCheckBox.setValue(false);
		//taxTextBox.setText("");
		taxListBox.setSelectedIndex(0);
		receiptNoteTextArea.setText("");
	}
	
	public TextBox getNameTextBox() {
		return nameTextBox;
	}
}
