package org.zbi.server.model.response;

/**
 * 模型描述
 */
public class ModelDescResp {

	/**
	 * 模型标识
	 */
	private String modelTag;

	/**
	 * 模型名称
	 */
	private String modelName;

	/**
	 * 模型ID
	 */
	private String modelID;

	public String getModelTag() {
		return modelTag;
	}

	public String getModelName() {
		return modelName;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelTag(String modelTag) {
		this.modelTag = modelTag;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

}
