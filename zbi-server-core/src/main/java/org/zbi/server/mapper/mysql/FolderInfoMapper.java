package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.entity.mysql.FolderInfo;


public interface FolderInfoMapper {

	public boolean createFolder(FolderInfo folder);

	public FolderInfo getFolder(String folderID);

	public List<FolderInfo> getFolders(String userID);

	public boolean checkFolderByName(String folderName,int folderType,String adminID);

	public void deleteFolderByID(String folderID);

	public boolean updateFolder(String folderID, String folderName, String folderDesc);

}
