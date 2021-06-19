package org.zbi.server.model.core;

public enum ColumnType {
	
	DATE("date"),MONTH("month"),YEAR("year"),WEEK("week"),NUMBER("number"),STRING("string");
	
	private String type;
	
	ColumnType(String type)
	{
		this.type=type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
