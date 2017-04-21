package com.kicon.ebiz.model;

import com.kicon.ebiz.crud.HasId;
import com.kicon.ebiz.crud.HasName;
import com.kicon.ebiz.crud.HasShortDescription;
import com.kicon.ebiz.model.User.Role;

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
@Table(name = "WarehouseOrder")
public class WarehouseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    public static enum OrderType {RECEIVING, SHIPPING};

    @Id
    @GeneratedValue
    @Column(name="id")
    private String id;
    
	@Enumerated(EnumType.STRING)
	@Column(name="orderType")
	private OrderType orderType = OrderType.RECEIVING;
	
    @ManyToOne
    @JoinColumn(name="warehouseId")
    private Warehouse warehouse;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="warehouseOrderId")
    private List<WarehouseOrderLine> warehouseOrderLines = new ArrayList<WarehouseOrderLine>();
	
    @ManyToOne
    @JoinColumn(name="categoryElementId")
    private CategoryElement categoryElement;
    
    @Column(name="customerRef")
    private String customerRef;
    
    @ManyToOne
    @JoinColumn(name="businessEntityId")
    private BusinessEntity businessEntity;
    
    @Column(name="businessName")
    private String businessName;
    
    @Column(name="transactionRecord") 	// chứng từ
    private String transactionRecord;

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

    @Column(name="number")
    private long number = -1;

    @Column(name="invoiceNumber")
    private long invoiceNumber = -1;
    
    @Column(name="informalName")
    private String informalName;
    
    @Column(name="taxRate")
    private double taxRate = 0;

    
    
    
    public WarehouseOrder() {
    }
    public WarehouseOrder(OrderType orderType) {
    	this.orderType = orderType;
    }

    public WarehouseOrder(OrderType orderType, Warehouse warehouse) {
    	this.orderType = orderType;
        this.warehouse = warehouse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public CategoryElement getCategoryElement() {
    	return categoryElement;
    }
    
    public void setCategoryElement(CategoryElement categoryElement) {
    	this.categoryElement = categoryElement;
    }
    
    public void setOrderType(OrderType orderType) {
    	this.orderType = orderType;
    }
    
    public OrderType getOrderType() {
    	return orderType;
    }
    
    public Warehouse getWarehouse() {
        return warehouse;
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

    public void addWarehouseOrderLine(Item item) {
        addWarehouseOrderLine(item, 1);
    }

    /**
     * @ deprecated 
     * @ param line
     */
    public void addWarehouseOrderLine(WarehouseOrderLine line) {
        warehouseOrderLines.add(line);
    }

    public void addWarehouseOrderLine(Item item, double quantity) {
        if (item == null) {
            throw new IllegalArgumentException("item was null.");

        }

        //If an item like this is already in the order then just increment the quantity
        WarehouseOrderLine match = getWarehouseOrderLine(item);
        if (match != null) {
            match.setQuantity(match.getQuantity() + quantity);
            return;
        }

        addWarehouseOrderLine(item.getName(), quantity, item.getCost(), item.getPrice(), item.getId(), item.getItemNo(), item.getTax());
    }

    public void addWarehouseOrderLine(String title, double quantity, double cost, double price, Tax tax) {
        addWarehouseOrderLine(title, quantity, cost, price, null, null, tax);
    }

    public void addWarehouseOrderLine(String title, double quantity, double cost, double price, String itemId, String itemno) {
        addWarehouseOrderLine(title, quantity, cost, price, itemId, itemno, null);
    }

    public void addWarehouseOrderLine(String title, double quantity, double cost, double price, String itemId, String itemno, Tax tax) {

        OrderLineTax lineTax;

        if (tax == null) {
            lineTax = null;
        } else {
            lineTax = new OrderLineTax(tax);
        }

        WarehouseOrderLine line = new WarehouseOrderLine();
        line.setTitle(title);
        line.setQuantity(quantity);
        line.setCost(cost);
        line.setPrice(price);
        //line.setItemId(itemId);
        line.setTax(lineTax);
        line.setItemNo(itemno);
        
        addWarehouseOrderLine(line);

    }

    public int getWarehouseOrderLineCount() {
        return warehouseOrderLines.size();
    }

    /* to be deleted
    public WarehouseOrderLine removeWarehouseOrderLine(int index) {
        Iterator<WarehouseOrderLine> it = warehouseOrderlines.iterator();

        int count =0;
        while(it.hasNext()) {
            WarehouseOrderLine current = it.next();
            if(count==index) {
                it.remove();
                return current;
            }
            count++;
        }
        return null;
    }
    */

    public void removeWarehouseOrderLine(WarehouseOrderLine line) {
    	warehouseOrderLines.remove(line);
    }
    
    public WarehouseOrderLine getWarehouseOrderLine(int index) {
        Iterator<WarehouseOrderLine> it = warehouseOrderLines.iterator();
        if(index<0 || index > warehouseOrderLines.size()) {
            return null;
        }

        int count =0;
        while(it.hasNext()) {
            WarehouseOrderLine current = it.next();
            if(count==index) {
                return current;
            }
            count++;
        }
        return null;
    }

    public int getOrderLineIndex(WarehouseOrderLine warehouseOrderline) {
        Iterator<WarehouseOrderLine> it = warehouseOrderLines.iterator();

        int count =0;
        while(it.hasNext()) {
            WarehouseOrderLine current = it.next();
            if(warehouseOrderline.equals(current)) {
                return count;
            }
            count++;
        }
        return -1;
    }

    public List<WarehouseOrderLine> getWarehouseOrderLines() {
        return new ArrayList<WarehouseOrderLine>(warehouseOrderLines);
    }

    public WarehouseOrderLine getWarehouseOrderLine(Item item) {
        if (item.getId() == null) {
            return null;
        }

        OrderLineTax lineTax;

        if (item.getTax() == null) {
            lineTax = null;
        } else {
            lineTax = new OrderLineTax(item.getTax());
        }

        for (WarehouseOrderLine line : warehouseOrderLines) {
            if (item.getId().equals(line.getItemId())
                    && item.getPrice() == line.getPrice()
                    && isSame(lineTax, line.getTax())) {
                return line;
            }

        }
        return null;
    }


    public void setWarehouseOrderLines(List<WarehouseOrderLine> warehouseOrderLines) {
        this.warehouseOrderLines = new ArrayList<WarehouseOrderLine>(warehouseOrderLines);
    }
    
    public void setWarehouse(Warehouse warehouse) {
    	this.warehouse = warehouse;
    }

    public void clear() {
        status = OrderStatus.New;
        warehouseOrderLines.clear();
        //payments.clear();
    }
    
    public double getTotalCost() {
        double total = 0;
        for (int i = 0; i < getWarehouseOrderLineCount(); i++) {
            WarehouseOrderLine line = getWarehouseOrderLine(i);
            total += line.getTotalCost();
        }
        return total;
    }
    
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < getWarehouseOrderLineCount(); i++) {
            WarehouseOrderLine line = getWarehouseOrderLine(i);
            total += line.getTotal();
        }
        return total;
    }
    
    public double getQuantity() {
        double total = 0;
        for (int i = 0; i < getWarehouseOrderLineCount(); i++) {
            WarehouseOrderLine line = getWarehouseOrderLine(i);
            total += line.getQuantity();
        }
        return total;
    }

    public double getTotalTax() {
        //return getTotalTax(null);
    	return getTotal() * warehouse.getShop().getTaxRate() / 100;
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
        for (WarehouseOrderLine line : warehouseOrderLines) {
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
        for (WarehouseOrderLine line : warehouseOrderLines) {
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
    
    public void setBusinessName(String businessName) {
    	this.businessName = businessName;
    }
    
    public String getBusinessName() {
    	return businessName;
    }
    
    public void setTransactionRecord(String transactionRecord) {
    	this.transactionRecord = transactionRecord;
    }
    
    public String getTransactionRecord() {
    	return transactionRecord;
    }
    
    public void setBusinessEntity(BusinessEntity businessEntity) {
    	this.businessEntity = businessEntity;
    }
    
    public BusinessEntity getBusinessEntity() {
    	return businessEntity;
    }
    
}
