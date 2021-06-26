package org.zbi.server.model.config;

public class QueryColumn {
	
	private String modelTag;
	
	private String colID;
	
	private String fromTable;

	public String getModelTag() {
		return modelTag;
	}

	public void setModelTag(String modelTag) {
		this.modelTag = modelTag;
	}

	public String getColID() {
		return colID;
	}

	public void setColID(String colID) {
		this.colID = colID;
	}

	public String getFromTable() {
		return fromTable;
	}

	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}

}
