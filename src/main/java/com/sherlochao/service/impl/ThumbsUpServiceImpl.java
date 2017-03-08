package com.sherlochao.service.impl;

import com.sherlochao.constant.ThumbsUpState;
import com.sherlochao.dao.ThumbsUpDao;
import com.sherlochao.model.ThumbsUp;
import com.sherlochao.service.ThumbsUpService;
import com.sherlochao.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Transactional
@Service("thumbsUpService")
public class ThumbsUpServiceImpl implements ThumbsUpService{

    @Resource
    private ThumbsUpDao thumbsUpDao;

    @Override
    public Integer saveThumbsUp(ThumbsUp thumbsUp) {
        String time = DateUtils.getDate1();
        thumbsUp.setThumbsupCreatetime(time);
        thumbsUp.setThumbsupState(ThumbsUpState.THUMBSUP);
        return thumbsUpDao.saveThumbsUp(thumbsUp);
    }

    @Override
    public Integer updateThumbsUp(ThumbsUp thumbsUp) {
        String time = DateUtils.getDate1();
        thumbsUp.setThumbsupCreatetime(time); //修改时间
        return thumbsUpDao.updateThumbsUp(thumbsUp);
    }

    @Override
    public ThumbsUp findThumbsUpByShareIdAndMemberId(Integer shareId, Integer memberId) {
        return thumbsUpDao.findThumbsUpByShareIdAndMemberId(shareId,memberId);
    }

    @Override
    public List<ThumbsUp> findThumbsUpBySharedId(Integer sharedId) {
        return thumbsUpDao.findThumbsUpBySharedId(sharedId);
    }

    @Override
    public Integer countThumbsBySharedId(Integer sharedId) {
        Integer thumbsupState = ThumbsUpState.THUMBSUP;
        return thumbsUpDao.countThumbsBySharedId(sharedId,thumbsupState);
    }
}
