package org.zbi.server.model.request;

import org.zbi.server.model.core.SortType;

import io.swagger.annotations.ApiModelProperty;

public class RequestMeasure {
	
	
	@ApiModelProperty( required = true, value = "唯一ID")
	private String uuid;
	
	@ApiModelProperty(example = "DEFAULT", required = true, value = "排序类型")
	private SortType sortType;


	@ApiModelProperty(example = "0", required = true, dataType="Boolean" ,value = "是否为新字段")
	private boolean isAdvanced;
	
	@ApiModelProperty(example = "12121", required = false, value = "新字段表达式")
	private String newField;
	
	@ApiModelProperty(example = "高级字段1", required = false, value = "新字段名称")
	private String newFieldName;
	
	private int sortOrder;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public SortType getSortType() {
		return sortType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	public String getNewField() {
		return newField;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

	public String getNewFieldName() {
		return newFieldName;
	}

	public void setNewFieldName(String newFieldName) {
		this.newFieldName = newFieldName;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isAdvanced() {
		return isAdvanced;
	}

	public void setAdvanced(boolean isAdvanced) {
		this.isAdvanced = isAdvanced;
	}

}
