package org.zbi.server.model.facade;

import java.util.List;

import org.zbi.server.model.response.BoardInfoResp;

import io.swagger.annotations.ApiModelProperty;

public class FacadeFolder {

	/**
	 * 文件夹ID
	 */
	@ApiModelProperty(example = "1", required = false, value = "文件夹ID")
	private String folderID;

	/**
	 * 文件夹名称
	 */
	@ApiModelProperty(example = "测试", required = true, value = "文件夹名称")
	private String folderName;

	/**
	 * 文件夹名称
	 */
	@ApiModelProperty(example = "测试", required = true, value = "文件夹描述")
	private String folderDesc;

	/**
	 * 创建者ID
	 */
	@ApiModelProperty(example = "1", required = false, value = "创建者ID")
	private int createrID;

	/**
	 * 所属用户
	 */
	@ApiModelProperty(example = "1", required = false, value = "所属用ID")
	private int belongID;

	/**
	 * 看板
	 */
	@ApiModelProperty(required = false, value = "文件夹下看板ID")
	private List<BoardInfoResp> boards;

	public String getFolderID() {
		return folderID;
	}

	public void setFolderID(String folderID) {
		this.folderID = folderID;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getCreaterID() {
		return createrID;
	}

	public void setCreaterID(int createrID) {
		this.createrID = createrID;
	}

	public int getBelongID() {
		return belongID;
	}

	public void setBelongID(int belongID) {
		this.belongID = belongID;
	}

	public List<BoardInfoResp> getBoards() {
		return boards;
	}

	public void setBoards(List<BoardInfoResp> boards) {
		this.boards = boards;
	}

	public String getFolderDesc() {
		return folderDesc;
	}

	public void setFolderDesc(String folderDesc) {
		this.folderDesc = folderDesc;
	}

}
