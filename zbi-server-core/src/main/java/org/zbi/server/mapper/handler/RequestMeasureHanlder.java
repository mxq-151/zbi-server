package org.zbi.server.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.zbi.server.model.request.RequestMeasure;

import com.alibaba.fastjson.JSONArray;

public class RequestMeasureHanlder extends  BaseTypeHandler<List<RequestMeasure>>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<RequestMeasure> parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(i, JSONArray.toJSONString(parameter));
		
	}

	@Override
	public List<RequestMeasure> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(rs.getString(columnName),RequestMeasure.class);
	}

	@Override
	public List<RequestMeasure> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(rs.getString(columnIndex),RequestMeasure.class);
	}

	@Override
	public List<RequestMeasure> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(cs.getString(columnIndex),RequestMeasure.class);
	}

}
