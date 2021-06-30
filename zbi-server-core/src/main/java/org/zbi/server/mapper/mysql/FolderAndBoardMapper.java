package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.BoardInfo;
import org.zbi.server.model.response.BoardInfoResp;

public interface FolderAndBoardMapper {

	public List<BoardInfoResp> getBoardDescByFolderID(String folderID);

	public boolean deleteBoardInFolder(String boardID, String folderID);

	public boolean addBoardToFolder(String boardID, String folderID);
	
	public String getBoardFolder(String boardID);

	public List<BoardInfo> getBoardsByFolderID(String folderID);

}
