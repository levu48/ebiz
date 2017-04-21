package com.kicon.ebiz.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import com.kicon.ebiz.crud.HasId;
import com.kicon.ebiz.crud.HasName;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WAREHOUSE")
public class Warehouse implements Serializable, HasId<String>, HasName {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Column(name="id")
    private String id;
    
    @Column(name="name")
    private String name;
    
    @ManyToOne
    @JoinColumn(name="shopId")
    private Shop shop;
    
    @OneToMany(cascade=CascadeType.ALL,  fetch=FetchType.EAGER, mappedBy="warehouse")
    private List<WarehouseItem> warehouseItems;
    

    public Warehouse() {
    }
    
    public Warehouse(String name) {
    	this.name = name;
    }
    
    public Warehouse(Shop shop) {
    	this.shop = shop;
    	
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public void setShops(Shop shop) {
    	this.shop = shop;
    }
    
    public Shop getShop() {
    	return shop;
    }
    
    /*
    public void addShop(Shop shop) {
    	if (shops == null) {
    		shops = new HashSet<Shop>();
    	}
    	shops.add(shop);
    	shop.addWarehouse(this);
    }
    
    public void removeShop(Shop shop) {
    	shops.remove(shop);
    	shop.removeWarehouse(this);
    }
    */
    
    public void copy(Warehouse warehouse) {
    	if (warehouse == null) return;
        name = warehouse.getName();
        //shops = warehouse.getShops();
    }

}
