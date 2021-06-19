package org.zbi.server.model.response;

public class ColumnMetaResp {

	private String uuid;
	/**
	 * 是否为度量
	 */
	private boolean isMeasure;
	private String label;
	private String name;
	private String schemaName;
	private String catelogName;
	private String tableName;// 表名
	private int precision;
	private int scale;
	private String chinese;
	public String getLabel() {
		return label;
	}
	public String getName() {
		return name;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public String getCatelogName() {
		return catelogName;
	}
	public String getTableName() {
		return tableName;
	}
	public int getPrecision() {
		return precision;
	}
	public int getScale() {
		return scale;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public void setCatelogName(String catelogName) {
		this.catelogName = catelogName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public void setScale(int scale) {
		this.scale = scale;
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
	public boolean isMeasure() {
		return isMeasure;
	}
	public void setMeasure(boolean isMeasure) {
		this.isMeasure = isMeasure;
	}
	

}
