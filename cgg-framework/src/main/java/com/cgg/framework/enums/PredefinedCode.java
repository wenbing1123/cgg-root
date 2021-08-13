package com.cgg.framework.enums;

public enum PredefinedCode {

    SUCCESS("0000", "请求成功"),
    NOT_LOGIN("1000", "未登录"),
    FORBIDDEN("1001", "未授权"),
    USER_NOT_EXISTS("1002", "帐户不存在"),
    USERNAME_PASSWORD_ERROR("1003", "用户名或密码错误"),
    USER_LOCKED("1004", "帐户被锁定"),
    USER_DISABLED("1005", "帐户被禁用"),
    USER_EXPIRED("1006", "帐户已过期"),
    USER_PASSWORD_EXPIRED("1007", "密码已过期"),
    CODE_ERROR("1008", "验证码错误"),
    AUTHENTICATION_FAIL("1009", "认证失败"),
    ARGUMENT_ERROR("2000", "参数错误"),
    DATA_NOT_FOUND("3000", "数据不存在"),
    DATA_DUPLICATE("3001", "数据重复"),
    DATA_EXPIRED("3002", "数据过期"),
    DATA_INVALID("3003", "数据失效"),
    UNCONFIRMED_RULE("3100", "不符合规则"),
    BIZ_FAIL("3200", "业务失败"),
    SVC_UNAVAILABLE("4000", "服务不可用"),
    SVC_TIMEOUT("4001", "服务请求超时"),
    SVC_ERROR("4002", "服务请求失败"),
    ACCESS_LIMIT("5000", "当前访问人数过多，请稍后重试"),
    ERROR("6019", "网络繁忙，请稍后重试");

    private final String code;
    private final String msg;

    PredefinedCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static boolean isSuccess(String code) {
        return SUCCESS.code.equals(code);
    }

}
