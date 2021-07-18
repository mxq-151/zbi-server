package org.zbi.server.model.facade;

import java.util.List;

import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.entity.mysql.FolderInfo;

import io.swagger.annotations.ApiModelProperty;

public class FacadeFolder extends FolderInfo {

	/**
	 * 看板数
	 */
	private int boardNum;

	/**
	 * 看板
	 */
	@ApiModelProperty(required = false, value = "文件夹下看板ID")
	private List<BoardInfo> boards;

	public List<BoardInfo> getBoards() {
		return boards;
	}

	public void setBoards(List<BoardInfo> boards) {
		this.boards = boards;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}


}
