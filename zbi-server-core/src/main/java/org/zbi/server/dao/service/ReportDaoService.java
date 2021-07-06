package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.entity.mysql.ReportInfo;

public interface ReportDaoService {

	/**
	 * 根据报表ID获取报表信息
	 * **/
	public List<ReportInfo> getReportsByBoardID(String boardID);
	
	/**
	 * 根据报表ID获取报表信息
	 * **/
	public ReportInfo getReportByID(String reportID);

	/**
	 * 更新报表信息
	 * **/
	public ReportInfo saveReport(ReportInfo rwi);

	/**
	 * 删除报表
	 */
	public void deleteReport(String reportID);

}
