package org.zbi.server.model.facade;

import java.util.List;

public class FacadeUser {

	/**
	 * 用户ID
	 */
	private String userID;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 
	 * 用户类型 1：超级用户 2：部门管理人员 3.普通成员
	 */
	private int roleType;

	private List<Integer> rights;

	public String getUserID() {
		return userID;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserID(String userID) {
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

	public List<Integer> getRights() {
		return rights;
	}

	public void setRights(List<Integer> rights) {
		this.rights = rights;
	}

}
