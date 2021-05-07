package com.ks.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();//接続閉じる
			} catch (SQLException e) {
				return null;
			}
		}
	}

	private void setParameter(PreparedStatement statement, Object... parameters) {

		for (int i = 0; i < parameters.length; i++) {
			Object parameter = parameters[i];//パラメータを取る
			int index = i + 1;//パラメータの位置をとる
			setParam_(statement, parameter, index);
		}

	}

	private void setParameter2(PreparedStatement statement, List<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			int index = i + 1;
			Object p = list.get(i);
			setParam_(statement, p, index);
		}
	}

	private void setParam_(PreparedStatement statement, Object p, int index) {
		try {
			if (p instanceof String) {
				statement.setString(index, (String) p);
			} else if (p instanceof Integer) {
				Integer num = (Integer) p;
				if (num == -1) {
					statement.setObject(index, null);
				} else
					statement.setInt(index, (Integer) p);
			} else if (p instanceof Long) {
				statement.setLong(index, (Long) p);
			} else if (p == null) {
				statement.setObject(index, null);
			}
		} catch (SQLException e) {

		}

	}

	@Override
	public <T> List<T> query2(String sql, RowMapper<T> rowMapper, List<Object> list) {
		List<T> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameter2(statement, list);//インプット（パラメータ）をセットする
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
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();//接続閉じる
			} catch (SQLException e) {
				return null;
			}
		}
	}

	@Override
	public boolean saveOrUpdate(String sql, Object... params) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean flag = false;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);//コミットを呼ぶとき、データベースを変更する
			statement = connection.prepareStatement(sql);
			setParameter(statement, params);//インプット（パラメータ）をセットする
			logger.info(statement);
			statement.executeUpdate();//SQLクエリーを実行する
			connection.commit();// データを変更する
			flag = true;

		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();//最初のデータを取る
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();//接続閉じる
				}
			} catch (SQLException e_) {
				e_.printStackTrace();
			}
		}
		return flag;

	}

}
