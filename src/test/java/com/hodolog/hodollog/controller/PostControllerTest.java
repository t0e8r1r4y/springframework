package com.hodolog.hodollog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titme\": \"제목입니다.\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("test4"))
                .andDo(print());
    }

    @Test
    @DisplayName("/v1/post4 요청 시 타이틀은 필수입니다.")
    void testPost4() throws Exception {
        mockMvc.perform(post("/v1/posts4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titme\": \"\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("test4"))
                .andDo(print());
    }

    @Test
    @DisplayName("/v1/post5 요청 시 타이틀은 필수입니다. spring validation을 사용한 처리 방법입니다.")
    void post5() throws Exception {
        mockMvc.perform(post("/v1/posts5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titme\": \"\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
//                .andExpect(content().string("{}"))
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titme\": \"\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
//                .andExpect(content().string("{}"))
                .andDo(print());
    }
}