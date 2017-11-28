package com.df.youxi.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.df.youxi.network.ApiInterface;
import com.df.youxi.network.MySubcriber;
import com.df.youxi.network.bean.PayTypeInfo;
import com.df.youxi.network.request.BaseRequest;
import com.df.youxi.ui.RechargeLogActivity;
import com.df.youxi.ui.RechargeOnlineFirstepActivity;
import com.df.youxi.ui.adapter.PayTypeListAdapter;
import com.df.youxi.R;
import com.df.youxi.network.HttpResultCallback;
import com.df.youxi.network.response.PayTypeListResponse;
import com.df.youxi.ui.AliAcountListActivity;
import com.df.youxi.ui.adapter.base.BaseRecyclerAdapter;
import com.df.youxi.ui.adapter.manager.FullyLinearLayoutManager;
import com.df.youxi.ui.base.BaseFragment;
import com.df.youxi.util.CheckUtil;
import com.df.youxi.util.T;

import java.util.List;


/**
 * Created by hang on 2017/1/23.
 */

public class RechargeContentFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView rvOnline;
    private RecyclerView rvOffline;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge_content;
    }

    @Override
    protected void init(View rootView) {
        rvOnline = getView(R.id.rvOnlineType);
        rvOffline = getView(R.id.rvOfflineType);

        rvOnline.setLayoutManager(new FullyLinearLayoutManager(activity));
        rvOffline.setLayoutManager(new FullyLinearLayoutManager(activity));

        getView(R.id.btnRechargeLog).setOnClickListener(this);
        getView(R.id.btnCusSvr).setOnClickListener(this);

        loadData();
    }

    public void loadData() {
        HttpResultCallback<PayTypeListResponse> callback = new HttpResultCallback<PayTypeListResponse>() {
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
            public void onNext(PayTypeListResponse data) {
                List<PayTypeInfo> data2  =  data.online;
                data2.remove(2);
                final PayTypeListAdapter onlineAdapter = new PayTypeListAdapter(activity,data2);
                onlineAdapter.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, int position) {
                        PayTypeInfo item = onlineAdapter.getmData().get(position);
                        Intent it = new Intent(activity, RechargeOnlineFirstepActivity.class);
                        it.putExtra("data", item);
                        startActivity(it);
                    }
                });
                rvOnline.setAdapter(onlineAdapter);

                final PayTypeListAdapter offlineAdapter = new PayTypeListAdapter(activity, data.offline);
                offlineAdapter.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, int position) {
                        PayTypeInfo item = offlineAdapter.getmData().get(position);
                        Intent it = new Intent(activity, AliAcountListActivity.class);
                        it.putExtra("type", item.type_key);
                        startActivity(it);
                    }
                });
                rvOffline.setAdapter(offlineAdapter);
            }
        };
        MySubcriber s = new MySubcriber(callback);
        ApiInterface.getPayTypeList(new BaseRequest(), s);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnRechargeLog:
                startActivity(new Intent(activity, RechargeLogActivity.class));
                break;

            case R.id.btnCusSvr:
                CheckUtil.startCusSvr(activity);
                break;
        }
    }
}
