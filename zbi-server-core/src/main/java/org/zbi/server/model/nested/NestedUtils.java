package org.zbi.server.model.nested;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zbi.server.model.parse.ParseDimension;
import org.zbi.server.model.parse.ParseMeasure;

public class NestedUtils {

	public NestedSqlParam transferWithSQL(String tableName1, String tableName2, String alias,
			List<ParseDimension> selectedDimenssion, List<ParseMeasure> measures, List<String> selectedCols,
			boolean etl) {

		NestedTable t1 = new NestedTable();
		List<NestedColumn> t1_columns = new ArrayList<>();
		t1_columns.addAll(this.transferMeasureColumnToZero(measures, alias + NestedRateConstant.TABLE1Post, alias + NestedRateConstant.TABLE2Post, etl));
		t1_columns.addAll(this.getNestedColumn(selectedDimenssion, alias + NestedRateConstant.TABLE1Post));
		t1.setColumns(t1_columns);
		t1.setSubQuery(false);
		t1.setTableAlias(alias + NestedRateConstant.TABLE1Post);
		t1.setTableName("("+tableName1+")");

		NestedTable t2 = new NestedTable();
		List<NestedColumn> t2_columns = new ArrayList<>();
		t2.setColumns(t2_columns);
		t2.setSubQuery(false);
		t2.setTableAlias(alias + NestedRateConstant.TABLE2Post);
		t2.setTableName("("+tableName2+")");

		NestedMainTable main1 = new NestedMainTable();
		main1.setMainTableName(alias + NestedRateConstant.TABLE1Post);

		NestedJoinNode joinNode3 = new NestedJoinNode();
		joinNode3.setJoinTableName(alias + NestedRateConstant.TABLE2Post);
		joinNode3.setJoinType(NestedJoinType.LEFTJOIN);
		for (ParseDimension dc : selectedDimenssion) {
			joinNode3.addNestedJoinCondition(new NestedJoinCondition(alias + NestedRateConstant.TABLE1Post,
					alias + NestedRateConstant.TABLE2Post, dc.getAlias(), dc.getAlias()));
		}
		main1.addNestedJoinNode(joinNode3);

		NestedSqlParam param = new NestedSqlParam();
		List<NestedTable> nestedTables = new ArrayList<>();
		nestedTables.add(t2);
		nestedTables.add(t1);

		param.setNestedTables(nestedTables);
		param.setTopTable("top");
		Map<String, NestedMainTable> nestedMainTable = new HashMap<>();
		nestedMainTable.put("top", main1);
		param.setNestedMainTable(nestedMainTable);

		return param;

	}
	
	 private List<NestedColumn> getNestedColumn(List<ParseDimension> cols, String alias) {
	        List<NestedColumn> ncs = new ArrayList<>();

	        for (ParseDimension col : cols) {
	            NestedColumn nc = new NestedColumn();
	            nc.setColumn(alias + "." + col.getAlias());
	            nc.setMeasure(false);
	            nc.setOuterColumn(col.getAlias());
	            nc.setAlias(col.getAlias());
	            ncs.add(nc);
	        }
	        return ncs;

	    }

	/**
	 * 对零值进行转换
	 */
	private List<NestedColumn> transferMeasureColumnToZero(List<ParseMeasure> measures, String table1, String table2,
			boolean etl) {
		List<NestedColumn> ncs = new ArrayList<NestedColumn>();

        for (ParseMeasure mc : measures) {

            String mstr = mc.getAlias();

            if (etl) {
                int index = mstr.indexOf("_");
                mstr = mstr.substring(index + 1, mstr.length());
            }

            String str1 = table1 + "_" + mstr;
            String str2 = table2 + "_" + mstr;

            NestedColumn nc1 = new NestedColumn();
            nc1.setOuterColumn(mstr);
            nc1.setColumn(str1);
            nc1.setMeasure(false);
            nc1.setAlias(mstr);
            ncs.add(nc1);
        }

        return ncs;
	}

}
