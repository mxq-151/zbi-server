package org.zbi.server.model.response;

import org.zbi.server.entity.mysql.EntityBase;

public class BoardInfoResp extends EntityBase{
	
	private String boardName;
	
	private String boardID;
	
	public BoardInfoResp()
	{
		
	}
	
	public BoardInfoResp(String boardName,String boardID)
	{
		this.boardID=boardID;
		this.boardName=boardName;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getBoardID() {
		return boardID;
	}

	public void setBoardID(String boardID) {
		this.boardID = boardID;
	}

}
