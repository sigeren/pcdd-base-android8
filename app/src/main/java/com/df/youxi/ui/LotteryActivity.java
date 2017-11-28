package com.df.youxi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by hang on 2017/6/18.
 * 抽奖转盘
 */

public class LotteryActivity extends WebLoadActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnTopRight1.setText("抽奖记录");
        btnTopRight1.setVisibility(View.VISIBLE);
        btnTopRight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LotteryActivity.this, LotteryLogActivity.class));
            }
        });
    }
}
