package com.df.vip.pcdd.ui.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.df.vip.pcdd.R;
import com.df.vip.pcdd.ui.base.BaseFragment;

/**
 * Created by hang on 2017/1/16.
 * 充值
 */

public class RechargeFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void init(View rootView) {
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.flContent, new RechargeContentFragment()).commit();
    }
}
