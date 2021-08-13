package com.cgg.app.bookkeeping.service.impl;

import com.cgg.app.bookkeeping.dao.entity.Customer;
import com.cgg.app.bookkeeping.dao.repository.CustomerRepository;
import com.cgg.app.bookkeeping.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Service("customerService")
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    @Override
    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(customer);
    }

}
