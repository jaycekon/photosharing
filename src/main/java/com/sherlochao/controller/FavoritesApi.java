package com.sherlochao.controller;

import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.constant.FavState;
import com.sherlochao.model.FavoriteMember;
import com.sherlochao.model.FavoriteShared;
import com.sherlochao.model.Member;
import com.sherlochao.model.Shared;
import com.sherlochao.service.FavoritesService;
import com.sherlochao.service.MemberService;
import com.sherlochao.service.SharedService;
import com.sherlochao.util.FileUtils;
import com.sherlochao.util.ParamsUtils;
import lombok.extern.slf4j.Slf4j;
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
 * 收藏接口
 * Created by Sherlock_chao on 2016/11/26.
 */
@Slf4j
@Controller
@RequestMapping("/favoritesapi")
public class FavoritesApi {

    @Resource
    private FavoritesService favoritesService;

    @Resource
    private MemberService memberService;

    @Resource
    private SharedService sharedService;


    /**
     * 收藏／取消收藏 用户／分享
     * @param request
     * @param choice 收藏or取消收藏
     * @param type 用户or分享
     * @return
     */
    @RequestMapping("/collect")
    @ResponseBody
    public JSONObject collect(HttpServletRequest request,
                              @RequestParam(value = "choice") Integer choice,
                              @RequestParam(value = "type") Integer type){
        JSONObject jsonObj = new JSONObject();
        try {
            Integer memberId = ParamsUtils.getInt(request
                    .getParameter("memberId"));
            Integer id = ParamsUtils.getInt(request
                    .getParameter("id"));
            //首先判断type
            if(1 == type){ //用户
                if(1 == choice){ //收藏用户
                    Member member = memberService.findByMemberId(id);
                    if(null == member){
                        jsonObj.put("result", 0);
                        jsonObj.put("msg", "无此用户");
                        return jsonObj;
                    }
                    FavoriteMember favoriteMember1 = favoritesService.findFavMemberById(memberId, id);
                    if(null == favoriteMember1){  //第一次 未收藏
                        FavoriteMember favoriteMember = new FavoriteMember();
                        favoriteMember.setMemberId(memberId);
                        favoriteMember.setColMemberId(id);
                        favoritesService.saveFavMember(favoriteMember);
                    }else{ //未收藏
                        Integer colMemberIdState = FavState.COLMEMBER;
                        favoriteMember1.setColMemberIdState(colMemberIdState);
                        favoritesService.updateFavMember(favoriteMember1);
                    }
                }else{
                    FavoriteMember favoriteMember = favoritesService.findFavMemberById(memberId, id);
                    if(null == favoriteMember){
                        jsonObj.put("result", 0);
                        jsonObj.put("msg", "");
                        return jsonObj;
                    }
                    Integer colMemberIdState = FavState.CANCELCOLMEMBER;
                    favoriteMember.setColMemberIdState(colMemberIdState);
                    favoritesService.updateFavMember(favoriteMember);
                }
            }else{//收藏分享
                if(1 == choice){
                    Shared shared = sharedService.findBySharedId(id);
                    if(null == shared){
                        jsonObj.put("result", 0);
                        jsonObj.put("msg", "无此分享");
                        return jsonObj;
                    }
                    Integer colMemberId = ParamsUtils.getInt(request
                            .getParameter("colMemberId")); //获取发该分享的用户id
                    FavoriteShared favoriteShared1 = favoritesService.findFavSharedById(memberId,id);
                    if(null == favoriteShared1){
                        FavoriteShared favoriteShared = new FavoriteShared();
                        favoriteShared.setMemberId(memberId);
                        favoriteShared.setColSharedId(id);
                        favoriteShared.setColMemberId(colMemberId);
                        favoritesService.saveFavShared(favoriteShared);
                    }else{
                        Integer colSharedIdState = FavState.COLSHARED;
                        favoriteShared1.setColSharedIdState(colSharedIdState);
                        favoritesService.updateFavShared(favoriteShared1);
                    }
                }else{
                    FavoriteShared favoriteShared = favoritesService.findFavSharedById(memberId,id);
                    if(null == favoriteShared){
                        jsonObj.put("result", 0);
                        jsonObj.put("msg", "");
                        return jsonObj;
                    }
                    Integer colSharedIdState = FavState.CANCELCOLSHARED;
                    favoriteShared.setColSharedIdState(colSharedIdState);
                    favoritesService.updateFavShared(favoriteShared);
                }
            }
            jsonObj.put("result", 1);
            if(choice == 1)
                jsonObj.put("msg", "收藏成功");
            else
                jsonObj.put("msg", "取消收藏成功");
        } catch (Exception e) {
            log.error("收藏API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }

        return jsonObj;
    }
}
