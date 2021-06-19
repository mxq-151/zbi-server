package org.zbi.server.dao.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zbi.server.dao.service.GroupDaoService;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.model.facade.FacadeGroup;
import org.zbi.server.model.facade.FacadeUser;

public class MemoryGroupDao implements GroupDaoService {
	
	private List<FacadeGroup> groups=new ArrayList<>();
	
	@Autowired
	private UserDaoService userDaoService;

	@Override
	public FacadeGroup getGroupWrapperInfoByGroupName(String groupName) {
		// TODO Auto-generated method stub
		
		for(FacadeGroup group:groups)
		{
			if(group.getGroupName().equals(groupName))
			{
				return group;
			}
		}
		return null;
	}

	@Override
	public FacadeGroup getGroupWrapperInfoByGroupID(int groupID) {
		// TODO Auto-generated method stub
		for(FacadeGroup group:groups)
		{
			if(group.getGroupID()==(groupID))
			{
				return group;
			}
		}
		return null;
	}

	@Override
	public List<FacadeGroup> getGroupWrapperInfoByGroupID(List<Long> groupIDS) {
		// TODO Auto-generated method stub
		
		List<FacadeGroup> result=new ArrayList<>(groupIDS.size());
		for(long groupID:groupIDS)
		{
			for(FacadeGroup group:groups)
			{
				
				if(group.getGroupID()==(groupID))
				{
					result.add(group);
					break;
				}
			}
			
		}
		
		return result;
	}

	@Override
	public List<FacadeUser> getUsersInGroup(int groupID) {
		// TODO Auto-generated method stub
		
		FacadeGroup group=this.getGroupWrapperInfoByGroupID(groupID);
		List<Integer> userIDS=group.getUsers();
		
		List<FacadeUser> users=new ArrayList<>(userIDS.size());
		for(int userID:userIDS)
		{
			FacadeUser user=this.userDaoService.getUserWrapperInfoByID(userID);
			if(user!=null)
			{
				users.add(user);
			}
		}
		return users;
	}

}
