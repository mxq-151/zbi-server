package org.zbi.server.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.BoardDaoService;
import org.zbi.server.dao.service.ReportDaoService;
import org.zbi.server.entity.mysql.ReportInfo;
import org.zbi.server.model.exception.QueryException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/report/v1")
public class ReportController extends BaseController {

	@Autowired
	BoardDaoService boardDaoService;

	@Autowired
	ReportDaoService reportDaoService;

	@RequestMapping(value = "/get/reports", method = RequestMethod.GET)
	@ApiOperation(value = "获取看板下报表接口", code = 200, httpMethod = "GET", response = ReportInfo.class, responseContainer = "List")
	public List<ReportInfo> getReports(@ApiParam(value = "看板ID", required = true) String boardID)
			throws QueryException {

		return this.reportDaoService.getReportsByBoardID(boardID);
	}

	@RequestMapping(value = "/get/report", method = RequestMethod.GET)
	@ApiOperation(value = "获取单报表接口", code = 200, httpMethod = "GET", response = ReportInfo.class)
	public ReportInfo getReport(@ApiParam(value = "报表ID", required = true) String reportID) {
		return this.reportDaoService.getReportByID(reportID);
	}

	@RequestMapping(value = "/delete/report", method = RequestMethod.GET)
	@ApiOperation(value = "删除报表接口", code = 200, httpMethod = "GET", response = ReportInfo.class)
	public boolean deleteReport(@ApiParam(value = "报表ID", required = true) String reportID) {
		this.reportDaoService.deleteReport(reportID);

		return true;
	}

	@RequestMapping(value = "/save/report", method = RequestMethod.POST)
	@ApiOperation(value = "保存或者更新报表接口", code = 200, httpMethod = "POST", notes = "{\"boardID\":1,\"drillDimensions\":[{\"advanced\":false,\"sortOrder\":-1,\"sortType\":\"DEFAULT\",\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490032\"}],\"modelName\":\"销售统计模型\",\"modelTag\":\"123456\",\"otherParams\":{\"说明\":\"此参数可以存放前端所需要的信息，比如报表自动更新机制、报表样式等，以kv存放\"},\"reportDesc\":\"this is a report save test\",\"reportID\":-1,\"reportName\":\"销售统计报表\",\"requestConditions\":[{\"queryType\":\"LARGEEQUAL\",\"requestValue\":[\"2011-01-01\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490036\"},{\"queryType\":\"LESSEQUAL\",\"requestValue\":[\"2019-01-02\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490036\"},{\"queryType\":\"IN\",\"requestValue\":[\"guangzhou\",\"shenzen\",\"shanghai\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490037\"}],\"requestDimensions\":[{\"advanced\":false,\"sortOrder\":3,\"sortType\":\"DESC\",\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490030\"},{\"advanced\":false,\"sortOrder\":-1,\"sortType\":\"DEFAULT\",\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490031\"},{\"$ref\":\"$.drillDimensions[0]\"},{\"advanced\":false,\"sortOrder\":-1,\"sortType\":\"DEFAULT\",\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490033\"}],\"requestMeasures\":[{\"advanced\":false,\"sortOrder\":-1,\"sortType\":\"DEFAULT\",\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490034\"},{\"advanced\":false,\"sortOrder\":-1,\"sortType\":\"DEFAULT\",\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490035\"},{\"advanced\":true,\"newField\":\"(2489fa3a-cf1b-42a1-bb64-895b3ea490035+2489fa3a-cf1b-42a1-bb64-895b3ea490034)\",\"newFieldName\":\"高级字段1\",\"sortOrder\":2,\"sortType\":\"ASC\"},{\"advanced\":true,\"newField\":\"((2489fa3a-cf1b-42a1-bb64-895b3ea490035+2489fa3a-cf1b-42a1-bb64-895b3ea490034))+100\",\"newFieldName\":\"高级字段2\",\"sortOrder\":1,\"sortType\":\"ASC\"}]}\n"
			+ "")
	public ReportInfo saveReport(@RequestBody ReportInfo reportWrapperInfo) throws QueryException {
		return this.reportDaoService.saveReport(reportWrapperInfo);
	}

}
