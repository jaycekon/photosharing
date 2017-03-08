package com.sherlochao.dao;

import com.sherlochao.model.ThumbsUp;

import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
public interface ThumbsUpDao {

    Integer saveThumbsUp(ThumbsUp thumbsUp);

    Integer updateThumbsUp(ThumbsUp thumbsUp);

    ThumbsUp findThumbsUpByShareIdAndMemberId(Integer shareId,Integer memberId);

    List<ThumbsUp> findThumbsUpBySharedId(Integer sharedId);

    Integer countThumbsBySharedId(Integer sharedId, Integer thumbsupState);

}
