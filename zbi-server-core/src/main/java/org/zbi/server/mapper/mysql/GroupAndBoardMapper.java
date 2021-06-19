package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.BoardInfo;

public interface GroupAndBoardMapper {

	public List<BoardInfo> getBoardInGroup(int groupID);

	public boolean addBoardToGroup(String boardID, String groupID);

	public boolean deleteBoardInGroup(int boardID, int groupID);

}
