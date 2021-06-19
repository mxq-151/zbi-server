package org.zbi.server.model.facade;

import java.util.List;

public class FacadeUser {
	
	/**
	 * 用户ID
	 * */
	private long userID;
	
	/**
	 * 邮箱
	 * */
	private String email;
	
	/**
	 * 用户名
	 * */
	private String userName;
	
	
	/**
	 * 
	 * 用户密码
	 * */
	private String password;
	
	
	/**
	 * 
	 * 用户类型
	 * 1：超级用户
	 * 2：部门管理人员
	 * 3.普通成员
	 * */
	private int roleType;
	
	/**
	 * 管理的用户组
	 * **/
	private List<Long> adminGroups;
	
	/**
	 * 所属的用户组
	 * */
	private List<Long> groups;

	public long getUserID() {
		return userID;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserID(long userID) {
		this.userID = userID;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public List<Long> getAdminGroups() {
		return adminGroups;
	}

	public List<Long> getGroups() {
		return groups;
	}

	public void setAdminGroups(List<Long> adminGroups) {
		this.adminGroups = adminGroups;
	}

	public void setGroups(List<Long> groups) {
		this.groups = groups;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
