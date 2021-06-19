package org.zbi.server.model.nested;

import java.util.ArrayList;
import java.util.List;

public class NestedMainTable {
	
	/**
	 * 
	 * 主表
	 * */
	private String mainTableName;
	
	/**
	 * 连表,一个NestedJoinNode代表一个join
	 * */
	private List<NestedJoinNode> joinTable;
	
	/**
	 * union 
	 * */
	private List<NestedJoinNode> unionTable;
	
	/**
	 * 添加union关系
	 * */
	public void addUnionNode(NestedJoinNode node)
	{
		if(this.unionTable==null)
		{
			this.unionTable=new ArrayList<>();
		}
		
		this.unionTable.add(node);
	}
	
	/**
	 * 添加连表关系
	 * */
	public void addNestedJoinNode(NestedJoinNode node)
	{
		if(this.joinTable==null)
		{
			this.joinTable=new ArrayList<>();
		}
		
		this.joinTable.add(node);
	}

	public String getMainTableName() {
		return mainTableName;
	}

	public List<NestedJoinNode> getJoinTable() {
		return joinTable;
	}

	public List<NestedJoinNode> getUnionTable() {
		return unionTable;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}

	public void setJoinTable(List<NestedJoinNode> joinTable) {
		this.joinTable = joinTable;
	}

	public void setUnionTable(List<NestedJoinNode> unionTable) {
		this.unionTable = unionTable;
	}

}
