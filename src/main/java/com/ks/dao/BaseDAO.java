package com.ks.dao;

import java.util.List;

import com.ks.mapper.RowMapper;

public interface BaseDAO<T> {
	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);// パラメータの数が未定
	<T> List<T> query2(String sql, RowMapper<T> rowMapper, List<Object> list);//パラメータの数が決定している
	boolean saveOrUpdate(String sql, Object... params);//新しいオブジェクト作成, オブジェクト変更

}
