package org.zbi.server.entity.mysql;

import org.zbi.server.model.core.EngineType;

public class ConnInfo extends EntityBase{
	
	private String connID;
	
	private String url;
	
	private EngineType engineType;
	
	private String connName;

	public String getConnID() {
		return connID;
	}

	public void setConnID(String connID) {
		this.connID = connID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}


}
