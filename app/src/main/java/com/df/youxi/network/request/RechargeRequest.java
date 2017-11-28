package com.df.youxi.network.request;

import com.df.youxi.annotation.Encrypt;

/**
 * Created by hang on 2017/2/28.
 */

public class RechargeRequest extends BaseRequest {
    @Encrypt
    public String total_fee;
}
