package com.df.youxi.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.df.youxi.network.ApiInterface;
import com.df.youxi.network.MySubcriber;
import com.df.youxi.network.bean.VersionInfo;
import com.df.youxi.network.HttpResultCallback;
import com.df.youxi.network.request.VersionRequest;
import com.df.youxi.util.ApkUtil;
import com.df.youxi.util.T;


/**
 * Created by hang on 2017/4/2.
 */

public class VersionManager {

    public static void checkVersion(final Context context) {
        try {
            VersionRequest req = new VersionRequest();
            req.client_no = ApkUtil.getVersionCode(context)+"";
            HttpResultCallback<VersionInfo> callback = new HttpResultCallback<VersionInfo>() {
                @Override
                public void onStart() {
                }

                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("aaaa","e "+e.toString());
                }

                @Override
                public void onNext(VersionInfo versionInfo) {
                    try {
                        Log.e("aaaa","versionInfo "+versionInfo.toString());
                        int version = Integer.parseInt(versionInfo.version_no);
                        if(version > ApkUtil.getVersionCode(context))
                            showTipDlg(context, versionInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            MySubcriber s = new MySubcriber(callback);
            ApiInterface.checkVersion(req, s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showTipDlg(final Context context, final VersionInfo versionInfo) {
        if(versionInfo.status == 1) {
            //强制升级
            T.showShort("发现新版本，此版本为强制升级");
            upgrade(context, versionInfo.version_url);
        } else {
            // 建议升级
            new AlertDialog.Builder(context).setTitle("发现新版本："+versionInfo.version_no)
                    .setMessage(versionInfo.update_content)
                    .setPositiveButton("取消", null)
                    .setNegativeButton("升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            upgrade(context, versionInfo.version_url);
                        }
                    }).show();
        }
    }

    public static void upgrade(Context context, String url) {
        Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        it.setData(uri);
        context.startActivity(Intent.createChooser(it, "请选择浏览器"));
    }
}
