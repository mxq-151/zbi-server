package org.zbi.server.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeFolder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/folder/v1")
public class FolderController extends BaseController {

	@Autowired
	FolderDaoService folderDaoService;

	@RequestMapping(value = "/get/folders", method = RequestMethod.GET)
	@ApiOperation(value = "获取文件夹列表接口", code = 200, httpMethod = "GET", response = FacadeFolder.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<FacadeFolder> getFolders() {
		return this.folderDaoService.getFolders();

	}

	@RequestMapping(value = "/delete/folder", method = RequestMethod.GET)
	@ApiOperation(value = "删除文件夹接口", code = 200, httpMethod = "GET", response = Boolean.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean deleteFolder(
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹ID", required = true) String folderID)
			throws QueryException {
		folderDaoService.deleteFolder(folderID);
		return true;

	}

	@RequestMapping(value = "/update/folder", method = RequestMethod.GET)
	@ApiOperation(value = "删除文件夹接口", code = 200, httpMethod = "GET", response = Boolean.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean updateeFolder(
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹ID", required = true) String folderID,
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹名称", required = true) String folderName,
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹描述", required = true) String folderDesc)
			throws QueryException {
		folderDaoService.updateFolder(folderID, folderName, folderDesc);
		return true;

	}

	@RequestMapping(value = "/create/folder", method = RequestMethod.GET)
	@ApiOperation(value = "创建文件夹接口", code = 200, httpMethod = "GET", response = Boolean.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public FacadeFolder createFolder(
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹名称", required = true) String folderName,
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹描述", required = true) String folderDesc)
			throws QueryException {
		return this.folderDaoService.createFolder(folderName, folderDesc,null);

	}

}
