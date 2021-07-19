package org.zbi.server.mapper.mysql;

import java.util.List;

import org.zbi.server.model.config.ConfigJoin;

public interface ConfigJoinMapper {

	public void saveJoin(ConfigJoin join);

	public List<ConfigJoin> loadAllJoin();

	public void deleteByJoinID(String joinID);
	
}
