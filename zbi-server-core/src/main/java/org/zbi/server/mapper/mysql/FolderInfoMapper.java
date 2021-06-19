package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.FolderInfo;


public interface FolderInfoMapper {

	public boolean createFolder(String folderID,String folderName, String folderDesc);

	public FolderInfo getFolder(String folderID);

	public List<FolderInfo> getFolders();

	public boolean checkFolderByName(String folderName);

	public void deleteFolderByID(String folderID);

	public boolean updateFolder(String folderID, String folderName, String folderDesc);

}
