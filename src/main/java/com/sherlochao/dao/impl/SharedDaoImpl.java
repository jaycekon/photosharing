package com.sherlochao.dao.impl;

import com.sherlochao.dao.SharedDao;
import com.sherlochao.model.Shared;
import com.sherlochao.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */
@Repository("sharedDao")
public class SharedDaoImpl implements SharedDao{

    @Resource
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Integer saveShared(Shared shared) {
        this.getSession().save(shared);
        return shared.getSharedId();
    }

    @Override
    public Integer updateShared(Shared shared) {
        this.getSession().saveOrUpdate(shared);
        return shared.getSharedId();
    }

    @Override
    public Shared findByShareId(Integer sharedId) {
        //System.out.println("我执行了这里哈哈哈哈哈");
        String hql = "from Shared where sharedId = ?";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedId);
        return (Shared) query.uniqueResult();
    }

    /**
     * 查看别人
     * @param memberId
     * @param sharedState
     * @return
     */
    @Override
    public List<Shared> listSharedByOtherMemberId(Integer memberId, Integer sharedState) {
        String hql = "from Shared where memberId = ? and sharedState = ? order by sharedCreatetime desc";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, memberId);
        query.setInteger(1, sharedState);
        return query.list();
    }

    /**
     * 查看自己
     * @param memberId
     * @param sharedState
     * @return
     */
    @Override
    public List<Shared> listSharedByMemberId(Integer memberId, Integer sharedState) {
        String hql = "from Shared where memberId = ? and sharedState != ? order by sharedCreatetime desc";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, memberId);
        query.setInteger(1, sharedState);
        return query.list();
    }

    @Override
    public List<Shared> listSharedByContent(String content,Integer sharedState, Integer administratorState) {
        String hql = "from Shared where sharedState = ? and administratorState = ? and sharedContent like ? order by sharedCreatetime desc";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedState);
        query.setInteger(1, administratorState);
        query.setString(2, "%" + content + "%");
        return query.list();
    }

    /**
     * 小于当前时间的都列出来
     * @param begintime
     * @param endtime
     * @param sharedState
     * @param administratorState
     * @return
     */
    @Override
    public List<Shared> listSharedInOneMinute(String begintime, String endtime, Integer sharedState, Integer administratorState) {
        String hql = "from Shared where sharedState = ? and administratorState = ? and sharedCreatetime <= ? order by sharedCreatetime desc";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedState);
        query.setInteger(1, administratorState);
        query.setString(2, begintime);
        //query.setString(3, endtime);
        return query.list();
    }

    @Override
    public List<Shared> listSharedByTime(String begintime, String endtime, Integer sharedState, Integer administratorState) {
        String hql = "from Shared where sharedState = ? and administratorState = ? and sharedCreatetime >= ? and sharedCreatetime <= ?";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sharedState);
        query.setInteger(1, administratorState);
        query.setString(2, begintime);
        query.setString(3, endtime);
        return query.list();
    }
}
