package org.zbi.server.model.parse;

import org.zbi.server.model.config.ModelDimension;
import org.zbi.server.model.core.QueryType;

public class ParseCondition extends ModelDimension{
	
	/**
	 * 比较类型
	 * */
	private QueryType queryType;
	
	/**
	 * 比较值
	 * */
	private String conditionVal;

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	public String getConditionVal() {
		return conditionVal;
	}

	public void setConditionVal(String conditionVal) {
		this.conditionVal = conditionVal;
	}


}
