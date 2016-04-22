package com.asm.wenhejiankang;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.CheckBox;
/*

登录

*/
public class EnterActivity extends StartActivity
	{

		EditText edit_user,edit_password;
		CheckBox check_deaw_password,check_save_userAndPassword;
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				
				super.onCreate(savedInstanceState);
				
				//全屏
				this.requestWindowFeature(Window.FEATURE_NO_TITLE);
				this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
				//
				
			}

		@Override
		public void onContextView()
			{
				
				super.onContextView();
				setContentView(R.layout.enter);
			}
	
	
	   
	
}
