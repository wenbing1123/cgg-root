package com.cgg.service.sms.constants;

public interface SmsConstants {

    String VERIFY_CODE_STR = "0123456789";

    String CFG_NAME_SMS_SEND_ALL_DAILY_LIMIT = "sms_send_all_daily_limit";
    String CFG_NAME_SMS_SEND_TPL_DAILY_LIMIT = "sms_send_tpl_daily_limit";

    String REDIS_COLLECTION_SMS_VERIFY_CODE = "sms:verifyCode:";

}
