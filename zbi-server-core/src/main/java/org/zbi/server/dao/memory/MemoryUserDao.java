package org.zbi.server.dao.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.model.facade.FacadeUser;

@Component
public class MemoryUserDao implements UserDaoService {
	
	private List<FacadeUser> users=new ArrayList<>();

	@Override
	public FacadeUser getUserWrapperInfoByEmail(String email) {
		// TODO Auto-generated method stub
		
		for(FacadeUser user:users)
		{
			if(user.getEmail().equals(email))
			{
				return user;
			}
		}
		return null;
	}

	@Override
	public FacadeUser getUserWrapperInfoByID(long userID) {
		// TODO Auto-generated method stub
		for(FacadeUser user:users)
		{
			if(user.getUserID()==userID)
			{
				return user;
			}
		}
		return null;
	}

	@Override
	public void createUser(FacadeUser user) {
		// TODO Auto-generated method stub
		this.users.add(user);
		
	}

}
