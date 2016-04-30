package com.asm.wenhejiankang;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.content.Intent;
import com.asm.wenhejiankang.net.Net_whjk_Listener;
import com.asm.wenhejiankang.model.User;
import com.asm.wenhejiankang.net.Net_whjk;
import com.asm.wenhejiankang.bluetooth.OnAnimalHeatDataChangedListener;
import com.asm.wenhejiankang.bluetooth.JKBluetoothManager;
/*

查体温
连接体温计
风的影子
*/

public class CtiwenActivity extends StartActivity implements OnClickListener,Net_whjk_Listener,OnAnimalHeatDataChangedListener
	{
		/*
		 * tp 温度
		 * temMetric 温度度量
		 * 		0 摄氏度 
		 * 		1华氏度
		 * temType 温度类型
		 * 		0人体温度
		 * 		1物体温度
		 * 		2环境温度
		 * state 状态
		 * 		0正常
		 * 		1测量温度过低
		 * 		2测量温度过高
		 * 		3环境温度过低
		 * 		4环境温度过高
		 * 		5EEPROM出错
		 * 		6传感器出错
		 */
		@Override
		public void onDataChanged(float tp, int temMetric, int temType, int state)
			{
				//
				switch(temMetric)
				{
					case 0:
						text.setText(""+tp);
						break;
					case 1:
						
				}
			}
		

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
				
			}

		@Override
		public void onTiwenError()
			{
				Toast.makeText(this,R.string.up_error,0).show();
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


		
		
		
		@Override
		public void onClick(View p1)
			{
				Intent intent;
				// TODO: Implement this method
				switch(p1.getId())
				{
					case R.id.item_img:
						
						break;
					case R.id.btn_search:
						intent = new Intent(this,SearchActivity.class);
						startActivity(intent);
						break;
					case R.id.btn_empty:
						adapter.clear();
						adapter.notifyDataSetChanged();
						break;
					case R.id.btn_collect:
						try
							{
								debug_add(new Date(),Float.parseFloat(text.getText().toString()));
							}
						catch (NumberFormatException e) {}
						break;
					default:
						p1.setVisibility(4);
						Tiwen_item item=list.get(p1.getId());
						net.addTiwen(item.date,""+item.num);
				}
			}
		
	
		LinearLayout layout_search;
    ListView listview;
		MyAdapter adapter;
		Button btn_search,btn_empty,btn_coll;
		TextView text;
		XlApplication application;
		//数据链接
		Net_whjk net;
		//蓝牙数据管理器
		JKBluetoothManager manager;
		ArrayList<Tiwen_item> list;
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO: Implement this method
				super.onCreate(savedInstanceState);
				setTitle(R.string.tiwenji);
				
			}
		
		
		
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.facility_tiwenji);
				layout_search=(LinearLayout)findViewById(R.id.layout_search);
				
				listview = (ListView) findViewById(R.id.facility_listview);
				btn_search=(Button)findViewById(R.id.btn_search);
				btn_empty=(Button)findViewById(R.id.btn_empty);
				btn_coll=(Button)findViewById(R.id.btn_collect);
				btn_search.setOnClickListener(this);
				btn_empty.setOnClickListener(this);
				btn_coll.setOnClickListener(this);
				text = (TextView)findViewById(R.id.tab_temperature);
				application = (XlApplication)getApplication();
				net = application.getNetContext();
				if(net==null)finish();
				net.setListener(this);
				
				list = new ArrayList<Tiwen_item>();
				adapter = new MyAdapter(this);
				
				listview.setAdapter(adapter);
				debug_add(new Date(),37.0);
        
			}

   //添加一条信息
	 void debug_add(Date date,double num)
	 {
		 Tiwen_item item = new Tiwen_item(date,num);
		 list.add(item);
		 adapter.add(""+date.getHours()+":"+date.getMinutes(),""+num);
		 adapter.notifyDataSetChanged();
	 }

		//开始搜索
		void searchStart()
			{
				Intent intent = new Intent(this, SearchActivity.class);
				startActivityForResult(intent,REQUEST_EX);
			}

		//关闭搜索
		void searchClose()
			{
				layout_search.setVisibility(View.GONE);
			}
			
		int REQUEST_EX =100;
			
		//搜索界面返回
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
				// TODO: Implement this method
				super.onActivityResult(requestCode, resultCode, intent);
				if(requestCode==REQUEST_EX)
					{
						switch(resultCode)
							{
								case RESULT_OK:
									manager = application.getBlueManager();
									manager.setOnAnimalHeatDataChangedListener(this);
									break;
							}
					}


			}
	
			public class Tiwen_item
			{
				public Date date;
				public double num;
				public Tiwen_item(Date date,double num)
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
						holder.img.setOnClickListener(CtiwenActivity.this);
						holder.img.setId(position);
						return convertView;
					}
			}
}
