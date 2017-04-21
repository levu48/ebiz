package com.kicon.ebiz.client.ui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class Test {

	/**
	 * @wbp.parser.entryPoint
	 */

	public void onModuleLoad() {
		FlexTable pan = new FlexTable();
		
		Label lblNewLabel = new Label("Tên cửa hàng");
		pan.setWidget(0, 0, lblNewLabel);
		lblNewLabel.setWidth("125px");
		
		TextBox textBox = new TextBox();
		pan.setWidget(1, 0, textBox);
		textBox.setWidth("300px");
		
		Label lblNewLabel_1 = new Label("Số nhà");
		pan.setWidget(2, 0, lblNewLabel_1);
		
		Label lblng = new Label("Đường");
		pan.setWidget(2, 1, lblng);
		
		Label lblThnhPh = new Label("Thành phố");
		pan.setWidget(2, 2, lblThnhPh);
		
		TextBox textBox_1 = new TextBox();
		pan.setWidget(3, 0, textBox_1);
		
		TextBox textBox_2 = new TextBox();
		pan.setWidget(3, 1, textBox_2);
		
		TextBox textBox_3 = new TextBox();
		pan.setWidget(3, 2, textBox_3);
		
		Label lblinThoi = new Label("Điện thoại");
		pan.setWidget(4, 0, lblinThoi);
		
		Label lblFax = new Label("Fax");
		pan.setWidget(4, 1, lblFax);
		
		Label lblEmail = new Label("Email");
		pan.setWidget(4, 2, lblEmail);
		
		TextBox textBox_4 = new TextBox();
		pan.setWidget(5, 0, textBox_4);
		
		TextBox textBox_5 = new TextBox();
		pan.setWidget(5, 1, textBox_5);
		
		TextBox textBox_6 = new TextBox();
		pan.setWidget(5, 2, textBox_6);
		
		Label lblWeb = new Label("Web");
		pan.setWidget(6, 0, lblWeb);
		
		Label lblTax = new Label("Tax");
		pan.setWidget(6, 1, lblTax);
		
		TextBox textBox_7 = new TextBox();
		pan.setWidget(7, 0, textBox_7);
		
		TextBox textBox_8 = new TextBox();
		pan.setWidget(7, 1, textBox_8);
		
		Label lblGhiCh = new Label("Ghi chú");
		pan.setWidget(8, 0, lblGhiCh);
		
		TextArea textArea = new TextArea();
		pan.setWidget(9, 0, textArea);
		textArea.setWidth("440px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		pan.setWidget(10, 0, horizontalPanel);
		
		Button btnNewButton = new Button("New button");
		btnNewButton.setText("Nạp");
		horizontalPanel.add(btnNewButton);
		btnNewButton.setWidth("50px");
		
		Button btnMi = new Button("Mới");
		horizontalPanel.add(btnMi);
		btnMi.setWidth("50px");
		
		Button btnXa = new Button("Xóa");
		horizontalPanel.add(btnXa);
		btnXa.setWidth("50px");
		pan.getFlexCellFormatter().setColSpan(1, 0, 3);
		pan.getFlexCellFormatter().setColSpan(9, 0, 3);


	}

}
