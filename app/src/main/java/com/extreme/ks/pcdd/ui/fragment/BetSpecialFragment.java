package com.extreme.ks.pcdd.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.impl.IBetOdds;
import com.extreme.ks.pcdd.network.bean.GameOddsInfo;
import com.extreme.ks.pcdd.ui.adapter.OddsDXDSAdapter;
import com.extreme.ks.pcdd.ui.adapter.base.BaseRecyclerAdapter;
import com.extreme.ks.pcdd.ui.adapter.manager.FullyGridLayoutManager;
import com.extreme.ks.pcdd.ui.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by hang on 2017/2/22.
 */

public class BetSpecialFragment extends BaseFragment
        implements BaseRecyclerAdapter.OnRecyclerItemClickListener, IBetOdds, View.OnClickListener {

    private TextView tvResult;
    private RecyclerView rvOdds;
    public ViewPager viewPager;

    private ArrayList<GameOddsInfo> data;
    private OddsDXDSAdapter oddsAdapter;

    public static BetSpecialFragment getInstance(ViewPager viewPager, ArrayList<GameOddsInfo> data) {
        BetSpecialFragment instance = new BetSpecialFragment();
        Bundle b = new Bundle();
        b.putSerializable("data", data);
        instance.setArguments(b);
        instance.viewPager = viewPager;
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bet_special;
    }

    @Override
    protected void init(View rootView) {
        data = (ArrayList<GameOddsInfo>) getArguments().getSerializable("data");

        tvResult = getView(R.id.tvOddsResult);
        rvOdds = getView(R.id.rvBetOdds);

        rvOdds.setLayoutManager(new FullyGridLayoutManager(activity, 2));
        oddsAdapter = new OddsDXDSAdapter(activity, data);
        oddsAdapter.setOnRecyclerItemClickListener(this);
        rvOdds.setAdapter(oddsAdapter);

        updateResult(data.get(0).result);

        getView(R.id.ivPreviousPage).setOnClickListener(this);
    }

    @Override
    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, int position) {
        oddsAdapter.selectedIndex = position;
        oddsAdapter.notifyDataSetChanged();
        updateResult(oddsAdapter.getmData().get(position).result);
    }

    /**
     * 更新中奖和值
     */
    public void updateResult(String result) {
        try {
            int r = Integer.parseInt(result);
            if(r >= 0)
                tvResult.setText(getString(R.string.bet_result_dxds, result));
            else
                tvResult.setText("");
        } catch (Exception e) {
            tvResult.setText(getString(R.string.bet_result_dxds, result));
            e.printStackTrace();
        }
    }

    @Override
    public GameOddsInfo getSelectedOdds() {
        return oddsAdapter.getSelectedItem();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivPreviousPage:
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                break;
        }
    }
}
