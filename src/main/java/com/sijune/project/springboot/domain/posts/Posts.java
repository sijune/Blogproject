package com.sijune.project.springboot.domain.posts;

import com.sijune.project.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본생성자 자동추가
@Entity //JPA 어노테이션, 테이블과 링크된다.
public class Posts extends BaseTimeEntity {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment, PK를 이렇게 설정하는 것이 좋다.
    private Long id;

    @Column(length=500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //안전한 객체 생성이 가능하다. 객체 초기 생성 시 사용, 이후 값변경이 필요하면 메서드를 이용한다.
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
