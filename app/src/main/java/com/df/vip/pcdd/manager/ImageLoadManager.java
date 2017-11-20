package com.df.vip.pcdd.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.df.vip.pcdd.ui.RechargeOnlineSecondActivity;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.df.vip.pcdd.R;
import com.df.vip.pcdd.app.PcddApp;
import com.df.vip.pcdd.network.ApiInterface;

public class ImageLoadManager {

	private static ImageLoadManager instance;
	
	private DisplayImageOptions options;
	
	private ImageLoadManager() {
		initImageLoader();
	}
	
	public synchronized static ImageLoadManager getInstance() {
		if(instance == null) {
			synchronized (ImageLoadManager.class) {
				if(instance == null)
					instance = new ImageLoadManager();
			}
		}
		return instance;
	}
	
	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
			    .Builder(PcddApp.applicationContext)
			    .memoryCacheExtraOptions(480, 800) // 
			    .threadPoolSize(15)
			    .threadPriority(Thread.NORM_PRIORITY - 2)  
			    .denyCacheImageMultipleSizesInMemory()  
			    .memoryCache(new UsingFreqLimitedMemoryCache(20 * 1024 * 1024))
			    .memoryCacheSize(20 * 1024 * 1024)    
			    .discCacheSize(100 * 1024 * 1024)    
			    .tasksProcessingOrder(QueueProcessingType.LIFO)
			    .discCacheFileCount(300) 
			    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
			    .writeDebugLogs() // Remove for release app  
			    .build();
		ImageLoader.getInstance().init(config);
		
		options = new DisplayImageOptions.Builder()
	        .cacheInMemory(true)
	        .cacheOnDisc(true)
	        .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
	        .showImageOnFail(R.drawable.p_load_failed)
	        .showImageOnLoading(R.drawable.p_load_failed)
	        .showImageForEmptyUri(R.drawable.p_load_failed)
	        .build();
	}
	
	public void displayImage(String url, ImageView view) {
		displayImage(url, view, options);
	}

	public void displayImage(String url, ImageView view, DisplayImageOptions options) {
		if(!TextUtils.isEmpty(url) && url.contains("http"))
			displayImageByNet(url, view);
		else
			ImageLoader.getInstance().displayImage(ApiInterface.HOST+url, view, options);
	}
	
	public void displayImageByNet(String url, ImageView view) {
		ImageLoader.getInstance().displayImage(url, view, options);
	}
	
	public void loadImage(String uri, ImageLoadingListener listener) {
		ImageLoader.getInstance().loadImage(ApiInterface.HOST+uri, options, listener);
	}
	
	public Bitmap loadImageSyn(String uri) {
		return ImageLoader.getInstance().loadImageSync(ApiInterface.HOST+uri, options);
	}

	/**
	 * 字符串转换成Bitmap类型
	 * @param string
	 * @param activity
	 * @return
	 */
	public Bitmap stringtoBitmap(String string, Activity activity){
		Bitmap bitmap=null;
		try {
			byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
			bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(bitmap==null){
			bitmap= BitmapFactory.decodeResource(activity.getResources(), R.drawable.p_load_failed);
		}
		return bitmap;
	}



}
