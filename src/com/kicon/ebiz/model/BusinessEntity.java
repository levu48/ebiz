package com.kicon.ebiz.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.MatchResult;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
@Table(name="BusinessEntity", uniqueConstraints=@UniqueConstraint(columnNames={"shopId", "email"}))
public class BusinessEntity implements Serializable  {
	public static final long serialVersionUID = 1L;
	public static enum Relation {CUSTOMER, SUPPLIER, BOTH, NONE};

	@Id
	@GeneratedValue
	@Column(name="id")
	private String id;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne
    @JoinColumn(name="shopId", nullable=false)
	private Shop shop;
	
	@Enumerated(EnumType.STRING)
	@Column(name="relation")
	private Relation relation = Relation.NONE;
	
	@Column(name="displayName")
	private String displayName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="middleName")
	private String middleName;
	
	@Column(name="isLocked")
	private boolean isLocked = false;
	
	// critical info includes email, relation, isLocked
	@Column(name="criticalInfoEditable")
	private boolean criticalInfoEditable = true;
	
	public BusinessEntity() {
	}
	
	public BusinessEntity(String email) {
		this.email = email;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public Shop getShop() {
		return shop;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public boolean isValidEmail() {
		//String emailPattern2 = “.+@.+\\.[a-z]+”;
		String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		//Pattern p = Pattern.compile(emailPattern);
		//Matcher m = p.matcher(email);
		//boolean matchFound = m.matches();
		
		RegExp regExp = RegExp.compile(emailPattern);
		MatchResult matcher = regExp.exec(email);
		boolean matchFound = regExp.test(email);
		
		if (matchFound) {
			return true;
		} 
		return false;
	}
	
	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public Relation getRelation() {
		return relation;
	}
	
	public int getRelationIndex() {
		if (relation == Relation.CUSTOMER) return 0;
		else if (relation == Relation.SUPPLIER) return 1;
		else if (relation == Relation.BOTH) return 2;
		else if (relation == Relation.NONE) return 3;
		return -1;
	}
	
    public void copy(BusinessEntity businessEntity) {
    	if (businessEntity == null) return;
        //shop = businessEntity.getShop();
        relation = businessEntity.relation;
        displayName = businessEntity.getDisplayName();
        firstName = businessEntity.getFirstName();
        middleName = businessEntity.getMiddleName();
        lastName = businessEntity.getLastName();
        email = businessEntity.getEmail();
    }
    
    public void setRelationByIndex(int i) {	
		if (i == 0) 
			relation = Relation.CUSTOMER;
		else if (i == 1) 
			relation = Relation.SUPPLIER;
		else if (i == 2) 
			relation = Relation.BOTH;
		else 
			relation = Relation.NONE;
    }
    
	public String getRelationString() {
		if (relation == Relation.CUSTOMER) {
			return "khách hàng";
		} else if (relation == BusinessEntity.Relation.SUPPLIER) {
			return "nhà cung cấp";
		} else if (relation == BusinessEntity.Relation.BOTH) {
			return "cả hai";
		} else if (relation == BusinessEntity.Relation.NONE) {
			return "chưa xác định";
		}
		return null;
	}
	
	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public boolean getIsLocked() {
		return isLocked;
	}
	
	public void setCriticalInfoEditable(boolean criticalInfoEditable) {
		this.criticalInfoEditable = criticalInfoEditable;
	}
	
	public boolean getCriticalInfoEditable() {
		return criticalInfoEditable;
	}
	
	public boolean isQualifiedForRelation(Relation relation) {
		/*
		if (relation == Relation.CUSTOMER 
				&& this.relation == Relation.CUSTOMER) {
			return true;
			
		} else if (relation == Relation.SUPPLIER 
				&& (this.relation == Relation.SUPPLIER || this.relation == Relation.SUPPLIER)) {
			return true;
		
		} else if (relation == Relation.BOTH 
				&& (this.relation == Relation.OWNER || this.relation == Relation.MANAGER || this.relation == Relation.WORKER)) {
			return true;
		}
		*/
		return false;
	}
	
}
