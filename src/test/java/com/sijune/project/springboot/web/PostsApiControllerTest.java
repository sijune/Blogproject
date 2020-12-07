package com.sijune.project.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sijune.project.springboot.domain.posts.Posts;
import com.sijune.project.springboot.domain.posts.PostsRepository;
import com.sijune.project.springboot.web.dto.PostsSaveRequestDto;
import com.sijune.project.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //JPA까지 테스트할 수 있도록 해준다. WebMvcTest는 단지 컨트롤러 테스트할 때 사용
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate; //webEnvironment설정시 이용 가능하다. Rest Api를 호출 후 응답을 기다리는 동기 방식

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private PostsRepository postsRepository;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER") //모의 사용자 생성
    public void Posts_Insert() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("author").build();

        String url = "http://localhost:"+port+"/api/v1/posts";

        //when
        //1.
        //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class); //응답을 Long을 받는다.
        //postForObject: post요청에 따른 객체를 응답받는다.
        //postForEntity: post요청에 따른 Entity를 응답받는다.

        //2.
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto))) //JSON변환
                .andExpect(status().isOk());

        //then
        //1.
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_Update() throws Exception {
        //given
        //1. 데이터 저장
        Posts savedPosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        //2. id값 얻고,
        Long updateId = savedPosts.getId();

        //3. 업데이트할 데이터 생성
        String expectedTitle = "title2";
        String expectedContent = "content2";

        //4. 업데이트 객체 생성
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        //5. 테스트할 url 생성
        String url = "http://localhost:"+port+"/api/v1/posts/" + updateId;

        //6. 업데이트할 객체를 http객체안에 넣는다.
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        //7. 테스트 시작, url에 post방식으로 http객체를 던지고, 반환값으로 long을 얻는다.
        //1.
        //ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //2.
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

}
