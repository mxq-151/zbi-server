package org.zbi.server.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.mapper.mysql.ConfigColumnMapper;
import org.zbi.server.mapper.mysql.ConfigJoinMapper;
import org.zbi.server.mapper.mysql.ConfigTableMapper;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigJoin;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;
import org.zbi.server.model.parse.ParseColumn;
import org.zbi.server.model.parse.ParseFilter;
import org.zbi.server.model.parse.ParseModel;
import org.zbi.server.model.request.RequestColumn;
import org.zbi.server.model.request.RequestParam;

@Component
public class ModelService {

	// 连表信息
	private Map<String, List<ConfigJoin>> joins = new HashMap<>();

	private Map<String, ConfigColumn> columns = new HashMap<>();

	private Map<String, ConfigTable> tables = new HashMap<>();

	@Autowired
	private ConfigTableMapper configTableMapper;

	@Autowired
	private ConfigColumnMapper configColumnMapper;

	@Autowired
	private ConfigJoinMapper configJoinMapper;

	private final static Logger logger = LoggerFactory.getLogger(ModelService.class);

	public ModelService() {

	}

	@PostConstruct
	public void init() {
		List<ConfigTable> configTables = this.configTableMapper.loadTables();
		int num = 0;
		logger.info("loading table info.....");
		for (ConfigTable table : configTables) {
			List<ConfigColumn> cols = this.configColumnMapper.listColumnsByTableID(table.getTableID());

			for (ConfigColumn col : cols) {
				this.columns.put(col.getUuid(), col);
			}
			num++;
			this.tables.put(table.getTableID(), table);
		}

		logger.info("loading {}  table info", num);
		List<ConfigJoin> joins = this.configJoinMapper.loadAllJoins();
		logger.info("loading join info.....");
		for (ConfigJoin join : joins) {
			StringBuilder sb = new StringBuilder();
			sb.append(join.getTblID1()).append("_").append(join.getTblID2());
			if (this.joins.containsKey(sb.toString())) {
				this.joins.get(sb.toString()).add(join);
			} else {
				List<ConfigJoin> array = new ArrayList<>();
				array.add(join);
				this.joins.put(sb.toString(), array);
			}

		}
	}
	
	public ConfigColumn getColumn(String uuid) {
		return this.columns.get(uuid);
	}

	public ParseModel getModel(RequestParam param) throws ParseException {

		HashSet<String> tables = new HashSet<>();
		HashSet<String> conns = new HashSet<>();
		HashSet<EngineType> engine = new HashSet<>();
		ParseModel configModel = new ParseModel();

		List<ParseColumn> dimensions = new ArrayList<>();

		List<ParseColumn> measures = new ArrayList<>();

		List<ConfigJoin> joins = new ArrayList<>();

		List<ParseFilter> filters = new ArrayList<>();
		List<RequestColumn> requestCols = param.getDimensions();
		List<RequestColumn> requestMeasure = param.getMeasures();

		List<RequestColumn> array = new ArrayList<>(requestCols);
		array.addAll(requestMeasure);

		for (RequestColumn col : array) {
			ConfigColumn cc = this.columns.get(col.getUuid());

			if (cc == null) {
				throw new ParseException("模型没有配置字段:" + param.getModelID(), ExceptionType.CONFIGERROR);
			}

			ParseColumn pc = new ParseColumn();
			BeanUtils.copyProperties(cc, pc);
			pc.setSortOrder(col.getSortOrder());
			pc.setSortType(col.getSortType());
			if (cc.isMeasure()) {
				measures.add(pc);
			} else {
				dimensions.add(pc);
			}

			ConfigTable configTable = this.tables.get(cc.getTableID());

			if (configTable == null) {
				throw new ParseException("模型没有配置字段:" + param.getModelID(), ExceptionType.CONFIGERROR);
			}

			conns.add(configTable.getConnID());
			engine.add(configTable.getEngineType());
			tables.add(configTable.getTableID());
		}

		int size = tables.size() - 1;
		List<String> arrays = new ArrayList<>(tables);
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < tables.size(); j++) {
				StringBuilder sb1 = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				sb1.append(arrays.get(i)).append("_").append(arrays.get(j));
				sb2.append(arrays.get(j)).append("_").append(arrays.get(i));
				if (!this.joins.containsKey(sb2.toString()) && !this.joins.containsKey(sb1.toString())) {
					throw new ParseException("没有找到两个表的连表信息:", ExceptionType.CONFIGERROR);
				} else {

				}
			}
		}

		if (engine.size() != 1) {
			throw new ParseException("所选字段不在同一种引擎内:", ExceptionType.CONFIGERROR);
		}

		if (conns.size() != 1) {
			throw new ParseException("所选字段不在同一种引擎内:", ExceptionType.CONFIGERROR);
		}

		configModel.setDimensions(dimensions);
		configModel.setMeasures(measures);
		configModel.setJoins(joins);
		configModel.setEngineType(engine.iterator().next());
		configModel.setConnID(conns.iterator().next());
		configModel.setOffSet(param.getOffSet());
		configModel.setFilters(filters);
		configModel.setPageSize(param.getPageSize());
		Iterator<String> iter = tables.iterator();
		int num = 0;
		while (iter.hasNext()) {
			ConfigTable table = this.tables.get(iter.next());
			if (table.getTableType() == 1) {
				num++;
				configModel.setMainTable(table);
			}
		}
		if (num > 1) {
			throw new ParseException("模型当中只能有一个主表", ExceptionType.CONFIGERROR);
		}

		String tableID = tables.iterator().next();
		configModel.setConnID(this.tables.get(tableID).getConnID());

		return configModel;

	}

}
