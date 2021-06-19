package org.zbi.server.entity.mysql;

public class GroupInfo {

	/**
	 * 用户组ID
	 */
	private int groupID;

	/**
	 * 用户组名
	 */
	private String groupName;

	/**
	 * 描述
	 */
	private String groupDesc;
	
	
	public int createrID;

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
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

	public int getCreaterID() {
		return createrID;
	}

	public void setCreaterID(int createrID) {
		this.createrID = createrID;
	}

}
