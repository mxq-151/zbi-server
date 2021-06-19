package org.zbi.server.model.core;

/**
 * 聚合类型
 * */
public enum AggType {
	
	SUM("sum"), COUNT("count"), MAX("max"), MIN("min"), DISTINCT("distinct"),
    AVG("avg"), COUNTDISTINCT("countdistinct"), NONE("none"),COMPLEX("complex");
	
	private String name;
	
	AggType(String name)
	{
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
