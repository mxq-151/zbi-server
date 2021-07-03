package org.zbi.server.entity.mysql;

public class GroupUser {

	public static final int EDITBOARD = 3;

	public static final int DELBOARD = 5;

	public static final int EDITGROUP = 7;

	public static final int DELGROUP = 11;

	private String groupID;

	private String userID;

	private int authRight;

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getAuthRight() {
		return authRight;
	}

	public void setAuthRight(int authRight) {
		this.authRight = authRight;
	}

}
