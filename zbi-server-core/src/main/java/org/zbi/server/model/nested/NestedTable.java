package org.zbi.server.model.nested;

import java.util.ArrayList;
import java.util.List;

public class NestedTable {
	
	/**
	 * 列信息
	 * */
	private List<NestedColumn> columns;
	
	/**
	 * 表名
	 * */
	private String tableName;
	
	
	/**
	 * 表别名
	 * */
	private String tableAlias;
	
	/**
	 * 是否是子查询
	 * */
	private boolean subQuery=false;
	
	/**
	 * 内部使用的变量，表示子查询是否合成完
	 * */
	private boolean combineDone=false;
	
	
	/**
	 * 连表关系
	 * **/
	private List<NestedJoinNode> joinNodes;
	
	
	public void addNestedColumn(NestedColumn column)
	{
		if(this.columns==null)
		{
			this.columns=new ArrayList<>();
		}
		
		this.columns.add(column);
	}


	public boolean isSubQuery() {
		return subQuery;
	}


	public void setSubQuery(boolean subQuery) {
		this.subQuery = subQuery;
	}


	public List<NestedColumn> getColumns() {
		return columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setColumns(List<NestedColumn> columns) {
		this.columns = columns;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public List<NestedJoinNode> getJoinNodes() {
		return joinNodes;
	}


	public void setJoinNodes(List<NestedJoinNode> joinNodes) {
		this.joinNodes = joinNodes;
	}


	public String getTableAlias() {
		return tableAlias;
	}


	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}


	public boolean isCombineDone() {
		return combineDone;
	}


	public void setCombineDone(boolean combineDone) {
		this.combineDone = combineDone;
	}

}
