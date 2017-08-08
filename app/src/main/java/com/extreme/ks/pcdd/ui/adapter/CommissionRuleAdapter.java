package com.extreme.ks.pcdd.ui.adapter;

import android.content.Context;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.bean.CommissionInfo;
import com.extreme.ks.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.ks.pcdd.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by hang on 2017/4/1.
 */

public class CommissionRuleAdapter extends BaseRecyclerAdapter<CommissionInfo> {

    public CommissionRuleAdapter(Context context, List<CommissionInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_commission_rule);
    }

    @Override
    public void onBind(ViewHolder holder, CommissionInfo item, int position) {
        holder.setText(R.id.tvLevel, item.level);
        holder.setText(R.id.tvUsrPoint, item.start_point+"-"+item.end_point);
        holder.setText(R.id.tvCommissionPoint, (item.get_point*100)+"%");
    }
}
