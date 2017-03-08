package com.sherlochao.service.impl;

import com.sherlochao.dao.SensitiveWordDao;
import com.sherlochao.model.SensitiveWord;
import com.sherlochao.service.SensitiveWordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/12/1.
 */

@Transactional
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements SensitiveWordService{

    @Resource
    private SensitiveWordDao sensitiveWordDao;


    @Override
    public Integer addSensitiveWord(SensitiveWord sensitiveWord) {
        return sensitiveWordDao.addSensitive(sensitiveWord);
    }

    @Override
    public List<SensitiveWord> listSensitiveWord() {
        return sensitiveWordDao.listSensitiveWord();
    }

    @Override
    public SensitiveWord findById(Integer sensitiveWordId) {
        return sensitiveWordDao.findById(sensitiveWordId);
    }

    @Override
    public void delSensitiveWord(SensitiveWord sensitiveWord) {
        sensitiveWordDao.delSensitiveWord(sensitiveWord);
    }
}
