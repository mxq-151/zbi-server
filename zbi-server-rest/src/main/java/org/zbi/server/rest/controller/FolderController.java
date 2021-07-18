package org.zbi.server.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeFolder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/folder/v1")
public class FolderController extends BaseController {

	@Autowired
	FolderDaoService folderDaoService;

	@RequestMapping(value = "/get/folders", method = RequestMethod.GET)
	@ApiOperation(value = "获取文件夹列表接口", code = 200, httpMethod = "GET", response = FacadeFolder.class, responseContainer = "List")
	public List<FacadeFolder> getFolders() {
		return this.folderDaoService.getQueryFolders();

	}

	@RequestMapping(value = "/delete/folder", method = RequestMethod.GET)
	@ApiOperation(value = "删除文件夹接口", code = 200, httpMethod = "GET", response = Boolean.class)
	public boolean deleteFolder(@ApiParam(value = "文件夹ID", required = true) String folderID) throws AdminException {
		folderDaoService.deleteFolder(folderID);
		return true;

	}

	@RequestMapping(value = "/update/folder", method = RequestMethod.GET)
	@ApiOperation(value = "更新文件夹接口", code = 200, httpMethod = "GET", response = Boolean.class)
	public boolean updateFolder(@ApiParam(value = "文件夹ID", required = true) String folderID,
			@ApiParam(value = "文件夹名称", required = true) String folderName,
			@ApiParam(value = "文件夹描述", required = true) String folderDesc) throws AdminException {
		folderDaoService.updateFolder(folderID, folderName, folderDesc);
		return true;

	}

	@RequestMapping(value = "/create/folder", method = RequestMethod.PUT)
	@ApiOperation(value = "创建文件夹接口", code = 200, httpMethod = "PUT", response = FacadeFolder.class)
	public FacadeFolder createFolder(@ApiParam(value = "文件夹名称", required = true) String folderName,
			@ApiParam(value = "文件夹描述", required = true) String folderDesc,
			@ApiParam(value = "文件夹类型", required = true) int folderType) throws AdminException {
		return this.folderDaoService.createFolder(folderName, folderDesc, folderType);

	}

}
