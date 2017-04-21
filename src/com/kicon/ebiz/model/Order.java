package com.kicon.ebiz.model;

import com.kicon.ebiz.crud.HasId;
import com.kicon.ebiz.crud.HasName;
import com.kicon.ebiz.crud.HasShortDescription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//import org.hibernate.annotations.GenericGenerator;

/**
 * An order
 */
@Entity
@Table(name = "ORDERMODEL")
public class Order implements Serializable, HasId<String>, HasName, HasShortDescription {

    private static final long serialVersionUID = 38532928449446398L;

    @Id
    @GeneratedValue
    @Column(name="id")
    private String id;

    @Column(name="currency")
    private String currency = "VND";

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="dateCreated")
    private Date dateCreated = new Date();

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="dateChanged")
    private Date dateChanged = new Date();

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="dateInvoiced")
    private Date dateInvoiced = null;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="dateTimelyPayment")
    private Date dateTimelyPayment = null;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.New;
    
    @ManyToOne
    @JoinColumn(name="shopId")
    private Shop shop;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="orderId")
    private List<OrderLine> orderlines = new ArrayList<OrderLine>();

    @Column(name="customerRef")
    private String customerRef;

    @Column(name="number")
    private long number = -1;

    @Column(name="invoiceNumber")
    private long invoiceNumber = -1;
    
    @Column(name="informalName")
    private String informalName;
    
    @Column(name="taxRate")
    private double taxRate = 0;

    
    public Order() {
    }

    public Order(Shop shop) {
        this.shop = shop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Shop getOwner() {
        return shop;
    }
    
    public Shop getShop() {
        return shop;
    }

    public double getTaxRate() {
    	return taxRate;
    }
    
    public void setTaxRate(double taxRate) {
    	this.taxRate = taxRate;
    }
    
    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public Date getDateInvoiced() {
        return dateInvoiced;
    }

    public void setDateInvoiced(Date dateInvoiced) {
        this.dateInvoiced = dateInvoiced;
    }

    public Date getDateTimelyPayment() {
        return dateTimelyPayment;
    }

    public void setDateTimelyPayment(Date dateTimelyPayment) {
        this.dateTimelyPayment = dateTimelyPayment;
    }

    public long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return Long.toString(number);
    }
    
    public void setInformalName(String informalName) {
    	this.informalName = informalName;
    }
    
    public String getInformalName() {
    	return informalName;
    }

    public String getShortDescription() {
        return status.name();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null) {
            currency = "VND";
        }
        this.currency = currency;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date date) {
        this.dateCreated = date;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public String getInfo() {
    	String str = informalName + ", " + getName() 
    			+ ", " + dateCreated + ", " + status;
    	return str;
    }

    public void addOrderLine(Item item) {
        addOrderLine(item, 1);
    }

    /**
     * @ deprecated 
     * @ param line
     */
    public void addOrderLine(OrderLine line) {
        orderlines.add(line);
    }

    public void addOrderLine(Item item, double quantity) {
        if (item == null) {
            throw new IllegalArgumentException("item was null.");

        }

        //If an item like this is already in the order then just increment the quantity
        OrderLine match = getOrderLine(item);
        if (match != null) {
            match.setQuantity(match.getQuantity() + quantity);
            return;
        }

        addOrderLine(item.getName(), quantity, item.getCost(), item.getPrice(), item.getId(), item.getItemNo(), item.getTax());
    }

    public void addOrderLine(String title, double quantity, double cost, double price, Tax tax) {
        addOrderLine(title, quantity, cost, price, null, null, tax);
    }

    public void addOrderLine(String title, double quantity, double cost, double price, String itemId, String itemno) {
        addOrderLine(title, quantity, cost, price, itemId, itemno, null);
    }

    public void addOrderLine(String title, double quantity, double cost, double price, String itemId, String itemno, Tax tax) {

        OrderLineTax lineTax;

        if (tax == null) {
            lineTax = null;
        } else {
            lineTax = new OrderLineTax(tax);
        }

        OrderLine line = new OrderLine();
        line.setTitle(title);
        line.setQuantity(quantity);
        line.setCost(cost);
        line.setPrice(price);
        line.setItemId(itemId);
        line.setTax(lineTax);
        line.setItemNo(itemno);
        
        addOrderLine(line);

    }

    public int getOrderLineCount() {
        return orderlines.size();
    }

    public OrderLine removeOrderLine(int index) {
        Iterator<OrderLine> it = orderlines.iterator();

        int count =0;
        while(it.hasNext()) {
            OrderLine current = it.next();
            if(count==index) {
                it.remove();
                return current;
            }
            count++;
        }
        return null;
    }

    public OrderLine getOrderLine(int index) {
        Iterator<OrderLine> it = orderlines.iterator();
        if(index<0 || index>orderlines.size()) {
            return null;
        }

        int count =0;
        while(it.hasNext()) {
            OrderLine current = it.next();
            if(count==index) {
                return current;
            }
            count++;
        }
        return null;
    }

    public int getOrderLineIndex(OrderLine orderline) {
        Iterator<OrderLine> it = orderlines.iterator();

        int count =0;
        while(it.hasNext()) {
            OrderLine current = it.next();
            if(orderline.equals(current)) {
                return count;
            }
            count++;
        }
        return -1;
    }

    public List<OrderLine> getOrderLines() {
        return new ArrayList<OrderLine>(orderlines);
    }

    public OrderLine getOrderLine(Item item) {
        if (item.getId() == null) {
            return null;
        }

        OrderLineTax lineTax;

        if (item.getTax() == null) {
            lineTax = null;
        } else {
            lineTax = new OrderLineTax(item.getTax());
        }

        for (OrderLine line : orderlines) {
            if (item.getId().equals(line.getItemId())
                    && item.getPrice() == line.getPrice()
                    && isSame(lineTax, line.getTax())) {
                return line;
            }

        }
        return null;
    }

    /**
     * @deprecated
     * @param orderlines
     */
    public void setOrderlines(List<OrderLine> orderlines) {
        this.orderlines = new ArrayList<OrderLine>(orderlines);
    }

    public void clear() {
        status = OrderStatus.New;
        orderlines.clear();
        //payments.clear();
    }
    
    public double getTotalCost() {
        double total = 0;
        for (int i = 0; i < getOrderLineCount(); i++) {
            OrderLine line = getOrderLine(i);
            total += line.getTotalCost();
        }
        return total;
    }
    
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < getOrderLineCount(); i++) {
            OrderLine line = getOrderLine(i);
            total += line.getTotal();
        }
        return total;
    }
    
    public double getQuantity() {
        double total = 0;
        for (int i = 0; i < getOrderLineCount(); i++) {
            OrderLine line = getOrderLine(i);
            total += line.getQuantity();
        }
        return total;
    }

    public double getTotalTax() {
        //return getTotalTax(null);
    	return getTotal() * shop.getTaxRate() / 100;
    }
    
    public double getTotalTaxLocal() {
        //return getTotalTax(null);
    	return getTotal() * taxRate / 100;
    }

    public double getTotalWithTax() {
        return getTotal() + getTotalTax();
    }

    public List<OrderLineTax> getTaxList() {
        List<String> refList = new ArrayList<String>();
        List<OrderLineTax> taxes = new ArrayList<OrderLineTax>();
        for (OrderLine line : orderlines) {
            OrderLineTax tax = line.getTax();
            if (tax != null && tax.getReferenceId() != null
                    && !refList.contains(tax.getReferenceId())) {
                taxes.add(tax);
                refList.add(tax.getReferenceId());
            }
        }
        return taxes;
    }

    public double getTotalTax(OrderLineTax tax) {
        double value = 0;
        for (OrderLine line : orderlines) {
            if (tax == null || hasSameTaxReferenced(tax, line.getTax())) {
                value += line.getTotalTax();
            }
        }
        return value;
    }

    private boolean isSame(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }

        if (obj1 == null) {
            return false;
        }

        return obj1.equals(obj2);
    }

    private boolean hasSameTaxReferenced(OrderLineTax t1, OrderLineTax t2) {
        if (t1 == null || t2 == null) {
            return false;
        }

        if (t1.getReferenceId() == null || t2.getReferenceId() == null) {
            return false;
        }

        return t1.getReferenceId().equals(t2.getReferenceId());
    }
    
}
