package com.asm.wenhejiankang;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import com.xl.view.MyAdapter6;
import com.asm.wenhejiankang.bluetooth.JKBluetoothManager;
import com.asm.wenhejiankang.bluetooth.OnBloudOxygenDataChangedListener;
import com.xl.game.tool.Log;
import com.asm.wenhejiankang.net.Net_whjk;
import com.asm.wenhejiankang.net.Net_whjk_Listener;
import com.asm.wenhejiankang.model.User;
/*
血氧计界面
连接血氧仪
风的影子

*/
public class CxueyangActivity extends StartActivity implements OnClickListener, OnBloudOxygenDataChangedListener,Net_whjk_Listener
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
				// TODO: Implement this method
			}

		@Override
		public void onXieyang(ArrayList<String> list)
			{
				
			}

		@Override
		public void onXieyangError()
			{
				Toast.makeText(this,R.string.up_error,0).show();
			}

		@Override
		public void onUpError()
			{
				// TODO: Implement this method
			}

		public static String TAG = "CxueyangActivity";
		@Override
		public void onDataChanged(byte[] ls, float saturability, int rate, float vqi)
			{
				//
				text_oxyhemoglobin_saturation.setText(""+saturability);
				text_pulse_rate.setText(""+rate);
				text_perfusion_index.setText(""+vqi);
				Log.e(TAG,"血氧仪回调："+saturability+" "+rate+" "+vqi);
			}


		@Override
		public void onClick(View view)
			{
				Intent intent ;
				// 
				switch(view.getId())
				{
					case R.id.btn_search: //搜索
					intent = new Intent(this,SearchActivity.class);
					startActivity(intent);
						break;
					case R.id.btn_collect: //采集
						try
							{
								debug_add(new Date(),Float.parseFloat( text_oxyhemoglobin_saturation.getText().toString()),Float.parseFloat( text_pulse_rate.getText().toString()), Float.parseFloat( text_perfusion_index.getText().toString()));
							}
						catch (NumberFormatException e) {}
						break;
					case R.id.btn_empty: //清空
					  adapter.clear();
						adapter.notifyDataSetChanged();
						break;
					case R.id.item_img:
						view.setVisibility(4);
					break;
					default:
					Xieyang_item item=list.get(view.getId());
					net.addXieyang(item.date,item.spo2,item.pr,item.pi);
					break;
				}
			}
		

		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO: Implement this method
				super.onCreate(savedInstanceState);
				setTitle(R.string.xueyangyi);
			}
	
	
		LinearLayout layout_search;
		Button btn_search,btn_collect,btn_empty;
		TextView text_oxyhemoglobin_saturation, text_pulse_rate, text_perfusion_index;
		ListView listview;
		MyAdapter adapter;
		XlApplication application;
		//蓝牙数据管理器
		JKBluetoothManager manager;
		//数据链接
		Net_whjk net;
		ArrayList<Xieyang_item> list;
		
		class XueYang
		{
			Date time;
			String spo2;
			String pr;
			String pi;
		}
		
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.facility_xueyangyi);
				layout_search=(LinearLayout)findViewById(R.id.layout_search);
        btn_search = (Button)findViewById(R.id.btn_search);
				btn_collect = (Button)findViewById(R.id.btn_collect);
				btn_empty = (Button)findViewById(R.id.btn_empty);
				listview = (ListView)findViewById(R.id.facility_listview);
				adapter = new MyAdapter(this);
				listview.setAdapter(adapter);
				btn_search.setOnClickListener(this);
				btn_empty.setOnClickListener(this);
				btn_collect.setOnClickListener(this);
				text_oxyhemoglobin_saturation = (TextView)findViewById(R.id.oxyhemoglobin_saturation);
				text_pulse_rate = (TextView)findViewById(R.id.pulse_rate);
				text_perfusion_index = (TextView)findViewById(R.id.perfusion_index);
				application =(XlApplication)getApplication();
				net = application.getNetContext();
				net.setListener(this);
				list=new ArrayList<Xieyang_item>();
				
				//debug_add(new Date(),30,30,30);
			}
			
		//测试添加项目
		void debug_add(Date date,double spo2,double pr,double pi)
		{
			adapter.add(""+date.getHours()+":"+date.getMinutes(), ""+spo2+"%", ""+pr, ""+pi+"%");
			adapter.notifyDataSetChanged();
			Xieyang_item item=new Xieyang_item(date,spo2,pr,pi);
			
			list.add(item);
		}
		
		

		void openfilelist()
			{
				//传送
				Intent intent = new Intent();
				startActivityForResult(intent, REQUEST_EX);
				//overridePendingTransition(R.anim.fade, R.anim.hold);
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
									manager.setOnBloudOxygenDataChangedListener(this);
									break;
							}
					}
				

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
				layout_search.setVisibility(View.VISIBLE);
			}

		//关闭搜索
		void searchClose()
			{
				layout_search.setVisibility(View.GONE);
			}
			
			public class Xieyang_item
			{
				public Date date;
				public double spo2;
				public double pr;
				public double pi;
				public Xieyang_item(Date date,double spo2,double pr,double pi)
				{
					this.date=date;
					this.spo2=spo2;
					this.pr=pr;
					this.pi=pi;
				}
			}
			
			
		
		public class MyAdapter extends BaseAdapter
			{


				public final class ViewHolder
					{

						public TextView text1; //列表项文字
						public TextView text2;
						public TextView text3;
						public TextView text4;
						public TextView text5;
						public ImageButton img;
					}




				private List<Map<String, Object>> mData;
				XlApplication application;
				private LayoutInflater mInflater;
				ArrayList<Xieyang_item> list;

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
				public void add(String text2,String text3,String text4,String text5)
					{
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("text1", ""+(mData.size()+1));
						map.put("text2", text2);
						map.put("text3", text3);
						map.put("text4", text4);
						map.put("text5", text5);
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
								convertView = mInflater.inflate(R.layout.item_5, null);
								holder.text1 = (TextView) convertView.findViewById(R.id.text1);
								holder.text2 = (TextView) convertView.findViewById(R.id.text2);
								holder.text3 = (TextView) convertView.findViewById(R.id.text3);
								holder.text4 = (TextView) convertView.findViewById(R.id.text4);
								holder.text5 = (TextView) convertView.findViewById(R.id.text5);
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
						holder.text4.setText((String)mData.get(position).get("text4"));
						holder.text5.setText((String)mData.get(position).get("text5"));
						holder.img.setOnClickListener(CxueyangActivity.this);
						return convertView;
					}
			}
	
}
