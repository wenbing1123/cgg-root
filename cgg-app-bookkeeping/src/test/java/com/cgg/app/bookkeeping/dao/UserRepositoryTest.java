package com.cgg.app.bookkeeping.dao;

import com.cgg.app.bookkeeping.dao.entity.Customer;
import com.cgg.app.bookkeeping.dao.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.annotation.Resource;
import java.util.Objects;

@SpringBootTest
public class UserRepositoryTest {

    @Resource
    private CustomerRepository customerRepository;

    @Test
    void testSave() {
        Mono<Customer> mono = customerRepository.save(Customer
                .builder()
                .build());

        StepVerifier.create(mono)
                .expectNextMatches(customer -> {
                    if (Objects.isNull(customer.getId())) return false;
                    if (Objects.isNull(customer.getGmtCreate())) return false;
                    return true;
                })
                .expectComplete()
                .verify();
    }

}
