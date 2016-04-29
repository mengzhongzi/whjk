package com.asm.wenhejiankang.bluetooth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xl.game.tool.Log;

public class JKBluetoothManager {
	public static final String MUUID = "00001101-0000-1000-8000-00805F9B34FB";
		public static final String TAG = "JKBluetoothManager";
	/*蓝牙状态值
	 * STATE_CLOSED 关闭的
	 * STATE_OPENFAIL 打开失败，临时状态
	 * STATE_OPENED 打开的
	 * STATE_DISCOVERYING 正在寻找设备
	 * STATE_FOUND 找到设备，临时状态
	 * STATE_DISCOVERIED 寻找结束
	 * STATE_CONNECTING 正在连接
	 * STATE_CONNECTED 连接成功
	 * STATE_CONNECTEDFAIL 连接失败，临时状态
	 * STATE_DISCONNECTED 中断连接，临时状态
	 */
	public static final int STATE_CLOSED = 0;
	public static final int STATE_OPENFAIL = 1;
	public static final int STATE_OPENED = 2;
	public static final int STATE_DISCOVERYING = 3;
	public static final int STATE_FOUND = 4;
	public static final int STATE_DISCOVERIED = 5;
	public static final int STATE_CONNECTING = 6;
	public static final int STATE_CONNECTED = 7;
	public static final int STATE_CONNECTEDFAIL = 8;
	public static final int STATE_DISCONNECTED=9;

	//设备列表
	private List<BluetoothDevice> devices = null;
	private JKBluetoothManager manager = null;
	private BluetoothAdapter adapter = null;
	private BroadcastReceiver mReceiver = null;
	private Context mContext = null;
	private OnStateChangedListener stateChangedListener = null;
	//血氧仪监听
	private OnBloudOxygenDataChangedListener bloudOxygenDataChangedListener = null;
	//血糖
	private OnGlycemicIndexDataChangedListener glycemicIndexDataChangedListener = null;
	//血压
	private OnBloudPressureDataChangedListener bloudPressureDataChangedListener = null;
	//温度
	private OnAnimalHeatDataChangedListener animalHeatDataChangedListener = null;
	//数据传输线程
	private DataCommunicationThread mDataCommunicationThread = null;

	int state = 0;
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	BluetoothSocket socket = null;
	BluetoothDevice currentDevice = null;

	private JKBluetoothManager() {
		state = STATE_CLOSED;
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (!adapter.isEnabled())
			if (!adapter.enable())
				notifyState(STATE_OPENFAIL);
		setState(STATE_OPENED);
		devices = new ArrayList<BluetoothDevice>();
		mReceiver = new BlueToothReceiver();
		
		//stateChangedListener = new OnStateChangedListener();
		bloudOxygenDataChangedListener = new onBindOxygen();
		glycemicIndexDataChangedListener = new onGlycem();
		bloudPressureDataChangedListener = new onBlound();
		animalHeatDataChangedListener = new OnAnimalHeatDataChangedListener();
	}
	
		public JKBluetoothManager(OnStateChangedListener listener)
		{
			state = STATE_CLOSED;
			setOnStateChangedListener(listener);
			
			adapter = BluetoothAdapter.getDefaultAdapter();
			if (!adapter.isEnabled())
				if (!adapter.enable())
					notifyState(STATE_OPENFAIL);
			setState(STATE_OPENED);
			devices = new ArrayList<BluetoothDevice>();
			mReceiver = new BlueToothReceiver();
			//stateChangedListener = new OnStateChangedListener();
			bloudOxygenDataChangedListener = new onBindOxygen();
			glycemicIndexDataChangedListener = new onGlycem();
			bloudPressureDataChangedListener = new onBlound();
			animalHeatDataChangedListener = new OnAnimalHeatDataChangedListener();
			
			
			
		}
	
		/*
		 * 设置蓝牙状态监听器
		 */
		public void setOnStateChangedListener(OnStateChangedListener sc) {
				stateChangedListener = sc;
			}
		/*
		 * 设置血氧仪监听器
		 */
		public void setOnBloudOxygenDataChangedListener(OnBloudOxygenDataChangedListener l) {
				bloudOxygenDataChangedListener = l;
			}
		/*
		 * 设置血糖仪监听器
		 */
		public void setOnGlycemicIndexDataChangedListener(OnGlycemicIndexDataChangedListener l) {
				glycemicIndexDataChangedListener = l;
			}
		/*
		 * 设置血压仪监听器
		 */
		public void setOnBloudPressureDataChangedListener(OnBloudPressureDataChangedListener l) {
				bloudPressureDataChangedListener = l;
			}
		

	/*
	 * 得到一个实例
	 * 第一次获得实例要调用getInstance(Context c)传入context
	 */
	public JKBluetoothManager getInstance() {
		if (manager == null)
			manager = new JKBluetoothManager();
		return manager;
	}

	public JKBluetoothManager getInstance(Context c) {
		mContext = c;
		return getInstance();
	}


	/*
	 * 查找设备，可以在蓝牙状态监听器里监听
	 */
	public void discoveryValidDevices() {
		registerDevicesFoundBroadCast();
		devices.clear();
		if (adapter.isDiscovering())
			adapter.cancelDiscovery();
		adapter.startDiscovery();
		setState(STATE_DISCOVERYING);
	}

	/*
	 * 中止查找设备
	 */
	public void cancleDiscoveryValidDevices() {
		if (state == STATE_DISCOVERYING) {
			adapter.cancelDiscovery();
			notifyState(STATE_DISCOVERYING);
			setState(STATE_DISCOVERIED);
		}
	}

	/*
	 * 连接
	 */
	public boolean connect(BluetoothDevice de) {
		if (state != STATE_DISCOVERIED)
			return false;
		if (devices.size() <= 0)
			return false;
		if (!BluetoothAdapter.checkBluetoothAddress(de.getAddress()))
			return false;
		if (state == STATE_DISCOVERYING || adapter.isDiscovering())
			adapter.cancelDiscovery();
		setState(STATE_CONNECTING);
		new ConnectThread(de, MUUID).start();
		return true;
	}

	/*
	 * 中止连接
	 */
	public boolean disConnect() throws IOException {
		if (state != STATE_CONNECTED)
			return false;
		if (socket == null)
			return false;
		mDataCommunicationThread.close();
		socket.close();
		bis.close();
		bos.close();
		socket = null;
		mDataCommunicationThread = null;
		bis = null;
		bos = null;
		notifyState(STATE_DISCONNECTED);
		state=STATE_DISCOVERIED;
		return true;
	}
	
	/*
	 * 获得查找到的设备列表
	 * 设备名称，地址，类别可以自己获取
	 */
	public List<BluetoothDevice> getDeviceList(){
		if(state==STATE_DISCOVERYING)
			return null;
		return devices;
	}
	
	/*
	 * 获得蓝牙状态
	 */
	public int getState() {
		return state;
	}

	/*
	 * 获得设备主要类别
	 * 包括  
            public static final int MISC              = 0x0000;
            public static final int COMPUTER          = 0x0100;
            public static final int PHONE             = 0x0200;
            public static final int NETWORKING        = 0x0300;
            public static final int AUDIO_VIDEO       = 0x0400;
            public static final int PERIPHERAL        = 0x0500;
            public static final int IMAGING           = 0x0600;
            public static final int WEARABLE          = 0x0700;
            public static final int TOY               = 0x0800;
            public static final int HEALTH            = 0x0900;
            public static final int UNCATEGORIZED     = 0x1F00;
       	该app用到的是HEALTH
	 */
	public int getDeviceMainType(BluetoothDevice de) throws JKBlutToothException {
		if (de == null)
			throw new JKBlutToothException("BluetoothDevice is null");
		BluetoothClass cs = de.getBluetoothClass();
		return cs.getMajorDeviceClass();
	}

	/*
	 * 获得设备具体类别
	 * 包括     
	 *  public static final int HEALTH_UNCATEGORIZED                = 0x0900;
        public static final int HEALTH_BLOOD_PRESSURE               = 0x0904;
        public static final int HEALTH_THERMOMETER                  = 0x0908;
        public static final int HEALTH_WEIGHING                     = 0x090C;
        public static final int HEALTH_GLUCOSE                      = 0x0910;
        public static final int HEALTH_PULSE_OXIMETER               = 0x0914;
        public static final int HEALTH_PULSE_RATE                   = 0x0918;
        public static final int HEALTH_DATA_DISPLAY                 = 0x091C;
        该app用到的是HEALTH_BLOOD_PRESSURE,HEALTH_THERMOMETER,HEALTH_PULSE_OXIMETER,HEALTH_GLUCOSE
	 */
	public int getDeviceSpecificType(BluetoothDevice de) throws JKBlutToothException {
		if (de == null)
			throw new JKBlutToothException("BluetoothDevice is null");
		BluetoothClass cs = de.getBluetoothClass();
		return cs.getDeviceClass();
	}

	/*
	 * 应用退出应调用此方法释放资源
	 */
	public void destory() throws IOException{
		disConnect();
		adapter.disable();
	}
	
	
	
	private void setState(int i) {
		state = i;
		notifyState(state);
	}

	private void notifyState(int i) {
		stateChangedListener.onStateChanged(devices, state);
	}

	private boolean registerDevicesFoundBroadCast() {
		if (mContext == null)
			return false;
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		mContext.registerReceiver(mReceiver, filter);
		return true;
	}

	private boolean unRegisterDevicesFoundBroadCast() {
		if (mContext == null)
			return false;
		mContext.unregisterReceiver(mReceiver);
		return true;
	}

	class BlueToothReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			String action = intent.getAction();
			//开始查找设备
			if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
			{
				setState(STATE_DISCOVERYING);
			}
			//搜索到一个设备
			else if (BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice d = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				devices.add(d);
				notifyState(STATE_FOUND);
			}
			//搜索完成
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			{
				setState(STATE_DISCOVERIED);
				unRegisterDevicesFoundBroadCast();
			}
		}
	}


		class onBindOxygen implements OnBloudOxygenDataChangedListener
			{

				@Override
				public void onDataChanged(byte[] ls, float saturability, int rate, float vqi)
					{
						// TODO: Implement this method
					}
				
			
		}

		class onGlycem implements OnGlycemicIndexDataChangedListener
			{

				@Override
				public void onDataChanged(float gi)
					{
						// TODO: Implement this method
					}
				
			
		}

	class onBlound implements	OnBloudPressureDataChangedListener
			{

				@Override
				public void onDataChanged(int highest, int lowest, int rate)
					{
						// TODO: Implement this method
					}
				
		
	}


	class ConnectThread extends Thread {
		BluetoothDevice de = null;
		String suuid = null;

		public ConnectThread(BluetoothDevice d, String s) {
			de = d;
			suuid = s;
		}

		@Override
		public synchronized void start() {
			// TODO 自动生成的方法存根
			try {
				super.start();
				socket = de.createInsecureRfcommSocketToServiceRecord(UUID.fromString(suuid));
				socket.connect();
				bis = new BufferedInputStream(socket.getInputStream());
				bos = new BufferedOutputStream(socket.getOutputStream());
				currentDevice = de;
				setState(STATE_CONNECTED);
				mDataCommunicationThread = new DataCommunicationThread(bis, bos, getDeviceSpecificType(currentDevice));
				mDataCommunicationThread.start();
			} catch (Exception e) {
				e.printStackTrace();
				notifyState(STATE_CONNECTEDFAIL);
				try {
					throw e;
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		}
	}

	class DataCommunicationThread extends Thread {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		int type = 0;
		boolean isOn=false;
		

		public DataCommunicationThread(BufferedInputStream in, BufferedOutputStream out, int type) {
			// TODO 自动生成的构造函数存根
			this.in = in;
			this.out = out;
			this.type = type;
			isOn=true;
		}

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			try {
				super.run();
				int count=0;
				byte[] data = new byte[20];
				while (isOn) 
				{
					while(count==0)
					{
						count=in.available();
					}
					Log.e(TAG,"开始获取输入信息");
					in.read(data);
					if (data[0] != 0xFE)
						throw new JKBlutToothException("data head error");
					if (data[1] != 0x6A)
						throw new JKBlutToothException("is not buletooth");
					switch (type) {
					// 血氧仪
					case BluetoothClass.Device.HEALTH_PULSE_OXIMETER:
						if (data[2] != 0x76)
							throw new JKBlutToothException("is not pulse oximeter");
						if (true) {
							if (data[3] == 0x51) {
								// 体积描记图
								byte[] ls = subByte(data, 5, 11);
								bloudOxygenDataChangedListener.onDataChanged(ls, -1, -1, -1);
							} else if (data[3] == 0x52) {
								// 血氧饱和度和脉率数据
								bloudOxygenDataChangedListener.onDataChanged(null, data[7], data[6], data[8]);
							} else if (data[3] == 0x53) {
								// 血氧饱和度和脉率报警限
								bloudOxygenDataChangedListener.onDataChanged(null, data[7], data[6], data[8]);
							}
						}
						break;
					// 血压仪
					case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE:
						if (data[2] != 0x73)
							throw new JKBlutToothException("is not bloud pressure");
						if (true) {
							if (data[3] == 0x5A) {
								bloudPressureDataChangedListener.onDataChanged(data[4], data[5], data[6]);
							}
						}
						break;
					// 耳温仪
					case BluetoothClass.Device.HEALTH_THERMOMETER:
						if (data[2] != 0x72)
							throw new JKBlutToothException("is not animal heat");
						if (true) {
							if (data[3] == 0x5A) {
								char b = byteToChar(new byte[] { data[4], data[5] });
								StringBuilder sb = new StringBuilder(String.valueOf(b));
								sb.insert(sb.length() - 2, ".");
								float f = Float.valueOf(sb.toString());
								byte ss=data[6];
								animalHeatDataChangedListener.onDataChanged(f,(int)data[7],binToDecimal(data[6]+""+data[5]),binToDecimal(data[4]+""+data[3]+""+data[2]));
							}
						}
						break;
					// 血糖仪
					case BluetoothClass.Device.HEALTH_GLUCOSE:
						if (data[2] != 0x75)
							throw new JKBlutToothException("is not glycemic index");
						if (true) {
							if (data[3] == 0x5A) {
								glycemicIndexDataChangedListener
										.onDataChanged(((float) byteToChar(new byte[] { data[4], data[5] })) / 18);
							}
						}
					default:
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void close() {
			isOn=false;
		}

		public void writeData(byte[] b) {
			
		}
	}

	public static byte[] subByte(byte[] b, int bpos, int length) {
		byte[] da = new byte[length];
		for (int i = 0; i < length; i++)
			da[i] = b[bpos + i];
		return da;
	}

	public static int bytesToInt(byte[] byteNum) {
		int num = 0;
		for (int ix = 0; ix < 4; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

	public static char byteToChar(byte[] b) {
		char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
		return c;
	}
	
	public static int binToDecimal(String s){
		int x = 0;
        for(char c: s.toCharArray())
             x = x * 2 + (c == '1' ? 1 : 0);
        return x;
	}
}
