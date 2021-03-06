package org.zbi.server.entity.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zbi.server.model.request.RequestColumn;
import org.zbi.server.model.request.RequestCondition;

import io.swagger.annotations.ApiModelProperty;

public class ReportInfo extends EntityBase{

	/**
	 * 报表ID
	 */
	@ApiModelProperty(example = "1", required = false, value = "报表ID")
	private String reportID;

	/**
	 * 看板ID
	 */
	private String boardID;

	/**
	 * 报表名称
	 */
	@ApiModelProperty(example = "test", required = true, value = "报表名称")
	private String reportName;

	/**
	 * 报表描述
	 */
	@ApiModelProperty(example = "this is a test", required = true, value = "报表描述")
	private String reportDesc;

	/**
	 * 模型标识
	 */
	@ApiModelProperty(example = "123456", required = true, value = "所用模型标识")
	private String modelID;

	/**
	 * 模型名称
	 */
	@ApiModelProperty(example = "销量模型", required = true, value = "所用模型名称")
	private String modelName;

	/**
	 * 其他关于页展示的信息
	 */
	@ApiModelProperty(required = false, value = "其他关于页展示的信息", dataType = "Map")
	private Map<String, Object> otherParams;

	/**
	 * 请求列
	 */
	@ApiModelProperty(required = false, value = "请求维度")
	private List<RequestColumn> requestDimensions;

	/**
	 * 下钻维度
	 */
	@ApiModelProperty(required = false, value = "下钻维度")
	private List<RequestColumn> drillDimensions = new ArrayList<>();

	@ApiModelProperty(required = false, value = "请求度量")
	private List<RequestColumn> requestMeasures;

	/**
	 * 请求条件
	 */
	@ApiModelProperty(required = false, value = "请求条件")
	private List<RequestCondition> requestConditions;

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getBoardID() {
		return boardID;
	}

	public void setBoardID(String boardID) {
		this.boardID = boardID;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}


	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Map<String, Object> getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(Map<String, Object> otherParams) {
		this.otherParams = otherParams;
	}

	public List<RequestColumn> getRequestDimensions() {
		return requestDimensions;
	}

	public void setRequestDimensions(List<RequestColumn> requestDimensions) {
		this.requestDimensions = requestDimensions;
	}

	public List<RequestColumn> getRequestMeasures() {
		return requestMeasures;
	}

	public void setRequestMeasures(List<RequestColumn> requestMeasures) {
		this.requestMeasures = requestMeasures;
	}

	public List<RequestCondition> getRequestConditions() {
		return requestConditions;
	}

	public void setRequestConditions(List<RequestCondition> requestConditions) {
		this.requestConditions = requestConditions;
	}

	public List<RequestColumn> getDrillDimensions() {
		return drillDimensions;
	}

	public void setDrillDimensions(List<RequestColumn> drillDimensions) {
		this.drillDimensions = drillDimensions;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

}
