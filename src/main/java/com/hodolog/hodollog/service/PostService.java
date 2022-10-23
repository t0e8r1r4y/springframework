package com.hodolog.hodollog.service;

import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.dto.PostSearch;
import com.hodolog.hodollog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.*;

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


    public List<PostResponse> getListByPage(int page)  {
        Pageable pageable = PageRequest.of(page,5, by(Direction.DESC, "id"));
        return postRepository.findAll(pageable).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public List<PostResponse> getListByPage2(Pageable pageable)  {
        return postRepository.findAll(pageable).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public List<PostResponse> getListByPageDSL(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream().map(PostResponse::new).collect(Collectors.toList());
    }
}
