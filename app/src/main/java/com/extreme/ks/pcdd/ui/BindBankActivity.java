package com.extreme.ks.pcdd.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.manager.UserInfoManager;
import com.extreme.ks.pcdd.network.ApiInterface;
import com.extreme.ks.pcdd.network.HttpResultCallback;
import com.extreme.ks.pcdd.network.MySubcriber;
import com.extreme.ks.pcdd.network.bean.UserInfo;
import com.extreme.ks.pcdd.network.request.BindBankRequest;
import com.extreme.ks.pcdd.ui.base.BaseTopActivity;
import com.extreme.ks.pcdd.util.MD5Utils;
import com.extreme.ks.pcdd.util.T;
import com.extreme.ks.pcdd.util.ViewUtil;


/**
 * Created by hang on 2017/1/23.
 * 绑定银行卡
 */

public class BindBankActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edRealName;
    private TextView edBankName;
    private EditText edBankNo;
    private EditText edBankAddr;
    private EditText edWithdrawPwd;
    private LinearLayout llBankAddr;

    private UserInfo userInfo;

    private String[] types = {"银行卡", "支付宝"};
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_bank);
        init();
    }

    private void init() {
        initTopBar("绑定提现账户");
        edRealName = getView(R.id.edRealName);
        edBankName = getView(R.id.edBankName);
        edBankNo = getView(R.id.edBankNo);
        edBankAddr = getView(R.id.edBankAddr);
        edWithdrawPwd = getView(R.id.edWithdrawPwd);
        llBankAddr = getView(R.id.llBankAddr);

        userInfo = UserInfoManager.getUserInfo(this);
        edRealName.setText(userInfo.real_name);
        edBankName.setText(userInfo.bank_name);
        edBankNo.setText(userInfo.bank_no);
        edBankAddr.setText(userInfo.open_card_address);

        if(userInfo.account_bandType != 0)
            type = userInfo.account_bandType;
        updateTypeView(type);

        edBankName.setOnClickListener(this);
        getView(R.id.btnOK).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.edBankName:
                new AlertDialog.Builder(this).setTitle("选择绑定类型")
                        .setItems(types, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edBankName.setText(types[which]);
                                tvTopTitle.setText("绑定"+types[which]);
                                type = which+1;
                                updateTypeView(type);
                            }
                        }).show();
                break;

            case R.id.btnOK:
                if(ViewUtil.checkEditEmpty(edRealName, "请填写真实姓名"))
                    return;
                if(type == 0) {
                    T.showShort("请选择绑定类型");
                    return;
                }
                if(ViewUtil.checkEditEmpty(edBankNo, "请填写银行卡号"))
                    return;
                if(type==1 && ViewUtil.checkEditEmpty(edBankAddr, "请填写开户地址"))
                    return;
                if(ViewUtil.checkEditEmpty(edWithdrawPwd, "请填写提现密码"))
                    return;
                bind();
                break;
        }
    }

    public void updateTypeView(int type) {
        if(type == 1) {
            //bank
            llBankAddr.setVisibility(View.VISIBLE);
        } else {
            llBankAddr.setVisibility(View.GONE);
        }
    }

    public void bind() {
        String pwd = edWithdrawPwd.getText().toString();
        for (int i=0; i<3; i++)
            pwd = MD5Utils.getMD5String(pwd);
        final BindBankRequest req = new BindBankRequest();
        req.withdrawals_password = pwd;
        req.real_name = edRealName.getText().toString();
        req.bank_name = edBankName.getText().toString();
        req.bank_no = edBankNo.getText().toString();
        req.open_card_address = edBankAddr.getText().toString();
        req.account_band_type = type+"";
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("绑定成功");
                userInfo.real_name = req.real_name;
                userInfo.bank_name = req.bank_name;
                userInfo.bank_no = req.bank_no;
                userInfo.open_card_address = req.open_card_address;
                UserInfoManager.saveUserInfo(BindBankActivity.this, userInfo);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "绑定中");
        ApiInterface.bindBank(req, s);
    }
}
