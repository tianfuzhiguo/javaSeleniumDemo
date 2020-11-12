package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库工具类
 *
 * @author dujianxiao
 *
 */
public class DBUtil {
	private static ResultSet rs = null;
	private static Statement stmt = null;
	private static Connection conn = null;

	/**
	 * 执行sql语句
	 *
	 * @param sql
	 */
	public static void execute(String sql) {
		try {
			Statement stmt = getConnect().createStatement();
			stmt.execute(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAll();
	}

	/**
	 * 数据库查询 只支持查询一个字段值
	 *
	 * @param sql
	 * @return 返回字符串类型的字段
	 */
	public static String getStrFiled(String sql) {
		String str = null;
		try {
			Statement stmt = getConnect().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				str = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAll();
		return str;
	}

	/**
	 * 数据库查询 只支持查询一个字段值
	 *
	 * @param sql
	 * @return 返回数字类型的字段
	 */
	public static int getIntFiled(String sql) {
		int str = 0;
		try {
			Statement stmt = getConnect().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				str = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAll();
		return str;
	}

	/**
	 * 含占位符的数据库查询 只支持查询一个字段值
	 *
	 * @param sql
	 * @return 返回数字类型的字段
	 */
	public static int getIntFiled(String sql, String value) {
		int str = 0;
		try {
			PreparedStatement ps = getConnect().prepareStatement(sql);
			ps.setString(1, value);
			rs = ps.executeQuery();
			while (rs.next()) {
				str = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAll();
		return str;
	}

	/**
	 * 返回结果集
	 *
	 * @param sql
	 * @return ResultSet
	 */
	public static ResultSet getRS(String sql) {
		ResultSet rs = null;
		try {
			Statement stmt = getConnect().createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAll();
		return rs;
	}

	/**
	 * 连接数据库
	 *
	 * @return 返回数据库连接
	 */
	public static Connection getConnect() {
		String DBUrl = FileUtil.getValue("global.properties", "DBUrl");
		String DBName = FileUtil.getValue("global.properties", "DBName");
		String username = FileUtil.getValue("global.properties", "username");
		String password = FileUtil.getValue("global.properties", "password");
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ":1433;databaseName=" + DBName + ";user="
					+ username + ";password=" + password + "");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭所有可关闭资源
	 */
	public static void closeAll() {
		String[] str = { "Connection", "Statement", "ResultSet" };
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals("Connection")) {
				close(conn);
			}
			if (str[i].equals("Statement")) {
				close(stmt);
			}
			if (str[i].equals("ResultSet")) {
				close(stmt);
			}

		}
	}

	public static void close(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}