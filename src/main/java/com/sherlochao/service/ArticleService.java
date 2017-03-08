package com.sherlochao.service;

import com.sherlochao.model.Article;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface ArticleService {
    void save(Article article);

    List<Article> findAll();

    Article findById(int articleId);
}
