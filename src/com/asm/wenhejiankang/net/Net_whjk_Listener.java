package com.asm.wenhejiankang.net;
import com.asm.wenhejiankang.model.User;
import java.util.ArrayList;

public interface Net_whjk_Listener
{
	//
	public void onEnter(User user);
	
	public void onError(String text);
	
	public void onTiWen(ArrayList<String> list);
	
		
	
		//获取体温信息失败
		public void onTiwenError();

		public void onXieya(ArrayList<String> list);
		
		//获取血压信息失败
		public void onXieyaError();

		
		public void onXietang(ArrayList<String> list);
		
		//获取血糖信息失败
		public void onXietangError();

		public void onXieyang(ArrayList<String> list);
		//获取血氧信息失败
		public void onXieyangError();
    
		//上传数据成功
		public void onUp();
		
		//上传数据失败
		public void onUpError();
	
}
