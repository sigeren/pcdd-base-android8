package com.extreme.ks.pcdd.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.bean.RechargeAccountInfo;
import com.extreme.ks.pcdd.ui.TransferAliActivity;
import com.extreme.ks.pcdd.ui.TransferBankActivity;
import com.extreme.ks.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.ks.pcdd.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by hang on 2017/2/28.
 */

public class AliAccountAdapter extends BaseRecyclerAdapter<RechargeAccountInfo> {

    private int itemType;

    private String typeKey; // 1 银行卡 2 支付宝  3 微信

    public AliAccountAdapter(Context context, List<RechargeAccountInfo> data, String keyType) {
        super(context, data);
        this.typeKey = keyType;
        //1 支付宝/微信  2 银行卡
        putItemLayoutId(1, R.layout.item_ali_account);
        putItemLayoutId(2, R.layout.item_bank_account);
        if("1".equals(keyType))
            itemType = 2;
        else
            itemType = 1;
    }

    @Override
    public int getItemViewType(int position, RechargeAccountInfo item) {
        return itemType;
    }

    @Override
    public void onBind(ViewHolder holder, final RechargeAccountInfo item, int position) {
        if(holder.viewType == 1) {
            bindAliView(holder, item, position);
        } else {
            bindBankView(holder, item, position);
        }
    }

    public void bindAliView(ViewHolder holder, final RechargeAccountInfo item, int position) {
        holder.setText(R.id.tvAliAccount, item.account);
        holder.setText(R.id.tvRealName, item.real_name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, TransferAliActivity.class);
                it.putExtra("id", item.id);
                it.putExtra("type", typeKey);
                it.putExtra("qrUrl", item.photo);
                mContext.startActivity(it);
            }
        });
    }

    public void bindBankView(ViewHolder holder, final RechargeAccountInfo item, int position) {
        holder.setText(R.id.tvBankName, item.bank_name);
        holder.setText(R.id.tvBranchBank, item.open_card_address);
        holder.setText(R.id.tvBankAccount, item.account);
        holder.setText(R.id.tvRealName, item.real_name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, TransferBankActivity.class);
                it.putExtra("data", item);
                mContext.startActivity(it);
            }
        });
    }
}
