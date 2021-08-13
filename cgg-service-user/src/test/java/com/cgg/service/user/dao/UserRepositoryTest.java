package com.cgg.service.user.dao;

import com.cgg.service.user.dao.entity.User;
import com.cgg.service.user.dao.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.annotation.Resource;
import java.util.Objects;

@SpringBootTest
public class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void testSave() {
        Mono<User> mono = userRepository.save(User
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
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
