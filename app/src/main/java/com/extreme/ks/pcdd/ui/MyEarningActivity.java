package com.extreme.ks.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.ApiInterface;
import com.extreme.ks.pcdd.network.HttpResultCallback;
import com.extreme.ks.pcdd.network.MySubcriber;
import com.extreme.ks.pcdd.network.bean.EarningInfo;
import com.extreme.ks.pcdd.network.bean.ShareParamsInfo;
import com.extreme.ks.pcdd.network.request.AccountRecordRequest;
import com.extreme.ks.pcdd.network.request.BaseRequest;
import com.extreme.ks.pcdd.ui.adapter.EarningListAdapter;
import com.extreme.ks.pcdd.ui.adapter.divider.DividerItemDecoration;
import com.extreme.ks.pcdd.ui.base.BaseTopActivity;
import com.extreme.ks.pcdd.ui.widget.dialog.ShareMenuWindow;
import com.extreme.ks.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.extreme.ks.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/4/14.
 */

public class MyEarningActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView rvData;

    private int pageNo = 1;
    private int pageSize = 10;
    private EarningListAdapter adapter;

    private ShareMenuWindow shareWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_earning);
        init();
    }

    private void init() {
        initTopBar("我的收益");
        btnTopRight2.setBackgroundResource(R.drawable.wode_share);
        btnTopRight2.setVisibility(View.VISIBLE);
        btnTopRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shareWindow == null) {
                    loadShareParams();
                } else {
                    shareWindow.showAtBottom();
                }
            }
        });

        getView(R.id.tvShareRule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyEarningActivity.this, ShareRuleActivity.class));
            }
        });

        rvData = getView(R.id.rvData);
        rvData.setLinearLayout();
        rvData.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvData.setOnPullLoadMoreListener(this);

        rvData.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }

    public void loadData() {
        AccountRecordRequest req = new AccountRecordRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<List<EarningInfo>> callback = new HttpResultCallback<List<EarningInfo>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                rvData.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(Throwable e) {
                rvData.setPullLoadMoreCompleted();
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(List<EarningInfo> accountRecordInfos) {
                updateView(accountRecordInfos);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getEarningList(req, s);
    }

    private void updateView(List<EarningInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new EarningListAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }

    public void loadShareParams() {
        HttpResultCallback<ShareParamsInfo> callback = new HttpResultCallback<ShareParamsInfo>() {
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
            public void onNext(ShareParamsInfo data) {
                shareWindow = new ShareMenuWindow(MyEarningActivity.this);
                shareWindow.title = data.share_title;
                shareWindow.content = data.share_content;
                shareWindow.url = data.share_url;
                shareWindow.showAtBottom();
            }
        };
        MySubcriber s = new MySubcriber(this, callback, true, "");
        ApiInterface.getShareParams(new BaseRequest(), s);
    }
}
