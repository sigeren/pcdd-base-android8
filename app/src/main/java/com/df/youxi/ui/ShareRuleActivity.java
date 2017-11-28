package com.df.youxi.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.df.youxi.R;
import com.df.youxi.network.ApiInterface;
import com.df.youxi.network.HttpResultCallback;
import com.df.youxi.network.MySubcriber;
import com.df.youxi.network.bean.ProxyRuleInfo;
import com.df.youxi.network.request.BaseRequest;
import com.df.youxi.ui.adapter.CommissionRuleAdapter;
import com.df.youxi.ui.adapter.manager.FullyLinearLayoutManager;
import com.df.youxi.ui.base.BaseTopActivity;
import com.df.youxi.util.T;


/**
 * Created by hang on 2017/4/19.
 * 分享规则
 */

public class ShareRuleActivity extends BaseTopActivity {

    private RecyclerView rvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_rule);
        init();
    }

    public void init() {
        initTopBar("分享规则");
        rvData = getView(R.id.rvData);
        rvData.setLayoutManager(new FullyLinearLayoutManager(this));

        loadData();
    }

    public void loadData() {
        HttpResultCallback<ProxyRuleInfo> callback = new HttpResultCallback<ProxyRuleInfo>() {
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
            public void onNext(ProxyRuleInfo proxyRuleInfo) {
                rvData.setAdapter(new CommissionRuleAdapter(ShareRuleActivity.this, proxyRuleInfo.bili_list));
                setText(R.id.tvProxyTip, getString(R.string.rule_vip_share, proxyRuleInfo.num));
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "加载数据");
        ApiInterface.getProxyRule(new BaseRequest(), s);
    }
}
