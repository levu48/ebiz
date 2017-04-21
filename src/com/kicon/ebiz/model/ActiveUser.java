package com.kicon.ebiz.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ActiveUser implements Serializable  {
	public static final long serialVersionUID = 1L;
	
	private String email;
	private String displayName;
	private Account account;
	private List<User> userList;
	
	public ActiveUser() {}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public List<User> getUserList() {
		return userList;
	}
	
	public List<Shop> getShopList() {
		List<Shop> shopList = new ArrayList<Shop>();
		for (User user:userList) {
			Shop shop = user.getShop();
			shopList.add(shop);
		}
		return shopList;
	}
	
	public boolean isManagement() {
		if (account != null && !account.isLocked()) {
			return true;
		}
		for (User user:userList) {
			User.Role role = user.getRole();
			if (role.equals(User.Role.OWNER)|| role.equals(User.Role.MANAGER)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isOwner() {
		if (account != null && !account.isLocked()) {
			return true;
		}
		return false;
	}

}
