package com.cgg.service.user.constants;

public interface UserConstants {

    String TPL_CODE_REGISTER= "register";
    String TPL_CODE_LOGIN = "login";

    String CFG_NAME_LOGIN_AUTO_REGISTER = "login_auto_register";
    String CFG_NAME_LOGIN_ERROR_COUNT_TO_CAPTCHA = "login_error_count_to_captcha";
    String CFG_NAME_LOGIN_ERROR_COUNT_TO_LOCK = "login_error_count_to_lock";
    String CFG_NAME_LOGIN_ERROR_TIME_WINDOW = "login_error_time_window";

    String REDIS_COLLECTION_LOGIN_CAPTCHA = "user:loginCaptcha:";
    String REDIS_COLLECTION_LOGIN_ERROR_COUNT = "user:loginError:";

}
