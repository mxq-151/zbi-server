package org.zbi.server.model.core;


/**
 * 查询类型
 * */
public enum QueryType {
	
	EQUAL("equal"),NOTEQUAL("notequal"),LARGE("large"),LARGEEQUAL("largeequal"),LESS("less"),LESSEQUAL("lessequal"),IN("in"), NOTIN("notin");

	private String type;
	
	QueryType(String type)
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
