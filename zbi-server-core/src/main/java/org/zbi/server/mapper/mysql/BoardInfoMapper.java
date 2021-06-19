package org.zbi.server.mapper.mysql;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.zbi.server.entity.mysql.BoardInfo;

@Mapper
public interface BoardInfoMapper {

	public boolean createBoard(String boardID,String boardName, String boardDesc);

	public BoardInfo getBoardByID(String boardID);

	public BoardInfo getBoardByName(String boardName);

	public boolean checkBoardName(String boardName);

	public boolean updateBoardParam(String boardID, Map<String, Object> otherParams);

	public boolean updateBoardName(String boardID, String boardName, String boardDesc);

	public boolean deleteBoard(String boardID);

}
