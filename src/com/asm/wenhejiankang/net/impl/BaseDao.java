package com.asm.wenhejiankang.net.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC连接基本类
 * @author jiy
 *
 */
public class BaseDao {
	
	
//	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	private static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=db_miniSNS?useUnicode=true&characterEncoding=UTF-8";
	
	private static final String DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String URL = "jdbc:mysql://5718a2de5c228.gz.cdb.myqcloud.com:5644/db_database0320?useUnicode=true&characterEncoding=UTF-8";
	private static final String USER = "cdb_outerroot";
	private static final String PWD = "###@kk123";
	public Connection conn = null;
	public PreparedStatement pstmt = null;
	//public ResultSet res = null;
	
	static{
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("数据库驱动加载失败！信息:" + e.getMessage());
		}
	}
	
	/**
	 * 获取jdbc连接
	 */
	public void getConnection()
	{
		try {
			conn = DriverManager.getConnection(URL,USER,PWD);
			
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失败！" + e.getMessage());
		}
	}
	
	/**
	 * 执行查询操作
	 * @param sql sql语句
	 * @param paras 参数数组
	 * @return 查询结果的ResultSet对象
	 */
	public ResultSet getQuery(String sql,Object...paras)
	{
		ResultSet queryRes = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if(paras != null && paras.length != 0)
			{				
				for(int i = 0;i < paras.length;i++)
				{
					pstmt.setObject(i+1, paras[i]);
				}
			}
			queryRes = pstmt.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException("执行查询失败！"+e.getMessage());
		}
		return queryRes;
	}
	
	
	/**
	 * 执行更新操作
	 * @param sql sql语句
	 * @param paras 参数列表
	 * @return 受影响的行数
	 */
	public int getUpdate(String sql,Object...paras)
	{
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			if(paras != null && paras.length != 0)
			{				
				for(int i = 0;i < paras.length;i++)
				{
					pstmt.setObject(i+1, paras[i]);
				}
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("执行更新失败！" +e.getMessage());
		}
		return result;
	}
	
	/**
	 * 关闭资源
	 * 关闭ResultSet、PreparedStatement、Connection对象
	 */
	public void closeAll()
	{
//		if(res != null)
//		{
//			try {
//				res.close();
//			} catch (SQLException e) {
//				throw new RuntimeException("关闭ResultSet失败！");
//			}
//		}
		if(pstmt != null)
		{
			try {
				pstmt.close();
			} catch (SQLException e) {
				throw new RuntimeException("关闭PreparedStatement失败！");
			}
		}
		if(conn != null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException("关闭Connection失败！");
			}
		}
	}
}
