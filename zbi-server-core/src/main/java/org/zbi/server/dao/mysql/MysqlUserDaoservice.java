package org.zbi.server.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.UserInfoMapper;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.service.LoginUserService;

@Component
public class MysqlUserDaoservice implements UserDaoService {

	@Autowired
	UserInfoMapper userInfoMapper;

	@Autowired
	private LoginUserService loginUserService;

	@Override
	public void createUser(FacadeUser user) throws AdminException {
		// TODO Auto-generated method stub
		boolean exsit=this.userInfoMapper.checkEmail(user.getEmail());
		if(exsit)
		{
			throw new AdminException("已存在用户:"+user.getEmail());
		}
		UserInfo ui = this.toUserInfo(user);
		String userID = java.util.UUID.randomUUID().toString();
		String pwd = this.loginUserService.encodePassword("123456");
		ui.setUserID(userID);
		ui.setPassword(pwd);
		this.userInfoMapper.createUser(ui);
	}

	@Override
	public List<FacadeUser> loadAllUser() {
		// TODO Auto-generated method stub
		List<UserInfo> users = this.userInfoMapper.loadUsers();
		List<FacadeUser> fus = new ArrayList<>();
		for (UserInfo user : users) {
			FacadeUser fu = this.toFacadeUser(user);
			fus.add(fu);
		}

		return fus;
	}

	private FacadeUser toFacadeUser(UserInfo user) {
		FacadeUser fu = new FacadeUser();
		BeanUtils.copyProperties(user, fu);
		return fu;
	}

	private UserInfo toUserInfo(FacadeUser user) {
		UserInfo fu = new UserInfo();
		BeanUtils.copyProperties(user, fu);
		return fu;
	}

}
