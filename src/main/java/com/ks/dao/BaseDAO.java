package com.ks.dao;

import java.util.List;

import com.ks.mapper.RowMapper;

public interface BaseDAO<T> {
	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);
}