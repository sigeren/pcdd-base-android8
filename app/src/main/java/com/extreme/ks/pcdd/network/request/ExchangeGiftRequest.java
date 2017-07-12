package com.extreme.ks.pcdd.network.request;

import com.extreme.ks.pcdd.annotation.Encrypt;

/**
 * Created by hang on 2017/2/28.
 */

public class ExchangeGiftRequest extends BaseRequest {
    public String gift_id;
    @Encrypt
    public String gift_count;
    public String address;
}
