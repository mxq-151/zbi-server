package org.zbi.server.entity.mysql;

public class UserInfo {
	
	/**
	 * 用户ID
	 * */
	private String userID;
	
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


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getRoleType() {
		return roleType;
	}


	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

}
