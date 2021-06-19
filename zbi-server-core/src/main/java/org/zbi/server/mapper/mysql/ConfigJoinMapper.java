package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.config.ConfigJoin;

public interface ConfigJoinMapper {

	public void insertJoin(ConfigJoin join);

	public List<ConfigJoin> loadAllJoins();

	public void deleteByPrimaryKey(String joinID);

}
