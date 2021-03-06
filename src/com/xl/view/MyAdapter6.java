package com.xl.view;


import android.app.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.graphics.drawable.*;
import com.asm.wenhejiankang.R;
import com.asm.wenhejiankang.XlApplication;

public class MyAdapter6 extends BaseAdapter
{
	
		
		public final class ViewHolder
			{
				
				public TextView text1; //列表项文字
				public TextView text2;
				public TextView text3;
				public TextView text4;
				public TextView text5;
			}

		
	
	
	private List<Map<String, Object>> mData;
  XlApplication application;
	private LayoutInflater mInflater;
	
	public MyAdapter6(Context context)
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
	/*
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
	*/
	
		public void add(String text1,String text2,String text3,String text4,String text5)
			{
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("text1", text1);
				map.put("text2", text2);
				map.put("text3", text3);
				map.put("text4", text4);
				map.put("text5", text5);
				mData.add(map);
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
			convertView = mInflater.inflate(R.layout.listitem_6, null);
			holder.text1 = (TextView) convertView.findViewById(R.id.text1);
			holder.text2 = (TextView) convertView.findViewById(R.id.text2);
			holder.text3 = (TextView) convertView.findViewById(R.id.text3);
			holder.text4 = (TextView) convertView.findViewById(R.id.text4);
			holder.text5 = (TextView) convertView.findViewById(R.id.text5);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
    
		
		
		
		holder.text1.setText((String) mData.get(position).get("text1"));
		holder.text2.setText((String) mData.get(position).get("text2"));
		holder.text3.setText((String) mData.get(position).get("text3"));
		holder.text4.setText((String) mData.get(position).get("text4"));
		holder.text5.setText((String) mData.get(position).get("text5"));
		
		return convertView;
	}
}
