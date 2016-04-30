package com.asm.wenhejiankang;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.asm.wenhejiankang.bluetooth.JKBluetoothManager;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import com.asm.wenhejiankang.bluetooth.OnStateChangedListener;
import java.util.List;
import android.widget.Adapter;
import com.xl.view.ListAdapter;
import android.view.View;
import com.xl.game.tool.Log;
import android.widget.AdapterView;
import com.asm.wenhejiankang.bluetooth.OnBloudOxygenDataChangedListener;
/*

设备搜索界面
风的影子
*/
public class SearchActivity extends StartActivity implements OnStateChangedListener , AdapterView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> p1, View p2, int pos, long p4)
			{
				// TODO: Implement this method
				manager.connect(list_dev.get(pos));
				Log.e(TAG,"连接设备 "+list_dev.get(pos).getName());
				setResult(RESULT_OK);

				finish();
			}
		
		public static final String TAG="SearchActivity";
		@Override
		public void onStateChanged(List<BluetoothDevice> devices, int s)
			{
				Log.e(TAG, "搜索到一个设备");
				if(devices!=null)
				{
				adapter.clear();
				list_dev = devices;
				for(BluetoothDevice d:devices)
				adapter.add(d.getName(),d.getAddress());
				adapter.notifyDataSetChanged();
				setProgressVisiblity(View.GONE);
				}
			}
			

		//
		JKBluetoothManager manager;
		//控制进度条的显示和隐藏
		LinearLayout layout_progress;
		ListView listview;
    ListAdapter adapter;
		List<BluetoothDevice>  list_dev;
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO: Implement this method
				super.onCreate(savedInstanceState);
			}

			
			
		
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.search);
				layout_progress = (LinearLayout) findViewById(R.id.search_progress);
				listview = (ListView)findViewById(R.id.searchListView);
				listview.setOnItemClickListener(this);
				adapter = new ListAdapter(this);
				listview.setAdapter(adapter);
				manager = new JKBluetoothManager(this);
				XlApplication application=(XlApplication)getApplication();
				application.setBluetoothManager(manager);
			}
			
		//
			
			
		//隐藏搜索进度条
		void setProgressVisiblity(int type)
		{
			layout_progress.setVisibility(type);
		}
	
		/*蓝牙状态值
		 * STATE_CLOSED 关闭的
		 * STATE_OPENFAIL 打开失败，临时状态
		 * STATE_OPENED 打开的
		 * STATE_DISCOVERYING 正在寻找设备
		 * STATE_FOUND 找到设备，临时状态
		 * STATE_DISCOVERIED 寻找结束
		 * STATE_CONNECTING 正在连接
		 * STATE_CONNECTED 连接成功
		 * STATE_CONNECTEDFAIL 连接失败，临时状态
		 * STATE_DISCONNECTED 中断连接，临时状态
		 */
		public static final int STATE_CLOSED = 0;
		public static final int STATE_OPENFAIL = 1;
		public static final int STATE_OPENED = 2;
		public static final int STATE_DISCOVERYING = 3;
		public static final int STATE_FOUND = 4;
		public static final int STATE_DISCOVERIED = 5;
		public static final int STATE_CONNECTING = 6;
		public static final int STATE_CONNECTED = 7;
		public static final int STATE_CONNECTEDFAIL = 8;
		public static final int STATE_DISCONNECTED=9;
		/*
		血氧仪监听
		
		*/
		public class OnBloud implements OnBloudOxygenDataChangedListener
			{
				public void onDataChanged(byte[] ls, float saturability, int rate, float vqi) 
				{
          
				}
				

			}
		
		
}
