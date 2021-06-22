package org.zbi.server.model.facade;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class FacadeBoard {

	/**
	 * 看板ID
	 */
	@ApiModelProperty(example = "-1", required = false, value = "看板ID")
	private String boardID;

	/**
	 * 看板名称
	 */
	@ApiModelProperty(example = "大数据测试2", required = true, value = "看板名称")
	private String boardName;

	@ApiModelProperty(example = "这是个测试看板", required = true, value = "看板描述")
	private String boardDesc;

	/**
	 * 所属文件夹
	 */
	@ApiModelProperty(example = "1", required = true, value = "所属文件夹ID")
	private String folderID;

	@ApiModelProperty(required = false, value = "其他参数")
	private Map<String, Object> otherParams;

	/**
	 * 报表ID,报表样式
	 */
	@ApiModelProperty(example = "", required = false, value = "看板下报表ID", dataType = "List")
	private List<String> reports = null;

	public String getBoardID() {
		return boardID;
	}

	public String getBoardName() {
		return boardName;
	}

	public String getFolderID() {
		return folderID;
	}

	public void setBoardID(String boardID) {
		this.boardID = boardID;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public void setFolderID(String folderID) {
		this.folderID = folderID;
	}

	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
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

}
