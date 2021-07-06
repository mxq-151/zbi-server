package org.zbi.server.rest.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zbi.server.dao.service.BoardDaoService;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeBoard;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/board/v1")
public class BoardController extends BaseController {

	@Autowired
	BoardDaoService boardDaoService;

	@Autowired
	FolderDaoService folderDaoService;

	@RequestMapping(value = "/get/board", method = RequestMethod.GET)
	@ApiOperation(value = "获取看板列表接口", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public BoardInfo getBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板ID", required = true) String boardID)
			throws QueryException {

		return this.boardDaoService.getBoardByID(boardID);

	}

	@RequestMapping(value = "/get/boards", method = RequestMethod.GET)
	@ApiOperation(value = "获取看板列表接口", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public List<FacadeBoard> getBoards(
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹ID", required = true) String folderID)
			throws QueryException {
		return this.boardDaoService.getBoardByFolderID(folderID);

	}

	@RequestMapping(value = "/delete/board", method = RequestMethod.GET)
	@ApiOperation(value = "删除看板列表接口", code = 200, httpMethod = "GET", response = FacadeBoard.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean deleteBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板ID", required = true) String boardID,
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹ID", required = true) String folderID)
			throws AdminException {
		return this.folderDaoService.removeBoard(boardID, folderID);
	}

	@RequestMapping(value = "/create/board", method = RequestMethod.GET)
	@ApiOperation(value = "保存看板", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public BoardInfo createNewBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板名称", required = true) String boardName,
			@Valid @RequestParam(required = true) @ApiParam(value = "看板描述", required = true) String boardDesc,
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹ID", required = true) String folderID)
			throws QueryException {
		return this.boardDaoService.createNewBoard(boardName, boardDesc, folderID);

	}

	@RequestMapping(value = "/update/board", method = RequestMethod.POST)
	@ApiOperation(value = "保存看板", code = 200, httpMethod = "POST", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public BoardInfo updateBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板ID", required = true) String boardID,
			@Valid @RequestBody @ApiParam(value = "看板样式", required = true) Map<String, Object> otherParam)
			throws QueryException {
		return this.boardDaoService.updateBoardStype(otherParam, boardID);

	}

	@RequestMapping(value = "/update/name", method = RequestMethod.GET)
	@ApiOperation(value = "看板名称", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public BoardInfo updateBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板ID", required = true) String boardID,
			@Valid @RequestParam(required = true) @ApiParam(value = "看板名称", required = true) String boardName,
			@Valid @RequestParam(required = true) @ApiParam(value = "看板描述", required = true) String boardDesc)
			throws QueryException {

		return this.boardDaoService.updateBoardName(boardID, boardName, boardDesc);

	}

}
