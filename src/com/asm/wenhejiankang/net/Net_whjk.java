package com.asm.wenhejiankang.net;
import com.asm.wenhejiankang.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import com.asm.wenhejiankang.net.impl.NetContextImpl;
import android.os.Handler;
import android.os.Message;
import java.util.Date;

public class Net_whjk extends Handler
{
		public void handleMessage(android.os.Message msg) 
		{
			super.handleMessage(msg);
			switch(msg.what)
			{
				case NET_ENTER:
					if(listener!=null)
						listener.onEnter((User)msg.obj);
						user=(User)user;
					break;
				case NET_ERROE:
					if(listener!=null)
						listener.onError("登录失败");
					break;
				case NET_TIWEN:
					if(listener!=null)
						listener.onTiWen((ArrayList<String>)msg.obj);
					break;
				case NET_XIEYA:
					if(listener!=null)
						listener.onXieya((ArrayList<String>)msg.obj);
					break;
				case NET_XIEYANG:
					if(listener!=null)
						listener.onXieyang((ArrayList<String>)msg.obj);
					break;
				case NET_XIETANG:
					if(listener!=null)
						listener.onXietang((ArrayList<String>)msg.obj);
					break;
				case NET_UPERROR:
					if(listener!=null)
						listener.onUpError();
			}
		}
	
		public static final int
		NET_ENTER=1, //登录成功
		NET_ERROE=2, //登录失败
		NET_TIWEN=3, //体温计
		NET_XIEYA=4, //血压
		NET_XIEYANG=5, //血氧
		NET_XIETANG=6, //血糖
		NET_UP=7, //上传成功
		NET_UPERROR=8; //上传失败(暂用)
	
	NetContextImpl net;
	Thread thread;
	Net_whjk_Listener listener;
	Handler handler;
	Runnable runnable;
	User user;
	
	public Net_whjk()
	{
		net = new NetContextImpl();
		handler=this;
		
	}
	
	public void setListener(Net_whjk_Listener listener)
	{
		this.listener = listener;
	}
	
		

				/**
				 * 登录方法
				 * @param username 用户名
				 * @param password 用户密码
				 * @return 成功返回用户信息  失败返回null
				 */
				public void login(final String username,final String password)
				{
					thread = new Thread()
					{
						public void run()
						{
							user =net.login(username,password);
							Message msg=new Message();
							if(user!=null)
							{
								msg.obj = user;
								msg.what=NET_ENTER;
							}
							else
								msg.what=NET_ERROE;
							
							handler.sendMessage(msg);
						}
					};
					thread.start();
				}


				/**
				 * 根据id获取用户信息
				 * @param id 用户id
				 * @return 包含用户信息的对象
				 */
				public void getUser(Serializable id)
				{
					thread = new Thread()
					{
						public void run()
						{
							Message msg=new Message();
							
						}
					};
					thread.start();
				}
				

				/**
				 * 判断用户名是否存在
				 * @param name 用户名
				 * @return 存在返回true  不存在返回false
				 * @throws SQLException 
				 */
				public void isExistUser(String name)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
								}
						};
					thread.start();
				}



				/**
				 * 设置监听器
				 * @param listener 监听器对象
				 */
				public void setListener(NetListener listener)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
								}
						};
					thread.start();
				}



				/**
				 * 获取体温计
				 *从指定日期到指定日期的体温数据
				 * @param id 用户id
				 * @param start 开始日期
				 * @param end 结束日期
				 * @return 包含温度信息的集合
				 */
				public void getTiwen(final Date start,final Date end)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.obj=net.getTiwen(user.getId(),start,end);
									
										
									
									msg.what=NET_TIWEN;
									handler.sendMessage(msg);
								}
						};
					thread.start();
				}

				/**
				 * 设置体温计
				 * @param id 用户id
				 * @param date 检测时间
				 * @param text 温度
				 */
				public void addTiwen(final Date date,final String text)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.what=NET_UP;
									net.addTiwen(user.getId(),date,text);
								}
						};
					thread.start();
				}

				/**
				 * 获取指定日期到指定日期的血压数据
				 * @param id 用户id
				 * @param start 开始时间
				 * @param end 结束时间
				 * @return 指定日期的血压数据
				 */
				public void getXieya(final Date start,final Date end)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.what = NET_XIEYA;
									msg.obj= net.getXieya(user.getId(),start,end);
									handler.sendMessage(msg);
								}
						};
					thread.start();
				}

				/**
				 * 设置血压
				 * @param id 用户id
				 * @param date 检测时间
				 * @param max 高压
				 * @param min 低压
				 * @param rate 脉率
				 */
				public void addXieya(final Date date,final double max,final double min,final double rate)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.what=NET_UP;
									net.addXieya(user.getId(),date,max,min,rate);
								}
						};
					thread.start();
				}

				/**
				 * 获取血氧仪
				 * 从指定日期到指定日期的血氧数据 spo2 pr pi
				 * @param id
				 * @param start
				 * @param end
				 * @return 血氧仪信息，从指定日期到指定日期的血氧数据 spo2 pr pi
				 */
				public void getXieyang(final Date start,final Date end)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.what=NET_XIEYANG;
									net.getXieyang(user.getId(),start,end);
									handler.sendMessage(msg);
								}
						};
					thread.start();
				}

				/**
				 * 设置血氧仪
				 * 添加指定日期的一行数据
				 * @param id 用户id
				 * @param date 测试日期
				 * @param spo2 血氧饱和度
				 * @param pr 脉率
				 * @param pi 灌注指数
				 */
				public void addXieyang(final Date date,final double spo2,final double pr,final double pi)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.what=NET_XIEYANG;
									net.addXieyang(user.getId(),date,spo2,pr,pi);
								}
						};
					thread.start();
				}

				/**
				 * 获取血糖
				 * 从指定日期到指定日期
				 * @param id
				 * @param start
				 * @param end
				 * @return 血糖数据
				 */
				public void getXietang(final Date start, final Date end)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.what=NET_XIETANG;
									msg.obj=net.getXietang(user.getId(),start,end);
									handler.sendMessage(msg);
								}
						};
					thread.start();
				}

				/**
				 * 添加血糖信息
				 * @param id 用户id
				 * @param date  测试日期
				 * @param text  血糖浓度
				 */
				public void addXietang(final Date date, final double text)
				{
					thread = new Thread()
						{
							public void run()
								{
									Message msg=new Message();
									msg.what=NET_UP;
									net.addXietang(user.getId(),date,text);
								}
						};
					thread.start();
				}


			
	
	
	
	
}
