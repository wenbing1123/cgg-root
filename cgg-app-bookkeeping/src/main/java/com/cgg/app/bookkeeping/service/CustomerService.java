package com.cgg.app.bookkeeping.service;

import com.cgg.app.bookkeeping.dao.entity.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> save(Customer customer);

}
