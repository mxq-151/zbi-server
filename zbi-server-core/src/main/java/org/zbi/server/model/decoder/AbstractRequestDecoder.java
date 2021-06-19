package org.zbi.server.model.decoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.zbi.server.model.config.ModelDimension;
import org.zbi.server.model.config.ModelMeasure;
import org.zbi.server.model.config.ModelConfig;
import org.zbi.server.model.config.QuerySqlModel;
import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.StringUtils;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;
import org.zbi.server.model.parse.DefaultParseSqlParam;
import org.zbi.server.model.parse.ParseCondition;
import org.zbi.server.model.parse.ParseDimension;
import org.zbi.server.model.parse.ParseMeasure;
import org.zbi.server.model.parse.ParseSqlParam;
import org.zbi.server.model.request.RequestColumn;
import org.zbi.server.model.request.RequestCondition;
import org.zbi.server.model.request.RequestMeasure;
import org.zbi.server.model.request.RequestParam;

public abstract class AbstractRequestDecoder implements IRequestDecoder<ParseSqlParam> {

	@Override
	public ParseSqlParam parseToConfig(RequestParam param, ModelConfig modelConfig,ParseSqlParam parseSqlParam) throws ParseException {
		// TODO Auto-generated method stub

		DefaultParseSqlParam psp = new DefaultParseSqlParam();
		psp.setOffSet(param.getOffSet());
		psp.setPageSize(param.getPageSize());

		QuerySqlModel model = modelConfig.getQuerySqlModel();
		psp.setProject(model.getProject());
		psp.setJoinConditionMap(model.getJoinConditionMap());
		psp.setFactTable(model.getFactTable());

		List<ModelDimension> dimensions = model.getDimensions();
		List<ModelMeasure> measures = model.getMeasures();

		psp.setDimensions(this.filterDimensions(dimensions, param.getDimensions()));
		psp.setMeasures(this.filterMeasures(measures, param.getMeasures()));

		List<ParseCondition> conditions = new ArrayList<ParseCondition>();
		conditions.addAll(this.generateDimensionCondition(dimensions, param.getConditions()));
		conditions.addAll(this.generateMeasureCondition(measures, param.getConditions()));

		psp.setConditions(conditions);

		return psp;
	}

	protected List<ParseDimension> filterDimensions(List<ModelDimension> dimensions,
			List<RequestColumn> requestDimensions) {
		List<ParseDimension> newDimensions = new ArrayList<>();
		for (RequestColumn column : requestDimensions) {
			for (ModelDimension dimension : dimensions) {
				if (dimension.getUuid().equals(column.getUuid())) {
					ParseDimension newDimension = new ParseDimension();
					newDimension.setAdvanced(false);
					newDimension.setAlias(dimension.getAlias());
					newDimension.setChinese(dimension.getChinese());
					newDimension.setColumnType(dimension.getColumnType());
					newDimension.setMeasure(false);
					newDimension.setSortOrder(column.getSortOrder());
					newDimension.setColName(dimension.getColName());
					newDimension.setSortType(column.getSortType());
					newDimension.setUuid(dimension.getUuid());
					newDimensions.add(newDimension);
					break;
				}
			}
		}

		return newDimensions;
	}

	protected List<ParseMeasure> filterMeasures(List<ModelMeasure> measures, List<RequestMeasure> requestDimensions) throws ParseException {
		List<ParseMeasure> newMeasures = new ArrayList<ParseMeasure>();
		int loop=0;
		for (RequestMeasure column : requestDimensions) {
			if (!column.isAdvanced()) {
				for (ModelMeasure measure : measures) {
					if (measure.getUuid().equals(column.getUuid())) {
						ParseMeasure newMeasure = new ParseMeasure();

						newMeasure.setAdvanced(false);
						newMeasure.setAggType(measure.getAggType());
						newMeasure.setAlias(measure.getAlias());
						newMeasure.setChinese(measure.getChinese());
						newMeasure.setColName(measure.getColName());
						newMeasure.setMeasure(true);
						newMeasure.setSortOrder(column.getSortOrder());
						newMeasure.setSortType(column.getSortType());
						newMeasure.setUuid(column.getUuid());

						newMeasures.add(newMeasure);
						break;
					}
				}
			} else {
				String newField=column.getNewField();
				StringBuffer sb=new StringBuffer();
				for (ModelMeasure measure : measures) {
					if (newField.contains(measure.getUuid())) {
						
						newField=newField.replace(measure.getUuid(), formatNewMeasure(measure));
						
						sb.append(measure.getAlias());
						sb.append("_");
					}
				}
				loop++;
				sb.append(loop);
				
				ParseMeasure newMeasure = new ParseMeasure();

				UUID uuid=UUID.randomUUID();
				newMeasure.setAdvanced(true);
				newMeasure.setAggType(AggType.COMPLEX);
				newMeasure.setAlias(sb.toString());
				newMeasure.setChinese(column.getNewFieldName());
				newMeasure.setColName(newField);
				newMeasure.setMeasure(true);
				newMeasure.setSortOrder(column.getSortOrder());
				newMeasure.setSortType(column.getSortType());
				newMeasure.setUuid(uuid.toString());

				newMeasures.add(newMeasure);

			}
		}

		return newMeasures;
	}
	
	protected String formatNewMeasure(ModelMeasure measure) throws ParseException
	{
		StringBuffer sb=new StringBuffer();
		AggType aggType = measure.getAggType();
		switch (aggType) {
		case COUNTDISTINCT:
			sb.append("count(distinct ").append(StringUtils.getCol(measure.getColName())).append(")");
			break;
		case COUNT:
			sb.append("count(").append(measure.getColName()).append(")");
			break;
		case AVG:
			sb.append("sum(").append(StringUtils.getCol(measure.getColName())).append(")/count(")
					.append(StringUtils.getCol(measure.getColName())).append(")");
			break;
		case MAX:
			sb.append("max(").append(StringUtils.getCol(measure.getColName())).append(")");
		case MIN:
			sb.append("max(").append(StringUtils.getCol(measure.getColName())).append(")");
			break;
		case SUM:
			sb.append("sum(").append(StringUtils.getCol(measure.getColName())).append(")");
			break;
		default:
			break;
		}
		
		return sb.toString();
	}

	protected List<ParseCondition> generateMeasureCondition(List<ModelMeasure> measures,
			List<RequestCondition> requestConditioins) throws ParseException {
		List<ParseCondition> conditions = new ArrayList<>();

		if (requestConditioins == null || requestConditioins.isEmpty()) {
			return conditions;
		}
		int size1 = requestConditioins.size();
		int size2 = measures.size();
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				if (requestConditioins.get(i).getUuid().equals(measures.get(j).getUuid())) {
					String[] values = requestConditioins.get(i).getRequestValue();

					ParseCondition condition = new ParseCondition();

					condition.setConditionVal(values[0]);

					condition.setMeasure(true);
					condition.setAlias(measures.get(j).getAlias());
					condition.setChinese(measures.get(j).getChinese());
					condition.setColName(measures.get(j).getColName());
					condition.setQueryType(requestConditioins.get(i).getQueryType());
					condition.setColumnType(measures.get(j).getColumnType());
					condition.setUuid(measures.get(j).getUuid());
					conditions.add(condition);
					break;

				}
			}
		}

		return conditions;
	}

	protected List<ParseCondition> generateDimensionCondition(List<ModelDimension> dimensions,
			List<RequestCondition> requestConditioins) throws ParseException {
		List<ParseCondition> conditions = new ArrayList<>();

		if (requestConditioins == null || requestConditioins.isEmpty()) {
			return conditions;
		}

		int size1 = requestConditioins.size();
		int size2 = dimensions.size();
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				if (requestConditioins.get(i).getUuid().equals(dimensions.get(j).getUuid())) {
					ParseCondition condition = new ParseCondition();
					ModelDimension dimension = dimensions.get(j);

					RequestCondition rc = requestConditioins.get(i);
					String[] values = rc.getRequestValue();
					ColumnType type = dimensions.get(j).getColumnType();
					StringBuffer tmp = new StringBuffer();
					if (type == ColumnType.DATE) {
						if (values.length != 1) {
							throw new ParseException("度量只能传一个值", ExceptionType.REQUESTERROR);
						}
						tmp.append("'").append(values[0]).append("'");
						condition.setConditionVal(tmp.toString());
					} else if (type == ColumnType.STRING) {
						for (String str : values) {
							tmp.append("'").append(str).append("',");
						}
						condition.setConditionVal(tmp.substring(0, tmp.length() - 1));

					} else {
						condition.setConditionVal(values[0]);
					}

					condition.setUuid(dimension.getUuid());
					condition.setColName(dimension.getColName());
					condition.setAlias(dimension.getAlias());
					condition.setColumnType(dimension.getColumnType());
					condition.setMeasure(false);
					condition.setAdvanced(false);
					condition.setQueryType(requestConditioins.get(i).getQueryType());
					conditions.add(condition);

					break;
				}
			}
		}

		return conditions;
	}


}
