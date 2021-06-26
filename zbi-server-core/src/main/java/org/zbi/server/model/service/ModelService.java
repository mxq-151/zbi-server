package org.zbi.server.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigJoin;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.core.EngineType;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;
import org.zbi.server.model.parse.ParseColumn;
import org.zbi.server.model.parse.ParseModel;
import org.zbi.server.model.request.RequestColumn;
import org.zbi.server.model.request.RequestParam;

public class ModelService {

	// 连表信息
	private Map<String, List<ConfigJoin>> joins = new HashMap<>();

	private Map<String, ConfigColumn> columns = new HashMap<>();

	private Map<String, ConfigTable> tables = new HashMap<>();

	public ParseModel getModel(RequestParam param) throws ParseException {

		HashSet<String> tables = new HashSet<>();
		HashSet<EngineType> engine = new HashSet<>();
		ParseModel configModel = new ParseModel();

		List<ParseColumn> dimensions = new ArrayList<>();

		List<ParseColumn> measures = new ArrayList<>();

		List<ConfigJoin> joins = new ArrayList<>();

		List<RequestColumn> requestCols = param.getDimensions();
		List<RequestColumn> requestMeasure = param.getMeasures();

		List<RequestColumn> array = new ArrayList<>(requestCols);
		array.addAll(requestMeasure);

		for (RequestColumn col : array) {
			ConfigColumn cc = this.columns.get(col.getUuid());

			if (cc == null) {
				throw new ParseException("模型没有配置字段:" + param.getModelTag(), ExceptionType.CONFIGERROR);
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
				throw new ParseException("模型没有配置字段:" + param.getModelTag(), ExceptionType.CONFIGERROR);
			}

			engine.add(configTable.getEngineType());
			tables.add(configTable.getTableID());
		}

		if (engine.size() != 1) {
			throw new ParseException("所选字段不在同一种引擎内:", ExceptionType.CONFIGERROR);
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

		configModel.setDimensions(dimensions);
		configModel.setMeasures(measures);
		configModel.setJoins(joins);

		return configModel;

	}

}
