package com.extreme.ks.pcdd.ui.adapter;

import android.content.Context;
import android.widget.RelativeLayout;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.bean.RoomInfo;
import com.extreme.ks.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.ks.pcdd.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * Created by hang on 2017/2/7.
 */

public class RoomGridAdapter extends BaseRecyclerAdapter<RoomInfo> {

    private int[] iconRes = {R.drawable.icon_vip_1, R.drawable.icon_vip_2, R.drawable.icon_vip_3, R.drawable.icon_vip_4};

    public RoomGridAdapter(Context context, List<RoomInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_room);
    }

    @Override
    public void onBind(ViewHolder holder, RoomInfo item, int position) {
        RelativeLayout rlBg = holder.getView(R.id.rlRoomBg);
        rlBg.setBackgroundResource(iconRes[position%4]);
        holder.setText(R.id.tvRoomName, item.room_name);
        holder.setText(R.id.tvPeopleCount, item.people_count+"人");
    }
}
