package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.UserInfoMapper;
import org.zbi.server.model.facade.FacadeUser;

@Component
public class MysqlUserDaoservice implements UserDaoService {
	
	@Autowired
	UserInfoMapper userInfoMapper;

	@Override
	public FacadeUser getUserWrapperInfoByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FacadeUser getUserWrapperInfoByID(long userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(FacadeUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<FacadeUser> loadAllUser() {
		// TODO Auto-generated method stub
		List<UserInfo> users=this.userInfoMapper.loadUsers();
		List<FacadeUser> fus=new ArrayList<>();
		for(UserInfo user:users)
		{
			FacadeUser fu=this.toFacadeUser(user);
			fus.add(fu);
		}
		
		return fus;
	}
	
	private FacadeUser toFacadeUser(UserInfo user)
	{
		FacadeUser fu=new FacadeUser();
		BeanUtils.copyProperties(user, fu);
		return fu;
	}

}
