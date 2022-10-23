package com.hodolog.hodollog.controller;

import com.hodolog.hodollog.dto.PostCreate;
import com.hodolog.hodollog.dto.PostEdit;
import com.hodolog.hodollog.dto.PostResponse;
import com.hodolog.hodollog.dto.PostSearch;
import com.hodolog.hodollog.exception.InvalidRequest;
import com.hodolog.hodollog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/v1/posts")
    public String post(@RequestParam String title, @RequestParam String content) {
        log.info("title = {}, content={}", title, content);
        return "test";
    }

    @PostMapping("/v1/posts2")
    public String post2(@RequestParam Map<String, String> params) {
        log.info("params ={}", params.toString());
        return "test2";
    }

    @PostMapping("/v1/posts3")
    public  String post3(@ModelAttribute PostCreate params){
        log.info("params = {}", params.toString());
        return "test3";
    }

    @PostMapping("/v1/posts4")
    public  String post4(@RequestBody @Valid PostCreate params) throws Exception {
        log.info("params={}", params.toString());
        String title = params.getTitle();
        String content = params.getContent();

        // 아래와 같이 사용하는 것은 비효율적이다.
        if(title == null || title.equals("")){
            throw new Exception("타이틀이 없어요.!");
        }

        if(content == null || content.equals("")){
            throw new Exception("콘텐츠가 없어요.!");
        }

        return "test4";
    }

    @PostMapping("/v1/posts5")
    public  Map<String, String> post5(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {
        log.info("params={}", params.toString());
        String title = params.getTitle();
        String content = params.getContent();

        if(result.hasErrors()) {
            List<FieldError> fieldErrorList = result.getFieldErrors();
            FieldError firstFieldError = fieldErrorList.get(0);
            String fieldName = firstFieldError.getField();
            String errorMessage = firstFieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }

        return Map.of();
    }

    @PostMapping("/v1/posts6")
    public  Map<String, String> post6(@RequestBody @Valid PostCreate params /*BindingResult result*/) throws Exception {
        log.info("params={}", params.toString());
        String title = params.getTitle();
        String content = params.getContent();

        // 아래 방식의 문제점 -> 매번 메서드 마다 값을 검증 ㅐㅎ야한다. 개발자가 휴먼 에러를 일읠 수 있다. >> 검증 부분에서 버그가 발생 할 수 있다.
        // 개발자로서 간지가 안난다.
        // HashMap 보다는 응답 클래스를 만들어 주는 것이 중요합니다.
        // 여러개의 응답을 줘야하는 경우 처리가 어려울 수 있음.
        // 세번 이상의 반복적인 작업은 반드시 피해야 한다. -> 코드 && 개발에 관한 모든 것
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html

        return Map.of();
    }

    @PostMapping("/v1/posts7")
    public Map<String, String> post7(@RequestBody @Valid PostCreate params) {

        params.isValid();

        postService.write(params);
        return Map.of();
    }


    // 글 조회기능 추가
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long postId ) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList() {
        return postService.getList();
    }

    @GetMapping("/pageposts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) { // Pageable 앞에 PageableDefault 라고 쓸 수 있다. 혹은 @PageableDefault(size = 10) 이런식으로 가능
        return postService.getListByPageDSL(postSearch);
    }

    // 아래와 같이 받으면 안된다.
//    public List<PostResponse> getList(@RequestParam int page) {
//        return postService.getListByPage(page);
//    }

    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit reuest) {
        return postService.edit(postId, reuest);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

}
