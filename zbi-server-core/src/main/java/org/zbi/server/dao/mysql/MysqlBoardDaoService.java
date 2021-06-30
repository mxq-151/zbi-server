package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.BoardDaoService;
import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.entity.mysql.BoardInfo.BoardVersion;
import org.zbi.server.mapper.mysql.BoardInfoMapper;
import org.zbi.server.mapper.mysql.FolderAndBoardMapper;
import org.zbi.server.mapper.mysql.FolderInfoMapper;
import org.zbi.server.mapper.mysql.ReportInfoMapper;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeBoard;

@Component
public class MysqlBoardDaoService implements BoardDaoService {

	@Autowired
	FolderInfoMapper folderInfoMapper;

	@Autowired
	BoardInfoMapper boardInfoMapper;

	@Autowired
	FolderAndBoardMapper folderAndBoardMapper;

	@Autowired
	ReportInfoMapper reportInfoMapper;

	@Override
	public FacadeBoard createNewBoard(String boardName, String boardDesc, String folderID) throws QueryException {
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
		boardInfoMapper.createBoard(board);

		FacadeBoard facadeBoard = new FacadeBoard();
		facadeBoard.setBoardDesc(boardDesc);
		facadeBoard.setBoardName(boardName);
		facadeBoard.setBoardID(boardID);
		facadeBoard.setFolderID(folderID);
		facadeBoard.setOtherParams(null);
		facadeBoard.setReports(Collections.emptyList());

		folderAndBoardMapper.addBoardToFolder(boardID, folderID);

		return facadeBoard;

	}

	@Override
	public boolean deleteBoard(String boardID) throws QueryException {
		// TODO Auto-generated method stub
		return boardInfoMapper.deleteBoard(boardID);
	}

	@Override
	public FacadeBoard getBoardByID(String boardID) throws QueryException {
		// TODO Auto-generated method stub
		String folderID=this.folderAndBoardMapper.getBoardFolder(boardID);
		return toFacadeBoard(boardInfoMapper.getBoardByID(boardID), this.reportInfoMapper.getReportID(boardID),folderID);
	}

	@Override
	public FacadeBoard updateBoardStype(Map<String, Object> otherParams, String boardID) throws QueryException {
		// TODO Auto-generated method stub
		String folderID=this.folderAndBoardMapper.getBoardFolder(boardID);
		this.boardInfoMapper.updateBoardParam(boardID, otherParams);
		return toFacadeBoard(boardInfoMapper.getBoardByID(boardID),folderID);
	}

	@Override
	public FacadeBoard updateBoardName(String boardID, String boardName, String boardDesc) throws QueryException {
		// TODO Auto-generated method stub
		this.boardInfoMapper.updateBoardName(boardID, boardName, boardDesc);
		String folderID=this.folderAndBoardMapper.getBoardFolder(boardID);
		return toFacadeBoard(boardInfoMapper.getBoardByID(boardID),folderID);
	}

	protected FacadeBoard toFacadeBoard(BoardInfo board,String folderID) {
		FacadeBoard facadeBoard = new FacadeBoard();
		
		if(board==null)
		{
			return facadeBoard;
		}
		
		facadeBoard.setBoardDesc(board.getBoardDesc());
		facadeBoard.setBoardName(board.getBoardName());
		facadeBoard.setBoardID(board.getBoardID());
		facadeBoard.setFolderID(folderID);
		facadeBoard.setOtherParams(board.getOtherParams());
		facadeBoard.setReports(Collections.emptyList());

		return facadeBoard;
	}

	protected FacadeBoard toFacadeBoard(BoardInfo board, List<String> reports,String folderID) {
		FacadeBoard facadeBoard = new FacadeBoard();
		if(board==null)
		{
			return facadeBoard;
		}
		facadeBoard.setBoardDesc(board.getBoardDesc());
		facadeBoard.setBoardName(board.getBoardName());
		facadeBoard.setBoardID(board.getBoardID());
		facadeBoard.setFolderID(folderID);
		facadeBoard.setOtherParams(board.getOtherParams());
		facadeBoard.setReports(reports);
		return facadeBoard;
	}

	@Override
	public List<FacadeBoard> getBoardByFolderID(String folderID) throws QueryException {
		// TODO Auto-generated method stub

		List<BoardInfo> boards = this.folderAndBoardMapper.getBoardsByFolderID(folderID);

		List<FacadeBoard> fboards = new ArrayList<>();
		for (BoardInfo board : boards) {
			List<String> reportIDS = this.reportInfoMapper.getReportID(board.getBoardID());
			fboards.add(this.toFacadeBoard(board, reportIDS,folderID));
		}

		return fboards;
	}

}
