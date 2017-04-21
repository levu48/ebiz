package com.kicon.ebiz.model;

import com.kicon.ebiz.crud.HasId;
import com.kicon.ebiz.crud.HasName;
import com.kicon.ebiz.crud.HasShortDescription;

//import dk.apaq.qash.share.util.ShopFieldBridge;
import java.io.Serializable;
import java.util.Date;

//import dk.apaq.qash.share.util.TaxTool;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/*
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
 */
import javax.persistence.Temporal;

@Entity
@Table(name="ITEM")
public class Item implements Serializable, Cloneable,
        HasId<String>, HasName, HasShortDescription, Product {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Column(name="id")
    private String id;

    @Column(name="name")
    private String name;
    
    @Column(name="itemNo")
    private String itemNo;

    @Column(name="barcode")
    private String barcode;
    
    @Column(name="cost")
    private double cost=0;
    
    @Column(name="price")
    private double price=0;

    @Column(name="discountPrice")
    private double discountPrice=0;

    @Column(name="quantityInStock")
    private double quantityInStock=0;

    @Column(name="stockProduct")
    private boolean stockProduct = false;

    @ManyToOne
    @JoinColumn(name="taxReference")
    private Tax tax = null;

    @ManyToOne
    @JoinColumn(name="shopId")
    private Shop shop;

    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="manufacturingDate")
    private Date manufacturingDate = null;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="expirationDate")   
    private Date expirationDate;
    
    /*
	@Enumerated(EnumType.STRING)
	@Column(name="unit")
    private Unit unit = Unit.Each;
	
	@Column(name="chemicalContent")
    private int chemicalContent; //hàm lượng, content (chemistry)
	*/
    
    @OneToOne
    @JoinColumn(name="unitElementId")
    private CategoryElement unitElement;
    
    @OneToOne
    @JoinColumn(name="contentElementId")
    private CategoryElement contentElement;
    
    @OneToOne
    @JoinColumn(name="categoryElementId")
    private CategoryElement categoryElement;

    public Item() { }
    
    public Item(String name, String itemNo, double price) {
    	setName(name);
    	setItemNo(itemNo);
    	setPrice(price);
    }

    public Item(Shop shop) {
        this.shop = shop;
    }
    
    public void setUnitElement(CategoryElement unitElement) {
    	this.unitElement = unitElement;
    }
    
    public CategoryElement getUnitElement() {
    	return unitElement;
    }
    
    public void setContentElement(CategoryElement contentElement) {
    	this.contentElement = contentElement;
    }
    
    public CategoryElement getContentElement() {
    	return contentElement;
    }
    
    public void setCategoryElement(CategoryElement categoryElement) {
    	this.categoryElement = categoryElement;
    }
    
    public CategoryElement getCategoryElement() {
    	return categoryElement;
    }


    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }
    
    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the price including tax value. This is the same as calling
     * getPrice() + getTaxValue();
     * @return
     */
    public double getPriceWithTax() {
        return getPrice() + getTaxValue();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the price of item including tax. This will use taxes to calculate how much
     * of the price is tax and then call setPrice() to adjust the price. This means that
     * changing the tax after calling this method with also change the retrieved value with
     * getPriceWithTax.
     */
    public void setPriceWithTax(double price) {
        if(tax!=null) {
            price = ((price / (tax.getRate() + 100.0)) * 100.0);
        }

        setPrice(price);
    }

    public double getTaxValue() {
        return TaxTool.getAddableTaxValue(getPrice(), getTax());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return itemNo;
    }

    public double getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(double quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public boolean isStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(boolean stockProduct) {
        this.stockProduct = stockProduct;
    }

    public String getType() {
        return "Item";
    }

    public Shop getOwner() {
        return shop;
    }
    
    public void setManufacturingDate(Date manufacturingDate) {
    	this.manufacturingDate = manufacturingDate;
    }
    
    public Date getManufacturingDate() {
    	return manufacturingDate;
    }
    
    public void setExpirationDate(Date expirationDate) {
    	this.expirationDate = expirationDate;
    }
    
    public Date getExpirationDate() {
    	return expirationDate;
    }
    
    /*
    public void setUnit(Unit unit) {
    	this.unit = unit;
    }
    
    public Unit getUnit() {
    	return unit;
    }
    
    public void setContent(int chemicalContent) {
    	this.chemicalContent = chemicalContent;
    }
    
    public int getChemicalContent() {
    	return chemicalContent;
    }
    */

    public Item clone() {
        Item clone = new Item();
        clone.shop = shop;
        clone.barcode = barcode;
        clone.discountPrice = discountPrice;
        clone.id = id;
        clone.stockProduct = stockProduct;
        clone.itemNo = itemNo;
        clone.cost = cost;
        clone.price = price;
        clone.quantityInStock = quantityInStock;
        clone.name = name;
        clone.tax = tax;
        clone.manufacturingDate = manufacturingDate;
        clone.expirationDate = expirationDate;
        //clone.unit = unit;
        //clone.chemicalContent = chemicalContent;
        clone.unitElement = unitElement;
        clone.contentElement = contentElement;
        clone.categoryElement = categoryElement;
        return clone;
    }
    
    public void copy(Item item) {
    	if (item == null) return;
        //id = item.id;
        //shop = item.shop;
        barcode = item.barcode;
        discountPrice = item.discountPrice;
        stockProduct = item.stockProduct;
        itemNo = item.itemNo;
        cost = item.cost;
        price = item.price;
        quantityInStock = item.quantityInStock;
        name = item.name;
        tax = item.tax;
        manufacturingDate = item.manufacturingDate;
        expirationDate = item.expirationDate;
        //unit = item.unit;
        //chemicalContent = item.chemicalContent;
        unitElement = item.getUnitElement();
        contentElement = item.getContentElement();
        categoryElement = item.getCategoryElement();
    }
}
