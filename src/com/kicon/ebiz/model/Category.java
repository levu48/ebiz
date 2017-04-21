package com.kicon.ebiz.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Cache;

@Entity
@Table(name="CATEGORY")
public class Category implements Serializable {
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
    
    @OneToMany(cascade=CascadeType.ALL,  fetch=FetchType.EAGER, mappedBy="category")
    private List<CategoryElement> elements;
    
    public Category() {
    }
      
    public Category(String name) {
    	this.name = name;
    }
    
    public Category(String name, Shop shop) {
    	this.name = name;
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
    
    public void setElements(List<CategoryElement> elements) {
    	this.elements = elements;
    }
    
    public List<CategoryElement> getElements() {
    	return elements;
    }
    
    public void addElement(CategoryElement element) {
    	if (element == null) return;
    	if (!elements.contains(element)) {
    		elements.add(element);
    	}
		element.setCategory(this);
    }
    
	public void removeElement(CategoryElement element) {
		if (element == null) return;
		if (elements.contains(element)) {
			elements.remove(element);
		}
		element.setCategory(null);
	}
    
    public void setShop(Shop shop) {
    	this.shop = shop;
    }
    
    public Shop getShop() {
    	return shop;
    }
    
	public void copy(Category category) {
		name = category.getName();
		//shop = category.getShop();
		elements = category.getElements();
	}

}