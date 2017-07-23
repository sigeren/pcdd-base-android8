package com.extreme.ks.pcdd.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.network.bean.PayTypeInfo;
import com.extreme.ks.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.ks.pcdd.ui.adapter.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hang on 2017/5/10.
 */

public class PayTypeListAdapter extends BaseRecyclerAdapter<PayTypeInfo> {

    private Map<String, Integer> icons;

    public PayTypeListAdapter(Context context, List<PayTypeInfo> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_pay_type);
        icons = new HashMap<>();
        icons.put("1", R.drawable.cz_07);
        icons.put("2", R.drawable.cz_03);
        icons.put("3", R.drawable.cz_05);
        icons.put("aiyi_ali", R.drawable.cz_03);
        icons.put("aiyi_weixin", R.drawable.cz_05);
    }

    @Override
    public void onBind(ViewHolder holder, final PayTypeInfo item, int position) {
        TextView text = holder.getView(R.id.tvPayType);
        int icon = R.drawable.cz_03;
        if("1".equals(item.type_key))
            icon = R.drawable.cz_07;
        else if("2".equals(item.type_key))
            icon = R.drawable.cz_03;
        else if("3".equals(item.type_key))
            icon = R.drawable.cz_05;
        else if(item.type_key.contains("ali"))
            icon = R.drawable.cz_03;
        else if(item.type_key.contains("weixin"))
            icon = R.drawable.cz_05;
        Drawable drawable = mContext.getResources().getDrawable(icon);
        if(drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        text.setCompoundDrawables(drawable, null, null, null);
        text.setText(item.name);
    }
}
