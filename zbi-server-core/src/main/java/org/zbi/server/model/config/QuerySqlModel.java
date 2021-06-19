package org.zbi.server.model.config;

import java.util.List;
import java.util.Map;

import org.zbi.server.model.core.JoinCondition;

import com.alibaba.fastjson.annotation.JSONField;

public class QuerySqlModel {
	

	/**
	 * 所属project
	 */
	private String project;
	
	
	/**
	 * 主表
	 * */
	@JSONField(serialize = true)
	private String factTable;

	/**
	 * 维度
	 */
	private List<ModelDimension> dimensions;

	/**
	 * 度量
	 */
	private List<ModelMeasure> measures;

	/**
	 * 连表条件
	 */
	@JSONField(serialize = true)
	protected Map<String, List<JoinCondition>> joinConditionMap;
	
	

	public List<ModelDimension> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<ModelDimension> dimensions) {
		this.dimensions = dimensions;
	}

	public List<ModelMeasure> getMeasures() {
		return measures;
	}

	public void setMeasures(List<ModelMeasure> measures) {
		this.measures = measures;
	}

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

	public String getFactTable() {
		return factTable;
	}

	public void setFactTable(String factTable) {
		this.factTable = factTable;
	}


}
