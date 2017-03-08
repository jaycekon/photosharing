package com.sherlochao.controller;

import com.sherlochao.model.Article;
import com.sherlochao.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@Slf4j
@Controller
@RequestMapping("/articleapi")
public class ArticleApi {
    @Resource
    private ArticleService articleService;


    @RequestMapping("/addArticle")
    @ResponseBody
    public Object addArticle(Article article){
        articleService.save(article);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","success");
        return jsonObject;
    }

    @RequestMapping("/listArticle")
    @ResponseBody
    public Object listArticle(){
        List<Article> list = articleService.findAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("articles",list);
        return jsonObject;
    }

    @RequestMapping("/getArticle")
    @ResponseBody
    public Object findAticle(Integer articleId){
        Article article = articleService.findById(articleId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article",article);
        return jsonObject;
    }
}
