package com.sherlochao.service;

import com.sherlochao.model.SMSValidCode;

public interface SMSValidCodeService {
	int delete(String smsValidcodeId);

    int save(SMSValidCode record);

    SMSValidCode findSMS(SMSValidCode record);

    int updateById(SMSValidCode record);
    
    void updateBySMSId(SMSValidCode record);
}
