package org.zbi.server.model.config;

import com.alibaba.fastjson.annotation.JSONField;

public class QuerySqlModel {

	/**
	 * 所属project
	 */
	private String project;

	/**
	 * 主表
	 */
	@JSONField(serialize = true)
	private String factTable;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getFactTable() {
		return factTable;
	}

	public void setFactTable(String factTable) {
		this.factTable = factTable;
	}

}
