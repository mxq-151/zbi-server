package org.zbi.server.model.nested;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zbi.server.model.core.ColumnType;
import org.zbi.server.model.core.QueryType;
import org.zbi.server.model.parse.ParseCondition;

public class NestedSqlEncoderImpl implements INestedSqlEncoder {

	private static final Logger logger = LoggerFactory.getLogger(NestedSqlEncoderImpl.class);

	@Override
	public String encoderSql(NestedSqlParam param, List<ParseCondition> conditions, List<String> selectedCols) {
		// TODO Auto-generated method stub

		this.printGraph(param);

		Map<String, List<JoinTag>> selectedColumns = new HashMap<>();

		List<NestedTable> tables = param.getNestedTables();
		for (NestedTable table : tables) {
			for (String str : selectedCols) {
				this.putSelect(selectedColumns, table.getTableAlias(), str, false);
			}
		}

		this.findSelectedColumnOnCondition(param.getNestedMainTable(), param.getNestedTables(), selectedColumns,
				conditions);
		this.findSelectedColumnOnInvole(param.getNestedMainTable(), param.getNestedTables(), selectedColumns,selectedCols);
		this.findSelectedColumnOnJoin(param.getNestedMainTable(), param.getNestedTables(), selectedColumns);
		return this.loopNestedTable(param.getNestedMainTable(), param.getNestedTables(),
				param.getNestedMainTable().get("top"), selectedColumns, conditions).getTableName();
	}

	/**
	 * 调试使用，打印表关系
	 * */
	protected void printGraph(NestedSqlParam param) {
		Map<String, NestedMainTable> joinNode = param.getNestedMainTable();
		Iterator<Entry<String, NestedMainTable>> iter = joinNode.entrySet().iterator();
		while (iter.hasNext()) {
			StringBuffer sb = new StringBuffer();
			Entry<String, NestedMainTable> entry = iter.next();
			NestedMainTable nestedMainTable = entry.getValue();
			List<NestedJoinNode> joinNodes = nestedMainTable.getJoinTable();

			sb.append("表:").append(entry.getKey()).append("由 表:").append(nestedMainTable.getMainTableName())
					.append(",");

			if (joinNodes == null) {
				sb.append(" 组成");
				logger.debug(sb.toString());
				continue;
			}

			for (NestedJoinNode node : joinNodes) {
				sb.append(node.getJoinTableName());
				sb.append(",");
			}

			sb.append(" 组成");
			logger.debug(sb.toString());

		}

	}

	/**
	 * 在连表上找字段
	 */
	protected void findSelectedColumnOnJoin(Map<String, NestedMainTable> mainTable, List<NestedTable> tables,
			Map<String, List<JoinTag>> selectedCols) {

		Iterator<Entry<String, NestedMainTable>> iter = mainTable.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, NestedMainTable> entry = iter.next();
			NestedMainTable nestedMainTable = entry.getValue();
			String tableAlias = entry.getKey();
			List<NestedJoinNode> joinNodes = nestedMainTable.getJoinTable();
			if (joinNodes == null) {
				continue;
			}
			for (NestedJoinNode node : joinNodes) {
				List<NestedJoinCondition> joinConditions = node.getJoinConditions();
				for (NestedJoinCondition condition : joinConditions) {
					String firstTableName = condition.getFirstTableName();
					String secondTableName = condition.getSecondTableName();
					if (!tableAlias.equals(firstTableName)) {
						loopPutSelect(mainTable, firstTableName, tables, selectedCols, condition.getFirstTableColumn(),
								true);
					}

					if (!tableAlias.equals(secondTableName)) {
						loopPutSelect(mainTable, secondTableName, tables, selectedCols,
								condition.getSecondTableColumn(), true);
					}

				}
			}
		}

	}

	/**
	 * 将字段下推到下层
	 */
	protected void loopPutSelect(Map<String, NestedMainTable> mainTable, String tableName, List<NestedTable> tables,
			Map<String, List<JoinTag>> selectedCols, String column, boolean isJoin) {

		NestedMainTable nestedMainTable = mainTable.get(tableName);
		if (nestedMainTable == null) {
			return;
		}

		List<NestedJoinNode> joinNodes = nestedMainTable.getJoinTable();

		String mainTableName = nestedMainTable.getMainTableName();
		putSelect(selectedCols, mainTableName, column, isJoin);
		if (joinNodes != null) {

			for (NestedJoinNode node : joinNodes) {
				List<NestedJoinCondition> joinConditions = node.getJoinConditions();
				for (NestedJoinCondition condition : joinConditions) {
					String firstTableName = condition.getFirstTableName();
					String secondTableName = condition.getSecondTableName();
					if (this.getNestedTable(firstTableName, tables) != null) {
						loopPutSelect(mainTable, firstTableName, tables, selectedCols, column, isJoin);
					}

					if (this.getNestedTable(secondTableName, tables) != null) {
						loopPutSelect(mainTable, secondTableName, tables, selectedCols, column, isJoin);
					}

					putSelect(selectedCols, secondTableName, column, isJoin);

				}
			}
		}

		List<NestedJoinNode> unionNodes = nestedMainTable.getUnionTable();
		if(unionNodes!=null)
		{
			for (NestedJoinNode node : unionNodes) {
				putSelect(selectedCols, node.getJoinTableName(), column, isJoin);
			}
		}
		

	}

	/**
	 * 下推到各表中
	 * */
	private void putSelect(Map<String, List<JoinTag>> selectedCols, String tableName, String column, boolean isJoin) {
		if (selectedCols.containsKey(tableName)) {
			List<JoinTag> joinTags=selectedCols.get(tableName);
			boolean find=false;
			for(JoinTag jt:joinTags)
			{
				if(jt.getColumn().equals(column))
				{
					find=true;
					break;
				}
			}
			if(!find)
			{
				selectedCols.get(tableName).add(new JoinTag(column, isJoin));
			}
			
		} else {
			List<JoinTag> tmp = new ArrayList<>();
			tmp.add(new JoinTag(column, isJoin));
			selectedCols.put(tableName, tmp);
		}
	}

	/**
	 * 在相关字段上找字段
	 */
	protected void findSelectedColumnOnInvole(Map<String, NestedMainTable> mainTable, List<NestedTable> tables,
			Map<String, List<JoinTag>> selectedCols,List<String> selectedColumns) {

		for (NestedTable table : tables) {
			List<NestedColumn> columns = table.getColumns();
			for (NestedColumn column : columns) {
				List<String> involeColumns = column.getInvoleColumns();
				if(!selectedColumns.contains(column.getAlias()) || involeColumns == null)
				{
					continue;
				}
				for (String str : involeColumns) {
					String columnName = this.getColumn(str);
					String tableName=this.getTableName(str);
					loopPutSelect(mainTable, tableName, tables, selectedCols, columnName, false);
				}
			}
		}

	}

	/**
	 * 在条件上找字段
	 */
	protected void findSelectedColumnOnCondition(Map<String, NestedMainTable> mainTable, List<NestedTable> tables,
			Map<String, List<JoinTag>> selectedCols, List<ParseCondition> conditions) {

		for (NestedTable table : tables) {
			List<NestedColumn> columns = table.getColumns();
			for (NestedColumn column : columns) {
				for (ParseCondition condition : conditions) {

					if (condition.getAlias().equals(column.getOuterColumn())) {
						loopPutSelect(mainTable, table.getTableAlias(), tables, selectedCols, condition.getAlias(),
								false);
						break;
					}
				}
			}
		}

	}

	/**
	 * 递归构造表
	 * */
	protected NestedTable loopNestedTable(Map<String, NestedMainTable> mainTableMap, List<NestedTable> tables,
			NestedMainTable nestedMainTable, Map<String, List<JoinTag>> selectedCols, List<ParseCondition> conditions) {

		String mainTableName = nestedMainTable.getMainTableName();
		NestedTable mainTable = this.getNestedTable(mainTableName, tables);
		List<NestedTable> doneTables = new ArrayList<>();
		if (mainTable.isSubQuery()) {
			NestedTable doneTable = loopNestedTable(mainTableMap, tables, mainTableMap.get(mainTableName), selectedCols,
					conditions);
			doneTables.add(doneTable);
		} else {
			doneTables.add(mainTable);
		}

		List<NestedJoinNode> joinTables = nestedMainTable.getJoinTable();
		if (joinTables != null) {
			for (NestedJoinNode join : joinTables) {
				String joinTableName = join.getJoinTableName();
				NestedTable joinTable = this.getNestedTable(joinTableName, tables);
				if (joinTable.isSubQuery()) {
					NestedTable doneTable = loopNestedTable(mainTableMap, tables,
							mainTableMap.get(joinTable.getTableAlias()), selectedCols, conditions);
					joinTable.setTableName(doneTable.getTableName());
					joinTable.setCombineDone(true);
					doneTables.add(joinTable);

				} else {
					doneTables.add(joinTable);
				}
			}

		}

		List<NestedJoinNode> unionTables = nestedMainTable.getUnionTable();
		if (unionTables != null) {
			for (NestedJoinNode join : unionTables) {
				String joinTableName = join.getJoinTableName();
				NestedTable joinTable = this.getNestedTable(joinTableName, tables);
				if (joinTable.isSubQuery()) {
					NestedTable doneTable = loopNestedTable(mainTableMap, tables,
							mainTableMap.get(joinTable.getTableAlias()), selectedCols, conditions);
					joinTable.setTableName(doneTable.getTableName());
					joinTable.setCombineDone(true);
					doneTables.add(joinTable);

				} else {
					doneTables.add(joinTable);
				}
			}

		}

		String sql = this.constructNewTable(mainTableMap, doneTables, nestedMainTable, selectedCols, conditions);

		NestedTable table = new NestedTable();
		table.setTableName(sql);
		table.setTableAlias(mainTableName);
		return table;

	}

	/**
	 * 找到连表信息
	 */
	private void findJoinNode(Map<String, List<JoinTag>> selectedCols, List<NestedJoinNode> needJoins,
			NestedMainTable nestedMainTable) {
		Iterator<Entry<String, List<JoinTag>>> iter = selectedCols.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, List<JoinTag>> entry = iter.next();
			List<JoinTag> joinTags = entry.getValue();
			boolean find = false;
			for (JoinTag jt : joinTags) {
				if (!jt.isJoin) {
					find = true;
					break;
				}
			}

			List<NestedJoinNode> joinNodes = nestedMainTable.getJoinTable();
			if (find && joinNodes != null) {

				for (NestedJoinNode joinNode : joinNodes) {
					if (joinNode.getJoinTableName().equals(entry.getKey())) {
						needJoins.add(joinNode);
					}
				}
			}

			List<NestedJoinNode> unionNodes = nestedMainTable.getUnionTable();
			if (find && unionNodes != null) {

				for (NestedJoinNode joinNode : unionNodes) {
					if (joinNode.getJoinTableName().equals(entry.getKey())) {
						needJoins.add(joinNode);
					}
				}
			}
		}
	}

	/**
	 * 构建新表
	 */
	protected String constructNewTable(Map<String, NestedMainTable> mainTableMap, List<NestedTable> doneTables,
			NestedMainTable nestedMainTable, Map<String, List<JoinTag>> selectedCols, List<ParseCondition> conditions) {
		StringBuffer sb = new StringBuffer();

		sb.append(" select ");

		List<NestedJoinNode> needJoins = new ArrayList<>();
		this.findJoinNode(selectedCols, needJoins, nestedMainTable);

		Map<String, List<NestedColumn>> selectedNestedColumn = this.chooseSelectedColumn(selectedCols, doneTables,
				nestedMainTable.getUnionTable());
		if (selectedNestedColumn.isEmpty()) {
			return "";
		}
		boolean hasMeasure = checkHasMeasure(selectedNestedColumn);
		sb.append(constructSelect(selectedNestedColumn));

		sb.append(" from ");
		sb.append(constructTableName(this.getNestedTable(nestedMainTable.getMainTableName(), doneTables)));
		sb.append(" ");

		sb.append(PlaceHolders.JOINHOLDER);

		// 构建条件
		ArrayList<String> newCondition = new ArrayList<String>();
		for (NestedTable table : doneTables) {
			String condition = this.constructFilter(table, conditions);
			if (condition.trim().length() > 0) {
				newCondition.add(condition);
			}

		}

		if (!newCondition.isEmpty()) {
			sb.append(" where ");
			sb.append(" ");
			sb.append(PlaceHolders.FILTERHOLDER);

			if (!newCondition.isEmpty()) {

				int index = sb.indexOf(PlaceHolders.FILTERHOLDER);
				StringBuffer filter = new StringBuffer();
				for (int i = 0; i < newCondition.size(); i++) {

					filter.append(newCondition.get(i));
					filter.append(" and ");

				}

				insertStr(index, sb, filter.substring(0, filter.length() - 5));

			}
		}

		if (hasMeasure) {
			String groupby = this.constructGroupBy(selectedNestedColumn);
			sb.append(" group by ").append(groupby);
		}

		for (NestedJoinNode joinNode : needJoins) {
			NestedTable tmpTable = this.getNestedTable(joinNode.getJoinTableName(), doneTables);
			if (tmpTable.getTableName().trim().isEmpty()) {
				continue;
			}
			this.appendJoin(tmpTable, sb, joinNode, mainTableMap, doneTables, selectedCols, conditions);
		}

		String newSQL = sb.toString().replace(PlaceHolders.GROUPHOLDER, " ").replace(PlaceHolders.FILTERHOLDER, " ")
				.replace(PlaceHolders.JOINHOLDER, " ").replace(PlaceHolders.SELECTHOLDER, " ")
				.replace(PlaceHolders.UNIONHOLDER, " ").replace(PlaceHolders.ORDERHOLDER, " ");
		return newSQL;

	}

	/**
	 * 添加join部分
	 */
	private void appendJoin(NestedTable table, StringBuffer sql, NestedJoinNode node,
			Map<String, NestedMainTable> mainTableMap, List<NestedTable> doneTables,
			Map<String, List<JoinTag>> selectedCols, List<ParseCondition> conditions) {

		StringBuffer tmp = new StringBuffer();

		int index = 0;

		index = sql.indexOf(PlaceHolders.JOINHOLDER);

		String tableName = this.constructTableName(table);

		NestedJoinType joinType = node.getJoinType();
		String joinCondition = null;
		String str = null;
		switch (joinType) {
		case INNERJOIN:
			joinCondition = constructJoinCondition(node.getJoinConditions());
			tmp.append(" inner join ").append(tableName).append(" on ").append(joinCondition);
			insertStr(index, sql, tmp.toString());
			break;

		case OUTERJOIN:
			joinCondition = constructJoinCondition(node.getJoinConditions());
			tmp.append(" outer join ").append(tableName).append(" on ").append(joinCondition);
			insertStr(index, sql, tmp.toString());
			break;

		case LEFTJOIN:
			joinCondition = constructJoinCondition(node.getJoinConditions());
			tmp.append(" left join ").append(tableName).append(" on ").append(joinCondition);
			insertStr(index, sql, tmp.toString());
			break;

		case RIGHTJOIN:
			joinCondition = constructJoinCondition(node.getJoinConditions());
			tmp.append(" right join ").append(tableName).append(" on ").append(joinCondition);
			insertStr(index, sql, tmp.toString());
			break;

		case UNION:

			str = this.loopNestedTable(mainTableMap, doneTables, mainTableMap.get(table.getTableAlias()), selectedCols,
					conditions).getTableName();
			sql.append(" union ").append(str);
			break;

		case UNIONALL:
			str = this.loopNestedTable(mainTableMap, doneTables, mainTableMap.get(table.getTableAlias()), selectedCols,
					conditions).getTableName();
			sql.append(" union all").append(str);
			break;

		case INSELECT:
			// sql.append(" union all").append(tableName);
			break;

		default:
			break;

		}

	}

	/**
	 * 静态连表
	 */
	private String constructJoinCondition(List<NestedJoinCondition> joinConditions) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < joinConditions.size(); i++) {

			sb.append(joinConditions.get(i).getFirstTableName()).append(".");
			sb.append(joinConditions.get(i).getFirstTableColumn());
			sb.append("=");
			sb.append(joinConditions.get(i).getSecondTableName()).append(".");
			sb.append(joinConditions.get(i).getSecondTableColumn());

			if (i < joinConditions.size() - 1) {
				sb.append(" and ");
			}
		}
		return sb.toString();
	}

	/**
	 * 构造条件部分
	 */
	private String constructFilter(NestedTable table, List<ParseCondition> conditions) {
		StringBuffer sb = new StringBuffer();

		if (conditions == null) {
			return sb.toString();
		}

		List<NestedColumn> nestedColumns = table.getColumns();
		if (nestedColumns == null || nestedColumns.isEmpty()) {
			return sb.toString();
		}

		List<ParseCondition> newConditions = new ArrayList<>();
		for (ParseCondition condition : conditions) {

			for (NestedColumn nc : nestedColumns) {

				if (nc.getOuterColumn().equals(condition.getAlias())) {

					ParseCondition newCondition = new ParseCondition();

					newCondition.setConditionVal(nc.getColumn());

					newCondition.setColumnType(condition.getColumnType());
					newCondition.setQueryType(condition.getQueryType());
					newConditions.add(newCondition);
					break;
				}
			}

		}

		for (ParseCondition condition : newConditions) {
			String str = getWhereCondition(condition);
			if (str.trim().length() > 0) {
				sb.append(getWhereCondition(condition));
				sb.append(" and ");
			}

		}

		if (newConditions.isEmpty()) {
			return sb.toString();
		}

		return sb.delete(sb.length() - 5, sb.length()).toString();
	}

	/**
	 * 选择需要的字段，并根据需要的字段判断连表关系
	 */
	protected Map<String, List<NestedColumn>> chooseSelectedColumn(Map<String, List<JoinTag>> selectedCols,
			List<NestedTable> tables, List<NestedJoinNode> unionTables) {
		Map<String, List<NestedColumn>> chooseColumns = new HashMap<>();
		for (NestedTable table : tables) {
			boolean find = false;
			if (unionTables != null) {
				for (NestedJoinNode joinNode : unionTables) {
					if (joinNode.getJoinTableName().equals(table.getTableAlias())) {
						find = true;
					}
				}
			}

			if (find) {
				continue;
			}
			List<JoinTag> columns = selectedCols.get(table.getTableAlias());
			List<NestedColumn> column = table.getColumns();
			List<NestedColumn> ccs = new ArrayList<>();
			boolean hasNotJoin = false;
			for (JoinTag joinTag : columns) {
				for (NestedColumn col : column) {
					if (col.getAlias().equals(joinTag.column)) {
						ccs.add(col);

						if (!joinTag.isJoin) {
							hasNotJoin = true;
						}

						break;
					}

				}
			}

			if (hasNotJoin) {
				chooseColumns.put(table.getTableAlias(), ccs);
			}
		}

		return chooseColumns;

	}

	/**
	 * 构造select部分
	 */
	protected String constructSelect(Map<String, List<NestedColumn>> columns) {

		Iterator<Entry<String, List<NestedColumn>>> colIter = columns.entrySet().iterator();

		StringBuffer tmp = new StringBuffer();
		boolean has = false;

		while (colIter.hasNext()) {
			Entry<String, List<NestedColumn>> entry = colIter.next();

			List<NestedColumn> ncols = entry.getValue();
			for (NestedColumn col : ncols) {

				has = true;
				tmp.append(col.getColumn());
				tmp.append(" as " + col.getAlias());
				tmp.append(",");

			}
		}
		if (!has) {
			return tmp.toString();
		}

		return tmp.substring(0, tmp.length() - 1);

	}

	protected NestedTable getNestedTable(String alias, List<NestedTable> tables) {
		for (NestedTable table : tables) {
			if (alias.equals(table.getTableAlias())) {
				return table;
			}
		}

		return null;
	}

	protected void insertStr(int index, StringBuffer sql, String str) {
		sql.insert(index, str);
	}

	protected String getTableName(String str) {
		String[] tmp = str.split("\\.");
		return tmp[0];
	}

	protected String getColumn(String str) {
		String[] tmp = str.split("\\.");
		return tmp[1];
	}

	/**
	 * 构建表名，这里把子查询也抽象成表
	 */
	protected String constructTableName(NestedTable table) {
		StringBuffer sb = new StringBuffer();
		if (table.isSubQuery()) {
			sb.append("(");
			sb.append(table.getTableName());
			sb.append(")");
			sb.append(" as ");
			sb.append(table.getTableAlias());
		} else {
			sb.append(table.getTableName());
			sb.append(" as ");
			sb.append(table.getTableAlias());
		}

		return sb.toString();
	}

	protected String BeautySQL(String sql) {

		return sql.replace("    ", " ").replace("   ", " ").replace("  ", " ");
	}

	/**
	 * 构造group by 部分
	 */
	protected String constructGroupBy(Map<String, List<NestedColumn>> cols) {

		Iterator<Entry<String, List<NestedColumn>>> colIter = cols.entrySet().iterator();

		StringBuffer sb = new StringBuffer();

		boolean has = false;
		while (colIter.hasNext()) {
			Entry<String, List<NestedColumn>> entry = colIter.next();

			List<NestedColumn> ncols = entry.getValue();

			for (NestedColumn nc : ncols) {
				if (!nc.isMeasure()) {
					sb.append(nc.getAlias());
					sb.append(",");
					has = true;
				}
			}

		}

		if (has) {
			return sb.substring(0, sb.length() - 1);
		}

		return sb.toString();
	}

	/**
	 * 检查是否有度量
	 */
	protected boolean checkHasMeasure(Map<String, List<NestedColumn>> chooseColumn) {
		Iterator<Entry<String, List<NestedColumn>>> colIter = chooseColumn.entrySet().iterator();
		while (colIter.hasNext()) {
			Entry<String, List<NestedColumn>> entry = colIter.next();
			List<NestedColumn> ncols = entry.getValue();
			for (NestedColumn col : ncols) {
				if (col.isMeasure()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 条件构造
	 */
	public String getWhereCondition(ParseCondition condition) {

		StringBuffer sb = new StringBuffer();
		if ("".equals(condition.getConditionVal()) || null == condition.getConditionVal()) {

			return sb.toString();
		}

		if (condition.getColumnType().equals(ColumnType.STRING)) {

			if (condition.getQueryType().equals(QueryType.EQUAL)) {
				sb.append(condition.getColName());
				sb.append(" = ");

				sb.append("'");
				sb.append(condition.getConditionVal());
				sb.append("'");

			} else if (condition.getQueryType().equals(QueryType.NOTEQUAL)) {

				sb.append(condition.getColName());
				sb.append("!=");
				sb.append("'");
				sb.append(condition.getConditionVal());
				sb.append("'");
			} else if (condition.getQueryType().equals(QueryType.IN)) {
				sb.append(condition.getColName());
				sb.append(" in (");

				String arr = condition.getConditionVal();
				String[] vals = arr.split(",");
				for (int i = 0; i < vals.length; i++) {
					sb.append("'");
					sb.append(vals[i]);
					sb.append("',");
				}
				sb = new StringBuffer(sb.subSequence(0, sb.length() - 1));
				sb.append(")");
			} else if (condition.getQueryType().equals(QueryType.NOTIN)) {
				sb.append(condition.getColName());
				sb.append(" not in (");

				String arr = condition.getConditionVal();
				String[] vals = arr.split(",");
				for (int i = 0; i < vals.length; i++) {
					sb.append("'");
					sb.append(vals[i]);
					sb.append("',");
				}
				sb = new StringBuffer(sb.subSequence(0, sb.length() - 1));
				sb.append(")");
			} else if (condition.getQueryType().equals(QueryType.LESS)
					|| condition.getQueryType().equals(QueryType.LARGE)
					|| condition.getQueryType().equals(QueryType.LARGEEQUAL)
					|| condition.getQueryType().equals(QueryType.LESSEQUAL)) {
				throw new RuntimeException("relation is not lleigal!");
			}

		} else if (condition.getColumnType().equals(ColumnType.NUMBER)) {

			if (condition.getQueryType().equals(QueryType.EQUAL)) {

				sb.append(condition.getColName());
				sb.append("=");
				sb.append(condition.getConditionVal());

			} else if (condition.getQueryType().equals(QueryType.LARGE)) {

				sb.append(condition.getColName());
				sb.append(">");
				sb.append(condition.getConditionVal());

			} else if (condition.getQueryType().equals(QueryType.LARGEEQUAL)) {

				sb.append(condition.getColName());
				sb.append(">=");
				sb.append(condition.getConditionVal());

			} else if (condition.getQueryType().equals(QueryType.LESS)) {

				sb.append(condition.getColName());
				sb.append("<");
				sb.append(condition.getConditionVal());

			} else if (condition.getQueryType().equals(QueryType.LESSEQUAL)) {

				sb.append(condition.getColName());
				sb.append("<=");
				sb.append(condition.getConditionVal());

			} else if (condition.getQueryType().equals(QueryType.IN)) {

				sb.append(condition.getColName());
				sb.append(" in (");
				sb.append(condition.getConditionVal());
				sb.append(")");

			} else if (condition.getQueryType().equals(QueryType.NOTIN)) {

				sb.append(condition.getColName());
				sb.append(" not in (");
				sb.append(condition.getConditionVal());
				sb.append(")");

			} else if (condition.getQueryType().equals(QueryType.NOTEQUAL)) {

				sb.append(condition.getColName());
				sb.append(" not in (");
				sb.append(condition.getConditionVal());
				sb.append(")");
			}

		} else if (condition.getColumnType().equals(ColumnType.DATE)) {

			if (!condition.getConditionVal().equals("ALL")) {

				if (condition.getQueryType().equals(QueryType.EQUAL)) {
					sb.append(condition.getColName());
					sb.append(">=");
					sb.append("'");
					sb.append(condition.getConditionVal());
					sb.append("'");
					sb.append(" and ");
					sb.append(condition.getColName());
					sb.append("<=");
					sb.append("'");
					sb.append(condition.getConditionVal());
					sb.append("'");

				} else if (condition.getQueryType().equals(QueryType.NOTEQUAL)) {

					sb.append(condition.getColName());
					sb.append(" not in ('");
					sb.append(condition.getConditionVal());
					sb.append("')");

				} else if (condition.getQueryType().equals(QueryType.LARGE)) {

					sb.append(condition.getColName());
					sb.append(">");
					sb.append("'");
					sb.append(condition.getConditionVal());
					sb.append("'");

				} else if (condition.getQueryType().equals(QueryType.LARGEEQUAL)) {

					sb.append(condition.getColName());
					sb.append(">=");
					sb.append("'");
					sb.append(condition.getConditionVal());
					sb.append("'");

				} else if (condition.getQueryType().equals(QueryType.LESSEQUAL)) {

					sb.append(condition.getColName());
					sb.append("<=");
					sb.append("'");
					sb.append(condition.getConditionVal());
					sb.append("'");

				} else if (condition.getQueryType().equals(QueryType.LESS)) {

					sb.append(condition.getColName());
					sb.append("<");
					sb.append("'");
					sb.append(condition.getConditionVal());
					sb.append("'");

				} else if (condition.getQueryType().equals(QueryType.IN)) {
					sb.append(condition.getColName());
					sb.append(" in (");

					String arr = condition.getConditionVal();
					String[] vals = arr.split(",");
					for (int i = 0; i < vals.length; i++) {
						sb.append("'");
						sb.append(vals[i]);
						sb.append("',");
					}
					sb = new StringBuffer(sb.subSequence(0, sb.length() - 1));
					sb.append(")");
				} else if (condition.getQueryType().equals(QueryType.NOTIN)) {
					sb.append(condition.getColName());
					sb.append(" not in (");

					String arr = condition.getConditionVal();
					String[] vals = arr.split(",");
					for (int i = 0; i < vals.length; i++) {
						sb.append("'");
						sb.append(vals[i]);
						sb.append("',");
					}
					sb = new StringBuffer(sb.subSequence(0, sb.length() - 1));
					sb.append(")");
				}
			}
		}

		return sb.toString();

	}

	public static class JoinTag {

		public JoinTag(String column, boolean isJoin) {
			this.column = column;
			this.isJoin = isJoin;
		}

		private String column;

		private boolean isJoin;

		public String getColumn() {
			return column;
		}

		public boolean isJoin() {
			return isJoin;
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public void setJoin(boolean isJoin) {
			this.isJoin = isJoin;
		}

	}

}
