package com.kicon.ebiz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import com.kicon.ebiz.crud.HasId;
import com.kicon.ebiz.crud.HasName;
import com.kicon.ebiz.crud.HasShortDescription;

@Entity
@Table(name="WarehouseItem")
public class WarehouseItem implements Serializable {
	    private static final long serialVersionUID = 1L;
	    
	    @Id
	    @GeneratedValue
	    @Column(name="id")
	    private String id;
	    
	    @ManyToOne
	    @JoinColumn(name="warehouseId")
	    private Warehouse warehouse;

	    @ManyToOne
	    @JoinColumn(name="itemId")
	    private Item item;
	    
	    @Column(name="quantity")
	    private double quantity = 0;
	    
	    
	    public WarehouseItem() {
	    	
	    }
	    
	    public String getId() {
	    	return id;
	    }
	    
	    public String getName() {
	    	if (item == null) return null;
	    	return item.getName();
	    }
	    
	    public String getItemNo() {
	    	if (item == null) return null;
	    	return item.getItemNo();
	    }
	    
	    public void setQuantity(double quantity) {
	    	this.quantity = quantity;
	    }
	    
	    public double getQuantity() {
	    	return quantity;
	    }
	    
	    public void setItem(Item item) {
	    	this.item = item;
	    }
	    
	    public Item getItem() {
	    	return item;
	    }
	    
	    public void setWarehouse(Warehouse warehouse) {
	    	this.warehouse = warehouse;
	    }
	    
	    public Warehouse getWarehouse() {
	    	return warehouse;
	    }
	    
	    public void copy(WarehouseItem warehouseItem) {
	    	this.item = warehouseItem.getItem();
	    	this.quantity = warehouseItem.getQuantity();
	    }

}
