package org.zbi.server.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.response.ModelDescResp;
import org.zbi.server.model.response.ModelInfoResp;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/model/v1")
public class ModelController extends BaseController {

	@Autowired
	private ConfigDaoService daoService;

	@RequestMapping(value = "/models", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型列表接口", code = 200, httpMethod = "GET", response = ModelDescResp.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<QueryModel> getModels(
			@Valid @RequestParam(required = false) @ApiParam(value = "关键词查询", required = false) String keyWord) {
		return this.daoService.getModelDscriptions(keyWord);
	}

	@RequestMapping(value = "/model/info", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET", response = ModelDescResp.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public ModelInfoResp getModelInfo(
			@Valid @RequestParam(required = false) @ApiParam(value = "模型ID", required = false) String modelID) {
		return this.daoService.getModelInfo(modelID);
	}

}
