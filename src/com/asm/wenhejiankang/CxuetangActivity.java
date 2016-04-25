package com.asm.wenhejiankang;
import android.app.Activity;
import android.widget.LinearLayout;
import android.view.View;
import android.os.Bundle;
/*
查血糖
搜索设备并显示设备

*/
public class CxuetangActivity extends StartActivity
	{
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO: Implement this method
				super.onCreate(savedInstanceState);
				setTitle(R.string.xuetangyi);

			}
		
LinearLayout layout_search;
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.facility_xuetangyi);
				layout_search=(LinearLayout)findViewById(R.id.layout_search);
				
			}
	
	
	
	     //开始搜索
			 void searchStart()
			 {
				 layout_search.setVisibility(View.VISIBLE);
			 }
			 
			 //关闭搜索
	     void searchClose()
			 {
				 layout_search.setVisibility(View.GONE);
			 }
	
	
	
	
	
	
}
