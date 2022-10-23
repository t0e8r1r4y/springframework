package com.hodolog.hodollog.service;

import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void write() {
        // given
        PostCreate postCreate = PostCreate.builder().title("제목입니다.").content("내용입니다.").build();

        // when
        postService.write(postCreate);

        // then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void get() {
        // given
        Post post = Post.builder().title("제목").content("내용").build();
        postRepository.save(post);

        Long id = 1L;

        // when
        PostResponse result = postService.get(post.getId());

        // then
        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());
        assertEquals(post.getContent(), result.getContent());
    }
}