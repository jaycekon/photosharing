package com.sherlochao.dao.impl;

import com.sherlochao.dao.SensitiveWordDao;
import com.sherlochao.model.SensitiveWord;
import com.sherlochao.model.Shared;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/12/1.
 */
@Repository("sensitiveWordDao")
public class SensitiveWordDaoImpl implements SensitiveWordDao {

    @Resource
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Integer addSensitive(SensitiveWord sensitiveWord) {
        this.getSession().save(sensitiveWord);
        return sensitiveWord.getSensitiveWordId();
    }

    @Override
    public List<SensitiveWord> listSensitiveWord() {
        String hql = "from SensitiveWord";
        Query query = this.getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public SensitiveWord findById(Integer sensitiveWordId) {
        String hql = "from SensitiveWord where sensitiveWordId = ?";
        Query query = this.getSession().createQuery(hql);
        query.setInteger(0, sensitiveWordId);
        return (SensitiveWord) query.uniqueResult();
    }

    @Override
    public void delSensitiveWord(SensitiveWord sensitiveWord) {
        this.getSession().delete(sensitiveWord);
    }
}
