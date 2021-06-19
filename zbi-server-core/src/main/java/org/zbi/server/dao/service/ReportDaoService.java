package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeReport;

public interface ReportDaoService {

	/**
	 * 创建新报表
	 * @throws QueryException 
	 * */
	public FacadeReport createNewReport(FacadeReport rwi) throws QueryException;

	/**
	 * 根据报表ID获取报表信息
	 * **/
	public List<FacadeReport> getReportsByBoardID(String boardID);
	
	/**
	 * 根据报表ID获取报表信息
	 * **/
	public FacadeReport getReportByID(String reportID);

	/**
	 * 更新报表信息
	 * **/
	public FacadeReport updateReport(FacadeReport rwi);

	/**
	 * 删除报表
	 */
	public void deleteReport(String reportID);

}
