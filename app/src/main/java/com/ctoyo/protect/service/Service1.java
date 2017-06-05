package com.ctoyo.protect.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class Service1 extends Service {

	@Override
	public void onCreate() {

		myBinder=new MyBinder();
		if(myConn==null){
			myConn=new MyConn();
		}
		Toast.makeText(Service1.this, "Service1 正在启动...", Toast.LENGTH_SHORT)
				.show();

	}


	@Override
	public IBinder onBind(Intent intent) {
		return  myBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private MyBinder myBinder;

	class MyBinder extends IProgressService.Stub{

		@Override
		public String getServiceName() throws RemoteException {
			return "Service1";
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Service1.this.bindService(new Intent(Service1.this,Service2.class),myConn, Context.BIND_IMPORTANT );
	}
	private MyConn myConn;
	class MyConn implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
				Log.i("Service1","程序 1 链接 程序2成功");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(Service1.this, "Service2 链接断开...", Toast.LENGTH_SHORT).show();
			Service1.this.startService(new Intent(Service1.this,Service2.class));
			Service1.this.bindService(new Intent(Service1.this,Service2.class),myConn, Context.BIND_IMPORTANT );
		}
	}
}
