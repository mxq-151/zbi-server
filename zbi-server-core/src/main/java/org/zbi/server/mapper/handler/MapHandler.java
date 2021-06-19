package org.zbi.server.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({Map.class})
public class MapHandler extends BaseTypeHandler<Map<String, ?>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, ?> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONObject.toJSONString(parameter));
    }

    @Override
    public Map<String, ?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JSONObject.parseObject(rs.getString(columnName), new TypeReference<Map<String, ?>>() {
        });
    }

    @Override
    public Map<String, ?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JSONObject.parseObject(rs.getString(columnIndex), new TypeReference<Map<String, ?>>() {
        });
    }

    @Override
    public Map<String, ?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JSONObject.parseObject(cs.getString(columnIndex), new TypeReference<Map<String, ?>>() {
        });
    }

}
