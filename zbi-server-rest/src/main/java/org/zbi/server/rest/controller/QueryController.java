package org.zbi.server.rest.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.response.QueryResultResp;
import org.zbi.server.model.service.IQueryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/query")
public class QueryController extends BaseController {

	@Autowired
	private IQueryService queryService;

	@RequestMapping(value = "/v1", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "通用查询接口", code = 200, httpMethod = "POST", response = QueryResultResp.class, notes = "{\"conditions\":[{\"queryType\":\"LARGEEQUAL\",\"requestValue\":[\"2011-01-01\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490030\"},{\"queryType\":\"LESSEQUAL\",\"requestValue\":[\"2019-01-02\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490030\"},{\"queryType\":\"IN\",\"requestValue\":[\"guangzhou\",\"shenzen\",\"shanghai\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490031\"}],\"dimensions\":[{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490030\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490031\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490032\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490033\"}],\"measures\":[{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490034\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490035\"},{\"advanced\":true,\"newField\":\"(2489fa3a-cf1b-42a1-bb64-895b3ea490035+2489fa3a-cf1b-42a1-bb64-895b3ea490034)\",\"newFieldName\":\"高级字段1\",\"sortOrder\":0},{\"advanced\":true,\"newField\":\"((2489fa3a-cf1b-42a1-bb64-895b3ea490035+2489fa3a-cf1b-42a1-bb64-895b3ea490034))+100\",\"newFieldName\":\"高级字段2\",\"sortOrder\":0}],\"modelTag\":\"123456\",\"offSet\":0,\"pageSize\":20}\n"
			+ "")
	public QueryResultResp query(@RequestBody org.zbi.server.model.request.RequestParam param)
			throws ParseException, SQLException, QueryException, IOException {

		QueryResultResp qr = this.queryService.query(param);
		return qr;

	}

	@RequestMapping(value = "/v1/search", method = RequestMethod.GET)
	@ApiOperation(value = "通用查询接口", code = 200, httpMethod = "GET", response = QueryResultResp.class)
	public QueryResultResp search(@ApiParam(value = "搜索关建词", required = false) String keyword,
			@ApiParam(value = "搜索路径", required = true) String indexPath,
			@ApiParam(value = "起始", required = true) int offset,
			@ApiParam(value = "分页大小", required = true) int pageSize)
			throws QueryException, ParseException, SQLException, IOException {

		return this.queryService.search(keyword, indexPath, offset, pageSize);

	}

}
