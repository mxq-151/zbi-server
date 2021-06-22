package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeGroup;

public interface GroupDaoService {

	/**
	 * 创建用户
	 */
	public FacadeGroup createGroup(String groupName, String groupDesc) throws AdminException;

	/**
	 * 获取用户组
	 */
	public FacadeGroup getGroupByGroupID(String groupID);

	/**
	 * 获取管理的用户组
	 */
	public List<FacadeGroup> getAdminGroup();

	/**
	 * 添加用户
	 */
	public int addUserToGroup(List<String> users, String groupID) throws AdminException;

	/**
	 * 移除用户
	 */
	public int deleteUserInGroup(List<String> users, String groupID) throws AdminException;

	/**
	 * 更换管理人员
	 * */
	public void updateGroupAdmin(String adminID, List<String> groupIDS) throws AdminException;
	
	/**
	 * 删除用户组
	 * */
	public void deleteGroup(String groupID) throws AdminException;

}
