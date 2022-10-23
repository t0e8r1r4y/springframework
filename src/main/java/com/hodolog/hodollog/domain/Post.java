package com.hodolog.hodollog.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String title;

    @Lob
    private String content;

    public Post() {
    }

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
