package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.facade.FacadeFolder;

public interface FolderDaoService {
	
	
	public List<FacadeFolder> getFolders();
	
	public FacadeFolder getFolder(String folderID);
	
	public FacadeFolder createFolder(String folderName,String folderDesc,String userID) throws QueryException;
	
	public boolean addBoardToFolder(String folderID,String boardID,String boardName);
	
	public boolean removeBoard(String boardID,String folderID);
	
	public boolean deleteFolder(String folderID) throws QueryException;
	
	public boolean updateFolder(String folderID,String folderName,String folderDesc) throws QueryException;

	

}