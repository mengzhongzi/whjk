package com.asm.wenhejiankang;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;


public class LoadActivity extends Activity implements Runnable
{

	@Override
	public void run()
	{
		start();
		// TODO: Implement this method
	}





	@Override
	

  
  FrameLayout layout;
  String logo;
  Handler handler;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//全屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
															WindowManager.LayoutParams.FLAG_FULLSCREEN);

		handler= new Handler();
		setContentView(R.layout.logo);
		
		
		
		

		

		
		handler.postDelayed(this,3000);
				



		

	}


	void start()
	{
		Intent intent = new Intent(this,EnterActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		//super.onBackPressed();
	}

    boolean isNet()
	{
	ConnectivityManager cwjManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo info = cwjManager.getActiveNetworkInfo();
	if (info != null && info.isAvailable())
		{
			//do nothing
			return true;
		}
	else
		{
			//Toast.makeText(this,"无互联网连接",Toast.LENGTH_SHORT).show();
		}
	return false;
	}
}
