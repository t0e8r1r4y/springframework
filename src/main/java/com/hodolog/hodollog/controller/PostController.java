package com.hodolog.hodollog.controller;

import com.hodolog.hodollog.dto.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.reflection.XMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class PostController {

//    @RequestMapping(method = RequestMethod.GET, path = "/v1/posts")
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
    public  String post4(@RequestBody PostCreate params) {
        log.info("params={}", params.toString());
        return "test4";
    }

}
