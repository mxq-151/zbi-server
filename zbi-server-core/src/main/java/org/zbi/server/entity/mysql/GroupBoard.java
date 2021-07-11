package org.zbi.server.entity.mysql;

public class GroupBoard extends EntityBase{
	
	private String boardID;
	
	private String groupID;

	public String getBoardID() {
		return boardID;
	}

	public void setBoardID(String boardID) {
		this.boardID = boardID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
}
