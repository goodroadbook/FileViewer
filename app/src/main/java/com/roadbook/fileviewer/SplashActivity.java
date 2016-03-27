package com.roadbook.fileviewer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity
{
	private Handler mHandler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		mHandler = new Handler();
		mHandler.postDelayed(finishActivity, 2000);
	}

	@Override
	public void onBackPressed()
	{
		;
	}

	private Runnable finishActivity = new Runnable()
	{
		@Override
		public void run()
		{
			setResult(Activity.RESULT_OK);
			finish();
		}		
	};
}
