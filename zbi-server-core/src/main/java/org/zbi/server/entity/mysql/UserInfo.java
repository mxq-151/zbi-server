package org.zbi.server.entity.mysql;

import java.sql.Timestamp;

public class UserInfo extends EntityBase{

	/**
	 * 超级管理员
	 */
	public static final int SUPERADMIN = 1;

	/**
	 * 部门管理员
	 */
	public static final int DEPARTMENTADMIN = 2;

	/**
	 * 组管理员
	 */
	public static final int GROUPADMIN = 3;

	/**
	 * 开发者
	 */
	public static final int DEVELOPER = 4;

	/**
	 * 普通成员
	 */
	public static final int MEMBERS = 5;

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
	 * 用户密码
	 */
	private String password;

	/**
	 * 
	 * 用户类型 1：超级用户 2：部门管理人员 3：组管理人员 4：开发人员 5：普通成员
	 */
	private int roleType;

	// 最后登录
	private Timestamp lastLogin;
	
	// 登录次数
	private int loginNum;

	/**
	 * 部门
	 */
	private String department;

	/**
	 * 是否离职
	 */
	private boolean leave;

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

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isLeave() {
		return leave;
	}

	public void setLeave(boolean leave) {
		this.leave = leave;
	}

}
