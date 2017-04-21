package com.kicon.ebiz.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
//import java.util.regex.Pattern;
//import java.util.regex.Matcher;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.MatchResult;
//import com.kicon.ebiz.model.UserRole.Role;

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
@Table(name="USER", uniqueConstraints=@UniqueConstraint(columnNames={"shopId", "email"}))
public class User implements Serializable  {
	public static final long serialVersionUID = 1L;
	public static enum Role {OWNER, MANAGER, WORKER, VIEWER, NONE};

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
	@Column(name="role")
	private Role role = Role.NONE;
	
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
	
	// critical info includes email, role, isLocked
	@Column(name="criticalInfoEditable")
	private boolean criticalInfoEditable = true;
	
	public User() {
	}
	
	public User(String email) {
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
		//String emailPattern2 = “.+@.+\\.[a-z]+�?;
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
	
	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}
	
	public int getRoleIndex() {
		if (role == Role.OWNER) return 0;
		else if (role == Role.MANAGER) return 1;
		else if (role == Role.WORKER) return 2;
		else if (role == Role.VIEWER) return 3;
		else if (role == Role.NONE) return 4;
		return -1;
	}
	
    public void copy(User user) {
    	if (user == null) return;
        //shop = user.getShop();
        role = user.role;
        displayName = user.getDisplayName();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        lastName = user.getLastName();
        email = user.getEmail();
    }
    
    public void setRoleByIndex(int i) {	
		if (i == 0) 
			role = Role.OWNER;
		else if (i == 1) 
			role = Role.MANAGER;
		else if (i == 2) 
			role = Role.WORKER;
		else if (i == 3) 
			role = Role.VIEWER;
		else 
			role = Role.NONE;
    }
    
	public String getRoleString() {
		if (role == Role.OWNER) {
			return "chủ nhân";
		} else if (role == User.Role.MANAGER) {
			return "quản lý";
		} else if (role == User.Role.WORKER) {
			return "nhân viên";
		} else if (role == User.Role.VIEWER) {
			return "quan sát viên";
		} else if (role == User.Role.NONE) {
			return "không vai trò";
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
	
	public boolean isQualifiedForRole(Role role) {
		if (role == Role.OWNER 
				&& this.role == Role.OWNER) {
			return true;
			
		} else if (role == Role.MANAGER 
				&& (this.role == Role.OWNER || this.role == Role.MANAGER)) {
			return true;
		
		} else if (role == Role.WORKER 
				&& (this.role == Role.OWNER || this.role == Role.MANAGER || this.role == Role.WORKER)) {
			return true;
		}
		return false;
	}
	
}
