package com.zbi.commons.server.rest;

import java.util.ArrayList;
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
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeBoard;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.model.response.BoardInfoResp;

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
	public FacadeBoard getBoard(
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
		FacadeFolder folder = this.folderDaoService.getFolder(folderID);

		List<BoardInfoResp> boards = folder.getBoards();
		ArrayList<FacadeBoard> bwfs = new ArrayList<>(boards.size());
		for (BoardInfoResp board : boards) {
			FacadeBoard bwf = this.boardDaoService.getBoardByID(board.getBoardID());
			bwfs.add(bwf);
		}

		return bwfs;

	}

	@RequestMapping(value = "/delete/board", method = RequestMethod.GET)
	@ApiOperation(value = "删除看板列表接口", code = 200, httpMethod = "GET", response = FacadeBoard.class, responseContainer = "List")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public boolean deleteBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板ID", required = true) String boardID,
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹ID", required = true) String folderID)
			throws QueryException {
		return this.folderDaoService.removeBoard(boardID, folderID);
	}

	@RequestMapping(value = "/create/board", method = RequestMethod.GET)
	@ApiOperation(value = "保存看板", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public FacadeBoard createNewBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板名称", required = true) String boardName,
			@Valid @RequestParam(required = true) @ApiParam(value = "看板描述", required = true) String boardDesc,
			@Valid @RequestParam(required = true) @ApiParam(value = "文件夹ID", required = true) String folderID)
			throws QueryException {

		FacadeFolder folder = this.folderDaoService.getFolder(folderID);

		if (folder == null) {
			throw new QueryException("文件夹不存在");
		}

		FacadeBoard bwf = this.boardDaoService.createNewBoard(boardName, boardDesc, folderID);
		// this.folderDaoService.addBoardToFolder(bwf.getFolderID(),
		// bwf.getBoardID(),boardName);
		return bwf;

	}

	@RequestMapping(value = "/update/board", method = RequestMethod.POST)
	@ApiOperation(value = "保存看板", code = 200, httpMethod = "POST", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public FacadeBoard updateBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板ID", required = true) String boardID,
			@Valid @RequestBody @ApiParam(value = "看板样式", required = true) Map<String, Object> otherParam)
			throws QueryException {
		return this.boardDaoService.updateBoardStype(otherParam, boardID);

	}

	@RequestMapping(value = "/update/name", method = RequestMethod.GET)
	@ApiOperation(value = "看板名称", code = 200, httpMethod = "GET", response = FacadeBoard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求错误"), @ApiResponse(code = 500, message = "响应失败") })
	public FacadeBoard updateBoard(
			@Valid @RequestParam(required = true) @ApiParam(value = "看板ID", required = true) String boardID,
			@Valid @RequestParam(required = true) @ApiParam(value = "看板名称", required = true) String boardName,
			@Valid @RequestParam(required = true) @ApiParam(value = "看板描述", required = true) String boardDesc)
			throws QueryException {

		return this.boardDaoService.updateBoardName(boardID, boardName, boardDesc);

	}

}
