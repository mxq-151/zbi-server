package org.zbi.server.entity.mysql;

import java.util.Map;

public class BoardInfo extends EntityBase {

	public enum BoardVersion {
		V1, V2, V3, V4
	}

	private String boardID;

	private String boardName;

	private String boardDesc;

	private BoardVersion boardVersion;

	private Map<String, Object> otherParams;

	private String adminID;
	
	private String folderID;

	public String getBoardID() {
		return boardID;
	}

	public void setBoardID(String boardID) {
		this.boardID = boardID;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public Map<String, Object> getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(Map<String, Object> otherParams) {
		this.otherParams = otherParams;
	}

	public String getBoardDesc() {
		return boardDesc;
	}

	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}

	public BoardVersion getBoardVersion() {
		return boardVersion;
	}

	public void setBoardVersion(BoardVersion boardVersion) {
		this.boardVersion = boardVersion;
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public String getFolderID() {
		return folderID;
	}

	public void setFolderID(String folderID) {
		this.folderID = folderID;
	}

}
