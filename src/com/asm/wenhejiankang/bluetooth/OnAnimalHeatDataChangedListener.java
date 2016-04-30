package com.asm.wenhejiankang.bluetooth;

	/*
	 * tp 温度
	 * temMetric 温度度量
	 * 		0 摄氏度 
	 * 		1华氏度
	 * temType 温度类型
	 * 		0人体温度
	 * 		1物体温度
	 * 		2环境温度
	 * state 状态
	 * 		0正常
	 * 		1测量温度过低
	 * 		2测量温度过高
	 * 		3环境温度过低
	 * 		4环境温度过高
	 * 		5EEPROM出错
	 * 		6传感器出错
	 */
	public interface OnAnimalHeatDataChangedListener
	{
		
		public void onDataChanged(float tp, int temMetric,int temType,int state) ;
			
		
	}
