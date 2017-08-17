package com.extreme.ks.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.ApiInterface;
import com.extreme.ks.pcdd.network.HttpResultCallback;
import com.extreme.ks.pcdd.network.MySubcriber;
import com.extreme.ks.pcdd.network.request.RechargeOfflineRequest;
import com.extreme.ks.pcdd.ui.base.BaseTopActivity;
import com.extreme.ks.pcdd.util.T;
import com.extreme.ks.pcdd.util.ViewUtil;


/**
 * Created by hang on 2017/2/28.
 */

public class TransferAliActivity extends BaseTopActivity implements View.OnClickListener {

    private EditText edAliAccount;
    private EditText edRealName;
    private EditText edTransferFee;

    private int accountId;
    private String type;   // 2支付宝 3 微信  4 qq
    private String qrUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_ali);
        init();
    }

    public void init() {
        accountId = getIntent().getIntExtra("id", 0);
        qrUrl = getIntent().getStringExtra("qrUrl");
        type = getIntent().getStringExtra("type");

        edAliAccount = getView(R.id.edAliAccount);
        edRealName = getView(R.id.edRealName);
        edTransferFee = getView(R.id.edTransferFee);

        if("2".equals(type)) {
            title = "支付宝";
            edRealName.setVisibility(View.GONE);
            getView(R.id.tvLabelName).setVisibility(View.GONE);
        } else if("3".equals(type)) {
            title = "微信";
        } else {
            title = "QQ";
            edRealName.setVisibility(View.GONE);
            getView(R.id.tvLabelName).setVisibility(View.GONE);
        }

        initTopBar(title+"转账");

        getView(R.id.btnOK).setOnClickListener(this);

        setText(R.id.tvLabelAccount, title+"账号");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOK:
                if("3".equals(type) && ViewUtil.checkEditEmpty(edRealName, "请填写户名"))
                    return;
                if(ViewUtil.checkEditEmpty(edAliAccount, "请填写账号"))
                    return;
                submit();
                break;
        }
    }

    public void submit() {
        RechargeOfflineRequest req = new RechargeOfflineRequest();
        req.account_id = accountId+"";
        req.account = edAliAccount.getText().toString();
        req.account_type = type;
        req.real_name = edRealName.getText().toString();
        req.point = edTransferFee.getText().toString();
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                Intent it = new Intent(TransferAliActivity.this, RechargeOnlineSecondActivity.class);
                it.putExtra("title", title+"转账");
                it.putExtra("qrUrl", qrUrl);
                startActivity(it);
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "提交中");
        ApiInterface.rechargeOffline(req, s);
    }
}
