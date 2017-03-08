package com.sherlochao.dao;

import com.sherlochao.model.Shared;

import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */
public interface SharedDao {

    Integer saveShared(Shared shared);

    Integer updateShared(Shared shared);

    Shared findByShareId(Integer sharedId);

    List<Shared> listSharedByOtherMemberId(Integer memberId, Integer sharedState);

    List<Shared> listSharedByMemberId(Integer memberId, Integer sharedState);

    List<Shared> listSharedByContent(String content,Integer sharedState, Integer administratorState);

    List<Shared> listSharedInOneMinute(String begintime, String endtime, Integer sharedState, Integer administratorState);

    List<Shared> listSharedByTime(String begintime, String endtime, Integer sharedState, Integer administratorState);
}
