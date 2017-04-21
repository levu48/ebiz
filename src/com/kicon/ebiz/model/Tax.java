package com.kicon.ebiz.model;

import com.kicon.ebiz.crud.HasId;
import com.kicon.ebiz.crud.HasName;
//import dk.apaq.qash.share.util.ShopFieldBridge;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
 */

@Entity
@Table (name="TAX")
public class Tax implements Serializable, HasName, HasId<String> {

    @Id
    @GeneratedValue
    @Column(name="id")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="rate")
    private double rate;

    @Column(name="defaultEnabled")
    private boolean defaultEnabled = false;

    //@OneToOne(mappedBy="tax")
    //@JoinColumn(name="shopId")
    //private Shop shop;
    
    public Tax() {
    }

    public Tax(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    /**
     * Retrieves the id of the tax.
     * @return The id of the tax.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id of the tax.
     * @param id The id of the tax.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /*
    public void setShop(Shop shop) {
    	this.shop = shop;
    }
    
    public Shop getShop() {
    	return shop;
    }
    */

    /**
     * Wether this is intended to be enabled by default. Most countries have a default tax which
     * are mostly used. This or these should be enabled by default.
     * @return Wether the tax is enabled by default.
     */
    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public void setDefaultEnabled(boolean defaultEnabled) {
        this.defaultEnabled = defaultEnabled;
    }

    /**
     * Retrieves the priority of the tax.
     * @return
     */
    /*public int getPriority() {
    return priority;
    }

    public void setPriority(int priority) {
    this.priority = priority;
    }

    public TaxType getTaxType() {
    return taxType;
    }*/
    /**
     * The percentage that applies to this tax. fx. 25.0 for 25%
     * @return
     */
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Retrieve the name of this tax.
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tax)) {
            return false;
        }

        Tax other = (Tax) obj;
        return niceEquals(id, other.id)
                && niceEquals(name, other.name)
                && rate == other.rate;

    }

    private boolean niceEquals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }

        if (obj1 == null) {
            return false;
        }

        return obj1.equals(obj2);
    }
}
