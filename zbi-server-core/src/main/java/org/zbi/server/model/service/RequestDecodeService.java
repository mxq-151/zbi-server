package org.zbi.server.model.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbi.server.dao.service.ConfigDaoService;
import org.zbi.server.model.config.QueryColumn;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.request.RequestColumn;
import org.zbi.server.model.request.RequestCondition;
import org.zbi.server.model.request.RequestParam;

@Component
public class RequestDecodeService {

	@Autowired
	ConfigDaoService configDaoService;

	private final static Logger logger = LoggerFactory.getLogger(RequestDecodeService.class);

	public RequestParam parseRquest(RequestParam param) throws ParseException {
		// TODO Auto-generated method stub

		List<QueryColumn> queryCols = this.configDaoService.getQueryColumns(param.getModelTag());

		List<RequestColumn> dims = param.getDimensions();
		List<RequestColumn> measures = param.getMeasures();

		List<RequestColumn> newDims = new ArrayList<>();
		for (RequestColumn dim : dims) {
			if (dim.isAdvanced()) {
				newDims.add(dim);
				continue;
			}
			boolean find = false;
			for (QueryColumn col : queryCols) {
				if (dim.getUuid().equals(col.getColID())) {
					newDims.add(dim);
					List<String> limits = this.configDaoService.getColumnLimits(col.getColID());
					List<RequestCondition> conditions = param.getConditions();
					for (RequestCondition condition : conditions) {
						if (condition.getUuid().equals(col.getColID())) {
							condition.getRequestValue().addAll(limits);
						}
					}
					find = true;
				}
			}
			if (!find) {
				logger.info("cant not  find col for {} in model {}", dim.getUuid(), param.getModelTag());
			}
		}

		List<RequestColumn> newMeasures = new ArrayList<>();
		for (RequestColumn measure : measures) {
			if (measure.isAdvanced()) {
				newMeasures.add(measure);
				continue;
			}
			boolean find = false;
			for (QueryColumn col : queryCols) {
				if (measure.getUuid().equals(col.getColID())) {
					newMeasures.add(measure);
					find = true;
				}
			}
			if (!find) {
				logger.info("cant not  find col for {} in model {}", measure.getUuid(), param.getModelTag());
			}
		}

		RequestParam newParam = new RequestParam();
		BeanUtils.copyProperties(param, newParam);
		newParam.setDimensions(newDims);
		newParam.setMeasures(newMeasures);

		return newParam;
	}

}
