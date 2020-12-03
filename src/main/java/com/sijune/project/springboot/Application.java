package com.sijune.project.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //Jpa Auditing 활성화 
@SpringBootApplication //스프링 부트의 자동설정, 스프링 빈 읽기 모두 자동으로 설정
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args); //내장 WAS를 실행, 톰캣 설치가 필요X, Jar파일을 실행하면 된다.
    }
}
