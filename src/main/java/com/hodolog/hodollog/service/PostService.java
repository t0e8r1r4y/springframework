package com.hodolog.hodollog.service;

import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        // 기획에 따라서 갈릴 수 있는 부분
        // 일반적으로 그냥 반환 안함
        // 프론트 요청에 따라 저장한 결과를 그냥 반환
        // 저장한 데이터의 PK만 리턴을 응답한다. -> 이게 좀 좋기는 하네
        postRepository.save(postCreate.toEntity());
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse postResponse =  new PostResponse(post);

        return postResponse;
    }
}
