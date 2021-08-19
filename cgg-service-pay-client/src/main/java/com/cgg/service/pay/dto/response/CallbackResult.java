package com.cgg.service.pay.dto.response;

import com.cgg.service.pay.enums.PayStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class CallbackResult {
    private String payGate;
    private String subGate;
    private String payOrderSn;
    private String bankOrderSn;
    private LocalDateTime bankOrderDate;
    private String bankStatus;
    private String failReason;
    private String bankAttach;
    private Integer orderStatus;
    private BigDecimal orderAmount;
    private MediaType replyContentType; //回复数据格式
    private Object replyBody; //回复数据
    @Singular("extend") private Map<String,String> extend;

    public boolean isPaid() {
        return PayStatus.isPaid(this.orderStatus);
    }
    public boolean isFailed() {
        return PayStatus.isFailed(this.orderStatus);
    }

}
