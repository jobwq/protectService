package com.ctoyo.protect.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;


public class Service2 extends Service {


	@SuppressLint("NewApi")
	public void onCreate() {
		myBinder=new MyBinder();

		if(myConn==null){
			myConn=new MyConn();
		}
		Toast.makeText(Service2.this, "Service2 启动中...", Toast.LENGTH_SHORT)
				.show();
	}



	@Override
	public IBinder onBind(Intent intent) {
//		return (IBinder) startS1;
		return  myBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("s","onDestroy2");
	}

	private MyBinder myBinder;

	class MyBinder extends IProgressService.Stub{

		@Override
		public String getServiceName() throws RemoteException {
			return "Service2";
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Service2.this.bindService(new Intent(Service2.this,Service1.class),myConn, Context.BIND_IMPORTANT );

	}

	private MyConn myConn;
	class MyConn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i("Service2","程序 2 链接 程序1成功");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(Service2.this, "Service1 链接断开...", Toast.LENGTH_SHORT).show();
			Service2.this.startService(new Intent(Service2.this,Service1.class));
			Service2.this.bindService(new Intent(Service2.this,Service1.class),myConn, Context.BIND_IMPORTANT );
		}
	}
}
