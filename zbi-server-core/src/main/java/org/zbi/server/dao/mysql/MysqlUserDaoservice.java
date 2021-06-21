package org.zbi.server.dao.mysql;

import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.UserDaoService;
import org.zbi.server.model.facade.FacadeUser;

@Component
public class MysqlUserDaoservice implements UserDaoService {

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

}
