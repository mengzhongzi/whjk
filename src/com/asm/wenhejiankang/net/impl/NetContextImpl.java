package com.asm.wenhejiankang.net.impl;

//import android.util.Log;
/*
import com.example.administrator.dbtest.MD5Tool;
import com.example.administrator.dbtest.NetContext;
import com.example.administrator.dbtest.NetListener;
import com.example.administrator.dbtest.User;
*/
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.asm.wenhejiankang.net.NetListener;
import com.asm.wenhejiankang.model.User;
import com.asm.wenhejiankang.net.NetContext;
import com.xl.game.tool.Log;
import java.util.*;


public class NetContextImpl extends BaseDao implements NetContext {

	String TAG="NETContextImpl";
		private NetListener listener;

		public NetContextImpl()
			{

			}

		public User login(String username, String password) {
				User user = findUser(username);
        //MD5Tool.validatePassword(password, user.getPwd())
        //Log.i("用户密码：",user.toString());
        if(user != null){
            if(user.getPwd().equals(password))
							{
                if(listener!=null)
									listener.onEnter();
                return user;
							}
					}

				if(listener!=null)
					listener.onError();
				return null;
			}

		public User findUser(String username)
			{
				User user = null;
				getConnection();
				String sql = "SELECT tb_user.id,tb_user.username, tb_user.pwd, tb_user.sex, tb_user.phone, tb_user.email, tb_user.doctor, tb_user.marriage, tb_user.id_card, tb_user.address FROM tb_user WHERE tb_user.username = ?";
				ResultSet res = getQuery(sql, username);
				try {
						if(res.next())
							{
                user = new User();
								user.setId(res.getLong(1));
								user.setName(res.getString(2));
								user.setPwd(res.getString(3));
								user.setGender(res.getString(4));
								user.setTel(res.getString(5));
								user.setEmail(res.getString(6));
								user.setDortor(res.getString(7));
								user.setMaritalStatus(res.getString(8));
								user.setIdentityNumber(res.getString(9));
								user.setAddress(res.getString(10));
							}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					} finally {
						closeAll();
					}
				return user;
			}

		public User getUser(Serializable id) {
				User user = new User();
				getConnection();
				String sql = "SELECT tb_user.username, tb_user.pwd, tb_user.sex, tb_user.phone, tb_user.email, tb_user.doctor, tb_user.marriage, tb_user.id_card, tb_user.address FROM tb_user WHERE tb_user.id = ?";
				ResultSet res = getQuery(sql, id);
				try {
						if(res.next())
							{
								user.setId((Long)id);
								user.setName(res.getString(1));
								user.setPwd(res.getString(2));
								user.setGender(res.getString(3));
								user.setTel(res.getString(4));
								user.setEmail(res.getString(5));
								user.setDortor(res.getString(6));
								user.setMaritalStatus(res.getString(7));
								user.setIdentityNumber(res.getString(8));
								user.setAddress(res.getString(9));
							}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					} finally {			
						closeAll();
					}
				return user;
			}

		public boolean isExistUser(String name) throws SQLException {
				String sql = "SELECT tb_user.id FROM tb_user where tb_user.username = ?";
				getConnection();
				ResultSet res = getQuery(sql, name);
				closeAll();
				return res.next();
			}

		public void setListener(NetListener listener) {
				this.listener = listener;

			}

		public ArrayList<String> getTiwen(Serializable id, Date start, Date end) {
				ArrayList<String> list = new ArrayList<String>();
				String sql = "SELECT tb_animal_heat.test_time,tb_animal_heat.temperature FROM tb_animal_heat where user_id = ? and test_time BETWEEN ? AND ?";
				getConnection();
				ResultSet res = getQuery(sql, id,start,end);
				try {
						while(res.next())
							{
								//数据格式："测试时间 体温"
								list.add(formatDateString(res.getString(1)) + " " + res.getString(2));
							}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
						if(listener!=null)
							listener.onTiwenError();
					}
			    showLog(list);
				return list;
			}

		public void addTiwen(Serializable id, Date date, String text) {
				String sql = "INSERT INTO tb_animal_heat (tb_animal_heat.user_id,tb_animal_heat.test_time,tb_animal_heat.temperature) VALUES (?,?,?)";
				getConnection();
				int res = getUpdate(sql, id,date,text);
				if(res != 1 && listener!=null)
					listener.onUpError();

			}

		public ArrayList<String> getXieya(Serializable id, Date start, Date end) {
				ArrayList<String> list = new ArrayList<String>();
				String sql = "SELECT tb_blood_pressure.test_time, tb_blood_pressure.highest, tb_blood_pressure.lowest, tb_blood_pressure.pulse_rate FROM tb_blood_pressure WHERE tb_blood_pressure.user_id = ? and test_time BETWEEN ? AND ?";
				getConnection();
				ResultSet res = getQuery(sql, id,start,end);
				try {
						while(res.next())
							{
								//数据格式："测试时间 高压 低压 脉率"
								list.add(formatDateString(res.getString(1)) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getString(4));
								Log.e(TAG,"血压 添加一行数据");
							}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
						if(listener!=null)
							listener.onXieyaError();
					}
			    showLog(list);
				return list;
			}

		public void addXieya(Serializable id, Date date, double max, double min,
		double rate) {
				String sql = "INSERT INTO tb_blood_pressure (tb_blood_pressure.user_id,tb_blood_pressure.test_time,tb_blood_pressure.highest,tb_blood_pressure.lowest,tb_blood_pressure.pulse_rate) VALUES (?,?,?,?,?)";
				getConnection();
				int res = getUpdate(sql,id,date,max,min,rate);
				if(res != 1)
					if(listener!=null)
						listener.onUpError();		
			}

		public ArrayList<String> getXieyang(Serializable id, Date start, Date end) {
				ArrayList<String> list = new ArrayList<String>();
				String sql = "SELECT tb_blood_oxygen.test_time, tb_blood_oxygen.saturability, tb_blood_oxygen.pulse_rate, tb_blood_oxygen.vqi FROM tb_blood_oxygen WHERE user_id = ? and test_time BETWEEN ? AND ?";
				getConnection();
				ResultSet res = getQuery(sql, id,start,end);
				try {
						while(res.next())
							{
								//数据格式："测试时间 血氧饱和度 脉率 灌注指数"
								list.add(formatDateString(res.getString(1)) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getString(4));
								Log.e(TAG,"血氧 添加一行数据");
							}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
						if(listener!=null)
							listener.onXieyangError();
					}
			    showLog(list);
				return list;
			}

		public void addXieyang(Serializable id, Date date, double spo2, double pr,
		double pi) {
				String sql = "INSERT INTO tb_blood_oxygen (user_id,test_time,saturability,pulse_rate,vqi) VALUES (?,?,?,?,?)";
				getConnection();
				int res = getUpdate(sql,id,date,spo2,pr,pi);
				if(res != 1 && listener!=null)
					listener.onUpError();

			}

		public ArrayList<String> getXietang(Serializable id, Date start, Date end) {
				ArrayList<String> list = new ArrayList<String>();
				String sql = "SELECT tb_glycemic_index.test_time,tb_glycemic_index.gi FROM tb_glycemic_index WHERE user_id =? AND test_time BETWEEN ? AND ?";
				getConnection();
				ResultSet res = getQuery(sql, id,start,end);
				try {
						while(res.next())
							{
								//数据格式："测试时间 血糖指数"
								list.add(formatDateString(res.getString(1)) + " " + res.getString(2));
							}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
						if(listener!=null)
							listener.onXietangError();
					}
			    showLog(list);
				return list;
			}

		public void addXietang(Serializable id, Date date, double text) {
				String sql = "INSERT INTO tb_glycemic_index (user_id,test_time,gi) VALUES (?,?,?)";
				getConnection();
				int res = getUpdate(sql,id,date,text);
				if(res != 1 &&listener!=null)
					listener.onUpError();

			}

		/**
		 * 格式化时间字符串
		 * @param dateString 需要格式化的字符串  如2001-1-2 10:01:01
		 * @return 格式化以后的字符串 如2001-1-2 10:01:01
     */
		private String formatDateString(String dateString)
			{
				return dateString.replace(" ","\n");
			}
			
		private void showLog(List<String> strs)
		{
			for(String str : strs)
			{
				Log.e("客官，这是您要的数据",str);
			}
		}

	}
