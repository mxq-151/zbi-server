package org.zbi.server.entity.mysql;

public class ConnParam {
	
	private String connID;
	
	private String paramKey;
	
	private String paramVal;

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public String getConnID() {
		return connID;
	}

	public void setConnID(String connID) {
		this.connID = connID;
	}

}
