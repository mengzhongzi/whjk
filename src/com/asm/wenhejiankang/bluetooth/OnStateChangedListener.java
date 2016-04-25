package com.asm.wenhejiankang.bluetooth;
import java.util.List;
import android.bluetooth.BluetoothDevice;

public interface OnStateChangedListener
{
		public void onStateChanged(List<BluetoothDevice> devices , int s);
}
