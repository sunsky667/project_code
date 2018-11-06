package com.sunsky.dataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtil {

	//define a connection pool
	private static BasicDataSource bds;

	//create and init the BasicDataSource
	static{
		Properties p = new Properties();
		try {
			//get the input steam
			p.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
			//get the value from properties file
			String driver = p.getProperty("driver");
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String pwd = p.getProperty("pwd");
			String initSize = p.getProperty("initSize");
			String maxSize = p.getProperty("maxSize");

			//create connection pool
			bds = new BasicDataSource();

			//init the bds
			bds.setDriverClassName(driver);
			bds.setUrl(url);
			bds.setUsername(user);
			bds.setPassword(pwd);
			bds.setInitialSize(Integer.parseInt(initSize));
			bds.setMaxActive(Integer.parseInt(maxSize));

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载db.properties失败");
		}
	}


	/**
	 *
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		return bds.getConnection();
	}

	/**
	 * close the connection
	 * @param con
	 */
	public static void close(Connection con){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭连接失败");
			}
		}
	}

	public static void main(String[] args) {
		Connection con=null;
		try {
			con = DBUtil.getConnection();
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(con);
		}

	}

}
