package com.extreme.ks.pcdd.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.extreme.ks.pcdd.R;
import com.extreme.ks.pcdd.impl.BannerImageHolderView;
import com.extreme.ks.pcdd.impl.OnLoginClickListener;
import com.extreme.ks.pcdd.network.ApiInterface;
import com.extreme.ks.pcdd.network.HttpResultCallback;
import com.extreme.ks.pcdd.network.MySubcriber;
import com.extreme.ks.pcdd.network.bean.BannerInfo;
import com.extreme.ks.pcdd.network.bean.HomeDataInfo;
import com.extreme.ks.pcdd.network.request.BannerListRequest;
import com.extreme.ks.pcdd.network.request.HomeDataRequest;
import com.extreme.ks.pcdd.ui.LevelSelectActivity;
import com.extreme.ks.pcdd.ui.MoreActivity;
import com.extreme.ks.pcdd.ui.WebLoadActivity;
import com.extreme.ks.pcdd.ui.base.BaseFragment;
import com.extreme.ks.pcdd.ui.widget.dialog.HomeFlowDialog;
import com.extreme.ks.pcdd.util.CheckUtil;
import com.extreme.ks.pcdd.util.T;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hang on 2017/1/16.
 * 首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private ConvenientBanner banner;
    private ImageView ivFlow;

    private List<BannerInfo> bannerList;
    private List<String> bannerImgRes = new ArrayList<String>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View rootView) {
        banner = getView(R.id.bannerHome);
        ivFlow = getView(R.id.ivHomeFlow);

        initListener();

        loadData();
        loadBanner();
    }

    private void loadData() {
        HomeDataRequest req = new HomeDataRequest();
        HttpResultCallback callback = new HttpResultCallback<HomeDataInfo>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(HomeDataInfo info) {
                setText(R.id.tvTotalEarnPoint, info.point+"");
                setText(R.id.tvTotalmembers, info.user_count+"");
                setText(R.id.tvTotalEarnRate, info.bili*100+"");
            }
        };
        MySubcriber<HomeDataInfo> s = new MySubcriber<HomeDataInfo>(activity, callback, false, "");
        ApiInterface.getHomeData(req, s);
    }

    private void loadBanner() {
        BannerListRequest req = new BannerListRequest();
        req.place = "1";
        HttpResultCallback callback = new HttpResultCallback<List<BannerInfo>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(List<BannerInfo> data) {
                bannerList = data;
                bannerImgRes.clear();
                Observable.from(data)
                    .observeOn(Schedulers.newThread())
                    .map(new Func1<BannerInfo, String>() {
                        @Override
                        public String call(BannerInfo bannerInfo) {
                            return bannerInfo.banner_imgurl;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            setupBannerView();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(String s) {
                            bannerImgRes.add(s);
                        }
                    });
            }
        };
        MySubcriber<List<BannerInfo>> s = new MySubcriber<List<BannerInfo>>(activity, callback, false, "");
        ApiInterface.getBannerList(req, s);
    }

    public void setupBannerView() {
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerImageHolderView();
            }
        }, bannerImgRes)
            .setPageIndicator(new int[]{R.drawable.dot_nor, R.drawable.dot_sel})
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
            .setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    BannerInfo info = bannerList.get(position);
                    Intent it = new Intent(activity, WebLoadActivity.class);
                    it.putExtra(WebLoadFragment.PARAMS_TITLE, info.banner_name);
                    String url = ApiInterface.WAP_BANNER_DETAIL + "?banner_id=" + info.id;
                    it.putExtra(WebLoadFragment.PARAMS_URL, url);
                    startActivity(it);
                }
            });
    }

    public void initListener() {
        getView(R.id.ivSidebar).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivCusSvr).setOnClickListener(new OnLoginClickListener(activity, this));
        ivFlow.setOnClickListener(new OnLoginClickListener(activity, this));

        getView(R.id.rlBeijing28).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlCanada28).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlBeijingK3).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlCqSsc).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlBjSc).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlXyFt).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlJsK3).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlBjl).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlSingapore28).setOnClickListener(new OnLoginClickListener(activity, this));

        getView(R.id.ivBeijingExplain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivCanadaExplain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivBjK3Explain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivCqSscExplain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivBjScExplain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivXyFtExplain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivJsK3Explain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivBjlExplain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivSingapore28Explain).setOnClickListener(new OnLoginClickListener(activity, this));
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startTurning(3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopTurning();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivSidebar:
                startActivity(new Intent(activity, MoreActivity.class));
                activity.overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
                break;

            case R.id.ivCusSvr:
                CheckUtil.startCusSvr(activity);
                break;

            case R.id.rlBeijing28:
                Intent bj = new Intent(activity, LevelSelectActivity.class);
                bj.putExtra("type", 1);
                startActivity(bj);
                break;

            case R.id.rlCanada28:
                Intent ca = new Intent(activity, LevelSelectActivity.class);
                ca.putExtra("type", 2);
                startActivity(ca);
                break;

            case R.id.rlBeijingK3:
                Intent bj2 = new Intent(activity, LevelSelectActivity.class);
                bj2.putExtra("type", 3);
                startActivity(bj2);
                break;

            case R.id.rlCqSsc:
                Intent ca2 = new Intent(activity, LevelSelectActivity.class);
                ca2.putExtra("type", 4);
                startActivity(ca2);
                break;

            case R.id.rlBjSc:
                Intent it5 = new Intent(activity, LevelSelectActivity.class);
                it5.putExtra("type", 5);
                startActivity(it5);
                break;

            case R.id.rlXyFt:
                Intent it6 = new Intent(activity, LevelSelectActivity.class);
                it6.putExtra("type", 6);
                startActivity(it6);
                break;

            case R.id.rlJsK3:
                Intent it7 = new Intent(activity, LevelSelectActivity.class);
                it7.putExtra("type", 7);
                startActivity(it7);
                break;

            case R.id.rlBjl:
                Intent it8 = new Intent(activity, LevelSelectActivity.class);
                it8.putExtra("type", 8);
                startActivity(it8);
                break;

            case R.id.rlSingapore28:
                Intent it9 = new Intent(activity, LevelSelectActivity.class);
                it9.putExtra("type", 9);
                startActivity(it9);
                break;

            case R.id.ivBeijingExplain:
                Intent bjExp = new Intent(activity, WebLoadActivity.class);
                bjExp.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                bjExp.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_BEIJING28_EXPLAIN);
                startActivity(bjExp);
                break;

            case R.id.ivCanadaExplain:
                Intent caExp = new Intent(activity, WebLoadActivity.class);
                caExp.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                caExp.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_CANADA28_EXPLAIN);
                startActivity(caExp);
                break;

            case R.id.ivBjK3Explain:
                Intent bjk3 = new Intent(activity, WebLoadActivity.class);
                bjk3.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                bjk3.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_BJK3_EXPLAIN);
                startActivity(bjk3);
                break;

            case R.id.ivCqSscExplain:
                Intent jsk3 = new Intent(activity, WebLoadActivity.class);
                jsk3.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                jsk3.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_CQSSC_EXPLAIN);
                startActivity(jsk3);
                break;

            case R.id.ivBjScExplain:
                Intent it17 = new Intent(activity, WebLoadActivity.class);
                it17.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                it17.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_BJSC_EXPLAIN);
                startActivity(it17);
                break;

            case R.id.ivXyFtExplain:
                Intent it18 = new Intent(activity, WebLoadActivity.class);
                it18.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                it18.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_XYFT_EXPLAIN);
                startActivity(it18);
                break;

            case R.id.ivJsK3Explain:
                Intent it19 = new Intent(activity, WebLoadActivity.class);
                it19.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                it19.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_JSK3_EXPLAIN);
                startActivity(it19);
                break;

            case R.id.ivBjlExplain:
                Intent it20 = new Intent(activity, WebLoadActivity.class);
                it20.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                it20.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_BJL_EXPLAIN);
                startActivity(it20);
                break;

            case R.id.ivSingapore28Explain:
                Intent it21 = new Intent(activity, WebLoadActivity.class);
                it21.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                it21.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_XYFT_EXPLAIN);
                startActivity(it21);
                break;

            case R.id.ivHomeFlow:
                new HomeFlowDialog(activity).showAsDropDown(ivFlow);
                break;
        }
    }
}
