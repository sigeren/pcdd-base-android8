package com.df.youxi.impl;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.df.youxi.manager.ImageLoadManager;

/**
 * Created by hang on 2017/1/16.
 */

public class BannerImageHolderView implements Holder<String> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String url) {
        Log.e("aaaa","BannerImageHolderView rul=="+url);
        ImageLoadManager.getInstance().displayImage(url, imageView);
    }
}
