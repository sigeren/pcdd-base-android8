package com.df.vip.pcdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.df.vip.pcdd.R;
import com.df.vip.pcdd.network.ApiInterface;
import com.df.vip.pcdd.network.HttpResultCallback;
import com.df.vip.pcdd.network.MySubcriber;
import com.df.vip.pcdd.network.bean.GiftInfo;
import com.df.vip.pcdd.network.request.GiftListRequest;
import com.df.vip.pcdd.ui.adapter.GiftListAdapter;
import com.df.vip.pcdd.ui.adapter.divider.DividerItemDecoration;
import com.df.vip.pcdd.ui.base.BaseTopActivity;
import com.df.vip.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.df.vip.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/25.
 * 礼品兑换
 */

public class GiftListActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener, View.OnClickListener {

    private PullLoadMoreRecyclerView rvData;

    private int pageNo = 1;
    private int pageSize = 10;
    private GiftListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);
        init();
    }

    private void init() {
        initTopBar("礼品兑换");
        btnTopRight1.setText("兑换记录");
        btnTopRight1.setOnClickListener(this);
        btnTopRight1.setVisibility(View.VISIBLE);
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
        GiftListRequest req = new GiftListRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<List<GiftInfo>> callback = new HttpResultCallback<List<GiftInfo>>() {
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
            public void onNext(List<GiftInfo> gameRecordInfos) {
                updateView(gameRecordInfos);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getGiftList(req, s);
    }

    private void updateView(List<GiftInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new GiftListAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTopRight1:
                startActivity(new Intent(this, GiftExchangeLogActivity.class));
                break;
        }
    }
}
