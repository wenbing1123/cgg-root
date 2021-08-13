package com.cgg.app.bookkeeping.controller.v1;

import com.cgg.app.bookkeeping.controller.v1.parammodel.CustomerCreatePM;
import com.cgg.app.bookkeeping.controller.v1.parammodel.CustomerListPM;
import com.cgg.app.bookkeeping.controller.v1.viewmodel.CustomerCreateVM;
import com.cgg.app.bookkeeping.controller.v1.viewmodel.CustomerListVM;
import com.cgg.app.bookkeeping.dao.entity.Customer;
import com.cgg.app.bookkeeping.service.CustomerService;
import com.cgg.framework.dto.response.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("v1/customers")
@Tag(name = "客户服务")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @GetMapping
    @Operation(tags = "客户列表")
    public Mono<Page<CustomerListVM>> list(CustomerListPM pm) {
        // TODO
        return Mono.empty();
    }

    @PostMapping
    @Operation(tags = "创建客户")
    public Mono<CustomerCreateVM> create(CustomerCreatePM pm) {
        // TODO
        return customerService.save(Customer
                    .builder()
                    .build())
                .map(customer -> CustomerCreateVM
                        .builder()
                        .build());

    }

}
