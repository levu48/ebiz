package com.kicon.ebiz.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORY_ELEMENT")
public class CategoryElement implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Column(name="id")
    private String id;
    
    @Column(name="name")
    private String name;
    
    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;
    
    public CategoryElement() {
    }
      
    public CategoryElement(String name) {
    	this.name = name;
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
    
    public void setCategory(Category category) {
    	this.category = category;
    }
    
    public Category getCategory() {
    	return category;
    }
    
    public void copy(CategoryElement element) {
    	if (element == null) return;
        name = element.getName();
        category = element.getCategory();
    }
	

}
