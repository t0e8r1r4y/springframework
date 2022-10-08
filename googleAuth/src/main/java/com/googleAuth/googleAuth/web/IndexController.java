package com.googleAuth.googleAuth.web;

import com.googleAuth.googleAuth.config.auth.dto.SessionUser;
import com.googleAuth.googleAuth.dto.PostsMainResponseDto;
import com.googleAuth.googleAuth.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("posts",postsService.findAllDesc());
        // userName을 사용할 수 있게 model에 저장
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            model.addAttribute("userName",user.getName());
        }
        return "index";
    }
    // 글 저장
    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    // 글 수정
//    @GetMapping("/posts/update/{id}")
//    public String postsUpdate(@PathVariable Long id, Model model){
//        PostsMainResponseDto dto = postsService.findById(id);
//        model.addAttribute("post",dto);
//
//        return "posts-update";
//    }

}
