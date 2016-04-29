package com.asm.wenhejiankang.bluetooth;

/*
 * ls 血氧体积描记图数据 ,10个字节
 * saturability 血氧饱和度
 * rate 脉率
 * vqi 灌注指数
 */
public interface OnBloudOxygenDataChangedListener 
	{
		public void onDataChanged(byte[] ls, float saturability, int rate, float vqi) ;

			

	}
