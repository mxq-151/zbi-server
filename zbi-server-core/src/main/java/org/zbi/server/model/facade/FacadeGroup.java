package org.zbi.server.model.facade;

import java.util.List;

public class FacadeGroup {

	/**
	 * 用户组ID
	 */
	private String groupID;

	/**
	 * 用户组名
	 */
	private String groupName;
	
	
	private String groupDesc;

	/**
	 * 用户组内用户
	 */
	private List<String> users;

	public String getGroupID() {
		return groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

}
