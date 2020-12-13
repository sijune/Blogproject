package com.sijune.project.springboot.web;

import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileControllerUnitTest {

    @Test
    public void real_profile_select() {
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        //profile엔 "real"만 담긴다.
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void not_exist_real_profile_select_first() {
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        //profile엔 "real"만 담긴다.
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void not_exist_real_profile_select_default() {
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        //profile엔 "real"만 담긴다.
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
