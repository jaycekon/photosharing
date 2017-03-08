package com.sherlochao.controller;

import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.constant.CommentState;
import com.sherlochao.model.Comment;
import com.sherlochao.model.Member;
import com.sherlochao.model.Shared;
import com.sherlochao.service.CommentService;
import com.sherlochao.util.FileUtils;
import com.sherlochao.util.JsonUtils;
import com.sherlochao.util.ParamsUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Slf4j
@Controller
@RequestMapping("/commentapi")
public class CommentApi {

    @Resource
    private CommentService commentService;

    /**
     * 评论／回复分享
     * @param request
     * @return
     */
    @RequestMapping("/comment")
    @ResponseBody
    public JSONObject comment(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        try {
            String commentContent = ParamsUtils.getString(request
                    .getParameter("commentContent"));
            Integer sharedId = ParamsUtils.getInt(request
                    .getParameter("sharedId"));
            Integer commentFromMemberId = ParamsUtils.getInt(request
                    .getParameter("commentFromMemberId"));
            Integer commentToMemberId = ParamsUtils.getInt(request
                    .getParameter("commentToMemberId"));
            String fromMemberNickname = ParamsUtils.getString(request
                    .getParameter("fromMemberNickname"));
            String toMemberNickname = ParamsUtils.getString(request
                    .getParameter("toMemberNickname"));
            String toMemberAvatar = ParamsUtils.getString(request
                    .getParameter("toMemberAvatar"));
            String fromMemberAvatar = ParamsUtils.getString(request
                    .getParameter("fromMemberAvatar"));
            Comment comment = new Comment();
            if(null != commentToMemberId) {
                comment.setCommentState(CommentState.REPLY);
                comment.setCommentToMemberId(commentToMemberId);
                comment.setToMemberNickname(toMemberNickname);
                comment.setToMemberAvatar(toMemberAvatar);
            }else
                comment.setCommentState(CommentState.COMMENT);
            comment.setFromMemberNickname(fromMemberNickname);
            comment.setCommentContent(commentContent);
            comment.setCommentFromMemberId(commentFromMemberId);
            comment.setSharedId(sharedId);
            comment.setFromMemberAvatar(fromMemberAvatar);
            Comment comment1 = commentService.saveComment(comment);
            jsonObj.put("result", 1);
            jsonObj.put("msg", "评论／回复成功");
            jsonObj.put("data", JSONArray.fromObject(comment1, JsonUtils.getJsonConfig()));

        } catch (Exception e) {
            log.error("评论／回复分享API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }
        return jsonObj;
    }

    //删除评论
    @RequestMapping("/delcomment")
    @ResponseBody
    public JSONObject delComment(HttpServletRequest request){
        JSONObject jsonObj = new JSONObject();
        try {
            Integer commentId = ParamsUtils.getInt(request
                    .getParameter("commentId"));
            //最好判断一下
            commentService.delComment(commentId);
            jsonObj.put("result", 1);
            jsonObj.put("msg", "删除评论／回复成功");
        } catch (Exception e) {
            log.error("删除评论／回复分享API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }
        return jsonObj;
    }
}
