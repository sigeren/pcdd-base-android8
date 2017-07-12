package com.extreme.ks.pcdd.ui.adapter;

import android.content.Context;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.bean.WithdrawRecordInfo;
import com.extreme.ks.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.ks.pcdd.ui.adapter.base.ViewHolder;
import com.extreme.ks.pcdd.util.DateUtil;

import java.util.List;

/**
 * Created by hang on 2017/1/23.
 */

public class WithdrawRecordListAdapter extends BaseRecyclerAdapter<WithdrawRecordInfo> {

    private String[] status = {"提现中", "成功", "失败", "异常处理"};

    public WithdrawRecordListAdapter(Context context, List<WithdrawRecordInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_recharge_record);
    }

    @Override
    public void onBind(ViewHolder holder, WithdrawRecordInfo item, int position) {
        holder.setText(R.id.tvRechargeFee, item.fee+"元");
        holder.setText(R.id.tvRechargeTime, DateUtil.getTime(item.create_time, 0));
        holder.setText(R.id.tvRechargeStatus, status[item.status]);
    }
}
