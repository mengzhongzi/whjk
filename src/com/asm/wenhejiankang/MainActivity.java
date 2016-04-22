package com.asm.wenhejiankang;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.xl.view.CheckButton;
import android.view.View.OnClickListener;
import android.graphics.drawable.Drawable;

public class MainActivity extends StartActivity implements OnClickListener
	{

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
				}
			}
		
	FrameLayout framelayout;
	CheckButton tab_btn[];
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
		
		public void onContextView()
		{
			
			setContentView(R.layout.main);
			framelayout = (FrameLayout)findViewById(R.id.frame_tab);
			tab_btn = new CheckButton[3];
			tab_btn[0] = (CheckButton)findViewById(R.id.tab_test);
			tab_btn[1] = (CheckButton)findViewById(R.id.tab_record);
			tab_btn[2] = (CheckButton)findViewById(R.id.tab_my);
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
		
		
		
		
}
