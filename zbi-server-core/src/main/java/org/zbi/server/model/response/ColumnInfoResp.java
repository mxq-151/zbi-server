package org.zbi.server.model.response;

import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;

import com.alibaba.fastjson.annotation.JSONField;

public class ColumnInfoResp {
	
	/**
	 * 是否为度量
	 */
	private boolean isMeasure;

	/**
	 * 中文名
	 */
	private String chinese;

	/**
	 * 唯一标识
	 */
	private String uuid;

	/**
	 * 别名
	 */
	@JSONField(serialize = false)
	private String alias;

	/**
	 * 列名，全限定
	 */
	@JSONField(serialize = false)
	private String colName;

	/**
	 * 字段类型
	 */
	private ColumnType columnType;

	
	private AggType aggType;


	public boolean isMeasure() {
		return isMeasure;
	}


	public void setMeasure(boolean isMeasure) {
		this.isMeasure = isMeasure;
	}


	public String getChinese() {
		return chinese;
	}


	public void setChinese(String chinese) {
		this.chinese = chinese;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getColName() {
		return colName;
	}


	public void setColName(String colName) {
		this.colName = colName;
	}


	public ColumnType getColumnType() {
		return columnType;
	}


	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}


	public AggType getAggType() {
		return aggType;
	}


	public void setAggType(AggType aggType) {
		this.aggType = aggType;
	}

}
