package com.hodolog.hodollog.dto;

import com.hodolog.hodollog.domain.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.") // 스프링이 만들어준 title : "must not be blank"
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
