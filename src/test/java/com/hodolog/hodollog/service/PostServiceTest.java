package com.hodolog.hodollog.service;

import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.dto.PostSearch;
import com.hodolog.hodollog.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("글 여러개 조회")
    void getList() {
        //given
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            postList.add(Post.builder().title("제목"+String.valueOf(i)).content("내용은 없습니다.").build());
        }
        postRepository.saveAll(postList);

        // when
        List<PostResponse> postResultList = postService.getList();

        assertNotNull(postResultList);
        assertEquals(100, postResultList.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(postList.get(i).getId(), postResultList.get(i).getId());
            assertEquals(postList.get(i).getTitle(), postResultList.get(i).getTitle());
            assertEquals(postList.get(i).getContent(), postResultList.get(i).getContent());
        }
    }

    @Test
    @DisplayName("페이징 처리")
    void getListByPaging() {
        // sql에서 select, limit, offset 같은 것들은 무조건 다 알고 있어야 한다.
        //given
        List<Post> requestPosts = IntStream.range(0,30)
                .mapToObj( i -> {
                    return Post.builder().title("테스트 제목 - " + i)
                            .content("둔촌주공아파트 - " + i)
                            .build();
                }).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        List<PostResponse> posts = postService.getListByPage(0);

        assertEquals(posts.size(), 5);
        for(PostResponse p : posts) {
            System.out.println(p.getTitle());
        }
        assertEquals(posts.get(0).getTitle(), "테스트 제목 - 29");
        assertEquals(posts.get(4).getTitle(), "테스트 제목 - 25");

    }

    @Test
    void getListByPageDSL() {
        List<Post> requestPosts = IntStream.range(0,30)
                .mapToObj( i -> {
                    return Post.builder().title("테스트 제목 - " + i)
                            .content("둔촌주공아파트 - " + i)
                            .build();
                }).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder().page(1).size(10).build();

        List<PostResponse> posts = postService.getListByPageDSL(postSearch);

        for( PostResponse r :  posts ){
            System.out.println(r.getId() + " " + r.getTitle() + " " + r.getContent());
        }

        assertEquals(10L,posts.size());
        assertEquals("테스트 제목 - 29",posts.get(0).getTitle());
    }
}