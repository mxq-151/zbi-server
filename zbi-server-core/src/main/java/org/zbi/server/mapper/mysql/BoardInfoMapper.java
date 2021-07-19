package org.zbi.server.mapper.mysql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.model.facade.FacadeBoard;

@Mapper
public interface BoardInfoMapper {

	public boolean saveBoard(BoardInfo board);

	public BoardInfo loadBoardByID(String boardID);

	public List<FacadeBoard> loadBoardByFolder(String folderID);

	public List<BoardInfo> loadBoardByFolders(List<String> list);

	public boolean checkBoardName(String boardName);

	public boolean updateBoardParam(String boardID, Map<String, Object> otherParams, String lastUpdater);

	public boolean updateBoardName(String boardID, String boardName, String boardDesc, String lastUpdater);

	public boolean deleteBoard(String boardID);

}
