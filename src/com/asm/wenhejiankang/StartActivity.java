package com.asm.wenhejiankang;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.view.View;
/*
activity父类
实现自定义标题栏


*/
public class StartActivity extends Activity
	{
  private TextView titleview;
	private ImageButton btn_back;
	private int back_visibility;
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题　
				//
				onContentView();
				getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);//自定义布局赋值
				//onContextView();
				initView();
			}
	
			
			void initView()
			{
					titleview = (TextView)findViewById(R.id.title);
					btn_back = (ImageButton)findViewById(R.id.title_back);
					if(btn_back!=null)
					btn_back.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View p1)
								{
									onBackPressed();
								}
							
						
					}
					);
					
					
					setBackVisibility(back_visibility);
			}
			
		
			
			
			
				
			public void onContentView()
			{
					
			}

			@Override
			public void setTitle(int titleId)
				{
					super.setTitle(titleId);
					titleview.setText(titleId);
				}
			
			

			@Override
			public void setTitle(CharSequence title)
				{
					// TODO: Implement this method
					super.setTitle(title);
					titleview.setText(title);
				}
			
			//隐藏返回按钮
			public void setBackVisibility(int type)
			{
				back_visibility = type;
				if(btn_back!=null)
				btn_back.setVisibility(type);
			}
			
}
