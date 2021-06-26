package org.zbi.server.model.service;

import java.util.List;

import org.zbi.server.model.parse.ParseColumn;
import org.zbi.server.model.parse.ParseModel;
import org.zbi.server.model.response.ColumnMetaResp;
import org.zbi.server.model.response.QueryResultResp;

public abstract class AbstractQueryService implements IQueryService {

	protected void encodeChinese(ParseModel parseSqlParam, QueryResultResp qr) {
		List<ParseColumn> dimensions = parseSqlParam.getDimensions();
		List<ParseColumn> measures = parseSqlParam.getMeasures();
		List<ColumnMetaResp> metas = qr.getColumnMetas();

		for (ColumnMetaResp meta : metas) {
			for (ParseColumn dim : dimensions) {
				if (dim.getAlias().toLowerCase().equals(meta.getName().toLowerCase())) {
					meta.setChinese(dim.getChinese());
					meta.setMeasure(false);
					meta.setUuid(dim.getUuid());
					break;
				}

			}

		}

		for (ColumnMetaResp meta : metas) {
			for (ParseColumn measure : measures) {
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
