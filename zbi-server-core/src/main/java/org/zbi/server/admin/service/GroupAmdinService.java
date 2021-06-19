package org.zbi.server.admin.service;

import java.util.List;
import java.util.Map;

import org.zbi.server.entity.mysql.BoardInfo;

public interface GroupAmdinService {
	
	

	/**
	 * 操作用户组里的用户----------------------------------------------------
	 * */
	public void addUserToGruop(String userName, int userID, int groupID);

	public void getUsersFromGroup(int groupID);

	public void deleteUserFromGruop(int groupID);

	/**
	 * 操作用户组--------------------------------------------------------------
	 * */
	public void createNewGroup(String groupName, String groupDesc, int createrID, int belongID);

	public void deleteGroup(int groupID, int deleterID);

	/**
	 * 操作用户组里的模型--------------------------------------------------------
	 * */
	public void addModelToGroup(int userID, String modelTag, int modelID, int groupID);

	public void deleteModelFromGroup(int userID, String modelTag, int modelID, int groupID);

	public void getModelsFromGroup(int groupID);
	
	public void updateBoardParams(Map<String,Object> params);
	
	
	/**
	 * 操作用户组里的看板--------------------------------------------------------
	 * */
	public void addBoardToGroup(int boardID,int groupID,int rightLevel);
	
	public void deleteBoardInGroup(int boardID,int groupID);
	
	public List<BoardInfo> getBoardsInGroup(int groupID,int userID);

}
