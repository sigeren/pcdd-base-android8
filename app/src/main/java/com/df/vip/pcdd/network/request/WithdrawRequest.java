package com.df.vip.pcdd.network.request;

import com.df.vip.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/27.
 */

public class WithdrawRequest extends BaseRequest {
//    @Encrypt
    public String withdrawals_password; //md5 三次加密
    @Encrypt
    public String fee;
    public String client;
}
