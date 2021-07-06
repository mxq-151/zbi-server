package org.zbi.server.dao.service;

import java.util.List;

import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeUser;

public interface UserDaoService {

	public void createUser(FacadeUser user) throws AdminException;

	public List<FacadeUser> loadAllUser();

}
