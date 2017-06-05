package com.ctoyo.protect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ctoyo.protect.service.JobScheduleService;
import com.ctoyo.protect.service.Service1;
import com.ctoyo.protect.service.Service2;


public class MainActivity extends Activity implements View.OnClickListener {

	private Button btn1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1= (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent i1 = new Intent(MainActivity.this, Service1.class);
			startService(i1);

			Intent i2 = new Intent(MainActivity.this, Service2.class);
			startService(i2);

			if(android.os.Build.VERSION.SDK_INT>=21){
				Intent i3 = new Intent(MainActivity.this, JobScheduleService.class);
				startService(i3);
			}

			break;
		}
	}

}
