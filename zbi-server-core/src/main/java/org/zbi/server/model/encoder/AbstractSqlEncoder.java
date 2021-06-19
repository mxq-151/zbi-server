package org.zbi.server.model.encoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.QueryType;
import org.zbi.server.model.core.SortType;
import org.zbi.server.model.core.StringUtils;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;
import org.zbi.server.model.parse.ParseCondition;
import org.zbi.server.model.parse.ParseDimension;
import org.zbi.server.model.parse.ParseMeasure;
import org.zbi.server.model.parse.ParseDimension;
import org.zbi.server.model.parse.ParseSqlParam;

public abstract class AbstractSqlEncoder<T extends ParseSqlParam> implements ISqlEncoder<ParseSqlParam> {

	public String encodeSql(ParseSqlParam param) throws ParseException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append(this.formatDimensions(param.getDimensions(), false));
		if (!param.getDimensions().isEmpty() && !param.getMeasures().isEmpty()) {
			sb.append(",");
		}
		sb.append(this.formatMeasures(param.getMeasures()));

		sb.append(" from ");
		sb.append(param.getFactTable());

		if (param.getJoinConditionMap() != null && !param.getJoinConditionMap().isEmpty()) {

		}

		if (!param.getConditions().isEmpty()) {

			sb.append(this.formatConditions(param.getConditions()));

		}

		if (needGroupby(param.getMeasures(), param.getDimensions())) {
			sb.append(" group by ");
			sb.append(this.formatDimensions(param.getDimensions(), true));
		}

		sb.append(this.formatHaving(param.getConditions(), param.getMeasures()));

		List<ParseDimension> sortColums = new ArrayList<>();
		sortColums.addAll(param.getDimensions());
		sortColums.addAll(param.getMeasures());
		Collections.sort(sortColums, new OrderByComparator());

		String orderby = formatOderby(sortColums);
		if (orderby.length() >= 1) {
			sb.append(" order by ").append(orderby);
		}

		if (param.getOffSet() != -1) {
			sb.append(" ");
			sb.append(this.formatLimit(param.getOffSet(), param.getPageSize()));
		}

		return sb.toString();
	}

	protected String formatOderby(List<ParseDimension> sortColums) {
		StringBuffer sb = new StringBuffer();
		boolean has = false;
		for (ParseDimension column : sortColums) {
			if (column.getSortOrder() > 0 && column.getSortType() != SortType.DEFAULT) {
				has = true;
				if (column.getSortType() == SortType.ASC) {
					sb.append(column.getAlias());
					sb.append(" asc ,");
				} else {
					sb.append(column.getAlias());
					sb.append(" desc ,");
				}
			}
		}

		if (has) {
			return sb.substring(0, sb.length() - 1);
		}

		return "";
	}

	protected String formatLimit(int offset, int pageSize) {
		StringBuffer sb = new StringBuffer();
		int realOffset = offset * pageSize;

		sb.append(" limit ");
		sb.append(pageSize);
		sb.append(" offset ");
		sb.append(realOffset);
		return sb.toString();
	}

	protected String formatDimensions(List<ParseDimension> dimensions, boolean groupby) throws ParseException {

		Map<String, List<ParseDimension>> map = new HashMap<>();
		for (ParseDimension dim : dimensions) {
			if (map.containsKey(dim.getFromTable())) {
				map.get(dim.getFromTable()).add(dim);
			} else {
				List<ParseDimension> array = new ArrayList<>();
				array.add(dim);
				map.put(dim.getFromTable(), array);
			}
		}
		
		Iterator<Entry<String, List<ParseDimension>>> iter=map.entrySet().iterator();
		
		while(iter.hasNext())
		{
			Entry<String, List<ParseDimension>> entry=iter.next();
			String fromTable=entry.getKey();
			List<ParseDimension> dims=entry.getValue();
			
		}

		StringBuffer sb = new StringBuffer();
		int size = dimensions.size();
		for (int i = 0; i < size; i++) {
			if (!groupby) {
				sb.append(StringUtils.getCol(dimensions.get(i).getColName())).append(" as ")
						.append(dimensions.get(i).getAlias());
			} else {
				sb.append(StringUtils.getCol(dimensions.get(i).getColName()));

			}
			sb.append(",");

		}
		if (dimensions.isEmpty()) {
			return "";
		}

		return sb.substring(0, sb.length() - 1).toString();
	}

	protected String formatMeasures(List<ParseMeasure> measures) throws ParseException {
		StringBuffer sb = new StringBuffer();
		int size = measures.size();
		for (int i = 0; i < size; i++) {

			ParseMeasure measure = measures.get(i);
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

			case COMPLEX:
				sb.append(measure.getColName());
				break;
			default:
				break;
			}
			sb.append(" as ").append(measure.getAlias()).append(",");
		}

		if (sb.length() == 0) {
			return "";
		}
		return sb.substring(0, sb.length() - 1).toString();
	}

	protected String formatMeasureName(ParseMeasure measure) {
		StringBuffer sb = new StringBuffer();

		return sb.toString();
	}

	protected String formateGroupby(List<ParseDimension> dimensions) {
		StringBuffer sb = new StringBuffer();
		sb.append(" group by  ");
		int size = dimensions.size();
		for (int i = 0; i < size; i++) {
			if (i < size) {
				sb.append(dimensions.get(i).getColName());
				sb.append(",");

			} else {
				sb.append(dimensions.get(i).getColName());
			}
		}

		return sb.toString();
	}

	protected String formatOrderby(List<ParseMeasure> measures, List<ParseDimension> dimensions) {
		StringBuffer sb = new StringBuffer();

		List<ParseDimension> columns = new ArrayList<>(measures.size() + dimensions.size());

		columns.addAll(measures);
		columns.addAll(dimensions);

		for (ParseDimension column : columns) {
			if (column instanceof ParseMeasure) {
				String mname = this.formatMeasureName((ParseMeasure) column);
				sb.append(mname);
			} else if (column instanceof ParseDimension) {
				sb.append(column.getColName());
			}

			if (column.getSortType() == SortType.ASC) {
				sb.append(" asc,");
			} else {
				sb.append(" desc,");
			}
		}

		return sb.substring(0, sb.length() - 1).toString();
	}

	protected boolean needGroupby(List<ParseMeasure> measures, List<ParseDimension> dimensions) {
		boolean find = false;

		for (ParseMeasure measure : measures) {
			if (measure.getAggType() != AggType.NONE) {
				find = true;
				break;
			}
		}

		if (find && !dimensions.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private String formatHaving(List<ParseCondition> conditions, List<ParseMeasure> measures) throws ParseException {
		StringBuffer sb = new StringBuffer();
		boolean has = false;

		for (ParseCondition condition : conditions) {
			if (condition.isMeasure()) {
				has = true;
			}
		}

		if (!has) {
			return "";
		}

		sb.append(" having ");
		for (ParseCondition condition : conditions) {
			if (condition.isMeasure()) {
				for (ParseMeasure measure : measures) {
					if (measure.getUuid().equals(condition.getUuid())) {
						sb.append(this.newHaving(condition, measure.getAggType())).append(" and ");
						break;
					}
				}

			}
		}

		return sb.substring(0, sb.length() - 5).toString();
	}

	protected String newHaving(ParseCondition condition, AggType aggType) throws ParseException {
		StringBuffer sb = new StringBuffer();
		switch (aggType) {
		case COUNTDISTINCT:
			sb.append("count(distinct ").append(StringUtils.getCol(condition.getColName())).append(")");
			break;
		case COUNT:
			sb.append("count(").append(condition.getColName()).append(")");
			break;
		case AVG:
			sb.append("sum(").append(StringUtils.getCol(condition.getColName())).append(")/count(")
					.append(StringUtils.getCol(condition.getColName())).append(")");
			break;
		case MAX:
			sb.append("max(").append(StringUtils.getCol(condition.getColName())).append(")");
		case MIN:
			sb.append("max(").append(StringUtils.getCol(condition.getColName())).append(")");
			break;
		case SUM:
			sb.append("sum(").append(StringUtils.getCol(condition.getColName())).append(")");
			break;
		default:
			break;
		}

		QueryType queryType = condition.getQueryType();
		switch (queryType) {
		case EQUAL:
			sb.append("=").append(condition.getConditionVal());
			break;
		case LARGE:
			sb.append("=").append(condition.getConditionVal());
			break;

		case LARGEEQUAL:
			sb.append(">=").append(condition.getConditionVal());
			break;
		case LESSEQUAL:
			sb.append("<=").append(condition.getConditionVal());
			break;
		case LESS:
			sb.append("<").append(condition.getConditionVal());
			break;
		default:
			break;

		}

		return sb.toString();
	}

	protected String formatConditions(List<ParseCondition> conditions) throws ParseException {
		StringBuffer sb = new StringBuffer();
		boolean has = false;

		for (ParseCondition condition : conditions) {
			if (!condition.isMeasure()) {
				has = true;
			}
		}

		if (!has) {
			return "";
		}
		sb.append(" where ");
		for (ParseCondition condition : conditions) {
			if (!condition.isMeasure()) {
				sb.append(this.newCondition(condition)).append(" and ");
			}
		}

		return sb.substring(0, sb.length() - 4);

	}

	protected String newCondition(ParseCondition condition) throws ParseException {
		ColumnType columnType = condition.getColumnType();
		switch (columnType) {
		case DATE:
			return newDateCondition(condition);

		case STRING:
			return newStringCondition(condition);
		default:
			break;

		}

		return "";

	}

	protected String newStringCondition(ParseCondition condition) throws ParseException {
		QueryType queryType = condition.getQueryType();
		String column = StringUtils.getCol(condition.getColName());
		StringBuffer sb = new StringBuffer();
		if (queryType == QueryType.EQUAL) {
			sb.append(column).append(" ='").append(condition.getConditionVal()).append("'");
			return sb.toString();
		} else if (queryType == QueryType.IN) {
			sb.append(column).append(" in (").append(condition.getConditionVal()).append(")");
			return sb.toString();
		} else if (queryType == QueryType.NOTIN) {
			sb.append(column).append(" not in (").append(condition.getConditionVal()).append(")");
			return sb.toString();
		} else {
			throw new ParseException("操作关系不对", ExceptionType.REQUESTERROR);
		}
	}

	protected String newDateCondition(ParseCondition condition) throws ParseException {
		QueryType queryType = condition.getQueryType();
		String column = StringUtils.getCol(condition.getColName());
		StringBuffer sb = new StringBuffer();
		if (queryType == QueryType.EQUAL) {
			sb.append(column).append(">=").append(condition.getConditionVal()).append(" and ").append(column)
					.append("<=").append(condition.getConditionVal());
			return sb.toString();
		} else if (queryType == QueryType.LARGE) {
			sb.append(column).append(">").append(condition.getConditionVal());
			return sb.toString();
		} else if (queryType == QueryType.LARGEEQUAL) {
			sb.append(column).append(">=").append(condition.getConditionVal());
			return sb.toString();
		} else if (queryType == QueryType.LESS) {
			sb.append(column).append("<").append(condition.getConditionVal());
			return sb.toString();
		} else if (queryType == QueryType.LESSEQUAL) {
			sb.append(column).append("<=").append(condition.getConditionVal());
			return sb.toString();
		} else {
			throw new ParseException("操作关系不对", ExceptionType.REQUESTERROR);
		}

	}

}
