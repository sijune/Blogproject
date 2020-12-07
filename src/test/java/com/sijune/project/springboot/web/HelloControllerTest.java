package com.sijune.project.springboot.web;

import com.sijune.project.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class) //Junit 프레임워크의 SpringRunner라는 클래스를 실행시켜라
@WebMvcTest(controllers = HelloController.class, excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
//@Controller등을 사용할 수 있다.
//excludeFilter를 이용해 스캔대상에서 SpringSecurity를 제거한다.
public class HelloControllerTest {
    @Autowired //스프링이 관리하는 빈 주입을 받는다.
    private MockMvc mvc; // 웹 API 테스트 시 사용(HTTP GET, POST), 스프링 MVC의 시작

    @Test
    @WithMockUser(roles="USER")
    public void hello_return() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) //status검증
                .andExpect(content().string(hello)); //내용 검증
    }

    @Test
    @WithMockUser(roles="USER")
    public void helloDto_return() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount))) //api테스트 시 요청 파라미터 설정, 단 String만 허용
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //json응답을 필드별 검증할 수 있는 메소드, $기준으로 필드명을 명시한다.
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}
