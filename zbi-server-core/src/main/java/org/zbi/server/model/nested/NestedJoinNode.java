package org.zbi.server.model.nested;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 把in查询也当成一种特殊的连表查询
 * */
public class NestedJoinNode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 连表类型
	 * */
	private NestedJoinType joinType;
	
	/**
	 * 连接
	 * */
	private String joinTableName;
	
	/**
	 * 连表信息
	 * */
	private List<NestedJoinCondition> joinConditions;
	
	
	public void addNestedJoinCondition(NestedJoinCondition joinCondition)
	{
		if(this.joinConditions==null)
		{
			this.joinConditions=new ArrayList<>();
		}
		
		this.joinConditions.add(joinCondition);
	}


	public NestedJoinType getJoinType() {
		return joinType;
	}


	public List<NestedJoinCondition> getJoinConditions() {
		return joinConditions;
	}


	public void setJoinType(NestedJoinType joinType) {
		this.joinType = joinType;
	}


	public void setJoinConditions(List<NestedJoinCondition> joinConditions) {
		this.joinConditions = joinConditions;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getJoinTableName() {
		return joinTableName;
	}


	public void setJoinTableName(String joinTableName) {
		this.joinTableName = joinTableName;
	}
	
	

}
