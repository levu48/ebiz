package com.kicon.ebiz.model;

import java.io.Serializable;
import com.kicon.ebiz.crud.HasId;
import com.kicon.ebiz.crud.HasName;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="STATION")
public class Station implements Serializable, HasId<String>, HasName {
	
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
    
    @OneToOne
    @JoinColumn(name="categoryElementId")
    private CategoryElement categoryElement;
    
    public Station() {
    }
    
    public Station(String name) {
    	this.name = name;
    }
    
    
    public Station(Shop shop) {
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
    
    
    public void setCategoryElement(CategoryElement categoryElement) {
    	this.categoryElement = categoryElement;
    }
    
    public CategoryElement getCategoryElement() {
    	return categoryElement;
    }
    
    public void copy(Station station) {
    	if (station == null) return;
        name = station.getName();
        categoryElement = station.getCategoryElement();
    }

}
