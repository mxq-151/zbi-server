package org.zbi.server.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.zbi.server.model.request.RequestColumn;

import com.alibaba.fastjson.JSONArray;

public class RequestColumnHandler extends BaseTypeHandler<List<RequestColumn>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<RequestColumn> parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(i, JSONArray.toJSONString(parameter));
		
	}

	@Override
	public List<RequestColumn> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(rs.getString(columnName),RequestColumn.class);
	}

	@Override
	public List<RequestColumn> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(rs.getString(columnIndex),RequestColumn.class);
	}

	@Override
	public List<RequestColumn> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(cs.getString(columnIndex),RequestColumn.class);
	}

}
