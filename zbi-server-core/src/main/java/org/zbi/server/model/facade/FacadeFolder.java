package org.zbi.server.model.facade;

import java.util.List;

import org.zbi.server.entity.mysql.FolderInfo;
import org.zbi.server.model.response.BoardInfoResp;

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
	private List<BoardInfoResp> boards;

	public List<BoardInfoResp> getBoards() {
		return boards;
	}

	public void setBoards(List<BoardInfoResp> boards) {
		this.boards = boards;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}


}
