package com.df.youxi.ui;

import android.os.Bundle;
import android.os.Handler;

import com.df.youxi.R;
import com.df.youxi.ui.base.BaseFragmentActivity;

public class LoadingActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
			}
		}, 1500);
	}

	@Override
	public void onBackPressed() {
	}
}
