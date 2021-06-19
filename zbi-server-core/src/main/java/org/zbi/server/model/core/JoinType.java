package org.zbi.server.model.core;

public enum JoinType {

	LEFT_JION("left join"), INNER_JION("inner join"), RIGHT_JION("right join");
	private String type;

	JoinType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
