package com.df.youxi.ui.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.df.youxi.network.bean.GameOddsInfo;
import com.df.youxi.ui.adapter.base.BaseRecyclerAdapter;
import com.df.youxi.ui.adapter.base.ViewHolder;
import com.df.youxi.R;

import java.util.List;

/**
 * Created by hang on 2017/2/23.
 */

public class OddsDXDSAdapter extends BaseRecyclerAdapter<GameOddsInfo> {

    public int selectedIndex = 0;

    public OddsDXDSAdapter(Context context, List<GameOddsInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_odds_dxds);
    }

    @Override
    public void onBind(ViewHolder holder, GameOddsInfo item, final int position) {
        holder.setText(R.id.tvOddsKey, item.bili_name);
        holder.setText(R.id.tvOddsValue, "1:"+item.bili);

        LinearLayout llContainer = holder.getView(R.id.llOddsContainer);
        llContainer.setBackgroundResource(selectedIndex==position? R.drawable.selected_border : 0);
    }

    public GameOddsInfo getSelectedItem() {
        return mData.get(selectedIndex);
    }
}
