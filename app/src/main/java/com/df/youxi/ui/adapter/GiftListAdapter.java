package com.df.youxi.ui.adapter;

import android.content.Context;
import android.view.View;

import com.df.youxi.network.bean.GiftInfo;
import com.df.youxi.ui.adapter.base.BaseRecyclerAdapter;
import com.df.youxi.ui.adapter.base.ViewHolder;
import com.df.youxi.ui.widget.dialog.ExchangeGiftDlg;
import com.df.youxi.R;
import com.df.youxi.util.CheckUtil;

import java.util.List;

/**
 * Created by hang on 2017/1/25.
 */

public class GiftListAdapter extends BaseRecyclerAdapter<GiftInfo> {

    public GiftListAdapter(Context context, List<GiftInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_gift);
    }

    @Override
    public void onBind(ViewHolder holder, final GiftInfo item, int position) {
        holder.setImageByURL(R.id.ivGiftThumb, item.gift_photo);
        holder.setText(R.id.tvGiftName, item.gift_name);
        holder.setText(R.id.tvGiftPoint, item.gift_point+"");

        holder.getView(R.id.btnExchange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckUtil.checkBindMobile(mContext) && CheckUtil.checkBindBank(mContext))
                    new ExchangeGiftDlg(mContext, item).show();
            }
        });
    }
}
