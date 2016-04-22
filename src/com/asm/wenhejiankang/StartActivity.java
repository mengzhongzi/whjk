package com.asm.wenhejiankang;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.ImageButton;

public class StartActivity extends Activity
	{
  private TextView titleview;
	private ImageButton btn_back;
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题　
				//
				onContextView();
				getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);//自定义布局赋值
				//onContextView();
				initView();
			}
	
			
			void initView()
			{
					titleview = (TextView)findViewById(R.id.title);
					btn_back = (ImageButton)findViewById(R.id.title_back);
					
			}
			
		
			
			
			
				
			public void onContextView()
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
				btn_back.setVisibility(type);
			}
			
}
