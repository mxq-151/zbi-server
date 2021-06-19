package org.zbi.server.model.service;

import java.util.List;

import org.zbi.server.model.parse.ParseDimension;
import org.zbi.server.model.parse.ParseMeasure;
import org.zbi.server.model.parse.ParseSqlParam;
import org.zbi.server.model.response.ColumnMetaResp;
import org.zbi.server.model.response.QueryResultResp;

public abstract class AbstractQueryService implements IQueryService {

	protected void encodeChinese(ParseSqlParam parseSqlParam, QueryResultResp qr) {
		List<ParseDimension> dimensions = parseSqlParam.getDimensions();
		List<ParseMeasure> measures = parseSqlParam.getMeasures();
		List<ColumnMetaResp> metas = qr.getColumnMetas();

		for (ColumnMetaResp meta : metas) {
			for (ParseDimension dim : dimensions) {
				if (dim.getAlias().toLowerCase().equals(meta.getName().toLowerCase())) {
					meta.setChinese(dim.getChinese());
					meta.setMeasure(false);
					meta.setUuid(dim.getUuid());
					break;
				}

			}

		}

		for (ColumnMetaResp meta : metas) {
			for (ParseMeasure measure : measures) {
				if (measure.getAlias().toLowerCase().equals(meta.getName().toLowerCase())) {
					meta.setChinese(measure.getChinese());
					meta.setMeasure(true);
					meta.setUuid(measure.getUuid());
					break;
				}

			}

		}
	}

}
