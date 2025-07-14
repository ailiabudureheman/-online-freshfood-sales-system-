package com.mall.common;

import java.sql.*;

public class DbUtil {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public DbUtil() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 使用新的JDBC驱动类名
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
					"root", "11223344");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return con;
	}

	/**
	 * 执行查询并返回ResultSet
	 */
	public ResultSet executeQuery(String sql, Object... params) throws SQLException {
		pstmt = con.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
		return pstmt.executeQuery();
	}

	/**
	 * 执行更新操作（INSERT/UPDATE/DELETE）
	 */
	public int executeUpdate(String sql, Object... params) throws SQLException {
		pstmt = con.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
		return pstmt.executeUpdate();
	}

	/**
	 * 执行插入操作并返回自增ID
	 */
	public int executeInsert(String sql, Object... params) throws SQLException {
		pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
		int affectedRows = pstmt.executeUpdate();
		if (affectedRows > 0) {
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return -1;
	}

	/**
	 * 开启事务
	 */
	public void beginTransaction() throws SQLException {
		if (con != null) {
			con.setAutoCommit(false);
		}
	}

	/**
	 * 提交事务
	 */
	public void commit() throws SQLException {
		if (con != null && !con.getAutoCommit()) {
			con.commit();
		}
	}

	/**
	 * 回滚事务
	 */
	public void rollback() throws SQLException {
		if (con != null && !con.getAutoCommit()) {
			con.rollback();
		}
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取最后插入的ID
	 */
	public int getLastInsertId() throws SQLException {
		String sql = "SELECT LAST_INSERT_ID()";
		try (PreparedStatement stmt = con.prepareStatement(sql);
			 ResultSet resultSet = stmt.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}
		return -1;
	}
}


//
//package com.mall.common;
//
//import java.sql.*;
//
//public class DbUtil {
//
//    private PreparedStatement pstmt = null;
//    private Connection con = null;
//
//    public DbUtil() {
//        try {
//            // 更新为新的 MySQL 驱动类（非弃用版本）
//            Class.forName("com.mysql.jdbc.Driver");
//
//            // 更新数据库连接信息
//            String url = "jdbc:mysql://t628b9d6.natappfree.cc:35380/shop" +
//                    "?useUnicode=true" +
//                    "&characterEncoding=UTF-8" +
//                    "&useSSL=false" +
//                    "&allowPublicKeyRetrieval=true" +
//                    "&serverTimezone=Asia/Shanghai"; // 添加时区设置
//
//            String username = "FreshFood";
//            String password = "123456"; // 替换为实际密码
//
//            con = DriverManager.getConnection(url, username, password);
//
//            System.out.println("数据库连接成功！");
//        } catch (ClassNotFoundException e) {
//            System.err.println("MySQL 驱动加载失败: " + e.getMessage());
//        } catch (SQLException e) {
//            System.err.println("数据库连接失败: " + e.getMessage());
//        }
//    }
//
//    public Connection getCon() {
//        return con;
//    }
//
//    public void close() {
//        if (pstmt != null) {
//            try {
//                pstmt.close();
//            } catch (SQLException e) {
//                System.err.println("关闭PreparedStatement失败: " + e.getMessage());
//            }
//        }
//        if (con != null) {
//            try {
//                con.close();
//                System.out.println("数据库连接已关闭");
//            } catch (SQLException e) {
//                System.err.println("关闭Connection失败: " + e.getMessage());
//            }
//        }
//    }
//}