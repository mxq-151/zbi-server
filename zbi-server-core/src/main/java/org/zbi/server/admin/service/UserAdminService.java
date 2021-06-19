package org.zbi.server.admin.service;

/**
 * 模型可以分配给个人
 * */
public interface UserAdminService {

	
	public void getModelsFromUser(int userID);
	
	public void getModelFromUser(int userID,String modelTag);

	public void addModelToUser(int userID,String modelTag,int modelID,int toUserID);

	public void deleteModelFromUser(int userID,String modelTag,int modelID,int toUserID);
	
	public void listUsers(int userID);
	
	public void listGroups(int userID);
	
	public void listModels(int userID);

}
