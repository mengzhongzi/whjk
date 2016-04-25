package com.asm.wenhejiankang;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class XlApplication extends Application
	{

		@Override
		public void onCreate()
			{
				
				super.onCreate();
			}
	
		
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
	
}
