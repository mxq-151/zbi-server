package org.zbi.server.model.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zbi.server.model.request.RequestColumn;
import org.zbi.server.model.request.RequestCondition;
import org.zbi.server.model.request.RequestMeasure;

import io.swagger.annotations.ApiModelProperty;

/**
 * 关于报表的描述
 * */
public class FacadeReport {
	
	
	/**
	 * 报表ID
	 * */
	@ApiModelProperty(example = "1", required = false, value = "报表ID")
	private String reportID;
	
	
	/**
	 * 所属看板ID
	 * */
	@ApiModelProperty(example = "1", required = true, value = "所属看板ID")
	private String boardID;
	
	
	/**
	 * 报表名称
	 * */
	@ApiModelProperty(example = "test", required = true, value = "报表名称")
	private String reportName;
	
	/**
	 * 报表描述
	 * */
	@ApiModelProperty(example = "this is a test", required = true, value = "报表描述")
	private String reportDesc;
	
	
	/**
	 * 模型标识
	 * */
	@ApiModelProperty(example = "123456", required = true, value = "所用模型标识")
	private String modelTag;
	
	/**
	 * 模型名称
	 * */
	@ApiModelProperty(example = "销量模型", required = true, value = "所用模型名称")
	private String modelName;
	
	
	/**
	 * 其他关于页展示的信息
	 * */
	@ApiModelProperty(required = false, value = "其他关于页展示的信息",dataType="Map")
	private Map<String,Object> otherParams;
	
	/**
	 * 请求列
	 * */
	@ApiModelProperty(required = false, value = "请求维度")
	private List<RequestColumn> requestDimensions;
	
	
	/**
	 * 下钻维度
	 * */
	@ApiModelProperty(required = false, value = "下钻维度")
	private List<RequestColumn> drillDimensions=new ArrayList<>();
	
	
	@ApiModelProperty(required = false, value = "请求度量")
	private List<RequestMeasure> requestMeasures;
	
	/**
	 * 请求条件
	 * */
	@ApiModelProperty(required = false, value = "请求条件")
	private List<RequestCondition> requestConditions;

	public String getModelTag() {
		return modelTag;
	}

	public String getModelName() {
		return modelName;
	}

	public Map<String, Object> getOtherParams() {
		return otherParams;
	}



	public List<RequestCondition> getRequestConditions() {
		return requestConditions;
	}

	public void setModelTag(String modelTag) {
		this.modelTag = modelTag;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setOtherParams(Map<String, Object> otherParams) {
		this.otherParams = otherParams;
	}

	public void setRequestConditions(List<RequestCondition> requestConditions) {
		this.requestConditions = requestConditions;
	}

	public String getReportName() {
		return reportName;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

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

	public List<RequestColumn> getRequestDimensions() {
		return requestDimensions;
	}

	public void setRequestDimensions(List<RequestColumn> requestDimensions) {
		this.requestDimensions = requestDimensions;
	}

	public List<RequestMeasure> getRequestMeasures() {
		return requestMeasures;
	}

	public void setRequestMeasures(List<RequestMeasure> requestMeasures) {
		this.requestMeasures = requestMeasures;
	}

	public List<RequestColumn> getDrillDimensions() {
		return drillDimensions;
	}

	public void setDrillDimensions(List<RequestColumn> drillDimensions) {
		this.drillDimensions = drillDimensions;
	}
	
	
	

}
