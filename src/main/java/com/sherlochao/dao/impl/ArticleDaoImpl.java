package com.sherlochao.dao.impl;

import com.sherlochao.dao.ArticleDao;
import com.sherlochao.model.Article;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;



/**
 * Created by Administrator on 2017/3/8.
 */


@Repository("articleDao")
public class ArticleDaoImpl extends GenericDaoImpl<Article> implements ArticleDao{
    @Override
    public Article findByArticle(int articleId) {
        String hql = "from Article where articleId = ?";
        Query query = super.getSession().createQuery(hql);
        query.setInteger(0,articleId);
        Article article = (Article)query.uniqueResult();
        return article;
    }
}
