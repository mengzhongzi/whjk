package com.asm.wenhejiankang.bluetooth;

/*--------------------------------以下各监听类值若为-1或null，则说明此帧不含该数据，应该无视------------------------------*/
	/*
	 * highest 高压
	 * lowest 低压
	 * rate 心律
	 */
	public class OnBloudPressureDataChangedListener {
		public void onDataChanged(int highest, int lowest, int rate) {

		}
	}
