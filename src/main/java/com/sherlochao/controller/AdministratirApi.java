package com.sherlochao.controller;

import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.constant.CommentState;
import com.sherlochao.constant.MemberState;
import com.sherlochao.model.Comment;
import com.sherlochao.model.Member;
import com.sherlochao.model.Shared;
import com.sherlochao.service.MemberService;
import com.sherlochao.service.SharedService;
import com.sherlochao.util.FileUtils;
import com.sherlochao.util.JsonUtils;
import com.sherlochao.util.ParamsUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 管理员功能
 * Created by Sherlock_chao on 2016/11/17.
 */
@Slf4j
@Controller
@RequestMapping("/administratirapi")
public class AdministratirApi {

    @Resource
    private MemberService memberService;

    @Resource
    private SharedService sharedService;

    /**
     *
     * @param memberId
     * @param choice
     * @return
     */
    @RequestMapping("/authorityMember")
    @ResponseBody
    public JSONObject authorityMember(@RequestParam(value = "memberId") Integer memberId,
                                      @RequestParam(value = "choice") Integer choice){
        JSONObject jsonObj = new JSONObject();
        try {
            Member member = memberService.findByMemberId(memberId);
            if(null == member){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "无此用户");
                return jsonObj;
            }
            String fun = "";
            if(1 == choice){ //允许进行分享
                member.setAdministratorState(MemberState.ALLOW);
                fun = "允许";
            }else{
                member.setAdministratorState(MemberState.PROHIBIT);
                fun = "禁止";
            }
            memberService.updateAuthorityMember(member);
            jsonObj.put("result", 1);
            jsonObj.put("msg", fun + "成功");
        } catch (Exception e) {
            log.error("发布分享API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }

        return jsonObj;
    }

    @RequestMapping("/listMember")
    @ResponseBody
    public JSONObject listMember(){
        JSONObject jsonObj = new JSONObject();
        try {
            List<Member> memberList = memberService.listAllMembers();
            jsonObj.put("result", 1);
            jsonObj.put("msg", "展示所有用户成功");
            jsonObj.put("data", JSONArray.fromObject(memberList, JsonUtils.getJsonConfig()));

        } catch (Exception e) {
            log.error("展示所有用户API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }
        return jsonObj;
    }
}
