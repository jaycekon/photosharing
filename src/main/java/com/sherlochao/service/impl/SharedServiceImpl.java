package com.sherlochao.service.impl;

import com.sherlochao.constant.FavState;
import com.sherlochao.constant.SharedState;
import com.sherlochao.dao.SharedDao;
import com.sherlochao.model.Shared;
import com.sherlochao.service.SharedService;
import com.sherlochao.util.DateUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */

@Transactional
@Service("sharedService")
public class SharedServiceImpl implements SharedService {

    @Resource
    private SharedDao sharedDao;

    @Override
    public Integer saveShared(Shared shared) {
        String time = DateUtils.getDate1();
        shared.setSharedCreatetime(time);
        //shared.setSharedCreateTime(DateUtils.toDate(time,null));
        shared.setAdministratorState(SharedState.ALLOW);
        shared.setSharedState(SharedState.SHAREDPUBLIC);
        shared.setSharedView(0);
        return sharedDao.saveShared(shared);
    }

    @Override
    public Integer hideShared(Shared shared) {
        shared.setSharedState(SharedState.SHAREDPEIVATE);
        return sharedDao.updateShared(shared);
    }

    @Override
    @Cacheable(value = "common", key = "'sharedId_'+#sharedId_")
    public Shared findBySharedId(Integer id){
        System.out.println("我执行了这里哈哈哈哈哈");
        return sharedDao.findByShareId(id);
    }

    @Override
    public Integer deleteShared(Shared shared) {
        shared.setSharedState(SharedState.SHAREDDELETE);
        return sharedDao.updateShared(shared);
    }

    @Override
    public Integer cancelHideShared(Shared shared) {
        shared.setSharedState(SharedState.SHAREDPUBLIC);
        return sharedDao.updateShared(shared);
    }

    @Override
    public Integer addSharedView(Shared shared) {
        return sharedDao.updateShared(shared);
    }

    @Override
    public List<Shared> listSharedByMemberId(Integer memberId) {
        Integer sharedState = SharedState.SHAREDDELETE;
        return sharedDao.listSharedByMemberId(memberId, sharedState);
    }

    @Override
    public List<Shared> listSharedByOtherMemberId(Integer memberId) {
        Integer sharedState = SharedState.SHAREDPUBLIC;
        return sharedDao.listSharedByOtherMemberId(memberId, sharedState);
    }

    @Override
    public List<Shared> listSharedByContent(String content) {
        Integer sharedState = SharedState.SHAREDPUBLIC;
        Integer administratorState = SharedState.ALLOW;
        return sharedDao.listSharedByContent(content, sharedState, administratorState);
    }

    @Override
    public List<Shared> listSharedInOneMinute() {
        String begintime = DateUtils.getDate1();
        Date date1= DateUtils.toDate(begintime, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.MINUTE,-1);
        String endtime = DateUtils.getDateStr1(calendar.getTime());
        Integer sharedState = SharedState.SHAREDPUBLIC;
        Integer administratorState = SharedState.ALLOW;
        return sharedDao.listSharedInOneMinute(begintime, endtime, sharedState, administratorState);
    }

    @Override
    public List<Shared> listSharedByTime(String begintime, String endtime) {
        Integer sharedState = SharedState.SHAREDPUBLIC;
        Integer administratorState = SharedState.ALLOW;
        return sharedDao.listSharedByTime(begintime, endtime, sharedState, administratorState);
    }
}
