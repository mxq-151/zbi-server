package org.zbi.server.entity.mysql;

public class GroupInfo extends EntityBase{

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
	
	/**
	 * 组内用户数
	 */
	private int userNum;
	
	private int boardNum;

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	
	
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

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

}
