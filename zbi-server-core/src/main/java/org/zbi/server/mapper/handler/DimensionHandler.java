package org.zbi.server.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.zbi.server.model.config.ConfigColumn;

import com.alibaba.fastjson.JSONArray;

public class DimensionHandler extends BaseTypeHandler<List<ConfigColumn>>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<ConfigColumn> parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(i, JSONArray.toJSONString(parameter));
		
	}

	@Override
	public List<ConfigColumn> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(rs.getString(columnName),ConfigColumn.class);
	}

	@Override
	public List<ConfigColumn> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return JSONArray.parseArray(rs.getString(columnIndex),ConfigColumn.class);
	}

	@Override
	public List<ConfigColumn> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return  JSONArray.parseArray(cs.getString(columnIndex),ConfigColumn.class);
	}

	

	

}
