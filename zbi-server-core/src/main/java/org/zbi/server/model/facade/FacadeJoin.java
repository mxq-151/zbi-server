package org.zbi.server.model.facade;

import org.zbi.server.model.config.ConfigJoin;

public class FacadeJoin extends ConfigJoin{
	
	private String tableName1;
	
	private String tableName2;
	
	private String colName1;
	
	private String colName2;

	public String getTableName1() {
		return tableName1;
	}

	public void setTableName1(String tableName1) {
		this.tableName1 = tableName1;
	}

	public String getTableName2() {
		return tableName2;
	}

	public void setTableName2(String tableName2) {
		this.tableName2 = tableName2;
	}

	public String getColName1() {
		return colName1;
	}

	public void setColName1(String colName1) {
		this.colName1 = colName1;
	}

	public String getColName2() {
		return colName2;
	}

	public void setColName2(String colName2) {
		this.colName2 = colName2;
	}

}
