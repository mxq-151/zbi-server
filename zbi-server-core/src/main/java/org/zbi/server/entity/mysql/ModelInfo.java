package org.zbi.server.entity.mysql;

import org.zbi.server.model.core.EngineType;

public class ModelInfo {
	
	/**
	 * 模型ID
	 * */
	private String modelID;
	
	
	/**
	 * 使用的链接的名称
	 * */
	private String connName;
	
	
	/**
	 * 模型标识
	 * */
	private String modelTag;
	
	/**
	 * 模型名称
	 * */
	private String modelName;
	
	/**
	 * 引擎类型
	 * */
	private EngineType engineType;
	

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}

	public String getModelTag() {
		return modelTag;
	}

	public void setModelTag(String modelTag) {
		this.modelTag = modelTag;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

}
