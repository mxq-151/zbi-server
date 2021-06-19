package org.zbi.server.model.facade;

import java.util.List;

public class FacadeGroup {
	
	/**
	 * 用户组ID
	 * */
	private int groupID;
	
	/**
	 * 用户组名
	 * */
	private String groupName;
	
	/**
	 * 用户组内用户
	 * */
	private List<Integer> users;
	
	
	/**
	 * 用户组管理员
	 * **/
	private List<Integer> admins;


	public int getGroupID() {
		return groupID;
	}


	public String getGroupName() {
		return groupName;
	}


	public List<Integer> getUsers() {
		return users;
	}


	public List<Integer> getAdmins() {
		return admins;
	}


	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public void setUsers(List<Integer> users) {
		this.users = users;
	}


	public void setAdmins(List<Integer> admins) {
		this.admins = admins;
	}

}
