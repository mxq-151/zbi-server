package org.zbi.server.model.parse;

import java.util.List;
import java.util.Map;

import org.zbi.server.model.core.JoinCondition;

public class ParseSqlParam {
	
	
	/***
	 * 起始页
	 * */
	private int offSet;
	
	/**
	 * 页大小
	 * */
	private int pageSize;
	
	/**
	 * 所属project
	 * */
	private String project;
	
	/**
	 * 主表
	 * */
	private String factTable;

	/**
	 * 维度
	 * */
	private List<ParseDimension> dimensions;

	/**
	 * 度量
	 * */
	private List<ParseMeasure> measures;
	
	/**
	 * 条件
	 * */
	private List<ParseCondition> conditions;
	
	/**
	 * 连表条件
	 * */
	protected Map<String, List<JoinCondition>> joinConditionMap;


	


	public Map<String, List<JoinCondition>> getJoinConditionMap() {
		return joinConditionMap;
	}


	public void setJoinConditionMap(Map<String, List<JoinCondition>> joinConditionMap) {
		this.joinConditionMap = joinConditionMap;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
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


	public List<ParseCondition> getConditions() {
		return conditions;
	}


	public void setConditions(List<ParseCondition> conditions) {
		this.conditions = conditions;
	}


	public String getFactTable() {
		return factTable;
	}


	public void setFactTable(String factTable) {
		this.factTable = factTable;
	}


	public List<ParseDimension> getDimensions() {
		return dimensions;
	}


	public List<ParseMeasure> getMeasures() {
		return measures;
	}


	public void setDimensions(List<ParseDimension> dimensions) {
		this.dimensions = dimensions;
	}


	public void setMeasures(List<ParseMeasure> measures) {
		this.measures = measures;
	}

}
