package org.zbi.server.dao.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.zbi.server.model.config.ModelConfig;
import org.zbi.server.model.response.ModelDescResp;

@Component
public interface ConfigDaoService<T> {
	
	public ModelConfig getQuerySqlModel(T modelTag);
	
	public List<ModelDescResp> getModelDscriptions(String key);

}
