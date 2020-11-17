package com.example.demo.model;

public class PageArticleResponse {

    private int id;

    public PageArticleResponse() {
    }

    public PageArticleResponse(ArticleListRequest articleListRequest) {
        this.id = articleListRequest.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
