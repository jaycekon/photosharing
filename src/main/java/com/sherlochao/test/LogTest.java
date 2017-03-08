package com.sherlochao.test;

import com.sherlochao.util.DateUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by Sherlock_chao on 2016/12/6.
 */

public class LogTest {
    public static void main(String[] args){
        Logger logger = LoggerFactory.getLogger(LogTest.class);
        logger.info("{} hello {}",new Date(),"Sherlochao");
    }
}
