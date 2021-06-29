package org.zbi.server.core.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zbi.server.entity.mysql.QueryColumn;
import org.zbi.server.entity.mysql.QueryModel;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.QueryException;
import org.zbi.server.model.request.RequestColumn;
import org.zbi.server.model.request.RequestParam;
import org.zbi.server.model.service.DefaultQueryServiceImpl;
import org.zbi.server.rest.ZBiApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZBiApp.class)
public class DefaultQueryServiceTest extends ServiceTestBase{
	
	@Autowired
	private DefaultQueryServiceImpl defaultQueryServiceImpl;
	
	@Autowired
	ConfigDaoServiceTests config;
	
	@Test
	public void testQuery() throws AdminException, ParseException, SQLException, QueryException, IOException
	{
		
		config.testSaveConfigTable();
		
		RequestParam param=new RequestParam();
		List<RequestColumn> dimensions=new ArrayList<>();
		QueryModel model=config.formatQueryModel();
		String modelTag=model.getModelTag();
		param.setDimensions(dimensions);
		param.setModelTag(modelTag);
		param.setPageSize(10);
		
		List<QueryColumn> columns=config.formatQueryColumns();
		for(QueryColumn qc:columns)
		{
			RequestColumn rc=new RequestColumn();
			rc.setUuid(qc.getColID());
			dimensions.add(rc);
		}
		this.departmentLogin();
		defaultQueryServiceImpl.query(param);
	}

}
