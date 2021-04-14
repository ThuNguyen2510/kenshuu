package com.ks.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ks.dao.BaseDAO;
import com.ks.mapper.RowMapper;

public class BaseDAOImpl<T> implements BaseDAO<T> {
	private static final String URL = "jdbc:postgresql://localhost:5432/kenshuu";

	private static final String DRIVER = "org.postgresql.Driver";

	private static final String USERNAME = "postgres";

	private static final String PASSWORD = "123456";

	private static Connection connection = null;
	private static final Logger logger = Logger.getLogger(BaseDAOImpl.class);

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//DB接続
			System.out.println("CONNECT SUCCESS!!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameter(statement, parameters);//インプット（パラメータ）をセットする
			logger.info(statement);
			resultSet = statement.executeQuery();//SQLクエリーを実行する
			while (resultSet.next()) {
				results.add(rowMapper.mapRow(resultSet));//結果をマップして、リストに保存する
			}
			return results;
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (connection != null || statement != null || resultSet != null) {
					connection.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}
	}

	private void setParameter(PreparedStatement statement, Object... parameters) {
		try {
			for (int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i];//パラメータを取る
				int index = i + 1;//パラメータの位置をとる
				if (parameter instanceof Long) {
					statement.setLong(index, (Long) parameter);
				} else if (parameter instanceof String) {
					statement.setString(index, (String) parameter);
				} else if (parameter instanceof Integer) {
					statement.setInt(index, (Integer) parameter);
				} else if (parameter instanceof Timestamp) {
					statement.setTimestamp(index, (Timestamp) parameter);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
