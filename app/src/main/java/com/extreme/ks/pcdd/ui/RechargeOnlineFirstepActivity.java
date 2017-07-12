package com.extreme.ks.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.manager.UserInfoManager;
import com.extreme.ks.pcdd.network.ApiInterface;
import com.extreme.ks.pcdd.network.HttpResultCallback;
import com.extreme.ks.pcdd.network.MySubcriber;
import com.extreme.ks.pcdd.network.bean.OrderInfo;
import com.extreme.ks.pcdd.network.bean.PayTypeInfo;
import com.extreme.ks.pcdd.network.bean.UserInfo;
import com.extreme.ks.pcdd.network.request.BaseRequest;
import com.extreme.ks.pcdd.network.request.DuobaoPayRequest;
import com.extreme.ks.pcdd.network.request.RechargeRequest;
import com.extreme.ks.pcdd.ui.base.BaseTopActivity;
import com.extreme.ks.pcdd.util.T;
import com.extreme.ks.pcdd.util.ViewUtil;


/**
 * Created by hang on 2017/1/25.
 * 线上充值1
 */

public class RechargeOnlineFirstepActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edRechargeFee;
    private TextView tvPoint;
    private TextView tvTips;

    private PayTypeInfo data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_online_firstep);
        init();
    }

    private void init() {
        data = (PayTypeInfo) getIntent().getSerializableExtra("data");
        initTopBar(data.name);

        edRechargeFee = getView(R.id.edRechargeFee);
        tvPoint = getView(R.id.tvUserPoint);
        tvTips = getView(R.id.tvRechargeTips);

        getView(R.id.btnNext).setOnClickListener(this);
        getView(R.id.btnRefreshPoint).setOnClickListener(this);

        UserInfo userInfo = UserInfoManager.getUserInfo(this);
        setText(R.id.tvUserAccount, userInfo.account);
        tvPoint.setText(userInfo.point+"");

        tvTips.setText(getString(R.string.label_recharge_channel, data.name));
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnNext:
                if(ViewUtil.checkEditEmpty(edRechargeFee, "请输入金额"))
                    return;

                recharge(Double.parseDouble(edRechargeFee.getText().toString()));
                break;

            case R.id.btnRefreshPoint:
                loadUserInfo();
                break;
        }
    }

    public void recharge(final double fee) {
        RechargeRequest req = new RechargeRequest();
        req.total_fee = fee+"";
        HttpResultCallback<OrderInfo> callback = new HttpResultCallback<OrderInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(OrderInfo orderInfo) {
                loadQrCode(fee, orderInfo.order_no);
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "充值");
        ApiInterface.recharge(req, s);
    }

    /**
     * 获取充值二维码
     */
    public void loadQrCode(final double fee, final String orderNo) {
        DuobaoPayRequest req = new DuobaoPayRequest();
        req.pay_type = data.type_key;
        req.order_no = orderNo;
        req.fee = fee+"";
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Intent it = new Intent(RechargeOnlineFirstepActivity.this, RechargeOnlineSecondActivity.class);
                it.putExtra("title", data.name);
                it.putExtra("orderNo", orderNo);
                it.putExtra("fee", fee);
                it.putExtra("qrUrl", s);
                startActivity(it);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.rechargeQr(req, s);
    }


    public void loadUserInfo() {
        HttpResultCallback<UserInfo> callback = new HttpResultCallback<UserInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserInfo userInfo) {
                tvPoint.setText(userInfo.point+"");
                UserInfoManager.saveUserInfo(RechargeOnlineFirstepActivity.this, userInfo);
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "");
        ApiInterface.getUserInfo(new BaseRequest(), s);
    }
}
