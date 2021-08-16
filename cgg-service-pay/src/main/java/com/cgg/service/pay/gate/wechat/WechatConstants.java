package com.cgg.service.pay.gate.wechat;

import org.apache.commons.lang3.StringUtils;

public class WechatConstants {

    public static final String PAY_GATE = "wechat";

    public static final String CREATE_IP = "127.0.0.1";
    public static final String TRADE_TYPE = "JSAPI";

    public static final String ATTACH_KEY = "attach";

    // 通信结果
    public static final String RETURN_CODE_SUCCESS = "SUCCESS";
    public static final String RETURN_CODE_FAIL = "FAIL";

    // 业务结果
    public static final String RESULT_CODE_SUCCESS = "SUCCESS";
    public static final String RESULT_CODE_FAIL = "FAIL";

    // 交易状态
    public static final String TRADE_STATE_SUCCESS = "SUCCESS"; //成功
    public static final String TRADE_STATE_REFUND = "REFUND"; //退款
    public static final String TRADE_STATE_NOTPAY = "NOTPAY"; //未支付
    public static final String TRADE_STATE_CLOSED = "CLOSED"; //关闭
    public static final String TRADE_STATE_REVOKED = "REVOKED"; //撤销
    public static final String TRADE_STATE_USERPAYING = "USERPAYING"; //支付中
    public static final String TRADE_STATE_PAYERROR = "PAYERROR"; //支付失败

    public static boolean isPaid(String returnCode, String resultCode) {
        return StringUtils.equals(RETURN_CODE_SUCCESS, returnCode)
                && StringUtils.equals(RESULT_CODE_SUCCESS, resultCode);
    }
}
