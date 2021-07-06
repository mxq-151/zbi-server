package org.zbi.server.mapper.mysql;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.zbi.server.entity.mysql.BoardInfo;

@Mapper
public interface BoardInfoMapper {

	public boolean createBoard(BoardInfo board);

	public BoardInfo getBoardByID(String boardID);

	public boolean checkBoardName(String boardName);

	public boolean updateBoardParam(String boardID, Map<String, Object> otherParams, String lastUpdater);

	public boolean updateBoardName(String boardID, String boardName, String boardDesc, String lastUpdater);

	public boolean deleteBoard(String boardID);

}
