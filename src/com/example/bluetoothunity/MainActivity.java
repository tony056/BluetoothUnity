package com.example.bluetoothunity;

import java.util.Set;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.R.bool;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity implements SensorEventListener{
	
	private Button startButton;
	private Button offButton;
	private Button listButton;
	private TextView listTextView;
	private TextView dataTextView;
	private BluetoothAdapter bluetoothAdapter;
	private Set<BluetoothDevice> pairedDevices;
	private SensorManager sensorManager;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startButton = (Button) findViewById(R.id.BTN_ON);
		offButton = (Button) findViewById(R.id.BTN_OFF);
		listButton = (Button) findViewById(R.id.BTN_LIST);
		
		dataTextView = (TextView) findViewById(R.id.SHOW_DATA);
		listTextView = (TextView) findViewById(R.id.SHOW_DEVICE);
		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
		
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!bluetoothAdapter.isEnabled()){
					Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(turnOn, 0);
					Toast.makeText(getApplicationContext(), "Turned on!", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Already on!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		offButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bluetoothAdapter.disable();
				Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG);
			}
		});
		
		listButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pairedDevices = bluetoothAdapter.getBondedDevices();
				String list = "";
				for(BluetoothDevice device : pairedDevices){
					String name = device.getName() + "\n";
					list += name;
				}
				listTextView.setText(list);
			}
		});	
	}
	
	@Override
	public void onStart(){
		super.onStart();
	}
	
	@Override
	public void onStop(){
		super.onStop(); 
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Log.e("Change", "fuck");
		if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
			String data = "0: "+event.values[0] + " 1: "+ event.values[1] + " 2: " + event.values[2];
			Log.e("data: ", data);
			dataTextView.setText(data);
		}
	}
}
