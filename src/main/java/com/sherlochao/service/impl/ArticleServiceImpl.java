package com.sherlochao.service.impl;

import com.sherlochao.dao.ArticleDao;
import com.sherlochao.model.Article;
import com.sherlochao.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@Transactional
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Override
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    @Override
    public Article findById(int articleId) {
        return articleDao.findByArticle(articleId);
    }
}
