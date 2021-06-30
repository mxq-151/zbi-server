package org.zbi.server.rest.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.response.QueryResultResp;
import org.zbi.server.model.service.IQueryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/query")
public class QueryController extends BaseController {

	@Autowired
	private IQueryService queryService;

	@RequestMapping(value = "/v1", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "通用查询接口", code = 200, httpMethod = "POST", response = QueryResultResp.class, responseContainer = "QueryResult", notes = "{\"conditions\":[{\"queryType\":\"LARGEEQUAL\",\"requestValue\":[\"2011-01-01\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490030\"},{\"queryType\":\"LESSEQUAL\",\"requestValue\":[\"2019-01-02\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490030\"},{\"queryType\":\"IN\",\"requestValue\":[\"guangzhou\",\"shenzen\",\"shanghai\"],\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490031\"}],\"dimensions\":[{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490030\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490031\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490032\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490033\"}],\"measures\":[{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490034\"},{\"advanced\":false,\"sortOrder\":0,\"uuid\":\"2489fa3a-cf1b-42a1-bb64-895b3ea490035\"},{\"advanced\":true,\"newField\":\"(2489fa3a-cf1b-42a1-bb64-895b3ea490035+2489fa3a-cf1b-42a1-bb64-895b3ea490034)\",\"newFieldName\":\"高级字段1\",\"sortOrder\":0},{\"advanced\":true,\"newField\":\"((2489fa3a-cf1b-42a1-bb64-895b3ea490035+2489fa3a-cf1b-42a1-bb64-895b3ea490034))+100\",\"newFieldName\":\"高级字段2\",\"sortOrder\":0}],\"modelTag\":\"123456\",\"offSet\":0,\"pageSize\":20}\n"
			+ "")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public QueryResultResp query(
			@Valid @RequestBody @ApiParam(value = "查询的维度", required = true) org.zbi.server.model.request.RequestParam param)
			throws QueryException, ParseException, SQLException, IOException {

		QueryResultResp qr = this.queryService.query(param);
		return qr;

	}

	@RequestMapping(value = "/v1/search", method = RequestMethod.GET)
	@ApiOperation(value = "通用查询接口", code = 200, httpMethod = "GET", response = QueryResultResp.class, responseContainer = "QueryResult")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public QueryResultResp search(
			@Valid @RequestParam(required = false) @ApiParam(value = "搜索关建词", required = false) String keyword,
			@Valid @RequestParam @ApiParam(value = "搜索路径", required = true) String indexPath,
			@Valid @RequestParam @ApiParam(value = "起始", required = true) int offset,
			@Valid @RequestParam @ApiParam(value = "分页大小", required = true) int pageSize)
			throws QueryException, ParseException, SQLException, IOException {

		return this.queryService.search(keyword, indexPath, offset, pageSize);

	}

}
