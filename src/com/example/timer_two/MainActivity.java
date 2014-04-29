package com.example.timer_two;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class MainActivity extends Activity {

	private long counter;
	private CountDownTimer timer;
	public boolean start = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Prevents the screen from dimming and going to sleep
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		counter = 0;
		timer = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		timer = null;		
	}
	
	@Override
	protected void onPause() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		super.onPause();
	}
	
	public void clickStartStop(View v) {
		if(timer != null){
			timer.cancel();
			timer = null;
		}else{
			startCounter();
		}
	}
	
	public void clickPlus(View v) {
		counter += 60 * 1000;
		displayCount(counter);
		restartCounter(false);
	}
	
	public void clickMinus(View v) {
		counter -= 60 * 1000;
		counter = Math.max(0, counter);	
		displayCount(counter);
		restartCounter(false);
	}
	
	public void clickPlusSec(View v) {
		counter += 1000;
		displayCount(counter);
		restartCounter(false);
	}
	
	public void clickMinusSec(View v) {
		counter -= 1000;
		counter = Math.max(0, counter);	
		displayCount(counter);
		restartCounter(false);
	}
	
	public void clickReset(View v) {
		if(timer != null){
			return;
		} else {	
			counter = 0;
			displayCount(0);
		}
	}
	
	private void displayCount(long c){
		TextView mCount = (TextView) findViewById(R.id.textView1);
		long seconds = c/ 1000;
		long minutes = seconds/ 60;
		long seconds2 = seconds - minutes*60;
		if(seconds2 == 0){
			mCount.setText(minutes + ":00");
		} else if(seconds2 < 10){
			mCount.setText(minutes + ":0" + seconds2);
		} else {
			mCount.setText(minutes + ":" + seconds2);
		}
		
	}
	
	private void startCounter(){
		if(timer != null){
			timer.cancel();
		}
		displayCount(counter);
		if(counter > 0){
			timer = new CountDownTimer(counter, 1000){
				@Override
				public void onTick(long remainingTimeMillis){
					counter = remainingTimeMillis;
					displayCount(counter);
				}
				@Override
				public void onFinish(){
					displayCount(0);
					counter = 0;
				}
			};
			timer.start();
		}
	}
	
	private void restartCounter(boolean always){
		// Restarts a counter always, or only if running
		boolean isRunning = (timer != null);
		if(isRunning){
			timer.cancel();
			timer = null;
		}
		if(always || isRunning){
			displayCount(counter);
			if(counter > 0){
				timer = new CountDownTimer(counter, 1000){
					@Override
					public void onTick(long remainingTimeMillis){
						counter = remainingTimeMillis;
						displayCount(counter);
					}
					@Override
					public void onFinish(){
						timer.cancel();
						timer = null;
						counter = 0;
						displayCount(0);
					}
				};
				timer.start();
			}
		}
	}
	
}
