package com.cgg.app.bookkeeping.dao.repository;

import com.cgg.app.bookkeeping.dao.entity.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CustomerRepository extends R2dbcRepository<Customer, Long> {
}
