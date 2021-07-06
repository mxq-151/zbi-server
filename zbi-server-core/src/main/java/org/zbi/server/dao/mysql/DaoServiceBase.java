package org.zbi.server.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zbi.server.entity.mysql.EntityBase;
import org.zbi.server.model.service.LoginUserService;

public class DaoServiceBase {

	@Autowired
	LoginUserService loginUserService;

	public void preHandle(EntityBase entity) {
		entity.setLastUpdater(this.loginUserService.getLoginUser().getUserID());
	}

	public void preHandle(List<? extends EntityBase> entities) {
		for (EntityBase entity : entities) {
			entity.setLastUpdater(this.loginUserService.getLoginUser().getUserID());
		}

	}

}
