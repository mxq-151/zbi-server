package org.zbi.server.model.sql.parse;

import org.zbi.server.model.config.QuerySqlModel;
import org.zbi.server.model.nested.NestedTable;

/***
 * sql解析结果
 * */
public class SqlParseResult {
	
	/**
	 * 对外模型
	 * */
	private QuerySqlModel querySqlmodel;
	
	
	/**
	 * 内嵌模型
	 * */
	private NestedTable nestedTable;


	public QuerySqlModel getQuerySqlmodel() {
		return querySqlmodel;
	}


	public NestedTable getNestedTable() {
		return nestedTable;
	}


	public void setQuerySqlmodel(QuerySqlModel querySqlmodel) {
		this.querySqlmodel = querySqlmodel;
	}


	public void setNestedTable(NestedTable nestedTable) {
		this.nestedTable = nestedTable;
	}

}
