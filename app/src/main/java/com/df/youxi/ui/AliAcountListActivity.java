package com.df.youxi.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.df.youxi.network.ApiInterface;
import com.df.youxi.network.MySubcriber;
import com.df.youxi.network.request.AccountListRequest;
import com.df.youxi.ui.base.BaseTopActivity;
import com.df.youxi.R;
import com.df.youxi.network.HttpResultCallback;
import com.df.youxi.network.bean.RechargeAccountInfo;
import com.df.youxi.ui.adapter.AliAccountAdapter;
import com.df.youxi.ui.adapter.manager.FullyLinearLayoutManager;
import com.df.youxi.util.T;

import java.util.List;

/**
 * Created by hang on 2017/2/28.
 * 线下充值支付宝账号
 */

public class AliAcountListActivity extends BaseTopActivity {

    private RecyclerView rvData;

    private String typeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_ali_list);
        init();
        loadAccountList();
    }

    public void init() {
        typeKey = getIntent().getStringExtra("type");

        initTopBar("选择充值账号");
        rvData = getView(R.id.rvData);
        rvData.setLayoutManager(new FullyLinearLayoutManager(this));
    }

    public void loadAccountList() {
        AccountListRequest req = new AccountListRequest();
        req.type = typeKey;
        HttpResultCallback<List<RechargeAccountInfo>> callback = new HttpResultCallback<List<RechargeAccountInfo>>() {
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
            public void onNext(List<RechargeAccountInfo> data) {
                if(data.size() == 0)
                    T.showShort("暂无数据");

                rvData.setAdapter(new AliAccountAdapter(AliAcountListActivity.this, data, typeKey));
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "加载中");
        ApiInterface.getAccountList(req, s);
    }
}
