package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.FolderInfo;

public interface FolderInfoMapper {

	public boolean saveFolder(FolderInfo folder);

	public FolderInfo loadFolder(String folderID);

	public List<FolderInfo> loadFolders(String userID);

	public List<FolderInfo> loadAdminFolder(String userID);

	public boolean checkFolderByName(String folderName, int folderType, String adminID);

	public void deleteFolderByID(String folderID);

	public boolean updateFolder(String folderID, String folderName, String folderDesc);

}
