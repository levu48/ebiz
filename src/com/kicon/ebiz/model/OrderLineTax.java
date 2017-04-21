package com.kicon.ebiz.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ORDERLINETAX")
public class OrderLineTax implements Serializable {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="referenceId")
    private String referenceId;

    @Column(name="name")
    private String name;

    @Column(name="rate")
    private double rate;

    public OrderLineTax() {
    }

    public OrderLineTax(String referenceId, String name, double rate) {
        this.referenceId = referenceId;
        this.name = name;
        this.rate = rate;
    }

    public OrderLineTax(Tax tax) {
        this(tax.getId(), tax.getName(), tax.getRate());
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderLineTax other = (OrderLineTax) obj;
        if ((this.referenceId == null) ? (other.referenceId != null) : !this.referenceId.equals(other.referenceId)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 31 * hash + (this.referenceId != null ? this.referenceId.hashCode() : 0);
        hash = 31 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 31 * hash + (int) (this.rate);
        return hash;
    }
}
