package org.zbi.server.model.core;
/**
 * 排序类型
 * 默认不排序
 * */
public enum SortType {

	DESC("desc"), ASC("asc"), DEFAULT("default");
	
	private String name;
	
	SortType(String name)
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


