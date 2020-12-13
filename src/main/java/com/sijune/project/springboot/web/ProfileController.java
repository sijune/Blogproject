package com.sijune.project.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        // 활성화된 profile을 담는다.
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        //filter로 사용될 profile 생성
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");

        //profile이 없다면 "default"를, 있다면 첫 profile을 가져온다.
        String defaultProfile = profiles.isEmpty()?"default":profiles.get(0);


        //filter로 사용한 profile만 걸러낸다.
        //filter로 사용된 profile내 존재하지 않지만, profile 내 데이터가 있다. => 제일 첫번째 profile
        //filter로 사용된 profile내 존재하지 않지만, profile 내 데이터가 없다. => default
        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }
}
