package com.sijune.project.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class) //Junit 프레임워크에 SpringRunner라는 실행자를 실행시켜라
@SpringBootTest //H2 자동으로 실행된다.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void get_savedPosts() {
        //given
        String title = "test111";
        String content = "content111";

        postsRepository.save(Posts.builder().title(title).content(content).author("sijune0525@naver.com").build()); //insert update 진행

        //when
        List<Posts> postsList = postsRepository.findAll(); //테이블에 있는 모든 데이터 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);


    }

    @Test
    public void BaseTimeEntity_Insert(){
        //given
        LocalDateTime now = LocalDateTime.of(2020,12,3,12,0,0);
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        //when
        List<Posts> postsList= postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>> "+posts.getCreatedDate()+" >>>>>>>> "+posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
