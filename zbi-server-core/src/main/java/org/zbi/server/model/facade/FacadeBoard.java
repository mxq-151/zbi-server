package org.zbi.server.model.facade;

import java.util.ArrayList;
import java.util.List;

import org.zbi.server.entity.mysql.BoardInfo;

import io.swagger.annotations.ApiModelProperty;

public class FacadeBoard extends BoardInfo {

	private String folderName;

	/**
	 * 报表ID,报表样式
	 */
	@ApiModelProperty(example = "", required = false, value = "看板下报表ID", dataType = "List")
	private List<String> reports = new ArrayList<>();


	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}
