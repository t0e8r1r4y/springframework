package com.hodolog.hodollog.controller;

import org.hibernate.annotations.common.reflection.XMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @RequestMapping(method = RequestMethod.GET, path = "/v1/posts")
    public String get() {
        return "test";
    }

}
