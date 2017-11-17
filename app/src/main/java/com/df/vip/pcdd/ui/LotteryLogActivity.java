package com.df.vip.pcdd.ui;

import android.os.Bundle;

import com.df.vip.pcdd.R;
import com.df.vip.pcdd.network.ApiInterface;
import com.df.vip.pcdd.network.HttpResultCallback;
import com.df.vip.pcdd.network.MySubcriber;
import com.df.vip.pcdd.network.bean.LotteryLogInfo;
import com.df.vip.pcdd.network.request.RechargeLogRequest;
import com.df.vip.pcdd.ui.adapter.ChouJiangLogAdapter;
import com.df.vip.pcdd.ui.base.BaseTopActivity;
import com.df.vip.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.df.vip.pcdd.util.T;

import java.util.List;

/**
 * Created by hang on 2017/6/18.
 * 大转盘抽奖记录
 */

public class LotteryLogActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView rvData;

    private int pageNo = 1;
    private int pageSize = 10;
    private ChouJiangLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_log);
        init();
    }

    private void init() {
        initTopBar("抽奖记录");
        rvData = getView(R.id.rvData);
        rvData.setLinearLayout();
        rvData.setOnPullLoadMoreListener(this);

        rvData.setRefreshing(true);
        onRefresh();
    }

    public void loadData() {
        RechargeLogRequest req = new RechargeLogRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<List<LotteryLogInfo>> callback = new HttpResultCallback<List<LotteryLogInfo>>() {
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
            public void onNext(List<LotteryLogInfo> data) {
                updateView(data);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getLotteryLog(req, s);
    }

    public void updateView(List<LotteryLogInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new ChouJiangLogAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
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
}
