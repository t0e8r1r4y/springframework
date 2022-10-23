package com.hodolog.hodollog.dto;

import lombok.ToString;

@ToString
public class PostCreate {
    public String title;
    public String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
