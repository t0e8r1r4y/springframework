package com.hodolog.hodollog.dto;

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

}
