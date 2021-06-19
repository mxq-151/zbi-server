package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.facade.FacadeGroup;
import org.zbi.server.model.facade.FacadeUser;

public interface GroupDaoService {
	
	
	/**
	 * 获取用户组
	 * */
	public FacadeGroup getGroupWrapperInfoByGroupName(String groupName);
	
	/**
	 * 获取用户组
	 * */
	public FacadeGroup getGroupWrapperInfoByGroupID(int groupID);
	
	/**
	 * 批量获取用户
	 * */
	public List<FacadeGroup> getGroupWrapperInfoByGroupID(List<Long> groupIDS);
	
	/**
	 * 获取用户组下所有用户
	 * */
	public List<FacadeUser> getUsersInGroup(int groupID);
	
	
	

}
