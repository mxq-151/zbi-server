package org.zbi.server.mapper.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.zbi.server.entity.mysql.ReportInfo;

@Mapper
public interface ReportInfoMapper {

	public boolean createReport(ReportInfo report);

	public ReportInfo getReportByID(String reportID);

	public List<ReportInfo> getReportByBoardID(String boardID);

	public boolean checkReportByName(String boardID, String reportName);

	public ReportInfo getReportByName(String boardID, String reportName);

	public boolean updateReport(ReportInfo report);

	public boolean deleteReport(String reportID);

	public List<Integer> getReportID(String boardID);

}
