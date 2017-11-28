package com.df.youxi.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.df.youxi.ui.base.BaseTopActivity;
import com.df.youxi.R;
import com.df.youxi.ui.fragment.MsgListFragment;

/**
 * Created by hang on 2017/2/27.
 */

public class MyMessageActivity extends BaseTopActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);

        initTopBar("我的消息");

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.flContent, MsgListFragment.getInstance(2)).commit();
    }
}
