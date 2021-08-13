package com.cgg.service.sms.dao.repository;

import com.cgg.service.sms.dao.entity.SmsLog;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface SmsLogRepository extends R2dbcRepository<SmsLog, Long> {

    @Query("select count(id) from t_sms_log t where t.phone = :phone and to_days(t.gmt_create) = to_days(now())")
    Mono<Integer> countSameDayByPhone(String phone);

    @Query("select count(id) from t_sms_log t where t.phone = :phone and t.tpl_code = :tplCode and to_days(t.gmt_create) = to_days(now())")
    Mono<Integer> countSameDayByPhoneAndTplCode(String phone, String tplCode);

}
