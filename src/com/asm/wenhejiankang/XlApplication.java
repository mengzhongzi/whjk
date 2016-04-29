package com.asm.wenhejiankang;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.asm.wenhejiankang.net.NetContext;
import com.asm.wenhejiankang.net.impl.NetContextImpl;
import com.asm.wenhejiankang.model.User;
import com.asm.wenhejiankang.bluetooth.JKBluetoothManager;
import com.asm.wenhejiankang.net.Net_whjk;

public class XlApplication extends Application
	{

		@Override
		public void onCreate()
			{
				
				super.onCreate();
				//net = new NetContextImpl();
			}
	
			//网络连接
		Net_whjk net;
		//蓝牙数据管理器
		JKBluetoothManager manager;
		User user;
		//保存数据
		public String saveText(Context context,String name,String value)
		{
			SharedPreferences sp=context.getSharedPreferences("data", Context.MODE_PRIVATE);

		
			SharedPreferences.Editor editor=sp.edit();	//修改Preferences文件
			
			editor.putString(name,value);
			editor.commit();	//提交修改  
			return value;
		}
		
		//读取数据
		public String readText(Context context,String name,String value)
		{
			SharedPreferences sp=context.getSharedPreferences("data", Context.MODE_PRIVATE);        
			

			//获取数据
			return sp.getString(name, value);
			
		}
		
		//保存数据
		public void saveBoolean(Context context,String name,boolean value)
			{
				SharedPreferences sp=context.getSharedPreferences("data", Context.MODE_PRIVATE);


				SharedPreferences.Editor editor=sp.edit();	//修改Preferences文件

				editor.putBoolean(name,value);
				editor.commit();	//提交修改  
				
			}

		//读取数据
		public boolean readBoolean(Context context,String name,boolean value)
			{
				SharedPreferences sp=context.getSharedPreferences("data", Context.MODE_PRIVATE);        


				//获取数据
				return sp.getBoolean(name, value);

			}
		
			public void setUser(User user)
			{
				this.user=user;
			}
			
			public User getUser()
			{
				return user;
			}
		
		public Net_whjk getNetContext()
		{
			return net;
		}
		
		public void setNetContext(Net_whjk net)
		{
			this.net=net;
		}
		
		public void setBluetoothManager(JKBluetoothManager manager)
		{
			this.manager=manager;
		}
		
		public JKBluetoothManager getBlueManager()
		{
			return manager;
		}
	
}
