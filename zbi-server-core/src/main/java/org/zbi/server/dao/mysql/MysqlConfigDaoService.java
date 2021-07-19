package org.zbi.server.dao.mysql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.entity.mysql.GroupColLimit;
import org.zbi.server.entity.mysql.GroupDataLimit;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.entity.mysql.UserInfo;
import org.zbi.server.mapper.mysql.ColumnLimitMapper;
import org.zbi.server.mapper.mysql.DataLimitMapper;
import org.zbi.server.mapper.mysql.QueryModelMapper;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.facade.FacadeUser;
import org.zbi.server.model.response.ModelInfoResp;
import org.zbi.server.model.service.ModelService;

@Component
@Transactional(rollbackFor = { SQLException.class, IOException.class })
public class MysqlConfigDaoService extends DaoServiceBase implements ConfigDaoService {

	@Autowired
	private DataLimitMapper dataLimitMapper;

	@Autowired
	ColumnLimitMapper columnLimitMapper;

	@Autowired
	private QueryModelMapper queryModelMapper;
	
	@Autowired
	private ModelService modelService;

	@Override
	public List<QueryModel> loadModel(String key) {
		// TODO Auto-generated method stub
		FacadeUser user = this.loginUserService.getLoginUser();
		if (user.getRoleType() == UserInfo.SUPERADMIN || user.getRoleType() == UserInfo.DEVELOPER) {
			return this.queryModelMapper.loadAllModel();
		}
		String userID = this.loginUserService.getLoginUser().getUserID();
		return this.queryModelMapper.loadQueryModel(userID);
	}

	@Override
	public List<GroupColLimit> loadGroupColLimit(String groupID, String modelID) {
		// TODO Auto-generated method stub
		return this.columnLimitMapper.loadGroupColLimit(groupID, modelID);
	}

	@Override
	public void saveGroupColLimit(List<String> cols, String groupID, String modelID) {
		// TODO Auto-generated method stub
		this.columnLimitMapper.deleteGroupColLimit(groupID, modelID);
		List<GroupColLimit> queryColumns = new ArrayList<>(cols.size());
		for (String col : cols) {
			GroupColLimit limit = new GroupColLimit();
			limit.setColID(col);
			limit.setModelTag(modelID);
			limit.setGroupID(groupID);
			queryColumns.add(limit);
		}

		this.preHandle(queryColumns);
		this.columnLimitMapper.saveGroupColLimit(queryColumns);
	}

	@Override
	public void saveGroupDataLimit(List<String> words, String groupID, String colID) {
		// TODO Auto-generated method stub
		this.dataLimitMapper.deleteGroupDataLimit(groupID, colID);
		List<GroupDataLimit> dataLimits = new ArrayList<>();

		for (String word : words) {
			GroupDataLimit limit = new GroupDataLimit();
			limit.setColID(colID);
			limit.setWord(word);
			limit.setGroupID(groupID);
			this.preHandle(limit);
			dataLimits.add(limit);
		}
		this.dataLimitMapper.saveGroupDataLimit(dataLimits);
	}

	@Override
	public List<String> loadGroupDataLimit(String groupID, String colID) {
		// TODO Auto-generated method stub
		return this.dataLimitMapper.loadGroupDataLimit(groupID, colID);
	}

	@Override
	public List<String> loadDataLimit(String colID) {
		// TODO Auto-generated method stub
		return this.dataLimitMapper.loadDataLimit(this.loginUserService.getLoginUser().getUserID(), colID);
	}

	@Override
	public ModelInfoResp loadModelInfo(String modelID) {
		// TODO Auto-generated method stub
		List<QueryColumn> columns = this.queryModelMapper.loadQueryColumn(modelID);
		ModelInfoResp model = new ModelInfoResp();
		List<ConfigColumn> dimensions = new ArrayList<>();
		model.setDimensions(dimensions);
		List<ConfigColumn> measures = new ArrayList<>();
		model.setMeasures(measures);
		
		for(QueryColumn qc:columns)
		{
			ConfigColumn cc=this.modelService.getColumn(qc.getColID());
			if(cc.isMeasure())
			{
				measures.add(cc);
			}else
			{
				dimensions.add(cc);
			}
		}
		return model;
	}

	@Override
	public List<String> loadColLimit(String modelID) {
		// TODO Auto-generated method stub
		String userID = this.loginUserService.getLoginUser().getUserID();
		return this.columnLimitMapper.loadColLimit(userID, modelID);
	}

	@Override
	public List<QueryModel> loadGroupModel(String groupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveGroupModel(String groupID, String modelID) {
		// TODO Auto-generated method stub

	}

}
