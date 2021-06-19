package org.zbi.server.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.zbi.server.model.config.ModelMeasure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MeasureHandler extends BaseTypeHandler<List<ModelMeasure>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<ModelMeasure> parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(i, JSON.toJSONString(parameter));
		
	}

	@Override
	public List<ModelMeasure> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return JSONObject.parseArray(rs.getString(columnName), ModelMeasure.class);
	}

	@Override
	public List<ModelMeasure> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return JSONObject.parseArray(rs.getString(columnIndex), ModelMeasure.class);
	}

	@Override
	public List<ModelMeasure> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return JSONObject.parseArray(cs.getString(columnIndex), ModelMeasure.class);
	}

}
