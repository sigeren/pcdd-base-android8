package com.df.youxi.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.df.youxi.network.Encrypt;
import com.df.youxi.R;
import com.df.youxi.network.request.BaseRequest;
import com.df.youxi.ui.base.BaseTopActivity;
import com.df.youxi.ui.fragment.WebLoadFragment;

public class WebLoadActivity extends BaseTopActivity {
	
	private String title;
	private String url;
	
	private WebLoadFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_load);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		
		title = getIntent().getStringExtra(WebLoadFragment.PARAMS_TITLE);
		url = getIntent().getStringExtra(WebLoadFragment.PARAMS_URL);
		BaseRequest baseRequest = new BaseRequest();
		Encrypt.encrypt(baseRequest);
		String urlb = url +"&sign="+baseRequest.sign+"&timestamp="+baseRequest.timestamp+"&user_id="+baseRequest.user_id;
		initTopBar(title);
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		fragment = WebLoadFragment.getInstance(urlb);
		t.replace(R.id.rlContent, fragment);
		t.commit();
	}

	@Override
	public void onBackPressed() {
		fragment.goBack();
	}
}
