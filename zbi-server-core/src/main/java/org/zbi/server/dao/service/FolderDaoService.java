package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeFolder;

public interface FolderDaoService {

	public List<FacadeFolder> getFolders();

	public FacadeFolder getFolder(String folderID);

	public FacadeFolder createFolder(String folderName, String folderDesc) throws AdminException;

	public boolean addBoardToFolder(String folderID, String boardID, String boardName) throws AdminException;

	public boolean removeBoard(String boardID, String folderID) throws AdminException;

	public boolean deleteFolder(String folderID) throws AdminException;

	public boolean updateFolder(String folderID, String folderName, String folderDesc) throws AdminException;

}
