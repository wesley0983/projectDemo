package com.example.demo.controller;

import com.example.demo.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Tcontroller {

    @PostMapping(value = "/article/backlist")
    public PageArticleResponse articleBackList(@RequestBody ArticleListRequest articleListRequest) {
        PageArticleResponse response = new PageArticleResponse(articleListRequest);
        return response;
    }
}
