package com.df.vip.pcdd.network.request;

import com.df.vip.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/28.
 */

public class RechargeRequest extends BaseRequest {
    @Encrypt
    public String total_fee;
}
