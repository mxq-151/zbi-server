package org.zbi.server.dao.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.model.config.ModelDimension;
import org.zbi.server.model.config.ModelMeasure;
import org.zbi.server.model.config.ModelConfig;
import org.zbi.server.model.config.QuerySqlModel;
import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.response.ModelDescResp;

@Component
public class MemoryConfigDao implements ConfigDaoService<String> {
	
	private List<ModelConfig> modelConfigs=new ArrayList<>();
	
	private  String uuid="2489fa3a-cf1b-42a1-bb64-895b3ea49003";
	
	private int loop=0;

	@Override
	public ModelConfig getQuerySqlModel(String modelTag) {
		// TODO Auto-generated method stub
		for(ModelConfig config:modelConfigs)
		{
			if(config.getModelTag().equals(modelTag))
			{
				return config;
			}
		}
		return null;
	}
	
	
	public void mockModelConfigs()
	{
		this.uuid="2489fa3a-cf1b-42a1-bb64-895b3ea49003";
		this.loop=0;
		
		ModelConfig config=new ModelConfig();
		config.setConnName("calcite-1");
		config.setModelID("1");
		config.setModelName("销售统计模型");
		config.setModelTag("123456");
		config.setEngineType(EngineType.CALCITE);
		QuerySqlModel querySqlModel=new QuerySqlModel();
		querySqlModel.setProject("kylin_test");
		querySqlModel.setDimensions(getConfigDimensions());
		querySqlModel.setMeasures(this.getConfigMeasures());
		querySqlModel.setFactTable("sales.sale");
		config.setQuerySqlModel(querySqlModel);
		modelConfigs.add(config);
		
		this.uuid="2489fa3a-cf1b-42a1-bb64-895b3ea49003";
		this.loop=0;
		config=new ModelConfig();
		config.setConnName("calcite-1");
		config.setModelID("2");
		config.setModelName("销售统计模型2");
		config.setModelTag("123456a");
		config.setEngineType(EngineType.CALCITE);
		querySqlModel=new QuerySqlModel();
		querySqlModel.setProject("kylin_test");
		querySqlModel.setDimensions(getConfigDimensions());
		querySqlModel.setMeasures(this.getConfigMeasures());
		querySqlModel.setFactTable("sales.sale");
		config.setQuerySqlModel(querySqlModel);
		modelConfigs.add(config);
		
		this.uuid="2489fa3a-cf1b-42a1-bb64-895b3ea49003";
		this.loop=0;
		config=new ModelConfig();
		config.setConnName("calcite-1");
		config.setModelID("3");
		config.setModelName("销售统计模型3");
		config.setModelTag("123456b");
		config.setEngineType(EngineType.CALCITE);
		querySqlModel=new QuerySqlModel();
		querySqlModel.setProject("kylin_test");
		querySqlModel.setDimensions(getConfigDimensions());
		querySqlModel.setMeasures(this.getConfigMeasures());
		querySqlModel.setFactTable("sales.sale");
		config.setQuerySqlModel(querySqlModel);
		modelConfigs.add(config);
		
		this.uuid="2489fa3a-cf1b-42a1-bb64-895b3ea49003";
		this.loop=0;
		config=new ModelConfig();
		config.setConnName("calcite-1");
		config.setModelID("4");
		config.setModelName("销售统计模型4");
		config.setModelTag("123456c");
		config.setEngineType(EngineType.CALCITE);
		querySqlModel=new QuerySqlModel();
		querySqlModel.setProject("kylin_test");
		querySqlModel.setDimensions(getConfigDimensions());
		querySqlModel.setMeasures(this.getConfigMeasures());
		querySqlModel.setFactTable("sales.sale");
		config.setQuerySqlModel(querySqlModel);
		modelConfigs.add(config);
	}
	
	private List<ModelDimension> getConfigDimensions()
	{
		List<ModelDimension> dimensions=new ArrayList<>();
		dimensions.add(getConfigDimension(ColumnType.DATE,"统计日期","statedate","sales.sale.statedate",null));
		dimensions.add(getConfigDimension(ColumnType.STRING,"城市","city","sales.sale.city","sales.sale.city"));
		dimensions.add(getConfigDimension(ColumnType.STRING,"产品名称","product","sales.sale.product","sales.sale.product"));
		dimensions.add(getConfigDimension(ColumnType.STRING,"销售人员","saller","sales.sale.saller","sales.sale.saller"));
		return dimensions;
	}
	
	private List<ModelMeasure> getConfigMeasures()
	{
		List<ModelMeasure> measures=new ArrayList<>();
		measures.add(getConfigMeasure(AggType.SUM,"销量","salenum","sales.sale.salenum"));
		measures.add(getConfigMeasure(AggType.SUM,"利润","profit","sales.sale.profit"));
		return measures;
	}
	
	private ModelDimension getConfigDimension(ColumnType columnType,String chinese,String alias,String colName,String indexPath)
	{
		ModelDimension d1=new ModelDimension();
		d1.setAlias(alias);
		d1.setColName(colName);
		d1.setColumnType(columnType);
		d1.setChinese(chinese);
		d1.setAdvanced(false);
		d1.setMeasure(false);
		d1.setUuid(this.uuid+(this.loop++));
		return d1;
	}
	
	private ModelMeasure getConfigMeasure(AggType aggType ,String chinese,String alias,String colName)
	{
		ModelMeasure m1=new ModelMeasure();
		m1.setAlias(alias);
		m1.setColName(colName);
		m1.setColumnType(ColumnType.NUMBER);
		m1.setChinese(chinese);
		m1.setAdvanced(false);
		m1.setMeasure(true);
		m1.setAggType(aggType);
		m1.setUuid(this.uuid+(this.loop++));
		return m1;
	}


	@Override
	public List<ModelDescResp> getModelDscriptions(String key) {
		// TODO Auto-generated method stub
		List<ModelDescResp> mds=new ArrayList<>();
		if(key==null || key.length()==0)
		{
			
			for(ModelConfig config:this.modelConfigs)
			{
				ModelDescResp desc=new ModelDescResp();
				desc.setModelID(config.getModelID());
				desc.setModelName(config.getModelName());
				desc.setModelTag(config.getModelTag());
				mds.add(desc);
			}
			
			return mds;
		}
		
		for(ModelConfig config:this.modelConfigs)
		{
			if(config.getModelName().startsWith(key))
			{
				ModelDescResp desc=new ModelDescResp();
				desc.setModelID(config.getModelID());
				desc.setModelName(config.getModelName());
				desc.setModelTag(config.getModelTag());
				mds.add(desc);
			}
		}
		
		return mds;
	}
	

}
