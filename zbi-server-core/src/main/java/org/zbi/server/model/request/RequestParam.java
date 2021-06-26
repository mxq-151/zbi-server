package org.zbi.server.model.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RequestParam {

	/**
	 * 模型标识
	 */
	@ApiModelProperty(example = "123456", required = true, dataType = "string", value = "模型标识")
	private String modelTag;

	/**
	 * 模型ID
	 */
	@ApiModelProperty(example = "1", required = true, dataType = "int", value = "模型ID")
	private int modelID;

	/**
	 * 维度
	 */
	@ApiModelProperty(required = false, value = "维度")
	private List<RequestColumn> dimensions;

	/**
	 * 度量
	 */
	@ApiModelProperty(required = false, value = "度量")
	private List<RequestColumn> measures;

	/**
	 * 条件
	 */
	@ApiModelProperty(required = false, value = "条件")
	private List<RequestCondition> conditions;

	/***
	 * 起始页
	 */
	@ApiModelProperty(example = "0", required = false, value = "起始页")
	private int offSet;

	/**
	 * 页大小
	 */
	@ApiModelProperty(example = "10", required = false, value = "页大小")
	private int pageSize;

	public List<RequestColumn> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<RequestColumn> dimensions) {
		this.dimensions = dimensions;
	}

	public List<RequestColumn> getMeasures() {
		return measures;
	}

	public void setMeasures(List<RequestColumn> measures) {
		this.measures = measures;
	}

	public List<RequestCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<RequestCondition> conditions) {
		this.conditions = conditions;
	}

	public int getOffSet() {
		return offSet;
	}

	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getModelTag() {
		return modelTag;
	}

	public void setModelTag(String modelTag) {
		this.modelTag = modelTag;
	}

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	}

}
