package org.zbi.server.entity.mysql;

public class QueryModel extends EntityBase{

	private String modelName;

	private String modelID;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

}
