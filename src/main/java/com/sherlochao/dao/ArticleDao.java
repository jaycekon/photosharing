package com.sherlochao.dao;


import com.sherlochao.model.Article;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface ArticleDao {
    void save(Article article);
    List<Article> findAll();
    Article findByArticle(int articleId);
}
