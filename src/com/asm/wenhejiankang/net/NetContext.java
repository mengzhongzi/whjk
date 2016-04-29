package com.asm.wenhejiankang.net;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.asm.wenhejiankang.model.User;

public interface NetContext
{
	
	/**
	 * 登录方法
	 * @param username 用户名
	 * @param password 用户密码
	 * @return 成功返回用户信息  失败返回null
	 */
	public User login(String username,String password);
	
	
	/**
	 * 根据id获取用户信息
	 * @param id 用户id
	 * @return 包含用户信息的对象
	 */
	public User getUser(Serializable id);
	
	/**
	 * 判断用户名是否存在
	 * @param name 用户名
	 * @return 存在返回true  不存在返回false
	 * @throws SQLException 
	 */
	public boolean isExistUser(String name) throws SQLException;
		
	
	
	/**
	 * 设置监听器
	 * @param listener 监听器对象
	 */
	public void setListener(NetListener listener);
	
	
	
	/**
	 * 获取体温计
	 *从指定日期到指定日期的体温数据
	 * @param id 用户id
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 包含温度信息的集合
	 */
	public ArrayList<String> getTiwen(Serializable id,Date start,Date end);	
	
	/**
	 * 设置体温计
	 * @param id 用户id
	 * @param date 检测时间
	 * @param text 温度
	 */
	public void addTiwen(Serializable id,Date date,String text);
	
	/**
	 * 获取指定日期到指定日期的血压数据
	 * @param id 用户id
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 指定日期的血压数据
	 */
	public ArrayList<String> getXieya(Serializable id,Date start,Date end);
	
	/**
	 * 设置血压
	 * @param id 用户id
	 * @param date 检测时间
	 * @param max 高压
	 * @param min 低压
	 * @param rate 脉率
	 */
	public void addXieya(Serializable id,Date date,double max,double min,double rate);
	
	/**
	 * 获取血氧仪
	 * 从指定日期到指定日期的血氧数据 spo2 pr pi
	 * @param id
	 * @param start
	 * @param end
	 * @return 血氧仪信息，从指定日期到指定日期的血氧数据 spo2 pr pi
	 */
	public ArrayList<String> getXieyang(Serializable id,Date start,Date end);
	
	/**
	 * 设置血氧仪
	 * 添加指定日期的一行数据
	 * @param id 用户id
	 * @param date 测试日期
	 * @param spo2 血氧饱和度
	 * @param pr 脉率
	 * @param pi 灌注指数
	 */
	public void addXieyang(Serializable id,Date date,double spo2,double pr,double pi);
	
	/**
	 * 获取血糖
	 * 从指定日期到指定日期
	 * @param id
	 * @param start
	 * @param end
	 * @return 血糖数据
	 */
	public ArrayList<String> getXietang(Serializable id,Date start, Date end);
	
	/**
	 * 添加血糖信息
	 * @param id 用户id
	 * @param date  测试日期
	 * @param text  血糖浓度
	 */
	public void addXietang(Serializable id,Date date, double text);
	
	
}
