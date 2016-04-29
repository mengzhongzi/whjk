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
import com.xl.game.tool.Log;
import com.asm.wenhejiankang.net.NetListener;
import com.asm.wenhejiankang.net.NetContext;
import com.asm.wenhejiankang.net.impl.NetContextImpl;
import com.asm.wenhejiankang.model.User;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.asm.wenhejiankang.net.impl.BaseDao;
import android.os.Handler;
import com.asm.wenhejiankang.net.Net_whjk_Listener;
import java.util.ArrayList;
import com.asm.wenhejiankang.net.Net_whjk;
/*

登录

*/
public class EnterActivity extends Activity implements CompoundButton.OnCheckedChangeListener,OnClickListener,Net_whjk_Listener
	{

		@Override
		public void onEnter(User user)
			{
				// TODO: Implement this method
				progress.setVisibility(8);
				application.setUser(user);
				login();
			}

		@Override
		public void onError(String text)
			{
				// TODO: Implement this method
				progress.setVisibility(8);
				Toast.makeText(this,R.string.enter_error,0).show();
				
			}

		@Override
		public void onTiWen(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieya(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXietang(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieyang(ArrayList<String> list)
			{
				// TODO: Implement this method
			}
		



		@Override
		public void onTiwenError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieyaError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXietangError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieyangError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onUpError()
			{
				// TODO: Implement this method
			}
		
public static final String TAG="EnterActivity";
		@Override
		public void onClick(View p1)
			{
				switch(p1.getId())
				{
					case R.id.btn_enter:
						start();
						break;
					case R.id.btn_cancel:
						onBackPressed();
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
		Button btn_enter,btn_cancel;
		ProgressBar progress;
		XlApplication application;
		//数据链接
		Net_whjk net;
		
		Handler handler;
		
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
				handler= new Handler();
				onContextView();
				
				//
				
				/*
        Thread thread=new Thread()
				{
public void run() 
     {
				BaseDao dao = new BaseDao();
				dao.getConnection();
				Log.e("info","连接数据库成功！\n\n");
			}
		
    };
		thread.start();
*/
//这样呢 会连接成功吗
			}

		@Override
		public void onContextView()
			{
				
				//super.onContextView();
				setContentView(R.layout.enter);
				edit_user = (EditText)findViewById(R.id.edit_user);
				edit_password = (EditText)findViewById(R.id.edit_password);
				
				check_draw_password = (CheckBox)findViewById(R.id.check_draw_password);
				check_save_userAndPassword = (CheckBox)findViewById(R.id.check_save_password);
				btn_enter = (Button)findViewById(R.id.btn_enter);
				btn_enter.setOnClickListener(this);
				btn_cancel = (Button)findViewById(R.id.btn_cancel);
				btn_cancel.setOnClickListener(this);
				progress = (ProgressBar)findViewById(R.id.enter_progress);
				
				check_draw_password.setOnCheckedChangeListener(this);
				application = (XlApplication)getApplication();
				net = new Net_whjk();
				 application.setNetContext(net);
				net.setListener(this);
				
				isSavePassWord = application.readBoolean(this,string_isSavePassWord,false);
				check_save_userAndPassword.setChecked(isSavePassWord);
				String user= application.readText(this,string_user,null);
				String password = application.readText(this,string_passWord,null);
				//boolean save_password= application.readBoolean(this,string_isSavePassWord,false);
				if(user!=null)
				{
					edit_user.setText(user);
				}
				if(password!=null && isSavePassWord)
				{
					edit_password.setText(password);
				}
				isSavePassWord = application.readBoolean(this,string_isSavePassWord,false);
			}
			
			public void start()
			{
					application.saveText(this,string_user,edit_user.getText().toString());
				if(check_save_userAndPassword.isChecked())
				{
					
					application.saveText(this,string_passWord, edit_password.getText().toString());
					Log.e(TAG,"");
				}
				application.saveBoolean(this,string_isSavePassWord,check_save_userAndPassword.isChecked());
				String name=edit_user.getText().toString();
				String password=edit_password.getText().toString();
				
				net.setListener(this);
				net.login(name,password);
				//User user=net.login(name,password);
					
			
				
				//application.setUser(user);
				progress.setVisibility(0);
		
			}
	
	    
	  void login()
		{
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	
}
