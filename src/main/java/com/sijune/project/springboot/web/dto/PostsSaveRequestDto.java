package com.sijune.project.springboot.web.dto;

import com.sijune.project.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor //기본 생성자만 생성
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder //Builder 어노테이션을 사용해 객체 생성 시 값을 안전하게 넣을 수 있다.
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //도메인 모델을 view layer에서 사용하기 위해서는 아래와 같이 객체를 생성해서 사용한다.
    //직접 entity객체를 view layer에서 사용하지 않도록 주의하자.
    public Posts toEntity() { //Posts 객체를 생성해서 가져온다.
        return Posts.builder().title(title).content(content).author(author).build();
    }
}
