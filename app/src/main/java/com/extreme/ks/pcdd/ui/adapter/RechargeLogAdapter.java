package com.extreme.ks.pcdd.ui.adapter;

import android.content.Context;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.bean.RechargeLogInfo;
import com.extreme.ks.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.ks.pcdd.ui.adapter.base.ViewHolder;
import com.extreme.ks.pcdd.util.DateUtil;

import java.util.List;

/**
 * Created by hang on 2017/3/4.
 */

public class RechargeLogAdapter extends BaseRecyclerAdapter<RechargeLogInfo> {

    private static final int VIEW_TYPE_BANK = 1;
    private static final int VIEW_TYPE_ALI = 2;
    private static final int VIEW_TYPE_WX = 3;

    private String[] status = {"待确认", "确认收到", "未收到"};

    public RechargeLogAdapter(Context context, List<RechargeLogInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_BANK, R.layout.item_transfer_bank);
        putItemLayoutId(VIEW_TYPE_ALI, R.layout.item_transfer_ali);
        putItemLayoutId(VIEW_TYPE_WX, R.layout.item_transfer_ali);
    }

    @Override
    public int getItemViewType(int position, RechargeLogInfo item) {
        return item.account_type;
    }

    @Override
    public void onBind(ViewHolder holder, RechargeLogInfo item, int position) {
        switch(holder.viewType) {
            case VIEW_TYPE_BANK:
                bindBankView(holder, item, position);
                break;

            case VIEW_TYPE_ALI:
            case VIEW_TYPE_WX:
                bindAliView(holder, item, position);
                break;
        }
    }

    private void bindBankView(ViewHolder holder, RechargeLogInfo item, int position) {
        holder.setText(R.id.tvTransferStatus, status[item.status]);
        //收款人
        if(item.account_info != null) {
            holder.setText(R.id.tvTransferBank, item.account_info.bank_name);
            holder.setText(R.id.tvTransferName, item.account_info.real_name);
            holder.setText(R.id.tvTransferBraunch, item.account_info.open_card_address);
            holder.setText(R.id.tvTransferAccount, item.account_info.account);
        } else {
            holder.setText(R.id.tvTransferBank, "");
            holder.setText(R.id.tvTransferName, "");
            holder.setText(R.id.tvTransferBraunch, "");
            holder.setText(R.id.tvTransferAccount, "");
        }
        //存款人
        holder.setText(R.id.tvDepositorName, item.real_name);
        holder.setText(R.id.tvDepositAccount, item.account);
        holder.setText(R.id.tvTransferFee, item.point+"元");
        holder.setText(R.id.tvTransferTime, DateUtil.getTime(item.create_time, 0));
    }

    private void bindAliView(ViewHolder holder, RechargeLogInfo item, int position) {
        if(item.account_type == 2)
            holder.setText(R.id.tvPayType, "支付宝");
        else
            holder.setText(R.id.tvPayType, "微信");

        holder.setText(R.id.tvTransferStatus, status[item.status]);
        //收款人
        if(item.account_info != null) {
            holder.setText(R.id.tvTransferAccount, item.account_info.account);
            holder.setText(R.id.tvTransferName, item.account_info.real_name);
        } else {
            holder.setText(R.id.tvTransferAccount, "");
            holder.setText(R.id.tvTransferName, "");
        }
        //存款人
        holder.setText(R.id.tvDepositorName, item.real_name);
        holder.setText(R.id.tvDepositAccount, item.account);
        holder.setText(R.id.tvTransferFee, item.point+"元");
        holder.setText(R.id.tvTransferTime, DateUtil.getTime(item.create_time, 0));
    }
}
