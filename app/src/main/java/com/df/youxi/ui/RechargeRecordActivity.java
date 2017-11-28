package com.df.youxi.ui;

import android.os.Bundle;

import com.df.youxi.network.ApiInterface;
import com.df.youxi.network.MySubcriber;
import com.df.youxi.network.bean.RechargeRecordInfo;
import com.df.youxi.network.request.RechargeRecordRequest;
import com.df.youxi.ui.adapter.RechargeRecordListAdapter;
import com.df.youxi.ui.adapter.divider.DividerItemDecoration;
import com.df.youxi.ui.base.BaseTopActivity;
import com.df.youxi.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.df.youxi.R;
import com.df.youxi.network.HttpResultCallback;
import com.df.youxi.util.T;

import java.util.List;

/**
 * Created by hang on 2017/1/23.
 * 充值记录
 */

public class RechargeRecordActivity extends BaseTopActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView rvData;

    private int pageNo = 1;
    private int pageSize = 10;
    private RechargeRecordListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_record);
        init();
    }

    private void init() {
        initTopBar("充值记录");
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
        RechargeRecordRequest req = new RechargeRecordRequest();
        req.page_no = pageNo+"";
        req.page_size = pageSize+"";
        HttpResultCallback<List<RechargeRecordInfo>> callback = new HttpResultCallback<List<RechargeRecordInfo>>() {
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
            public void onNext(List<RechargeRecordInfo> rechargeRecordInfos) {
                updateView(rechargeRecordInfos);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getRechargeRecord(req, s);
    }

    private void updateView(List<RechargeRecordInfo> data) {
        if(data.size() == 0)
            T.showShort("暂无数据");

        if(pageNo == 1) {
            adapter = new RechargeRecordListAdapter(this, data);
            rvData.setAdapter(adapter);
            rvData.setLessDataLoadMore();
        } else {
            adapter.getmData().addAll(data);
            adapter.notifyDataSetChanged();
        }
        pageNo++;
    }
}
