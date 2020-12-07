package com.sijune.project.springboot.config.auth;

import com.sijune.project.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and().authorizeRequests().antMatchers("/", "/css/**","/images/**", "/js/**", "/h2-console/**").permitAll()
                                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                                        .anyRequest().authenticated() //인증된 사용자만 허용가능
                .and().logout().logoutSuccessUrl("/")
                .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
                //userInfoEndpoint: 로그인 성공시 사용자 정보를 가져올 때 설정 담당
                //userService: 이후 후속초치 즉, 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시(ex. 가입, 정보수정, 세션 저장 등등)

    }
}
