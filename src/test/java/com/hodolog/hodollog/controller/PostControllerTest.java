package com.hodolog.hodollog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.hodollog.domain.Post;
import com.hodolog.hodollog.domain.PostEditor;
import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.repository.PostRepository;
import com.hodolog.hodollog.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void setUpDB(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/v1/posts 요청시 test를 출력한다.")
    void test() throws Exception {
        mockMvc.perform(post("/v1/posts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE) // default는 application/json입니다.
                        .param("title", "글 제목입니다.")
                        .param("content", "글 내용입니다. 하하"))
                .andExpect(status().isOk())
                .andExpect(content().string("test"))
                .andDo(print());
    }

    @Test
    @DisplayName("/v1/posts2 요청시 test2를 출력한다.")
    void post2() throws Exception {
        mockMvc.perform(post("/v1/posts2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("title", "글제목")
                .param("content", "글 내용"))
                .andExpect(status().isOk())
                .andExpect(content().string("test2"));
    }

    @Test
    @DisplayName("/v1/posts3 요청시 test3을 출력한다.")
    void post3() throws Exception{
        mockMvc.perform(post("/v1/posts3")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("title", "글제목")
                        .param("content", "글 내용"))
                .andExpect(status().isOk())
                .andExpect(content().string("test3"))
                .andDo(print());
    }

    @Test
    @DisplayName("/v1/posts4 요청시 test4을 출력한다.")
    void post4() throws Exception {
        mockMvc.perform(post("/v1/posts4")
                        .contentType(APPLICATION_JSON)
                        .content("{\"titme\": \"제목입니다.\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("/v1/post4 요청 시 타이틀은 필수입니다.")
    void testPost4() throws Exception {
        mockMvc.perform(post("/v1/posts4")
                        .contentType(APPLICATION_JSON)
                        .content("{\"titme\": \"\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("/v1/post5 요청 시 타이틀은 필수입니다. spring validation을 사용한 처리 방법입니다.")
    void post5() throws Exception {
        mockMvc.perform(post("/v1/posts5")
                        .contentType(APPLICATION_JSON)
                        .content("{\"titme\": \"\", \"content\": \"내용입니다.\"}"))
                .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
                .andDo(print());

        /**
         * MockHttpServletResponse:
         *            Status = 200
         *     Error message = null
         *           Headers = [Content-Type:"application/json"]
         *      Content type = application/json
         *              Body = {"title":"must not be blank"}
         *     Forwarded URL = null
         *    Redirected URL = null
         *           Cookies = []
         */
    }

    @Test
    void post6() throws Exception {
        // 위와 다르게 본래 테스트 대상의 메서드에서 BindingResult result를 제거해줘야 한다.
        mockMvc.perform(post("/v1/posts6")
                        .contentType(APPLICATION_JSON)
                        .content("{\"titme\": \"\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/post7 요청시 DB에 값이 저장된다.")
    void post7() throws Exception {
        // Given
        PostCreate request = PostCreate.builder()
                .title("제목임다.").content("내용입니다.").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String given = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/v1/posts7")
                        .contentType(APPLICATION_JSON)
                        .content(given))
                .andExpect(status().isOk())
                .andDo(print());
        // then
        Assertions.assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목임다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void requestTest() throws Exception {
        // given
        Post post = Post.builder().title("foo").content("bar").build();
        postRepository.save(post);

        // when
        mockMvc.perform( get("/posts/{postId}", post.getId() )
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void getList() throws Exception {
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            postList.add(Post.builder().title("제목"+String.valueOf(i)).content("내용은 없습니다.").build());
        }
        postRepository.saveAll(postList);

        List<PostResponse> result = postService.getList();

        mockMvc.perform(get("/posts").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(100)))
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[50].title").value("제목50"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회 시 페이징 처리")
    void getListByPage() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(0,30)
                .mapToObj( i -> {
                    return Post.builder().title("테스트 제목 - " + i)
                            .content("둔촌주공아파트 - " + i)
                            .build();
                }).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // when
        mockMvc.perform(get("/pageposts?page=1&size=10").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
//        mockMvc.perform(get("/pageposts?page=1&sort=id,desc&size=5").contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void patchPost() throws Exception {
        // given
        Post post = Post.builder().title("호돌맨").content("반포자이").build();
        postRepository.save(post);

        // when
        PostEditor postEditor = PostEditor.builder().title("소련여자").content("반포자이").build();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEditor))
                )
                .andExpect(status().isOk());

        //then
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteTest() throws Exception {
        // Given
        Post post = Post.builder().title("테스트").content("반포자이").build();
        postRepository.save(post);

        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void requestFailTest() throws Exception {

        // when
        mockMvc.perform( get("/posts/{postId}",1L )
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("글 쓰기 요청시 잘못된 값 반환")
    void InvalidTest() throws Exception {
        // Given
        PostCreate request = PostCreate.builder()
                .title("바보새퀴.").content("내용입니다.").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String given = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/v1/posts7")
                        .contentType(APPLICATION_JSON)
                        .content(given))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

}