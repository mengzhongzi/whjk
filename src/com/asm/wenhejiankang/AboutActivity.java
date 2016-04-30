package com.asm.wenhejiankang;
import android.widget.TextView;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;

/*
关于界面 风的影子
*/


public class AboutActivity extends StartActivity
	{
   TextView text_appname,text_versionCode,text_versionName;
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.about);
				text_appname = (TextView)findViewById(R.id.about_appname);
				text_versionName=(TextView)findViewById(R.id.about_versionName);
				text_versionCode=(TextView)findViewById(R.id.about_cersionCode);
				
				text_appname.setText(R.string.app_name);
				text_versionName.setText(getVersion());
				text_versionCode.setText(getVersionCode());
				
			}
	
	  

		String getVersion()
			{
				PackageManager manager=getPackageManager();
				PackageInfo info=null;
				try
					{
						info=manager.getPackageInfo(getPackageName(), 0);
						return info.versionName;
					}
				catch (PackageManager.NameNotFoundException e)
					{}

				return "1.0";
			}
	  
		String getVersionCode()
		{
			PackageManager manager=getPackageManager();
			PackageInfo info=null;
			try
				{
					info=manager.getPackageInfo(getPackageName(), 0);
					return String.valueOf( info.versionCode);
				}
			catch (PackageManager.NameNotFoundException e)
				{}

			return "1";
		}
	
}
