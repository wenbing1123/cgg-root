package com.cgg.framework.exception;

import com.cgg.framework.enums.PredefinedCode;

public class UnconfirmedRuleException extends BizException {

    public UnconfirmedRuleException() {
        super(PredefinedCode.UNCONFIRMED_RULE.getCode(), PredefinedCode.UNCONFIRMED_RULE.getMsg());
    }

    public UnconfirmedRuleException(String retInfo) {
        super(PredefinedCode.UNCONFIRMED_RULE.getCode(), retInfo);
    }

}
