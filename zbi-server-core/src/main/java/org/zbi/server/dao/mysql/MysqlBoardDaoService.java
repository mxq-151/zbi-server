package org.zbi.server.dao.mysql;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.BoardDaoService;
import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.entity.mysql.BoardInfo.BoardVersion;
import org.zbi.server.mapper.mysql.BoardInfoMapper;
import org.zbi.server.mapper.mysql.FolderInfoMapper;
import org.zbi.server.mapper.mysql.ReportInfoMapper;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeBoard;
import org.zbi.server.model.service.LoginUserService;

@Component
public class MysqlBoardDaoService implements BoardDaoService {

	@Autowired
	FolderInfoMapper folderInfoMapper;

	@Autowired
	BoardInfoMapper boardInfoMapper;

	@Autowired
	ReportInfoMapper reportInfoMapper;

	@Autowired
	private LoginUserService loginUserService;

	@Override
	public BoardInfo createNewBoard(String boardName, String boardDesc, String folderID) throws QueryException {
		// TODO Auto-generated method stub

		if (this.folderInfoMapper.getFolder(folderID) == null) {
			throw new QueryException("无法根据文件夹ID找到相对应的文件夹");
		}

		if (boardInfoMapper.checkBoardName(boardName)) {
			throw new QueryException("看板名称已存在");
		}

		String boardID = java.util.UUID.randomUUID().toString();
		BoardInfo board = new BoardInfo();
		board.setBoardDesc(boardDesc);
		board.setBoardID(boardID);
		board.setBoardName(boardName);
		board.setBoardVersion(BoardVersion.V1);
		board.setOtherParams(Collections.emptyMap());
		board.setFolderID(folderID);
		boardInfoMapper.createBoard(board);
		return board;

	}

	@Override
	public boolean deleteBoard(String boardID) {
		// TODO Auto-generated method stub
		return boardInfoMapper.deleteBoard(boardID);
	}

	@Override
	public BoardInfo getBoardByID(String boardID) {
		// TODO Auto-generated method stub
		return this.boardInfoMapper.getBoardByID(boardID);
	}

	@Override
	public BoardInfo updateBoardStype(Map<String, Object> otherParams, String boardID) throws QueryException {
		// TODO Auto-generated method stub
		this.boardInfoMapper.updateBoardParam(boardID, otherParams, this.loginUserService.getLoginUser().getUserID());
		return this.boardInfoMapper.getBoardByID(boardID);
	}

	@Override
	public BoardInfo updateBoardName(String boardID, String boardName, String boardDesc) throws QueryException {
		// TODO Auto-generated method stub
		this.boardInfoMapper.updateBoardName(boardID, boardName, boardDesc,
				this.loginUserService.getLoginUser().getUserID());
		return boardInfoMapper.getBoardByID(boardID);
	}

	@Override
	public List<FacadeBoard> getBoardByFolderID(String folderID) throws QueryException {
		// TODO Auto-generated method stub
		List<FacadeBoard> boards = this.boardInfoMapper.getBoardByFolder(folderID);
		return boards;
	}

}
