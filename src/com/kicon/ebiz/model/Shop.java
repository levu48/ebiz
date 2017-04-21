package com.kicon.ebiz.model;

/*
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
*/

//import dk.apaq.crud.HasId;
//import com.kicon.pos.config.Config;
import com.kicon.ebiz.crud.HasId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.CollectionTable;
//import org.hibernate.annotations.GenericGenerator;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="SHOP")
public class Shop implements Serializable, HasId<String> {
	private static final long serialVersionUID = 1L;
	public final static String CAT_STATION = "station";
	public final static String CAT_ITEM = "item";
	public final static String CAT_UNIT = "unit";
	public final static String CAT_CONTENT = "content"; // hàm lượng
	public final static String CAT_RECEIVING = "receiving.type"; // hình thức nhập
	public final static String CAT_SHIPPING = "shipping.type"; // hình thức xuất

    @Id
	@GeneratedValue
    @Column(name="id")
    private String id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="streetNo")
    private String streetNo;

    @Column(name="road")
    private String road;
    
    @Column(name="zipCode")
    private String zipCode;
    
    @Column(name="city")
    private String city;

    @Column(name="phoneNo")
    private String phoneNo;
    
    @Column(name="faxNo")
    private String faxNo;

    @Column(name="email")
    private String email;
    
    @Column(name="countryCode")
    private String countryCode = "VN";

    @Column(name="language")
    private String language;
    
    @Column(name="currency")
    private String currency = "USD";

    @Column(name="cvr")
    private String cvr;
    
    @Column(name="bankAccount")
    private String bankAccount;

    @Column(name="web")
    private String web;
    
    @Column(name="logoUrl")
    private String logoUrl;

    /*
    @Column(name="backendType")
    @Enumerated(EnumType.STRING)
    private QashBackendType backendType;
	*/
    
    @Column(name="backendUrl")
    private String backendUrl;
    
    @Column(name="receiptNode")
    private String receiptNote;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="serviceLevelExpireDate")
    private Date serviceLevelExpireDate = null;
    
    /*
    @Column(name="serviceLevel")
    @Enumerated(EnumType.STRING)
    private ServiceLevel serviceLevel = ServiceLevel.Basic;
	*/
  
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="shop")
	private Set<User> users;
	
    @Column(name="createdBy")
    private String createdBy;

    @Column(name="initialOrderNumber")
    private long initialOrderNumber=1;


    @Column(name="initialInvoiceNumber")
    private long initialInvoiceNumber=1;

    @Column(name="paymentPeriodInDays")
    private int paymentPeriodInDays=8;
    
    //@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    //@JoinColumn(name="taxId")
    //private Tax tax;
    
    @Column(name="taxRate")
    private int taxRate = 0;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="shop")
	private Set<Category> categories;
	
	/*
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name ="SHOP_WAREHOUSE",
    	joinColumns = @JoinColumn(name="shopId"),
    	inverseJoinColumns = @JoinColumn(name="warehouseId")
		)
	private Set<Warehouse> warehouses;
	*/
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="shop")
	private List<Warehouse> warehouses;

    public Shop() {
		//this.roles = new HashSet<UserRole>();
    	users = new HashSet<User>();
    	warehouses = new ArrayList<Warehouse>();
    	setCategories();
    	
    	//categories = new HashSet<Category>();
    	//this.stationCategories = new ArrayList<Category>();
    	//this.itemCategories = new ArrayList<Category>();
    	//this.tax = new Tax("VAT", 0);
    	//this.tax.setShop(this);
    }
    
    public Shop(String name) {
    	setName(name);
		//this.roles = new HashSet<UserRole>();
    	users = new HashSet<User>();
    	warehouses = new ArrayList<Warehouse>();
    	setCategories();
    	
    	//categories = new HashSet<Category>();
    	//this.stationCategories = new ArrayList<Category>();
    	//this.itemCategories = new ArrayList<Category>();
    	//this.tax = new Tax("VAT", 0);
    	//this.tax.setShop(this);
    }
    
    private void setCategories() {
    	categories = new HashSet<Category>();
    	categories.add(new Category(CAT_STATION, this));
    	categories.add(new Category(CAT_ITEM, this));
    	categories.add(new Category(CAT_UNIT, this));
    	categories.add(new Category(CAT_CONTENT, this));
    	categories.add(new Category(CAT_RECEIVING, this));
    	categories.add(new Category(CAT_SHIPPING, this));
    }
    

    /**
     * Retrieves the id of the shop.
     * @return The id of the shop.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the shop. This is typically filled out by the datastore.
     * @param id The id of the shop.
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public long getInitialInvoiceNumber() {
        return initialInvoiceNumber;
    }

    public void setInitialInvoiceNumber(long initialInvoiceNumber) {
        this.initialInvoiceNumber = initialInvoiceNumber;
    }

    public long getInitialOrdernumber() {
        return initialOrderNumber;
    }

    public void setInitialOrdernumber(long initialOrderNumber) {
        this.initialOrderNumber = initialOrderNumber;
    }

    public int getPaymentPeriodInDays() {
        return paymentPeriodInDays;
    }

    public void setPaymentPeriodInDays(int paymentPeriodInDays) {
        this.paymentPeriodInDays = paymentPeriodInDays;
    }

    /**
     * Retrieves the note to put on the end of receipts.
     * @return
     */
    public String getReceiptNote() {
        return receiptNote;
    }

    /**
     * Sets the receiptnote to put on hte end of a receipt
     * @param receiptNote The note to put at the end of a receipt.
     */
    public void setReceiptNote(String receiptNote) {
        this.receiptNote = receiptNote;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }

    /*
    public ServiceLevel getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(ServiceLevel serviceLevel) {
        this.serviceLevel = serviceLevel;
    }
    */

    public Date getServiceLevelExpireDate() {
        return serviceLevelExpireDate;
    }

    public void setServiceLevelExpireDate(Date serviceLevelExpireDate) {
        this.serviceLevelExpireDate = serviceLevelExpireDate;
    }
    
    /*
    public Tax getTax() {
    	return tax;
    }

    public void setTax(Tax tax) {
    	this.tax = tax;
    }
    */
  
    public void setTaxRate(int taxRate) {
    	this.taxRate = taxRate;
    }
    
    public void setTaxRate(String taxRateStr) {
    	this.taxRate = Integer.parseInt(taxRateStr);
    }
    
    public int getTaxRate() {
    	return taxRate;
    }
    /**
     * Retrieves the url to where the shops logo is to be found.
     * @return Url to the shops logo.
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Sets the url to the shops logo.
     * @param logoUrl Url tot he shops logo.
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * Retrieves a list of users that has access to this shop. The list can be manipulated to add/remove users when persisted.
     * @return The list of users.
     */
    /*
    public List<User> getUsers() {
        return users;
    }
	*/
    /**
     * Sets the list of users that has access to this shops.
     * @param users
     */
    /*
    public void setUsers(List<User> users) {
        this.users = users;
    }
	*/
    
    /**
     * Retrieves the user that created this shop.
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user that created the shop.
     * @param createdBy The name of the user.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /*
    public QashBackendType getBackendType() {
        return backendType;
    }

    public void setBackendType(QashBackendType backendType) {
        this.backendType = backendType;
    }
    */

    public String getBackendUrl() {
        return backendUrl;
    }

    public void setBackendUrl(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        if (countryCode == null) {
            countryCode = "US";
        }
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null) {
            currency = "USD";
        }
        this.currency = currency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
	
	public void setUsers(Set<User> set) {
		this.users = set;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	
    
    public void copy(Shop shop) {
    	if (shop == null) return;

    	name = shop.getName();
    	road = shop.getRoad();
    	city = shop.getCity();
    	phoneNo = shop.getPhoneNo();
    	faxNo = shop.getFaxNo();
    	email = shop.getEmail();
    	web = shop.getWeb();
    	taxRate = shop.getTaxRate();
    	receiptNote = shop.getReceiptNote();
    }
    
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	public Set<Category> getCategories() {
		return categories;
	}
	
	public void addCategory(Category category) {
		if (category == null) return;
		if (!categories.contains(category)) {
			categories.add(category);
		}
		category.setShop(this);
	}
	
	public void removeCategory(Category category) {
		if (category == null) return;
		if (categories.contains(category)) {
			categories.remove(category);
		}
		category.setShop(null);
	}
	
	public boolean replaceNewCategory(Category category) {
		if (category == null) return false;
		boolean found = false;
		for(Category cat:categories) {
			if (category.getId().equals(cat.getId())) {
				cat = category;
				found = true;
				break;
			}
		}
		return found;
	}
	
	public List<CategoryElement> getElementsByCategory(String categoryName) {
		if (categoryName == null || categoryName.equals("")) return null;
		for(Category cat:categories) {
			if (categoryName.equals(cat.getName())) {
				return cat.getElements();
			}
		}
		return null;
	}
	
	 public List<Category> getItemCategories() {
	    	return null;
	    }
    
	 public void setWarehouses(List<Warehouse> warehouses) {
	    	this.warehouses = warehouses;
	    }
	    
	    public List<Warehouse> getWarehouses() {
	    	return warehouses;
	    }
	    
	    public void addWarehouse(Warehouse warehouse) {
	    	warehouses.add(warehouse);
	    }
	    
	    public void removeWarehouse(Warehouse warehouse) {
	    	warehouses.remove(warehouse);
	    }
}
