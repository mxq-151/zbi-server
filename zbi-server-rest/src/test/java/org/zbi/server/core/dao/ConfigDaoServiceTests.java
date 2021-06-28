package org.zbi.server.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zbi.server.core.test.ServiceTestBase;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.model.service.LoginUserService;
import org.zbi.server.rest.ZBiApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZBiApp.class)
class ConfigDaoServiceTests extends ServiceTestBase {
	@Autowired
	ConfigDaoService configDaoService;

	@Autowired
	private LoginUserService loginUserService;

	String tableID = "1";

	@Test
	void testSaveConfigTable() throws AdminException {
		this.developerLogin();
		FacadeTable table = this.createTable();
		this.configDaoService.saveConfigTable(table);
		Assert.assertTrue(this.configDaoService.getConfigColumns(tableID).size() == 3);
		Assert.assertEquals(1, this.configDaoService.queryConfigTables().size());
	}

	private FacadeTable createTable() {
		FacadeTable table = new FacadeTable();
		table.setConnName("11");
		table.setEngineType(EngineType.CALCITE);
		table.setProject("sales");
		table.setSource(true);
		table.setTableID(tableID);
		table.setTableName("sale_detail");
		table.setTableType(1);
		table.setTblAlias("sd");
		List<ConfigColumn> columns = new ArrayList<>();
		columns.add(this.createColumn("日期", "sdate", "sale_date", ColumnType.DATE, true, false, "sd1", tableID, null));
		columns.add(this.createColumn("销售员", "sm", "sale_man", ColumnType.STRING, false, false, "sm1", tableID, null));
		columns.add(this.createColumn("销售量", "sn", "sale_num", ColumnType.NUMBER, false, false, "sn1", tableID,
				AggType.SUM));
		table.setColumns(columns);

		return table;
	}

	private ConfigColumn createColumn(String chinese, String alias, String colName, ColumnType type, boolean forceDim,
			boolean measure, String uuid, String tableID, AggType aggType) {

		ConfigColumn cc = new ConfigColumn();
		cc.setChinese(chinese);
		cc.setAlias(alias);
		cc.setColName(colName);
		cc.setColumnType(type);
		cc.setForceDim(forceDim);
		cc.setMeasure(measure);
		cc.setUuid(uuid);
		cc.setTableID(tableID);
		return cc;
	}

	@Test
	public void testDeleteTable() throws AdminException {
		this.developerLogin();
		this.configDaoService.deleteConfigTable(tableID);
		Assert.assertEquals(0, this.configDaoService.queryConfigTables().size());
	}

	@Test
	public void saveModel() throws AdminException {
		this.developerLogin();
		QueryModel model = this.formatQueryModel();
		List<QueryColumn> queryColumn = this.formatQueryColumns();
		this.configDaoService.saveModel(model.getModelTag(), model.getModelName(), queryColumn);
	}

	private QueryModel formatQueryModel() {
		QueryModel model = new QueryModel();
		model.setModelID("m1");
		model.setModelName("test");
		model.setModelTag("m1");
		return model;
	}

	private List<QueryColumn> formatQueryColumns() {

		List<QueryColumn> array = new ArrayList<>();
		QueryModel model = this.formatQueryModel();
		FacadeTable table = this.createTable();
		List<ConfigColumn> columns = table.getColumns();
		for (ConfigColumn cc : columns) {
			QueryColumn qc = new QueryColumn();
			qc.setColID(cc.getUuid());
			qc.setFromTable(table.getTableID());
			qc.setModelTag(model.getModelTag());
			array.add(qc);
		}

		return array;
	}

	@Test
	public void testInsertUserColLimit() throws AdminException {
		this.departmentLogin();
		String userID = this.loginUserService.getLoginUser().getUserID();
		this.superAdminLogin();
		FacadeTable table = this.createTable();
		List<ConfigColumn> columns = table.getColumns();
		ArrayList<String> cols = new ArrayList<>();
		for (ConfigColumn col : columns) {
			cols.add(col.getUuid());
			break;
		}

		String modelTag=this.formatQueryModel().getModelTag();
		this.configDaoService.insertUserColLimit(cols, userID, modelTag);
		Assert.assertEquals(1, this.configDaoService.queryUserColLimit(userID, modelTag).size());
		this.departmentLogin();
		Assert.assertEquals(1,this.configDaoService.queryUserTotalColLimit(modelTag).size());
	}

}
