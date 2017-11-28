package com.df.youxi.network.request;


import com.df.youxi.annotation.Encrypt;

/**
 * Created by hang on 2017/3/19.
 */

public class DuobaoPayRequest extends BaseRequest {
    @Encrypt
    public String order_no;//订单号
    public String fee;//充值金额
    public String pay_type; //1支付宝 2微信
}
