package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.entity.mysql.GroupBoard;

public interface GroupAndBoardMapper {

	public List<BoardInfo> getBoardInGroup(String groupID);

	public boolean addBoardToGroup(List<GroupBoard> list);

	public boolean deleteBoardInGroup(String groupID);

}
