package com.sijune.project.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { //Entity Id
    Optional<User> findByEmail(String email); //이메일을 통해 이미가입된 사용자인지 판단
    //NPE를 안전하게 받을 수 있다.
}
