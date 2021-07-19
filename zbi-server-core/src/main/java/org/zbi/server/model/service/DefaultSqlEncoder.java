package org.zbi.server.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zbi.server.model.config.ConfigColumn;
import org.zbi.server.model.config.ConfigTable;
import org.zbi.server.model.core.AggType;
import org.zbi.server.model.core.ColumnBase;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.QueryType;
import org.zbi.server.model.core.SortType;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.ParseException.ExceptionType;
import org.zbi.server.model.parse.ParseColumn;
import org.zbi.server.model.parse.ParseFilter;
import org.zbi.server.model.parse.ParseModel;

public class DefaultSqlEncoder implements ISqlEncoder {

	public String encodeSql(ParseModel model) throws ParseException {

		StringBuilder sb = new StringBuilder();

		sb.append(this.formatSelect(model.getDimensions(), model.getMeasures(), model.getMainTable()));
		ConfigTable mainTable = model.getMainTable();
		sb.append(" from ");
		if (mainTable.isSource()) {
			sb.append("`").append(mainTable.getProject()).append("`").append(".").append("`").append(mainTable.getTableName()).append("`").append(" as ").append("`")
					.append(mainTable.getTblAlias()).append("`");
		} else {
			String sql = this.encodeSql(null);
			sb.append("(").append(sql).append(") as ").append(mainTable.getTblAlias());
		}

		List<ParseFilter> filters = model.getFilters();
		if (!filters.isEmpty()) {
			sb.append(" where ");
			for (ParseFilter filter : filters) {
				if (!filter.isMeasure()) {
					String str = this.newCondition(filter);
					sb.append(str).append(" and ");
				}
			}
		}

		if (!model.getMeasures().isEmpty() && !model.getDimensions().isEmpty()) {
			sb.append(" group by ").append(this.formatGroupBy(model.getDimensions()));
		}

		this.formatHaving(filters, model.getMeasures());
		List<ParseColumn> array = new ArrayList<>(model.getDimensions());
		array.addAll(model.getMeasures());
		String orderBy = this.formatOderBy(array);
		sb.append(" ").append(orderBy);

		String limit = this.formatLimit(model.getOffSet(), model.getPageSize());
		sb.append(" ").append(limit);
		return sb.toString();

	}

	private String formatSelect(List<ParseColumn> dimensions, List<ParseColumn> measures, ConfigTable mainTable) {

		StringBuilder sb = new StringBuilder();
		sb.append("select ");

		for (int i = 0; i < dimensions.size(); i++) {
			ParseColumn cc = dimensions.get(i);
			sb.append(cc.getColName()).append(" as ").append(cc.getAlias());
			if (i < dimensions.size() - 1) {
				sb.append(",");
			}
		}

		if (!measures.isEmpty()) {
			if(!dimensions.isEmpty())
			{
				sb.append(", ");
			}
			
			int size = measures.size() - 1;
			for (int i = 0; i < measures.size(); i++) {
				ParseColumn measure = measures.get(i);
				sb.append(formatAggType(measure));
				sb.append(" as ").append(measure.getAlias());
				if (i < size) {
					sb.append(",");
				}

			}

		}
		return sb.toString();

	}

	private String formatGroupBy(List<ParseColumn> dimensions) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dimensions.size(); i++) {
			ParseColumn cc = dimensions.get(i);
			sb.append(cc.getColName());
			if (i < dimensions.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();

	}

	protected String formatAggType(ConfigColumn measure) {

		StringBuilder sb = new StringBuilder();

		AggType aggType = measure.getAggType();
		switch (aggType) {
		case COUNTDISTINCT:
			sb.append("count(distinct ").append(measure.getColName()).append(")");
			break;
		case COUNT:
			sb.append("count(").append(measure.getColName()).append(")");
			break;
		case AVG:
			sb.append("sum(").append(measure.getColName()).append(")/count(").append(measure.getColName()).append(")");
			break;
		case MAX:
			sb.append("max(").append(measure.getColName()).append(")");
		case MIN:
			sb.append("max(").append(measure.getColName()).append(")");
			break;
		case SUM:
			sb.append("sum(").append(measure.getColName()).append(")");
			break;

		case COMPLEX:
			sb.append(measure.getColName());
			break;
		default:
			break;
		}

		return sb.toString();
	}

	protected String formatOderBy(List<ParseColumn> sortColums) {
		StringBuffer sb = new StringBuffer();
		boolean has = false;
		for (ParseColumn column : sortColums) {
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
		int realOffset = offset -1;

		sb.append(" limit ");
		sb.append(pageSize);
		sb.append(" offset ");
		sb.append(realOffset);
		return sb.toString();
	}

	private String formatHaving(List<ParseFilter> conditions, List<ParseColumn> measures) throws ParseException {
		StringBuffer sb = new StringBuffer();
		boolean has = false;
		for (ParseFilter condition : conditions) {
			if (condition.isMeasure()) {
				has = true;
			}
		}

		if (!has) {
			return "";
		}

		sb.append(" having ");
		for (ParseFilter condition : conditions) {
			if (condition.isMeasure()) {
				for (ParseColumn measure : measures) {
					if (measure.getUuid().equals(condition.getUuid())) {
						sb.append(this.newHaving(condition, measure.getAggType())).append(" and ");
						break;
					}
				}

			}
		}

		return sb.substring(0, sb.length() - 5).toString();
	}

	protected String newCondition(ParseFilter condition) throws ParseException {
		if (!condition.getColProperties().isEmpty()) {
			Map<String, Object> properties = condition.getColProperties();
			if (properties.containsKey(ColumnBase.PROPERTIETYPE)) {
				if (properties.get(ColumnBase.PROPERTIETYPE).equals(ColumnBase.PROPERTIETYPE_TOPN)) {
					StringBuffer sb = new StringBuffer();
					String sql = properties.get(ColumnBase.PROPERTIETYPE_SUB_QUERY).toString();
					sb.append(condition.getColName()).append(" in (").append(sql).append(")");
					return sb.toString();
				}
			}
			return "";
		} else {
			ColumnType columnType = condition.getColumnType();
			switch (columnType) {
			case DATE:
			case MONTH:
			case YEAR:
				return newDateCondition(condition);
			case NUMBER:
				return neNumCondition(condition);
			case STRING:
				return newStringCondition(condition);
			default:
				return "";
			}
		}

	}

	protected String newHaving(ParseFilter condition, AggType aggType) throws ParseException {
		StringBuffer sb = new StringBuffer();
		this.formatAggType(condition);

		QueryType queryType = condition.getQueryType();
		String value = condition.getValues().get(0);
		switch (queryType) {
		case EQUAL:
			sb.append("=").append(value);
			break;
		case LARGE:
			sb.append(">").append(value);
			break;
		case LARGEEQUAL:
			sb.append(">=").append(value);
			break;
		case LESSEQUAL:
			sb.append("<=").append(value);
			break;
		case LESS:
			sb.append("<").append(value);
			break;
		default:
			break;

		}

		return sb.toString();
	}

	protected String newStringCondition(ParseFilter condition) throws ParseException {
		QueryType queryType = condition.getQueryType();
		String column = condition.getColName();
		StringBuffer sb = new StringBuffer();

		List<String> values = condition.getValues();
		StringBuilder tmp = new StringBuilder();
		int size = values.size() - 1;
		for (int i = 0; i < values.size(); i++) {
			sb.append("'");
			sb.append(values.get(i));
			sb.append("'");
			if (i < size) {
				sb.append(",");
			}

		}
		if (queryType == QueryType.EQUAL) {
			sb.append(column).append(" ='").append(tmp.toString()).append("'");
			return sb.toString();
		} else if (queryType == QueryType.IN) {
			sb.append(column).append(" in (").append(tmp.toString()).append(")");
			return sb.toString();
		} else if (queryType == QueryType.NOTIN) {
			sb.append(column).append(" not in (").append(tmp.toString()).append(")");
			return sb.toString();
		} else {
			throw new ParseException("操作关系不对", ExceptionType.REQUESTERROR);
		}
	}

	protected String newDateCondition(ParseFilter condition) throws ParseException {
		QueryType queryType = condition.getQueryType();
		String column = condition.getColName();
		StringBuffer sb = new StringBuffer();
		List<String> values = condition.getValues();
		String value = values.get(0);
		sb.append(column);
		if (queryType == QueryType.EQUAL) {
			sb.append("=").append("'").append(value).append("'");
			return sb.toString();
		} else if (queryType == QueryType.LARGE) {
			sb.append(">").append("'").append(value).append("'");
			return sb.toString();
		} else if (queryType == QueryType.LARGEEQUAL) {
			sb.append(">=").append("'").append(value).append("'");
			return sb.toString();
		} else if (queryType == QueryType.LESS) {
			sb.append("<").append("'").append(value).append("'");
			return sb.toString();
		} else if (queryType == QueryType.LESSEQUAL) {
			sb.append("<=").append("'").append(value).append("'");
			return sb.toString();
		} else {
			throw new ParseException("操作关系不对", ExceptionType.REQUESTERROR);
		}

	}

	protected String neNumCondition(ParseFilter condition) throws ParseException {
		QueryType queryType = condition.getQueryType();
		String column = condition.getColName();
		StringBuffer sb = new StringBuffer();
		List<String> values = condition.getValues();
		String value = values.get(0);
		if (queryType == QueryType.EQUAL) {
			sb.append(column).append(">=").append(value).append(" and ").append(column).append("<=").append(value);
			return sb.toString();
		} else if (queryType == QueryType.LARGE) {
			sb.append(column).append(">").append(value);
			return sb.toString();
		} else if (queryType == QueryType.LARGEEQUAL) {
			sb.append(column).append(">=").append(value);
			return sb.toString();
		} else if (queryType == QueryType.LESS) {
			sb.append(column).append("<").append(value);
			return sb.toString();
		} else if (queryType == QueryType.LESSEQUAL) {
			sb.append(column).append("<=").append(value);
			return sb.toString();
		} else {
			throw new ParseException("操作关系不对", ExceptionType.REQUESTERROR);
		}

	}

}
