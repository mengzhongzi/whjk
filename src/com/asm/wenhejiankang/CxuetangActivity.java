package com.asm.wenhejiankang;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;
import com.asm.wenhejiankang.bluetooth.OnGlycemicIndexDataChangedListener;
import android.content.Intent;
import com.asm.wenhejiankang.net.Net_whjk_Listener;
import com.asm.wenhejiankang.model.User;
import com.asm.wenhejiankang.net.Net_whjk;
/*
查血糖
搜索设备并显示设备

*/
public class CxuetangActivity extends StartActivity implements OnClickListener, OnGlycemicIndexDataChangedListener,Net_whjk_Listener
	{

		@Override
		public void onUp()
			{
				// TODO: Implement this method
			}


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
				// TODO: Implement this method
			}

		@Override
		public void onXieyaError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXietang(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXietangError()
			{
				Toast.makeText(this,R.string.up_error,0).show();
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
		public void onDataChanged(float gi)
			{
				// TODO: Implement this method
			}


		@Override
		public void onClick(View p1)
			{
				// 
				switch(p1.getId())
				{
					case R.id.item_img:
						
						break;
					case R.id.facility_search:
						searchStart();
						break;
					case R.id.facility_empty:
						adapter.clear();
						adapter.notifyDataSetChanged();
					default:
						p1.setVisibility(4);
						Xietang_item item=list.get(p1.getId());
						net.addXietang(item.date,item.num);
				}
			}
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO: Implement this method
				super.onCreate(savedInstanceState);
				setTitle(R.string.xuetangyi);

			}
		
LinearLayout layout_search;
Button btn_search,btn_empty;
ListView listview;
MyAdapter adapter;
		XlApplication application;
		ArrayList<Xietang_item> list;
		//数据链接
		Net_whjk net;

		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.facility_xuetangyi);
				layout_search=(LinearLayout)findViewById(R.id.layout_search);
				listview = (ListView)findViewById(R.id.facility_listview);
				btn_search = (Button)findViewById(R.id.facility_search);
				btn_empty = (Button)findViewById(R.id.facility_empty);
		
				btn_search.setOnClickListener(this);
				btn_empty.setOnClickListener(this);
				application = (XlApplication)getApplication();
				net=application.getNetContext();
				net.setListener(this);
				list=new ArrayList<Xietang_item>();
				adapter = new MyAdapter(this);
				listview.setAdapter(adapter);
				
				debug_add(new Date(),30);
			}
	
	
			void debug_add(Date date,double num)
			{
				adapter.add(""+date.getHours()+":"+date.getMinutes(),""+num);
				adapter.notifyDataSetChanged();
				Xietang_item item=new Xietang_item(date,num);
				list.add(item);
			}
			
	
	     //开始搜索
			 void searchStart()
			 {
				 //layout_search.setVisibility(View.VISIBLE);
				 searchDevice();
			 }
		//进入设备搜索界面
		void searchDevice()
			{
				Intent intent = new Intent(this, SearchActivity.class);
				startActivityForResult(intent,REQUEST_EX);
			}
			 //关闭搜索
	     void searchClose()
			 {
				 layout_search.setVisibility(View.GONE);
			 }
	
	class Xietang_item
	{
		public Date date;
		public double num;
		public Xietang_item(Date date,double num)
		{
			this.date=date;
			this.num=num;
		}
	}
	
		public class MyAdapter extends BaseAdapter
			{


				public final class ViewHolder
					{

						public TextView text1; //列表项文字
						public TextView text2;
						public TextView text3;
						public ImageButton img;
					}




				private List<Map<String, Object>> mData;
				XlApplication application;
				private LayoutInflater mInflater;

				public MyAdapter(Context context)
					{
						this.mInflater = LayoutInflater.from(context);
						application = (XlApplication)((Activity)context).getApplication();
						this.mData=new ArrayList<Map<String, Object>>();
					}



				@Override
				public void onClick(View p1)
					{
						mData.remove(	p1.getId());

						notifyDataSetChanged();

					}
				public void add(String text2,String text3)
					{
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("text1", ""+(mData.size()+1));
						map.put("text2", text2);
						map.put("text3", text3);
						mData.add(map);
					}
				public void add(String text1,String text2,String text3)
					{
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("text1", text1);
						map.put("text2", text2);
						map.put("text3", text3);
						mData.add(map);
					}
				public void add(int pos,String text1,String text2,String text3)
					{
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("text1", text1);
						map.put("text2", text2);
						map.put("text3", text3);
						mData.add(pos,map);
					}
				public void remove(int pos)
					{
						mData.remove(pos);
					}

				public void clear()
					{
						mData.clear();
					}



				public int getCount()
					{
						return mData.size();
					}

				public Object getItem(int arg0)
					{
						return mData.get(arg0);
					}

				public String getTitle(int pos)
					{
						return (String)mData.get(pos).get("title");
					}

				public long getItemId(int arg0)
					{
						return arg0;
					}
				//显示按钮
				public void isVisibility()
					{

					}

				public List<Map<String, Object>>getData()
					{
						return mData;
					}


				public View getView(int position, View convertView, ViewGroup parent)
					{
						ViewHolder holder = null;
						if (convertView == null)
							{
								holder = new ViewHolder();
								convertView = mInflater.inflate(R.layout.item_3, null);
								holder.text1 = (TextView) convertView.findViewById(R.id.text1);
								holder.text2 = (TextView) convertView.findViewById(R.id.text2);
								holder.text3 = (TextView) convertView.findViewById(R.id.text3);
								holder.img =      (ImageButton)     convertView.findViewById(R.id.item_img);

								convertView.setTag(holder);
							}
						else
							{
								holder = (ViewHolder) convertView.getTag();
							}




						holder.text1.setText((String) mData.get(position).get("text1"));
						holder.text2.setText((String) mData.get(position).get("text2"));
						holder.text3.setText((String) mData.get(position).get("text3"));
						holder.img.setId(position);
						holder.img.setOnClickListener(CxuetangActivity.this);
						return convertView;
					}
			}
	
	
}
