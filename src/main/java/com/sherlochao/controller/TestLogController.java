package com.sherlochao.controller;

import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.model.Book;
import com.sherlochao.model.Member;
import com.sherlochao.util.FileUtils;
import com.sherlochao.util.ParamsUtils;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sherlock_chao on 2016/12/11.
 */
@Controller
@RequestMapping("/test")
public class TestLogController {

    Logger logger = LoggerFactory.getLogger(TestLogController.class);

    @RequestMapping("/testlog")
    @ResponseBody
    public String TestLog(HttpServletRequest request) {
        logger.info("{} hello {}",new Date(),"Sherlochao");
        return "hello";
    }
}
