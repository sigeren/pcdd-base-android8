package com.extreme.ks.pcdd.ui.widget.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.app.PcddApp;
import com.extreme.ks.pcdd.impl.IBetOdds;
import com.extreme.ks.pcdd.network.ApiInterface;
import com.extreme.ks.pcdd.network.bean.GameOddsInfo;
import com.extreme.ks.pcdd.network.bean.GameTypeInfo;
import com.extreme.ks.pcdd.ui.WebLoadActivity;
import com.extreme.ks.pcdd.ui.adapter.BetPanelPagerAdapter;
import com.extreme.ks.pcdd.ui.fragment.BetPanelFragment;
import com.extreme.ks.pcdd.ui.fragment.WebLoadFragment;
import com.extreme.ks.pcdd.util.Arith;
import com.extreme.ks.pcdd.util.BitmapTool;
import com.extreme.ks.pcdd.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 2017/2/21.
 */

public class BettingOddsDlg extends DialogFragment implements View.OnClickListener {

    private ViewPager vpPanel;
    private EditText edPoint;
    private Button btnBetting;
    private Button btnMinPoint;
    private Button btnDoublePoint;

    private ArrayList<GameTypeInfo> gameTypeInfo;

    public int roomId;
    public int areaId;
    public double minPoint;  //个人下注下限
    public double maxPoint;  //个人下注上限

    public static BettingOddsDlg getInstance(ArrayList<GameTypeInfo> gameTypeInfo) {
        BettingOddsDlg instance = new BettingOddsDlg();
        Bundle b = new Bundle();
        b.putSerializable("data", gameTypeInfo);
        instance.setArguments(b);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCanceledOnTouchOutside(true);

        gameTypeInfo = (ArrayList<GameTypeInfo>) getArguments().getSerializable("data");

        View view = inflater.inflate(R.layout.dlg_betting_odds, container);
        vpPanel = (ViewPager) view.findViewById(R.id.vpBetPanel);
        edPoint = (EditText) view.findViewById(R.id.edBetPoint);
        btnBetting = (Button) view.findViewById(R.id.btnBetting);
        btnMinPoint = (Button) view.findViewById(R.id.btnMinPoint);
        btnDoublePoint = (Button) view.findViewById(R.id.btnDoublePoint);

        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i=0; i<gameTypeInfo.size(); i++) {
            GameTypeInfo item = gameTypeInfo.get(i);
            fragments.add(BetPanelFragment.getInstance(vpPanel, item, i));
        }
        vpPanel.setAdapter(new BetPanelPagerAdapter(getChildFragmentManager(), fragments));

        btnBetting.setOnClickListener(this);
        btnMinPoint.setOnClickListener(this);
        btnDoublePoint.setOnClickListener(this);
        view.findViewById(R.id.btnOddsExplain).setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, BitmapTool.dp2px(PcddApp.applicationContext, 320));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBetting:
                if(ViewUtil.checkEditEmpty(edPoint, "请输入下注金额"))
                    return;
                if(callback != null) {
                    IBetOdds iBetOdds = (IBetOdds) ((BetPanelPagerAdapter) vpPanel.getAdapter()).getItem(vpPanel.getCurrentItem());
                    callback.onBet(iBetOdds.getSelectedOdds(), Double.parseDouble(edPoint.getText().toString()));
                }
                edPoint.setText("");
                break;

            case R.id.btnMinPoint:
                IBetOdds iBetOdds = (IBetOdds) ((BetPanelPagerAdapter) vpPanel.getAdapter()).getItem(vpPanel.getCurrentItem());
                edPoint.setText(iBetOdds.getSelectedOdds().min_point+"");
                break;

            case R.id.btnDoublePoint:
                if(ViewUtil.checkEditEmpty(edPoint, "请输入投注金额"))
                    return;
                double point = Double.parseDouble(edPoint.getText().toString());
                edPoint.setText(Arith.mul(point, 2)+"");
                break;

            case R.id.btnOddsExplain:
                Intent it = new Intent(getActivity(), WebLoadActivity.class);
                it.putExtra(WebLoadFragment.PARAMS_TITLE, "赔率说明");
                it.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_ODDS_EXPLAIN+"?room_id="+roomId+"&area_id="+areaId);
                startActivity(it);
                break;
        }
    }

    private BetOddsCallback callback;

    public void setBetOddsCallback(BetOddsCallback callback) {
        this.callback = callback;
    }

    public interface BetOddsCallback {
        public void onBet(GameOddsInfo oddsInfo, double betPoint);
    }
}
