package com.asm.wenhejiankang;
import android.widget.LinearLayout;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.ListView;
import com.xl.view.MyAdapter5;
import com.xl.view.MyAdapter6;
/*
血氧计界面
风的影子

*/
public class CxueyangActivity extends StartActivity implements OnClickListener
	{

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
						break;
					case R.id.btn_empty: //清空
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
		ListView listview;
		MyAdapter6 adapter;
		
		
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
				listview = (ListView)findViewById(R.id.listview5);
				adapter = new MyAdapter6(this);
				listview.setAdapter(adapter);
				btn_search.setOnClickListener(this);
				btn_empty.setOnClickListener(this);
				btn_collect.setOnClickListener(this);
				debug_add();
			}
			
		//测试添加项目
		void debug_add()
		{
			adapter.add("1","13:13", "99%", "80", "3.9%");
			adapter.notifyDataSetChanged();
		}

    //进入设备搜索界面
		void searchDevice()
		{
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
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
