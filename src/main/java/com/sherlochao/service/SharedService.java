package com.sherlochao.service;

import com.sherlochao.model.Shared;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */

public interface SharedService {

    Integer saveShared(Shared shared);

    Integer hideShared(Shared shared);

    Shared findBySharedId(Integer sharedId);

    Integer deleteShared(Shared shared);

    Integer cancelHideShared(Shared shared);

    Integer addSharedView(Shared shared);

    List<Shared> listSharedByMemberId(Integer memberId);

    List<Shared> listSharedByOtherMemberId(Integer memberId);

    List<Shared> listSharedByContent(String content);

    List<Shared> listSharedInOneMinute();

    List<Shared> listSharedByTime(String begintime, String endtime);
}
