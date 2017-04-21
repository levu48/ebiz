package com.kicon.ebiz.client.module.admin.station;

import com.kicon.ebiz.client.view.AbstractView;
import com.kicon.ebiz.client.context.Context;
import com.kicon.ebiz.model.Station;
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

public class StationForm extends AbstractView {
	private FlexTable form;
	private Station station;
	
	private Label info;
	private Label idLabel;
	private Label createdByLabel;
	private TextBox nameTextBox;
	
	private Button submitButton;
	private Button newButton;
	private Button deleteButton;
	
	final private static String STYLE  = "kicon-form-Label";
	
	
	public StationForm(Context context) {
		super(context);

		form = new FlexTable();
		initWidget(form);
		
		Label nameLabel = new Label("Tên điểm bán");
		nameLabel.setStyleName(STYLE);
		form.setWidget(0, 0, nameLabel);
		
		nameTextBox = new TextBox();
		form.setWidget(1, 0, nameTextBox);
		nameTextBox.setWidth("334px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		form.setWidget(10, 0, horizontalPanel);
		
		submitButton = new Button("Nạp");
		horizontalPanel.add(submitButton);
		submitButton.setWidth("50px");
		
		newButton = new Button("Mới");
		horizontalPanel.add(newButton);
		newButton.setWidth("50px");
		
		deleteButton = new Button("Xóa");
		horizontalPanel.add(deleteButton);
		deleteButton.setWidth("50px");
		
	}
	
	public void setup() {}
	
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
		String shopTitle = nameTextBox.getText();
		if (shopTitle == null || shopTitle.equals("") || shopTitle.length() < 3) {
			Window.alert("Tên cửa hàng phải có tối thiểu 3 chữ cái");
			return false;
		}
		
		return true;
	}
	
	public Station getEditing() {
		Station station = new Station();
		
		station.setName(nameTextBox.getText());
		return station;
	}
	
	public void setStation(Station station) {
		this.station = station;
		if (station == null) {
			clearEditFields();
		} else {
			//info.setText(shop.getName());
			//idLabel.setText(shop.getId());
			//createdByLabel.setText(shop.getCreatedBy());
			
			nameTextBox.setText(station.getName());
			
		}
	}
	
	private void clearEditFields() {
		//info.setText("");
		//idLabel.setText("");
		//createdByLabel.setText("");
		nameTextBox.setText("");
	}
	
	public TextBox getNameTextBox() {
		return nameTextBox;
	}
}
