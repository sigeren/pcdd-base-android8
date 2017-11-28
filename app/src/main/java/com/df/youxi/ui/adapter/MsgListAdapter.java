package com.df.youxi.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.df.youxi.manager.UserInfoManager;
import com.df.youxi.network.ApiInterface;
import com.df.youxi.network.bean.NoticeInfo;
import com.df.youxi.ui.WebLoadActivity;
import com.df.youxi.ui.adapter.base.BaseRecyclerAdapter;
import com.df.youxi.ui.adapter.base.ViewHolder;
import com.df.youxi.ui.fragment.WebLoadFragment;
import com.df.youxi.util.DateUtil;
import com.df.youxi.R;

import java.util.List;

/**
 * Created by hang on 2017/1/18.
 */

public class MsgListAdapter extends BaseRecyclerAdapter<NoticeInfo> {

    public MsgListAdapter(Context context, List<NoticeInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_dynamic_msg);
    }

    @Override
    public void onBind(ViewHolder holder, final NoticeInfo item, int position) {
        holder.setText(R.id.tvMsgTitle, item.title);
        holder.setText(R.id.tvMsgDate, DateUtil.getTime(item.create_time, 0));
        holder.getView(R.id.ivMsgUnread).setVisibility(item.status==1? View.INVISIBLE : View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, WebLoadActivity.class);
                it.putExtra(WebLoadFragment.PARAMS_TITLE, item.title);
                it.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_NOTICE_DETAIL
                        +"?notice_id="+item.id+"&user_id="+ UserInfoManager.getUserId(mContext));
                mContext.startActivity(it);
                item.status = 1;
                notifyDataSetChanged();
            }
        });
    }

}
