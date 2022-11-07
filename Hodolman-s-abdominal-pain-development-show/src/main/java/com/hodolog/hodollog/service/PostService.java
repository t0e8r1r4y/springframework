package com.hodolog.hodollog.service;

import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.domain.PostEditor;
import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostEdit;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.dto.PostSearch;
import com.hodolog.hodollog.exception.PostNotFound;
import com.hodolog.hodollog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound()); // PostNotFound::new
        return new PostResponse(post);
    }

    public List<PostResponse> getList() {
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

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id).orElseThrow(()->new PostNotFound());

        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor postEditor = postEditorBuilder.title(postEdit.getTitle()).content(postEdit.getContent()).build();

        post.edit(postEditor);

        return new PostResponse(post);

    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound());

        postRepository.delete(post);
    }
}
