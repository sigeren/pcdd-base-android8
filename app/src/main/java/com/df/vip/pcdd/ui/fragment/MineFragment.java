package com.df.vip.pcdd.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.df.vip.pcdd.MainActivity;
import com.df.vip.pcdd.R;
import com.df.vip.pcdd.manager.ImageLoadManager;
import com.df.vip.pcdd.manager.UserInfoManager;
import com.df.vip.pcdd.network.ApiInterface;
import com.df.vip.pcdd.network.HttpResultCallback;
import com.df.vip.pcdd.network.MySubcriber;
import com.df.vip.pcdd.network.bean.ShareParamsInfo;
import com.df.vip.pcdd.network.bean.UserInfo;
import com.df.vip.pcdd.network.request.BaseRequest;
import com.df.vip.pcdd.network.request.ModifyUserInfoRequest;
import com.df.vip.pcdd.ui.AboutActivity;
import com.df.vip.pcdd.ui.AccountingRecordActivity;
import com.df.vip.pcdd.ui.BackwaterActivity;
import com.df.vip.pcdd.ui.EditPersonActivity;
import com.df.vip.pcdd.ui.GameRecordFilterActivity;
import com.df.vip.pcdd.ui.GiftListActivity;
import com.df.vip.pcdd.ui.LotteryActivity;
import com.df.vip.pcdd.ui.MyEarningActivity;
import com.df.vip.pcdd.ui.ProxyActivity;
import com.df.vip.pcdd.ui.SettingActivity;
import com.df.vip.pcdd.ui.ShareActivity;
import com.df.vip.pcdd.ui.WalletActivity;
import com.df.vip.pcdd.ui.base.BaseFragment;
import com.df.vip.pcdd.ui.widget.CircleImageView;
import com.df.vip.pcdd.ui.widget.dialog.PickPhotoWindow;
import com.df.vip.pcdd.ui.widget.dialog.ShareMenuWindow;
import com.df.vip.pcdd.util.T;


/**
 * Created by hang on 2017/1/16.
 * 我的
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, PickPhotoWindow.PhotoUploadCallback {

    private CircleImageView rivAvatar;
    private TextView tvNickname;
    private TextView tvPersonalSign;
    private TextView tvUserPoint;

    private PickPhotoWindow photoWindow;
    private ShareMenuWindow shareWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(View rootView) {
        rivAvatar = getView(R.id.rivAvatar);
        tvNickname = getView(R.id.tvNickname);
        tvPersonalSign = getView(R.id.tvPersonalSign);
        tvUserPoint = getView(R.id.tvUserPoint);

        rivAvatar.setOnClickListener(this);
        getView(R.id.llEditPerson).setOnClickListener(this);
        getView(R.id.llGiftExchange).setOnClickListener(this);
        getView(R.id.llWallet).setOnClickListener(this);
        getView(R.id.llBackwater).setOnClickListener(this);
        getView(R.id.llAccountingRecord).setOnClickListener(this);
        getView(R.id.llGameRecord).setOnClickListener(this);
        getView(R.id.llSetting).setOnClickListener(this);
        getView(R.id.llAbout).setOnClickListener(this);
        getView(R.id.llShare).setOnClickListener(this);
        getView(R.id.llMyEarning).setOnClickListener(this);
        getView(R.id.llLottery).setOnClickListener(this);

        photoWindow = new PickPhotoWindow(activity);
        photoWindow.setFragmentContext(this);
        photoWindow.setPhotoUploadCallback(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.rivAvatar:
                photoWindow.showAtBottom();
                break;

            case R.id.llEditPerson:
                startActivity(new Intent(activity, EditPersonActivity.class));
                break;

            case R.id.llGiftExchange:
                startActivity(new Intent(activity, GiftListActivity.class));
                break;

            case R.id.llWallet:
                startActivity(new Intent(activity, WalletActivity.class));
                break;

            case R.id.llBackwater:
                startActivity(new Intent(activity, BackwaterActivity.class));
                break;

            case R.id.llAccountingRecord:
                startActivity(new Intent(activity, AccountingRecordActivity.class));
                break;

            case R.id.llGameRecord:
                startActivity(new Intent(activity, GameRecordFilterActivity.class));
                break;
            case R.id.llSetting:
                activity.startActivityForResult(new Intent(activity, SettingActivity.class), MainActivity.REQ_PERSONAL_CENTER);
                break;

            case R.id.llAbout:
                startActivity(new Intent(activity, AboutActivity.class));
                break;

            case R.id.llMyEarning:
                startActivity(new Intent(activity, MyEarningActivity.class));
                break;

            case R.id.llShare:
                startActivity(new Intent(activity, ProxyActivity.class));
//                if(shareWindow == null) {
//                loadShareParams();
//                } else {
//                    shareWindow.showAtBottom();
//                }
                break;

            case R.id.llLottery:
                Intent lot = new Intent(activity, LotteryActivity.class);
                lot.putExtra(WebLoadFragment.PARAMS_TITLE, "大转盘");
                lot.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_LOTTERY.replace("[user_id]", UserInfoManager.getUserId(activity)+""));
                startActivity(lot);
                break;
        }
    }

    public void loadUserInfo() {
        HttpResultCallback<UserInfo> callback = new HttpResultCallback<UserInfo>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserInfo userInfo) {
                updateView(userInfo);
                UserInfoManager.saveUserInfo(activity, userInfo);
            }
        };
        MySubcriber s = new MySubcriber(activity, callback, false, "");
        ApiInterface.getUserInfo(new BaseRequest(), s);
    }

    public void updateView(UserInfo userInfo) {
        if(!TextUtils.isEmpty(userInfo.user_photo))
            ImageLoadManager.getInstance().displayImage(userInfo.user_photo, rivAvatar);
        tvNickname.setText(userInfo.nick_name);
        tvPersonalSign.setText(userInfo.personal_sign);
        tvUserPoint.setText("¥"+userInfo.point);
    }

    @Override
    public void uploadSucceed(String fileUrl) {
        editAvatar(fileUrl);
    }

    public void editAvatar(final String avatarUrl) {
        ModifyUserInfoRequest req = new ModifyUserInfoRequest();
        req.user_photo = avatarUrl;
        HttpResultCallback<String> callback = new HttpResultCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                T.showShort("设置成功");
                ImageLoadManager.getInstance().displayImage(avatarUrl, rivAvatar);
                UserInfo userInfo = UserInfoManager.getUserInfo(activity);
                userInfo.user_photo = avatarUrl;
                UserInfoManager.saveUserInfo(activity, userInfo);
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {
            }
        };
        MySubcriber s = new MySubcriber(activity, callback, false, "提交中");
        ApiInterface.modifyUserInfo(req, s);
    }

    public void loadShareParams() {
        HttpResultCallback<ShareParamsInfo> callback = new HttpResultCallback<ShareParamsInfo>() {
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
            public void onNext(ShareParamsInfo data) {
//                shareWindow = new ShareMenuWindow(activity);
//                shareWindow.title = data.share_title;
//                shareWindow.content = data.share_content;
//                shareWindow.url = data.share_url;
//                shareWindow.showAtBottom();
                toShare(data.share_url);
            }
        };
        MySubcriber s = new MySubcriber(activity, callback, true, "");
        ApiInterface.getShareParams(new BaseRequest(), s);
    }

    public void toShare(String url) {
        Intent it = new Intent(activity, ShareActivity.class);
        it.putExtra("url", url);
        startActivity(it);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        photoWindow.onActivityResult(requestCode, resultCode, data);
    }
}
