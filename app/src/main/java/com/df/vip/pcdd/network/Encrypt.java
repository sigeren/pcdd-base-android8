package com.df.vip.pcdd.network;

import android.util.Log;

import com.df.vip.pcdd.app.PcddApp;
import com.df.vip.pcdd.manager.UserInfoManager;
import com.df.vip.pcdd.network.request.BaseRequest;
import com.df.vip.pcdd.util.DateUtil;
import com.df.vip.pcdd.util.MD5Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hang on 2017/2/5.
 */

public class Encrypt {
    public static <T extends BaseRequest>void encrypt(T req) {
        req.user_id = UserInfoManager.getUserId(PcddApp.applicationContext)+"";
        req.timestamp = DateUtil.getCurrDateTime();
//        req.timestamp =System.currentTimeMillis()+"";
        StringBuilder sb = new StringBuilder(PcddApp.APP_KEY);
        sb.append(",").append( req.timestamp).append(",").append(req.user_id);



//        List<Field> fieldList = new ArrayList<Field>();
//        Class<?> clz = req.getClass();
//        //按顺序拼接参数，父类的在前
//        for(; clz!=Object.class; clz=clz.getSuperclass()) {
//            Field[] fields = clz.getDeclaredFields();
//            fieldList.addAll(0, Arrays.asList(fields));
//        }
//        for (Field field : fieldList) {
//            if (field.isAnnotationPresent(com.df.vip.pcdd.annotation.Encrypt.class)) {
//                try {
//                    String value = (String) field.get(req);
//                    sb.append(",").append(value);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        String sign = sb.toString();
//        String sign = "pjpcdd, 2017-11-10 17:07:54,0";

        try {
            sign = MD5Utils.BASE64Encode(MD5Utils.aesEncrypt(sign, PcddApp.APP_SECRET));
            sign = sign.replaceAll("\r\n", "");
            sign = MD5Utils.getMD5String(sign);
            sign = MD5Utils.getMD5String(sign);
            req.sign = sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
