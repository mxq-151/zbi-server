package org.zbi.server.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.response.ModelInfoResp;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/model/v1")
public class ModelController extends BaseController {

	@Autowired
	private ConfigDaoService daoService;

	@RequestMapping(value = "/models", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型列表接口", code = 200, httpMethod = "GET")
	public List<QueryModel> getModels(@ApiParam(value = "关键词查询", required = false) String keyWord) {
		return this.daoService.loadModel(keyWord);
	}

	@RequestMapping(value = "/model/info", method = RequestMethod.GET)
	@ApiOperation(value = "获取模型信息接口", code = 200, httpMethod = "GET")
	public ModelInfoResp getModelInfo(@ApiParam(value = "模型ID", required = true) String modelID) {
		return this.daoService.loadModelInfo(modelID);
	}

}
