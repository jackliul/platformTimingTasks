/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jacliu.test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class JDBCUtil {
	public static String dbClassName = "com.mysql.jdbc.Driver";
	public static String dbUrl = "jdbc:mysql://192.168.8.33:3306/platform_timing_tasks?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true";
	public String dbUser = "root";
	public String dbPsw = "123@qwerty";
	public static Connection conn = null;

	public JDBCUtil() {
		try {
			if (conn == null) {
				Class.forName(dbClassName);
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPsw);
			} else
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(String sql) {
		ResultSet rsl = null;
		try {
			if (conn == null) {
				new JDBCUtil();
				rsl = conn.prepareStatement(sql).executeQuery(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return rsl;
	}

	public static int executeUpdate(String sql) {
		int result = -1;
		try {
			if (conn == null) {
				new JDBCUtil();
				result = conn.createStatement().executeUpdate(sql);
			}
		} catch (SQLException ex) {
			Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return result;
	}

	public static void close() {
		try {
			conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			conn = null;
		}
	}
}
