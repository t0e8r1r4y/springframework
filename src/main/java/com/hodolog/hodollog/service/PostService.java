package com.hodolog.hodollog.service;

import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        postRepository.save(postCreate.toEntity());
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        return new PostResponse(post);
    }

    public List<PostResponse> getList() {
        // 아래는 좋지 않은 코드 작성 예시
//        return postRepository.findAll().stream().map(post -> PostResponse.builder()
//                                                                        .post(post)
//                                                                        .build())
//                                                .collect(Collectors.toList());
        return postRepository.findAll().stream().map(PostResponse::new).collect(Collectors.toList());
    }
}
