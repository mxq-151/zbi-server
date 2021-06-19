package org.zbi.server.model.request;

import org.zbi.server.model.core.SortType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用于请求
 * */
@ApiModel
public class RequestColumn{
	
	@ApiModelProperty(example="2489fa3a-cf1b-42a1-bb64-895b3ea490031", required = true, value = "唯一ID")
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


	public SortType getSortType() {
		return sortType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getNewField() {
		return newField;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getNewFieldName() {
		return newFieldName;
	}

	public void setNewFieldName(String newFieldName) {
		this.newFieldName = newFieldName;
	}

	public boolean isAdvanced() {
		return isAdvanced;
	}

	public void setAdvanced(boolean isAdvanced) {
		this.isAdvanced = isAdvanced;
	}
	


}
