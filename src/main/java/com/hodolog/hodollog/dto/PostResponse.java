package com.hodolog.hodollog.dto;

import com.hodolog.hodollog.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle().substring(0, Math.min(post.getTitle().length(), 10) ); // 요런 요구사항이 있으면 이것도 따로 테스트로 구현하는 것도 중요함
        this.content = post.getContent();
    }
}
