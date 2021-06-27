package org.zbi.server.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zbi.server.core.test.ServiceTestBase;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.facade.FacadeTable;
import org.zbi.server.rest.ZBiApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZBiApp.class)
class ConfigDaoServiceTests extends ServiceTestBase{
	@Autowired
	ConfigDaoService  configDaoService;
	
	String tableID="1";

	@Test
	void testSaveConfigTable() {

		FacadeTable table=new FacadeTable();
		table.setConnName("11");
		table.setEngineType(EngineType.CALCITE);
		table.setProject("sales");
		table.setSource(true);
		table.setTableID(tableID);
		table.setTableName("sale_detail");
		table.setTableType(1);
		table.setTblAlias("sd");
		List<ConfigColumn> columns=new ArrayList<>();
		ConfigColumn cc=new ConfigColumn();
		cc.setChinese("日期");
		cc.setAlias("sdate");
		cc.setColName("sale_date");
		cc.setColumnType(ColumnType.DATE);
		cc.setForceDim(true);
		cc.setMeasure(false);
		cc.setUuid("sd1");
		cc.setTableID(tableID);
		columns.add(cc);
		
		cc=new ConfigColumn();
		cc.setChinese("销售员");
		cc.setAlias("sm");
		cc.setColName("sale_man");
		cc.setColumnType(ColumnType.STRING);
		cc.setForceDim(false);
		cc.setMeasure(false);
		cc.setUuid("sm1");
		cc.setTableID(tableID);
		columns.add(cc);
		
		cc=new ConfigColumn();
		cc.setChinese("销售量");
		cc.setAlias("sn");
		cc.setColName("sale_num");
		cc.setColumnType(ColumnType.NUMBER);
		cc.setForceDim(false);
		cc.setMeasure(true);
		cc.setUuid("sn1");
		cc.setTableID(tableID);
		cc.setAggType(AggType.SUM);
		columns.add(cc);
		table.setColumns(columns);
		
		this.configDaoService.saveConfigTable(table);
	}
	
	@Test
	public void testDeleteTable()
	{
		this.configDaoService.deleteConfigTable(tableID);
	}

}
