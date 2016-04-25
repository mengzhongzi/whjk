package com.asm.wenhejiankang;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.xl.view.CheckButton;
import android.view.View.OnClickListener;
import android.graphics.drawable.Drawable;
import android.content.Intent;

public class MainActivity extends StartActivity implements OnClickListener
	{
   Intent intent;
		@Override
		public void onClick(View view)
			{
				// 切换选项卡
				switch(view.getId())
				{
					case R.id.tab_my:
						setTab(2);
						break;
					case R.id.tab_record:
					setTab(1);
					break;
					case R.id.tab_test:
						setTab(0);
						break;
					case R.id.jilu_wenduji:
						intent = new Intent(this,WendujiActivity.class);
						startActivity(intent);
						break;
					case R.id.jilu_xuetangyi:
						intent = new Intent(this,XuetangyiActivity.class);
						startActivity(intent);
						break;
					case R.id.jilu_xueyangyi:
						intent= new Intent(this,XueyangyiActivity.class);
						startActivity(intent);
						break;
					case R.id.jilu_xueyayi:
						intent = new Intent(this,XueyayiActivity.class);
						startActivity(intent);
						
						break;
            //第一页 测量
					case R.id.celiang_xuetang:
            intent = new Intent(this, CxuetangActivity.class);
            startActivity(intent);
						break;
					case R.id.celiang_xueyang:
            intent = new Intent(this,CxueyangActivity.class);
            startActivity(intent);
						break;
					case R.id.celiang_xueya:
            intent = new Intent(this,CxueyaActivity.class);
            startActivity(intent);
						break;
					case R.id.celiang_tiwen:
            intent = new Intent(this,CtiwenActivity.class);
            startActivity(intent);
            break;
						
            //第三页 个人
					case R.id.item_geren:
						intent = new Intent(this, DetailsActivity.class);
						startActivity(intent);
						break;
					case R.id.item_myrefer:
						break;
					case R.id.item_sendtype:
						break;
					case R.id.item_about:
						
						break;
				}
			}
		
	FrameLayout framelayout;
	CheckButton tab_btn[];
	//测量
	Button btn_celiang_xuetang,btn_celiang_xueya,btn_celiang_xueyang,btn_celiang_tiwen;
	//记录
	Button btn_jilu_wenduji,btn_jilu_xuetangyi,btn_jilu_xueyayi,btn_jilu_xueyangyi;
	//个人
	Button btn_geren,btn_myrefer,btn_sendtype,btn_about;
	
	//当前选项
	int tab_index;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
				setTitle(R.string.app_name);
    }
		
		public void onContentView()
		{
			
			setContentView(R.layout.main);
			setBackVisibility(View.GONE);
			framelayout = (FrameLayout)findViewById(R.id.frame_tab);
			tab_btn = new CheckButton[3];
			tab_btn[0] = (CheckButton)findViewById(R.id.tab_test);
			tab_btn[1] = (CheckButton)findViewById(R.id.tab_record);
			tab_btn[2] = (CheckButton)findViewById(R.id.tab_my);
			//记录
			btn_jilu_wenduji = (Button)findViewById(R.id.jilu_wenduji);
			btn_jilu_xueyayi = (Button)findViewById(R.id.jilu_xueyayi);
			btn_jilu_xueyangyi = (Button)findViewById(R.id.jilu_xueyangyi);
			btn_jilu_xuetangyi = (Button)findViewById(R.id.jilu_xuetangyi);
			btn_jilu_wenduji.setOnClickListener(this);
			btn_jilu_xueyayi.setOnClickListener(this);
			btn_jilu_xueyangyi.setOnClickListener(this);
			btn_jilu_xuetangyi.setOnClickListener(this);
			
			//测量
			btn_celiang_xuetang = (Button)findViewById(R.id.celiang_xuetang);
			btn_celiang_xueyang =(Button)findViewById(R.id.celiang_xueyang);
			btn_celiang_xueya = (Button)findViewById(R.id.celiang_xueya);
			btn_celiang_tiwen = (Button)findViewById(R.id.celiang_tiwen);
			
			btn_celiang_xuetang.setOnClickListener(this);
			btn_celiang_xueyang.setOnClickListener(this);
			btn_celiang_xueya.setOnClickListener(this);
			btn_celiang_tiwen.setOnClickListener(this);
			
			//个人
			btn_geren = (Button)findViewById(R.id.item_geren);
			btn_myrefer=(Button)findViewById(R.id.item_myrefer);
			btn_sendtype=(Button)findViewById(R.id.item_sendtype);
			btn_about = (Button)findViewById(R.id.item_about);
			btn_geren.setOnClickListener(this);
			btn_myrefer.setOnClickListener(this);
			btn_sendtype.setOnClickListener(this);
			btn_about.setOnClickListener(this);
			
			tab_btn[0].setOnClickListener(this);
			tab_btn[1].setOnClickListener(this);
			tab_btn[2].setOnClickListener(this);
			Drawable drawable = getResources().getDrawable(R.drawable.tab_check);
			tab_btn[0].setCheckDrawable(drawable);
			tab_btn[1].setCheckDrawable(drawable);
			tab_btn[2].setCheckDrawable(drawable);
			tab_index=0;
			setTab(tab_index);
		}
		
		//切换tab选项卡
		void setTab(int id)
		{
			//设置tab
			for(int n=0;n<3;n++)
			{
				if(n==id)
				{
					tab_btn[n].setChecked(true);
				}
				else
				{
					tab_btn[n].setChecked(false);
				}
			}
			//
			int len=framelayout.getChildCount();
			for(int i=0;i<len;i++)
			{
				View view = framelayout.getChildAt(i);
				if(i!=id)
				{
					view.setVisibility(View.GONE);
				}
				else
				{
					view.setVisibility(View.VISIBLE);
				}
			}
			
		}
		
		
		//按两次返回键退出
    long time;

    @Override
    public void onBackPressed()
      {
        if((System.currentTimeMillis()-time)>3000)
        {
          time=System.currentTimeMillis();
          Toast.makeText(this,R.string.exit_hint,Toast.LENGTH_SHORT).show();
        }
        else
        {
         time=0;
        super.onBackPressed();
        }
      }
    
		
}
