package com.asm.wenhejiankang.net;

/*
登录监听器

*/
public interface NetListener
{
	//登录成功
	public void onEnter();
	
	//登录失败
	public void onError();
	
	//获取体温信息失败
	public void onTiwenError();
	
	//获取血压信息失败
	public void onXieyaError();
	
	//获取血糖信息失败
	public void onXietangError();
	
	//获取血氧信息失败
	public void onXieyangError();
	
	//上传数据失败
	public void onUpError();
	
}
