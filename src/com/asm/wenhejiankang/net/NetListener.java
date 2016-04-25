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
	
}
