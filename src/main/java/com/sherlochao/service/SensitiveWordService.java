package com.sherlochao.service;

import com.sherlochao.model.SensitiveWord;

import java.util.List;

/**
 * Created by Sherlock_chao on 2016/12/1.
 */
public interface SensitiveWordService {

    Integer addSensitiveWord(SensitiveWord sensitiveWord);

    List<SensitiveWord> listSensitiveWord();

    SensitiveWord findById(Integer sensitiveWordId);

    void delSensitiveWord(SensitiveWord sensitiveWord);
}
