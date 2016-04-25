package com.asm.wenhejiankang;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.view.inputmethod.EditorInfo;
/*

登录

*/
public class EnterActivity extends Activity implements CompoundButton.OnCheckedChangeListener,OnClickListener
	{

		@Override
		public void onClick(View p1)
			{
				switch(p1.getId())
				{
					case R.id.btn_enter:
						start();
						break;
				}
			}
		

		
		@Override
		public void onCheckedChanged(CompoundButton p1, boolean p2)
			{
		   switch(p1.getId())
			 {
				 case R.id.check_draw_password:
					 if(p2==false)
					 {
				 
					 edit_password.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
					 
           }
				
					 else
					 {
					 edit_password.setInputType(  EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
					 }
					 break;
				 case R.id.check_save_userAndPassword:
					 isSavePassWord=p2;
					 break;
			 }
			}
		

		EditText edit_user,edit_password;
		CheckBox check_draw_password,check_save_userAndPassword;
		Button btn_enter;
		XlApplication application;
		
		//是否保存密码
		boolean isSavePassWord;
		static final String string_isSavePassWord="isSavePassWord",
		string_passWord="password",
		string_user="user"
		;
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				
				super.onCreate(savedInstanceState);
				
				//全屏
				this.requestWindowFeature(Window.FEATURE_NO_TITLE);
				this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
				onContextView();
				//
				
			}

		@Override
		public void onContextView()
			{
				
				//super.onContextView();
				setContentView(R.layout.enter);
				edit_user = (EditText)findViewById(R.id.edit_user);
				edit_password = (EditText)findViewById(R.id.edit_password);
				
				check_draw_password = (CheckBox)findViewById(R.id.check_draw_password);
				check_save_userAndPassword = (CheckBox)findViewById(R.id.check_save_userAndPassword);
				btn_enter = (Button)findViewById(R.id.btn_enter);
				btn_enter.setOnClickListener(this);
				
				check_draw_password.setOnCheckedChangeListener(this);
				application = (XlApplication)getApplication();
				isSavePassWord = application.readBoolean(this,string_isSavePassWord,false);
				
				String user= application.readText(this,string_user,null);
				String password = application.readText(this,string_passWord,null);
				if(user!=null)
				{
					edit_user.setText(user);
				}
				if(password!=null)
				{
					edit_password.setText(password);
				}
				isSavePassWord = application.readBoolean(this,string_isSavePassWord,false);
			}
			
			public void start()
			{
				if(isSavePassWord)
				{
					application.saveText(this,string_user,edit_user.getText().toString());
					application.saveText(this,string_passWord, edit_password.getText().toString());
					
				}
				application.saveBoolean(this,string_isSavePassWord,isSavePassWord);
				Intent intent = new Intent(this,MainActivity.class);
				startActivity(intent);
				finish();
			}
	
	    
	  
	
}
