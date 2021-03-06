package org.zbi.server.model.config;

import org.zbi.server.entity.mysql.EntityBase;

public class ConfigTable extends EntityBase {

	private String tableID;

	// 表别名
	private String tblAlias;

	// 库名
	private String project;

	// 表名
	private String tableName;

	/**
	 * 1:事实表, 2：维度表：3：子表
	 */
	private int tableType;

	/**
	 * 连接ID
	 * */
	private String connID;

	// 是否为源表
	private boolean source;

	public String getTableID() {
		return tableID;
	}

	public void setTableID(String tableID) {
		this.tableID = tableID;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}

	/**
	 * 连接名
	 */
	private String connName;

	public String getTblAlias() {
		return tblAlias;
	}

	public void setTblAlias(String tblAlias) {
		this.tblAlias = tblAlias;
	}

	public int getTableType() {
		return tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isSource() {
		return source;
	}

	public void setSource(boolean source) {
		this.source = source;
	}

	public String getConnID() {
		return connID;
	}

	public void setConnID(String connID) {
		this.connID = connID;
	}

}
