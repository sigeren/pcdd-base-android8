package com.df.vip.pcdd.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.df.vip.pcdd.R;
import com.df.vip.pcdd.manager.ImageLoadManager;
import com.df.vip.pcdd.network.ApiInterface;
import com.df.vip.pcdd.network.HttpResultCallback;
import com.df.vip.pcdd.network.MySubcriber;
import com.df.vip.pcdd.network.request.PayCheckRequest;
import com.df.vip.pcdd.ui.base.BaseTopActivity;
import com.df.vip.pcdd.util.ApkUtil;
import com.df.vip.pcdd.util.BitmapTool;
import com.df.vip.pcdd.util.T;

import java.io.File;

/**
 * Created by hang on 2017/1/25.
 * 线上充值2
 */

public class RechargeOnlineSecondActivity extends BaseTopActivity implements View.OnClickListener {

    private static final String PKG_ALI = "com.eg.android.AlipayGphone";
    private static final String PKG_WX = "com.tencent.mm";

    private ImageView ivQr;

    private String title;
    private String orderNo;
    private double fee;
    private String qrUrl;
    private String savePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_online_second);
        init();
    }

    private void init() {
        title = getIntent().getStringExtra("title");
        orderNo = getIntent().getStringExtra("orderNo");
        fee = getIntent().getDoubleExtra("fee", 0);
        qrUrl = getIntent().getStringExtra("qrUrl");
        savePath = Environment.getExternalStorageDirectory()+ File.separator+orderNo+".png";

        initTopBar(title);

        ivQr = getView(R.id.ivQrCode);

        setText(R.id.tvRechargeTips, getString(R.string.label_recharge_scan_qr, title));
        setText(R.id.tvRechargeStepTips, getString(R.string.recharge_step_tips, title, title));
        if(!TextUtils.isEmpty(orderNo) || fee>0) {
            setText(R.id.tvRechargeNo, getString(R.string.label_order_no, orderNo));
            setText(R.id.tvRechargeFee, getString(R.string.label_recharge_fee, fee));
        } else {
            getView(R.id.tvRechargeNo).setVisibility(View.GONE);
            getView(R.id.tvRechargeFee).setVisibility(View.GONE);
            getView(R.id.btnPayCompleted).setVisibility(View.GONE);
        }

        ImageLoadManager.getInstance().displayImage(qrUrl, ivQr);

        getView(R.id.btnPrevious).setOnClickListener(this);
        getView(R.id.btnRecharge).setOnClickListener(this);
        getView(R.id.btnPayCompleted).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPrevious:
                finish();
                break;

            case R.id.btnRecharge:
                Drawable drawable = null;
                Bitmap bitmap = null;
                drawable = ivQr.getDrawable();
                if(drawable == null)
                    return;
                try{
                    if((bitmap = ((BitmapDrawable)drawable).getBitmap()) != null) {
                        if (BitmapTool.saveBitmap2Local(this, bitmap, savePath)) {
                            T.showShort("二维码已保存至"+savePath+"，请打开支付APP");
//                        if(payType == 0) {
//                            startAppToPay(PKG_ALI);
//                        } else if(payType == 1) {
//                            startAppToPay(PKG_WX);
//                        }
                        } else {
                            T.showLong("二维码保存失败，请手动截图保存，然后打开支付APP");
                        }
                    }
                }catch (ClassCastException e){
                    T.showLong("二维码错误");
                }

                break;

            case R.id.btnPayCompleted:
                PayCheckRequest req = new PayCheckRequest();
                req.order_no = orderNo;
                HttpResultCallback<Integer> callback = new HttpResultCallback<Integer>() {
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
                    public void onNext(Integer i) {
                        if(i == 1) {
                            new AlertDialog.Builder(RechargeOnlineSecondActivity.this)
                                    .setMessage("如充值未及时到账，请联系客服")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        } else {
                            T.showShort("正在充值...");
                        }
                    }
                };
                MySubcriber s = new MySubcriber(this, callback, true, "");
                ApiInterface.checkPay(req, s);
                break;
        }
    }

    public void startAppToPay(String pkgName) {
        if(!ApkUtil.startApp(this, pkgName))
            T.showShort("启动应用失败，请手动打开应用支付");
    }
}
