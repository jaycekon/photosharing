package com.sherlochao.dao.impl;

import com.sherlochao.dao.CommentDao;
import com.sherlochao.model.Comment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Repository("commentDao")
public class CommentDaoImpl implements CommentDao{

    @Resource
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public Comment saveComment(Comment comment) {
        this.getSession().save(comment);
        return comment;
    }

    @Override
    public Integer updateComment(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> findCommentsBySharedId(Integer sharedId) {
        String hql = "from Comment where sharedId = ? order by commentCreateTime";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedId);
        return query.list();
    }

    @Override
    public void delComment(Integer commentId) {
        Comment comment = (Comment) this.getSession().load(Comment.class,commentId);
        this.getSession().delete(comment);
    }
}
