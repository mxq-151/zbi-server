package com.zbi.commons.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.model.config.ModelConfig;
import org.zbi.server.model.config.QuerySqlModel;
import org.zbi.server.model.response.ModelDescResp;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/model/v1")
public class ModelController extends BaseController{
	
	@Autowired
	private ConfigDaoService<String> daoService;
	
	
	
	
	@RequestMapping(value = "/models", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型列表接口", code = 200, httpMethod = "GET", response = ModelDescResp.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<ModelDescResp> getModels(@Valid @RequestParam(required=false) @ApiParam(value = "关键词查询", required = false) String keyWord)
	{
		return this.daoService.getModelDscriptions(keyWord);
	}
	

	@RequestMapping(value = "/model/info", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET", response = QuerySqlModel.class, responseContainer = "QuerySqlModel")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public QuerySqlModel getModelInfo(@Valid @RequestParam @ApiParam(name = "modelTag",value = "模型标识", required = true,example="123") String modelTag)
	{
		ModelConfig config=this.daoService.getQuerySqlModel(modelTag);
		if(config==null)
		{
			return null;
		}
		
		return config.getQuerySqlModel();
	}

}
