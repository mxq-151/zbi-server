package org.zbi.server.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ReportDaoService;
import org.zbi.server.entity.mysql.ReportInfo;
import org.zbi.server.mapper.mysql.ReportInfoMapper;

@Component
public class MysqlReportDaoService extends DaoServiceBase implements ReportDaoService {

	@Autowired
	ReportInfoMapper reportInfoMapper;

	@Override
	public List<ReportInfo> getReportsByBoardID(String boardID) {
		// TODO Auto-generated method stub
		return this.reportInfoMapper.getReportByBoardID(boardID);

	}

	@Override
	public ReportInfo getReportByID(String reportID) {
		// TODO Auto-generated method stub
		return this.reportInfoMapper.getReportByID(reportID);
	}

	@Override
	public ReportInfo saveReport(ReportInfo rwi) {
		// TODO Auto-generated method stub
		this.preHandle(rwi);
		if (rwi.getReportID() == null || "-1".equals(rwi.getReportID())) {
			String reportID = java.util.UUID.randomUUID().toString();
			rwi.setReportID(reportID);
			this.reportInfoMapper.createReport(rwi);
		} else {
			this.reportInfoMapper.updateReport(rwi);
		}
		return this.reportInfoMapper.getReportByID(rwi.getReportID());
	}

	@Override
	public void deleteReport(String reportID) {
		// TODO Auto-generated method stub
		this.reportInfoMapper.deleteReport(reportID);

	}

}
