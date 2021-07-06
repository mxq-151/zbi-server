package org.zbi.server.model.core;

import java.io.Serializable;
import java.util.Map;

import org.zbi.server.entity.mysql.EntityBase;

import com.alibaba.fastjson.annotation.JSONField;

public class ColumnBase extends EntityBase implements Serializable {

	public static final String PROPERTIETYPE = "properties_type";

	public static final String PROPERTIETYPE_TOPN = "properties_type_topn";

	public static final String PROPERTIETYPE_INVOLE_TABLE = "properties_type_invole_table";

	public static final String PROPERTIETYPE_SUB_QUERY = "properties_type_sub_query";

	public static final String PROPERTIETYPE_CASEWHEN = "properties_type_case_when";

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

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

	/**
	 * 是否为高级字段
	 */
	private boolean isAdvanced;

	/**
	 * 字段属性，通过属性控制字段的变换
	 */
	@JSONField(serialize = false)
	private Map<String, Object> colProperties;

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public boolean isAdvanced() {
		return isAdvanced;
	}

	public void setAdvanced(boolean isAdvanced) {
		this.isAdvanced = isAdvanced;
	}

	public Map<String, Object> getColProperties() {
		return colProperties;
	}

	public void setColProperties(Map<String, Object> colProperties) {
		this.colProperties = colProperties;
	}

}
