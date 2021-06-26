package org.zbi.server.model.request;

import java.util.List;

import org.zbi.server.model.core.QueryType;

import io.swagger.annotations.ApiModelProperty;

public class RequestCondition {

	@ApiModelProperty(example = "uuid", required = true, value = "唯一ID")
	private String uuid;

	@ApiModelProperty(example = "EQUAL", required = false, value = "比较关系")
	private QueryType queryType;

	@ApiModelProperty(example = "[\"12" + "]", required = false, value = "条件值")
	private List<String> RequestValue;

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getRequestValue() {
		return RequestValue;
	}

	public void setRequestValue(List<String> requestValue) {
		RequestValue = requestValue;
	}

}
