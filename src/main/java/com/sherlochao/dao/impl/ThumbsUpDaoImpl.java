package com.sherlochao.dao.impl;

import com.sherlochao.dao.ThumbsUpDao;
import com.sherlochao.model.ThumbsUp;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Repository("thumbsUpDao")
public class ThumbsUpDaoImpl implements ThumbsUpDao {

    @Resource
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public Integer saveThumbsUp(ThumbsUp thumbsUp) {
        this.getSession().save(thumbsUp);
        return thumbsUp.getThumbsupId();
    }

    @Override
    public Integer updateThumbsUp(ThumbsUp thumbsUp) {
        this.getSession().saveOrUpdate(thumbsUp);
        return thumbsUp.getThumbsupId();
    }

    @Override
    public ThumbsUp findThumbsUpByShareIdAndMemberId(Integer sharedId, Integer memberId) {
        String hql = "from ThumbsUp where sharedId = ? and memberId = ?";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedId);
        query.setInteger(1, memberId);
        return (ThumbsUp) query.uniqueResult();
    }

    @Override
    public List<ThumbsUp> findThumbsUpBySharedId(Integer sharedId) {
        String hql = "from ThumbsUp where sharedId = ? order by thumbsupCreatetime asc";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedId);
        return query.list();
    }

    @Override
    public Integer countThumbsBySharedId(Integer sharedId,Integer thumbsupState) {
        String hql = "from ThumbsUp where sharedId = ? and thumbsupState = ?";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedId);
        query.setInteger(1, thumbsupState);
        return query.list().size();
    }

}
