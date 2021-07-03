package org.zbi.server.entity.mysql;

public class GroupInfo {

	/**
	 * 用户组ID
	 */
	private String groupID;

	/**
	 * 用户组名
	 */
	private String groupName;

	/**
	 * 描述
	 */
	private String groupDesc;
	
	
	public String adminID;

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

}
