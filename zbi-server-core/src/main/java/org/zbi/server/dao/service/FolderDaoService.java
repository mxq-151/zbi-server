package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeFolder;

public interface FolderDaoService {

	public List<FacadeFolder> getQueryFolders();

	public List<FacadeFolder> listAdminFolders();

	public FacadeFolder createFolder(String folderName, String folderDesc, int folderType) throws AdminException;

	public boolean deleteFolder(String folderID) throws AdminException;

	public boolean updateFolder(String folderID, String folderName, String folderDesc) throws AdminException;

}
