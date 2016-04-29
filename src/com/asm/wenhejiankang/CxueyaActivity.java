package com.asm.wenhejiankang;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;
import com.asm.wenhejiankang.bluetooth.OnBloudPressureDataChangedListener;
import android.content.Intent;
import com.asm.wenhejiankang.net.Net_whjk_Listener;
import com.asm.wenhejiankang.model.User;
import com.asm.wenhejiankang.net.Net_whjk;
import com.xl.view.ItemAdapter;
/*
查血压
搜索连接血压仪

*/
public class CxueyaActivity extends StartActivity implements OnClickListener, OnBloudPressureDataChangedListener,Net_whjk_Listener
	{

		@Override
		public void onEnter(User user)
			{
				// TODO: Implement this method
			}

		@Override
		public void onError(String text)
			{
				// TODO: Implement this method
			}

		@Override
		public void onTiWen(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onTiwenError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieya(ArrayList<String> list)
			{
				for(String text:list)
        adapter.add(text);
			}

		@Override
		public void onXieyaError()
			{
				Toast.makeText(this,R.string.up_error,0).show();
			}

		@Override
		public void onXietang(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXietangError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieyang(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieyangError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onUpError()
			{
				// TODO: Implement this method
			}


		private static final int REQUEST_EX = 100;

		@Override
		public void onDataChanged(int highest, int lowest, int rate)
			{
				// TODO: Implement this method
			}


		@Override
		public void onClick(View p1)
			{
				// 
				switch(p1.getId())
				{
					
					case R.id.facility_search:
						searchDevice();
						break;
					case R.id.facility_empty:
						adapter.clear();
						adapter.notifyDataSetChanged();
						break;
					default:
						p1.setVisibility(4);
						//上传
						Xieya_item item=list.get(p1.getId());
						net.addXieya(item.date,item.max,item.min,item.rate);
				}
			}
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO: Implement this method
				super.onCreate(savedInstanceState);
				setTitle(R.string.xueyayi);

			}
			
		LinearLayout layout_search;
		Button btn_search,btn_empty;
		ListView listview;
		ItemAdapter adapter;
		XlApplication application;
		//数据链接
		Net_whjk net;
		
		ArrayList<Xieya_item> list;
		
		
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.facility_xueyayi);
				layout_search=(LinearLayout)findViewById(R.id.layout_search);
        listview = (ListView)findViewById(R.id.facility_listview);
				btn_search = (Button)findViewById(R.id.facility_search);
				btn_empty =(Button)findViewById(R.id.facility_empty);
			btn_search.setOnClickListener(this);
			btn_empty.setOnClickListener(this);
				application = (XlApplication)getApplication();
				net=application.getNetContext();
        net.setListener(this);
        
        
        
			list=new ArrayList<Xieya_item>();
			
				adapter = new ItemAdapter(this);
				listview.setAdapter(adapter);
				debug_add(new Date(),120,80,80);
			}

  //测试
	void debug_add(Date date,double max,double min,double rate)
	{
		adapter.add(""+date.getHours()+":"+date.getMinutes()+" "+""+max+" "+min+" "+rate);
		Xieya_item item=new Xieya_item(date,max,min,rate);
		list.add(item);
		adapter.notifyDataSetChanged();
	} 

  //进入设备搜索界面
void searchDevice()
	{
		Intent intent = new Intent(this, SearchActivity.class);
		startActivityForResult(intent,REQUEST_EX);
	}

		//开始搜索
		void searchStart()
			{
				//layout_search.setVisibility(View.VISIBLE);
				searchDevice();
			}

		//关闭搜索
		void searchClose()
			{
				layout_search.setVisibility(View.GONE);
			}
			
			class Xieya_item
			{
				public Date date;
				
				public double max;
				public double min;
				public double rate;
				public Xieya_item(Date date,double max,double min,double rate)
				{
					this.date=date;
					this.max=max;
					this.min=min;
					this.rate=rate;
				}
				
				public Date getDate()
				{
					return date;
				}
				
				
				
				}
			
				
			
				
		
		
	
} 
