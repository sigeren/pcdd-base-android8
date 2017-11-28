package com.df.youxi.ui.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.df.youxi.R;
import com.df.youxi.ui.RechargeActivity;
import com.df.youxi.ui.WithdrawActivity;
import com.df.youxi.util.CheckUtil;

/**
 * Created by hang on 2017/3/29.
 */

public class HomeFlowDialog extends CommonDialog implements View.OnClickListener {

    public HomeFlowDialog(Context context) {
        super(context, R.layout.dlg_home_flow, 100, 85);
    }

    @Override
    public void initDlgView() {
        getView(R.id.tvRecharge).setOnClickListener(this);
        getView(R.id.tvWithdraw).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRecharge:
                context.startActivity(new Intent(context, RechargeActivity.class));
                break;

            case R.id.tvWithdraw:
                if(CheckUtil.checkBind(context))
                    context.startActivity(new Intent(context, WithdrawActivity.class));
                break;
        }
    }
}
