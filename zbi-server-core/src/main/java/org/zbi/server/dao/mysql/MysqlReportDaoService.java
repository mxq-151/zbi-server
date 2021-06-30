package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ReportDaoService;
import org.zbi.server.entity.mysql.ReportInfo;
import org.zbi.server.mapper.mysql.ReportInfoMapper;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeReport;

@Component
public class MysqlReportDaoService implements ReportDaoService {

	@Autowired
	ReportInfoMapper reportInfoMapper;
	
	@Override
	public FacadeReport createNewReport(FacadeReport rwi) throws QueryException {
		// TODO Auto-generated method stub
		
		if(this.reportInfoMapper.checkReportByName(rwi.getBoardID(), rwi.getReportName()))
		{
			throw new QueryException("看板中存在相同名称的报表名称");
		}
		String reportID=java.util.UUID.randomUUID().toString();
		rwi.setReportID(reportID);
		this.reportInfoMapper.createReport(this.transferReportInfo(rwi));
		
		return this.transferFacadeReport(this.reportInfoMapper.getReportByName(rwi.getBoardID(), rwi.getReportName()));
	}

	@Override
	public List<FacadeReport> getReportsByBoardID(String boardID) {
		// TODO Auto-generated method stub
		List<ReportInfo> reportInfos=this.reportInfoMapper.getReportByBoardID(boardID);
		List<FacadeReport> reports=new ArrayList<>();
		for(ReportInfo reportInfo:reportInfos)
		{
			reports.add(this.transferFacadeReport(reportInfo));
		}
		
		return reports;
	}

	@Override
	public FacadeReport getReportByID(String reportID) {
		// TODO Auto-generated method stub
		return transferFacadeReport(this.reportInfoMapper.getReportByID(reportID));
	}

	@Override
	public FacadeReport updateReport(FacadeReport rwi) {
		// TODO Auto-generated method stub
		this.reportInfoMapper.updateReport(this.transferReportInfo(rwi));
		return this.transferFacadeReport(this.reportInfoMapper.getReportByID(rwi.getReportID()));
	}

	@Override
	public void deleteReport(String reportID) {
		// TODO Auto-generated method stub
		this.reportInfoMapper.deleteReport(reportID);

	}
	
	private FacadeReport transferFacadeReport(ReportInfo reportInfo)
	{
		FacadeReport report=new FacadeReport();
		report.setReportID(reportInfo.getReportID());
		report.setBoardID(reportInfo.getBoardID());
		report.setModelID(reportInfo.getModelID());
		report.setModelName(reportInfo.getModelName());
		report.setOtherParams(reportInfo.getOtherParams());
		report.setReportDesc(reportInfo.getReportDesc());
		report.setReportName(reportInfo.getReportName());
		report.setRequestConditions(reportInfo.getRequestConditions());
		report.setRequestDimensions(reportInfo.getRequestDimensions());
		report.setDrillDimensions(reportInfo.getDrillDimensions());
		report.setRequestMeasures(reportInfo.getRequestMeasures());
		return report;
	}
	
	private ReportInfo transferReportInfo(FacadeReport reportInfo)
	{
		ReportInfo report=new ReportInfo();
		report.setBoardID(reportInfo.getBoardID());
		report.setModelName(reportInfo.getModelName());
		report.setOtherParams(reportInfo.getOtherParams());
		report.setReportDesc(reportInfo.getReportDesc());
		report.setReportName(reportInfo.getReportName());
		report.setReportID(reportInfo.getReportID());
		report.setModelID(reportInfo.getModelID());
		report.setRequestConditions(reportInfo.getRequestConditions());
		report.setRequestDimensions(reportInfo.getRequestDimensions());
		report.setDrillDimensions(reportInfo.getDrillDimensions());
		report.setRequestMeasures(reportInfo.getRequestMeasures());
		return report;
	}

}
