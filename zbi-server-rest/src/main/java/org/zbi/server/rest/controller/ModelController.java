package org.zbi.server.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.response.ModelDescResp;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/model/v1")
public class ModelController extends BaseController {

	@Autowired
	private ConfigDaoService daoService;

	@RequestMapping(value = "/save/table", method = RequestMethod.POST)
	@ApiOperation(value = "保存表信息", code = 200, httpMethod = "POST", response = ModelDescResp.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public void saveTable(@RequestBody FacadeTable table) {
		this.daoService.saveTable(table);
	}
	
	@RequestMapping(value = "/delete/table", method = RequestMethod.POST)
	@ApiOperation(value = "删除表信息", code = 200, httpMethod = "POST", response = ModelDescResp.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public void deleteTable(@RequestBody FacadeTable table) {
		this.daoService.saveTable(table);
	}

	@RequestMapping(value = "/models", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型列表接口", code = 200, httpMethod = "GET", response = ModelDescResp.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<ModelDescResp> getModels(
			@Valid @RequestParam(required = false) @ApiParam(value = "关键词查询", required = false) String keyWord) {
		return this.daoService.getModelDscriptions(keyWord);
	}


}
