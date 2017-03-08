package com.sherlochao.controller;

import com.sherlochao.model.SensitiveWord;
import com.sherlochao.service.SensitiveWordService;
import com.sherlochao.util.DateUtils;
import com.sherlochao.util.JsonUtils;
import com.sherlochao.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Sherlock_chao on 2016/12/1.
 */

@Slf4j
@Controller
@RequestMapping("/sensitiveWordApi")
public class SensitiveWordApi {

    @Resource
    private SensitiveWordService sensitiveWordService;

    /**
     * ,分割
     * 添加
     * @param sensitiveWords
     * @return
     */
    @RequestMapping("/addSensitive")
    @ResponseBody
    public JSONObject addSensitive(
            @RequestParam(value = "sensitiveWords") String sensitiveWords){
        JSONObject jsonObj = new JSONObject();
        try {
            if (null == sensitiveWords){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "请输入敏感词");
                return jsonObj;
            }

            //取出每个关键词
            List<String> sensitiveWordList = StringUtils.removeComma(sensitiveWords);
            for (String sensitiveWord : sensitiveWordList){
                SensitiveWord sensitiveWord1 = new SensitiveWord();
                sensitiveWord1.setSensitiveContent(sensitiveWord);
                sensitiveWord1.setCreateTime(DateUtils.getDate1());
                sensitiveWordService.addSensitiveWord(sensitiveWord1);
            }
            jsonObj.put("result", 1);
            jsonObj.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("添加敏感词API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }

        return jsonObj;
    }

    /**
     * 展示所有敏感词
     * @return
     */
    @RequestMapping("/listSensitive")
    @ResponseBody
    public JSONObject listSensitive(){
        JSONObject jsonObj = new JSONObject();
        try {

            List<SensitiveWord> sensitiveWordList = sensitiveWordService.listSensitiveWord();
            jsonObj.put("result", 1);
            jsonObj.put("msg", "操作成功");
            jsonObj.put("data",
                    JSONArray.fromObject(sensitiveWordList, JsonUtils.getJsonConfig()));


        } catch (Exception e) {
            log.error("展示所有敏感词API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }

        return jsonObj;
    }

    /**
     * 删除敏感词
     * @param sensitiveWordId
     * @return
     */
    @RequestMapping("/delSensitive")
    @ResponseBody
    public JSONObject delSensitive(
            @RequestParam(value = "sensitiveWordId") Integer sensitiveWordId){
        JSONObject jsonObj = new JSONObject();
        try {
            SensitiveWord sensitiveWord = sensitiveWordService.findById(sensitiveWordId);
            if (null == sensitiveWord){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "敏感词不存在");
                return jsonObj;
            }
            sensitiveWordService.delSensitiveWord(sensitiveWord);
            jsonObj.put("result", 1);
            jsonObj.put("msg", "删除成功");
        } catch (Exception e) {
            log.error("删除敏感词API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }
        return jsonObj;
    }
}
