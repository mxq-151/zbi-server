package org.zbi.server.model.parse;

import java.util.List;

import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.core.QueryType;

public class ParseFilter extends ConfigColumn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> values;

	private QueryType queryType;

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

}
