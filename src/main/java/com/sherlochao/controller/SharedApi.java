package com.sherlochao.controller;

import com.sherlochao.bean.*;
import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.constant.FavState;
import com.sherlochao.constant.MemberState;
import com.sherlochao.constant.SharedState;
import com.sherlochao.constant.ThumbsUpState;
import com.sherlochao.model.*;
import com.sherlochao.service.*;
import com.sherlochao.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 分享接口
 * Created by Sherlock_chao on 2016/11/16.
 */
@Slf4j
@Controller
@RequestMapping("/sharedapi")
public class SharedApi {

    @Resource
    private SharedService sharedService;

    @Resource
    private MemberService memberService;

    @Resource
    private ThumbsUpService thumbsUpService;

    @Resource
    private CommentService commentService;

    @Resource
    private FavoritesService favoritesService;

    @Resource
    private SensitiveWordService sensitiveWordService;

    public static final Logger logger = LoggerFactory.getLogger(SharedApi.class);



    /**
     * 发布分享
     * @param request
     * @return
     */
    @RequestMapping("/publishShared")
    @ResponseBody
    public JSONObject publishShared(HttpServletRequest request,
                                    @RequestParam(value = "sharedPhoto",required = false) String[] sharedPhoto){
        JSONObject jsonObj = new JSONObject();
        try {
            String sharedContent = ParamsUtils.getString(request
                    .getParameter("sharedContent"));
            //sharedContent = new String(sharedContent.getBytes("ISO-8859-1"), "UTF-8");
            Integer memberId = ParamsUtils.getInt(request
                    .getParameter("memberId"));
            //如果为游客则返回不可分享
            if(null == memberId){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "请先登陆");
                return jsonObj;
            }
            Member member = memberService.findByMemberId(memberId);
            if(null == member){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "无此用户");
                return jsonObj;
            }

            //如果权限为禁止 则不允许分享
            if(MemberState.PROHIBIT == member.getAdministratorState()){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "管理员已禁止你分享，请联系管理员");
                return jsonObj;
            }

            //检测是否有敏感词，有的话不给分享
            List<SensitiveWord> sensitiveWordList = sensitiveWordService.listSensitiveWord();
            List<String> sensitiveWords = new ArrayList<>();
            for (SensitiveWord sensitiveWord : sensitiveWordList)
                sensitiveWords.add(sensitiveWord.getSensitiveContent());
            SensitivewordFilter filter = new SensitivewordFilter(sensitiveWords);
            Set<String> set = filter.getSensitiveWord(sharedContent, 1);
            if (set.size() > 0){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "分享中有敏感词");
                return jsonObj;
            }

            Shared shared = new Shared();
            shared.setSharedContent(sharedContent);
            shared.setMemberId(memberId);
            if (null != sharedPhoto){
                for (String string : sharedPhoto)
                    System.out.println(string);
                //获取图片 base64编码
                if (sharedPhoto.length > 0) {
                    Map<String, Object> map = BASE64DecodedMultipartFileUtils.Base64Image(sharedPhoto,
                            CommonConstants.FILE_BASEPATH,
                            Constants.SHARE_UPLOAD_URL);
                    if ("true".equals(map.get("success") + "")) {
                        shared.setSharedPhoto(map.get("result") + "");
                    }
                }
            }
            sharedService.saveShared(shared);
            jsonObj.put("result", 1);
            jsonObj.put("msg", "发布成功");
        } catch (Exception e) {
            log.error("发布分享API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }

        return jsonObj;
    }


    /**
     * 用户隐藏,取消隐藏,删除自己的分享
     * @param sharedId
     * @param choice
     * @return
     */
    @RequestMapping("/sharedHide")
    @ResponseBody
    public JSONObject sharedHide(
            @RequestParam(value = "sharedId") Integer sharedId,
            @RequestParam(value = "choice") Integer choice) {
        JSONObject jsonObj = new JSONObject();
        String operation = "";
        try {
            Shared shared = sharedService.findBySharedId(sharedId); //可能有逻辑漏洞 需保证已删除的不被查出来 后续优化
            if(shared == null){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "无此分享");
                return jsonObj;
            }

            Integer sharedId1 = 0;
            if(1 == choice) {
                sharedId1 = sharedService.hideShared(shared); //隐藏
                operation = "隐藏";
            }
            else if (2 == choice) {
                sharedId1 = sharedService.cancelHideShared(shared); //取消隐藏
                operation = "取消隐藏";
            }
            else {
                sharedId1 = sharedService.deleteShared(shared); //删除 3
                operation = "删除";
            }

            if (sharedId1 == sharedId) {
                jsonObj.put("result", 1);
                jsonObj.put("msg", "用户" + operation + "分享成功");
            }

        } catch (Exception e) {
            log.error( "用户" + operation + "自己的分享API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }
        return jsonObj;
    }


    /**
     * 查看分享
     * 本人查看自己的分享不增加浏览量 否则加1 （需考虑多线程问题）
     * 如果是查看自己的 显示出状态 屏蔽or公开  暂时未做
     * 访客也可以查看详情
     * @param sharedId
     * @param memberId
     * @return
     */
    @RequestMapping("/viewShared")
    @ResponseBody
    public JSONObject viewShared(
            @RequestParam(value = "sharedId") Integer sharedId,
            @RequestParam(required = false, value = "memberId" ,defaultValue = "0") Integer memberId) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj = new JSONObject();
            SharedApiBean sharedApiBean = new SharedApiBean();
            Shared shared = sharedService.findBySharedId(sharedId);
            if (null == shared) {
                jsonObj.put("result", 1);
                jsonObj.put("data", "[]");
                jsonObj.put("msg", "无数据");
                return jsonObj;
            }
            MyBeanUtils.copyBeanNotNull2Bean(shared, sharedApiBean);

            //处理图片
            if(null != shared.getSharedPhoto()){
                List<String> list = StringUtils.removeComma(shared.getSharedPhoto());
                sharedApiBean.setPhotos(list);
            }

            //根据memberId查询用户的头像以及名字
            Member member = memberService.findByMemberId(shared.getMemberId());
            sharedApiBean.setMemberNickname(member.getMemberNickname());
            sharedApiBean.setMemberAvatar(member.getMemberAvatar());

            //根据sharedId查询出所有该分享的点赞  按照时间排序
            List<ThumbsUp> thumbsUpList = thumbsUpService.findThumbsUpBySharedId(sharedId);

            //装进bean里面
            if(null != thumbsUpList){
                for (ThumbsUp thumbsUp : thumbsUpList){
                    ThumbUpApiBean thumbUpApiBean = new ThumbUpApiBean();
                    MyBeanUtils.copyBeanNotNull2Bean(thumbsUp, thumbUpApiBean);
                    sharedApiBean.setThumbUps(thumbUpApiBean);
                }
            }

            //根据sharedId查询该分享下的所有评论和回复
            List<Comment> commentList = commentService.findCommentsBySharedId(sharedId);

            //装进bean里面
            if(null != commentList){
                for (Comment comment : commentList){
                    CommentApiBean commentApiBean = new CommentApiBean();
                    MyBeanUtils.copyBeanNotNull2Bean(comment, commentApiBean);
                    sharedApiBean.setComments(commentApiBean);
                }
            }

            Integer sumThumbs = thumbsUpService.countThumbsBySharedId(shared.getSharedId());
            sharedApiBean.setSumThumbs(sumThumbs);

            //查询该用户是否有点过赞
            ThumbsUp thumbsUp = thumbsUpService.findThumbsUpByShareIdAndMemberId(shared.getSharedId(), memberId);
            if (null == thumbsUp || thumbsUp.getThumbsupState() == ThumbsUpState.CANCELTHUMBSUP)
                sharedApiBean.setMemberIsThumbs(ThumbsUpState.CANCELTHUMBSUP);
            else
                sharedApiBean.setMemberIsThumbs(ThumbsUpState.THUMBSUP);


            //查询该用户是否有收藏本分享
            FavoriteShared favoriteShared = favoritesService.findFavSharedById(memberId,shared.getSharedId());
            if(null == favoriteShared || favoriteShared.getColSharedIdState() == FavState.CANCELCOLSHARED)
                sharedApiBean.setMemberIsFavShared(FavState.CANCELCOLSHARED);
            else
                sharedApiBean.setMemberIsFavShared(FavState.COLSHARED);


            jsonObj.put("result", 1);
            jsonObj.put("msg", "获取成功");
            jsonObj.put("data",
                    JSONArray.fromObject(sharedApiBean, JsonUtils.getJsonConfig()));

            if(memberId == shared.getMemberId()){
                //暂时想不到
            }else{
                shared.setSharedView(shared.getSharedView() + 1);
                sharedService.addSharedView(shared);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
            jsonObj.put("data", "[]");
        }

        return jsonObj;
    }

    /**
     * 查看收藏的注册用户或收藏的分享。
     * 浏览量 加1 （需考虑多线程问题）
     * 1 用户 返回id nickname
     * 2 分享 返回id content photo time ==
     * @param memberId
     * @return
     */
    @RequestMapping("/viewCollect")
    @ResponseBody
    public JSONObject viewCollect(
            @RequestParam(value = "memberId") Integer memberId,
            @RequestParam(value = "choice") Integer choice) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj = new JSONObject();
            Member member = memberService.findByMemberId(memberId);
            if (null == member) {
                jsonObj.put("result", 1);
                jsonObj.put("data", "[]");
                jsonObj.put("msg", "无此用户");
                return jsonObj;
            }

            if(1 == choice){ //返回所有收藏的用户
                List<FavoriteMember> favoriteMemberList = favoritesService.listFavMemberById(memberId);
                List<FavMemberApiBean> favMemberApiBeanList = new ArrayList<>();
                for(FavoriteMember favoriteMember : favoriteMemberList){
                    FavMemberApiBean favMemberApiBean = new FavMemberApiBean();
                    MyBeanUtils.copyBeanNotNull2Bean(favoriteMember, favMemberApiBean);
                    Member member1 = memberService.findByMemberId(favoriteMember.getColMemberId());
                    favMemberApiBean.setMemberAvatar(member1.getMemberAvatar());
                    favMemberApiBean.setMemberNickname(member1.getMemberNickname());
                    favMemberApiBeanList.add(favMemberApiBean);
                }
                jsonObj.put("result", 1);
                jsonObj.put("msg", "获取成功");
                jsonObj.put("data",
                        JSONArray.fromObject(favMemberApiBeanList, JsonUtils.getJsonConfig()));
            }else{ //返回收藏的分享
                //首先取出所有收藏过的分享
                List<FavoriteShared> favoriteSharedList = favoritesService.listFavSharedById(memberId);
                List<FavSharedApiBean> favSharedApiBeanList = new ArrayList<>();
                for(FavoriteShared favoriteShared : favoriteSharedList){
                    Member member1 = memberService.findByMemberId(favoriteShared.getColMemberId());
                    if(member1.getAdministratorState() == MemberState.ALLOW){
                        //说明管理员允许
                        Shared shared = sharedService.findBySharedId(favoriteShared.getColSharedId());
                        if(shared.getSharedState() == SharedState.SHAREDPUBLIC){ //公开
                            FavSharedApiBean favSharedApiBean = new FavSharedApiBean();
                            MyBeanUtils.copyBeanNotNull2Bean(favoriteShared, favSharedApiBean);
                            MyBeanUtils.copyBeanNotNull2Bean(shared, favSharedApiBean);
                            if(null != shared.getSharedPhoto()) {
                                List<String> list = StringUtils.removeComma(shared.getSharedPhoto());
                                favSharedApiBean.setPhotos(list);
                            }
                            //查询该用户是否有点过赞
                            ThumbsUp thumbsUp = thumbsUpService.findThumbsUpByShareIdAndMemberId(shared.getSharedId(), memberId);
                            if (null == thumbsUp || thumbsUp.getThumbsupState() == ThumbsUpState.CANCELTHUMBSUP)
                                favSharedApiBean.setMemberIsThumbs(ThumbsUpState.CANCELTHUMBSUP);
                            else
                                favSharedApiBean.setMemberIsThumbs(ThumbsUpState.THUMBSUP);
                            Integer sumThumbs = thumbsUpService.countThumbsBySharedId(shared.getSharedId());
                            favSharedApiBean.setSumThumbs(sumThumbs);
                            favSharedApiBean.setMemberAvatar(member1.getMemberAvatar());
                            favSharedApiBean.setMemberNickname(member1.getMemberNickname());
                            favSharedApiBeanList.add(favSharedApiBean);
                        }
                    }
                }
                jsonObj.put("result", 1);
                jsonObj.put("msg", "获取成功");
                jsonObj.put("data",
                        JSONArray.fromObject(favSharedApiBeanList, JsonUtils.getJsonConfig()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
            jsonObj.put("data", "[]");
        }

        return jsonObj;
    }

    /**
     * 查看自己or其他人的所有分享
     * @param memberId
     * @param choice
     * @return
     */
    @RequestMapping("/viewOwnShared")
    @ResponseBody
    public JSONObject viewOwnShared(
            @RequestParam(value = "memberId",required = false, defaultValue = "0") Integer memberId,
            @RequestParam(value = "otherMemberId",required = false, defaultValue = "0") Integer otherMemberId,
            @RequestParam(value = "choice") Integer choice) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj = new JSONObject();
            List<SharedApiBean> list = new ArrayList<>();
            List<Shared> sharedList = new ArrayList<>();
            if(1 == choice){ //查看自己所有的分享
                Member member = memberService.findByMemberId(memberId);
                if (null == member) {
                    jsonObj.put("result", 1);
                    jsonObj.put("data", "[]");
                    jsonObj.put("msg", "无此用户");
                    return jsonObj;
                }
                sharedList = sharedService.listSharedByMemberId(memberId);
                for(Shared shared : sharedList){
                    SharedApiBean sharedApiBean = new SharedApiBean();
                    MyBeanUtils.copyBeanNotNull2Bean(shared, sharedApiBean);
                    if(null != shared.getSharedPhoto())
                        sharedApiBean.setPhotos(StringUtils.removeComma(shared.getSharedPhoto()));
                    sharedApiBean.setMemberAvatar(member.getMemberAvatar());
                    sharedApiBean.setMemberNickname(member.getMemberNickname());
                    //查询该用户是否有点过赞
                    ThumbsUp thumbsUp = thumbsUpService.findThumbsUpByShareIdAndMemberId(shared.getSharedId(), memberId);
                    if (null == thumbsUp || thumbsUp.getThumbsupState() == ThumbsUpState.CANCELTHUMBSUP)
                        sharedApiBean.setMemberIsThumbs(ThumbsUpState.CANCELTHUMBSUP);
                    else
                        sharedApiBean.setMemberIsThumbs(ThumbsUpState.THUMBSUP);
                    Integer sumThumbs = thumbsUpService.countThumbsBySharedId(shared.getSharedId());
                    sharedApiBean.setSumThumbs(sumThumbs);
                    list.add(sharedApiBean);
                }
            }else{ //查看别人所有的分享
                Member otherMember = memberService.findByMemberId(otherMemberId);
                if(otherMember.getAdministratorState() == MemberState.ALLOW)
                    sharedList = sharedService.listSharedByOtherMemberId(otherMemberId);
                for(Shared shared : sharedList){
                    SharedApiBean sharedApiBean = new SharedApiBean();
                    MyBeanUtils.copyBeanNotNull2Bean(shared, sharedApiBean);
                    if(null != shared.getSharedPhoto())
                        sharedApiBean.setPhotos(StringUtils.removeComma(shared.getSharedPhoto()));
                    sharedApiBean.setMemberAvatar(otherMember.getMemberAvatar());
                    sharedApiBean.setMemberNickname(otherMember.getMemberNickname());
                    //查询该用户是否有点过赞
                    ThumbsUp thumbsUp = thumbsUpService.findThumbsUpByShareIdAndMemberId(shared.getSharedId(), memberId);
                    if (null == thumbsUp || thumbsUp.getThumbsupState() == ThumbsUpState.CANCELTHUMBSUP)
                        sharedApiBean.setMemberIsThumbs(ThumbsUpState.CANCELTHUMBSUP);
                    else
                        sharedApiBean.setMemberIsThumbs(ThumbsUpState.THUMBSUP);
                    //查询该用户是否有收藏本分享
                    FavoriteShared favoriteShared = favoritesService.findFavSharedById(memberId,shared.getSharedId());
                    if(null == favoriteShared || favoriteShared.getColSharedIdState() == FavState.CANCELCOLSHARED)
                        sharedApiBean.setMemberIsFavShared(FavState.CANCELCOLSHARED);
                    else
                        sharedApiBean.setMemberIsFavShared(FavState.COLSHARED);
                    Integer sumThumbs = thumbsUpService.countThumbsBySharedId(shared.getSharedId());
                    sharedApiBean.setSumThumbs(sumThumbs);
                    list.add(sharedApiBean);
                }
            }
            jsonObj.put("result", 1);
            jsonObj.put("msg", "获取成功");
            jsonObj.put("data",
                    JSONArray.fromObject(list, JsonUtils.getJsonConfig()));
        } catch (Exception e) {
            e.printStackTrace();
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
            jsonObj.put("data", "[]");
        }
        return jsonObj;
    }

    /**
     * 简易版搜索
     * @param content
     * @return
     */
    @RequestMapping("/searchShared")
    @ResponseBody
    public JSONObject searchShared(
            @RequestParam(value = "content") String content) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj = new JSONObject();

            List<SharedApiBean> list = new ArrayList<>();
            List<Shared> sharedList = sharedService.listSharedByContent(content);
            for(Shared shared : sharedList){
                SharedApiBean sharedApiBean = new SharedApiBean();
                MyBeanUtils.copyBeanNotNull2Bean(shared, sharedApiBean);
                if(null != shared.getSharedPhoto())
                    sharedApiBean.setPhotos(StringUtils.removeComma(shared.getSharedPhoto()));
                Member member = memberService.findByMemberId(shared.getMemberId());
                sharedApiBean.setMemberNickname(member.getMemberNickname());
                sharedApiBean.setMemberAvatar(member.getMemberAvatar());
                list.add(sharedApiBean);
            }
            jsonObj.put("result", 1);
            jsonObj.put("msg", "获取成功");
            jsonObj.put("data",
                    JSONArray.fromObject(list, JsonUtils.getJsonConfig()));
        } catch (Exception e) {
            e.printStackTrace();
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
            jsonObj.put("data", "[]");
        }
        return jsonObj;
    }


    /**
     * 列出一分钟内新产生的数据
     * @return
     */
    @RequestMapping("/listOneMinuteShared")
    @ResponseBody
    public JSONObject listOneMinuteShared(
            @RequestParam(required = false, value = "memberId" ,defaultValue = "0") Integer memberId) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj = new JSONObject();
            List<SharedApiBean> list = new ArrayList<>();
            List<Shared> shareds = sharedService.listSharedInOneMinute();
            for(Shared shared : shareds){
                SharedApiBean sharedApiBean = new SharedApiBean();
                Member member = memberService.findByMemberId(shared.getMemberId());
                sharedApiBean.setMemberNickname(member.getMemberNickname());
                sharedApiBean.setMemberAvatar(member.getMemberAvatar());
                MyBeanUtils.copyBeanNotNull2Bean(shared, sharedApiBean);
                if(null != shared.getSharedPhoto())
                    sharedApiBean.setPhotos(StringUtils.removeComma(shared.getSharedPhoto()));
                //查询该用户是否有点过赞
                ThumbsUp thumbsUp = thumbsUpService.findThumbsUpByShareIdAndMemberId(shared.getSharedId(), memberId);
                if (null == thumbsUp || thumbsUp.getThumbsupState() == ThumbsUpState.CANCELTHUMBSUP)
                    sharedApiBean.setMemberIsThumbs(ThumbsUpState.CANCELTHUMBSUP);
                else
                    sharedApiBean.setMemberIsThumbs(ThumbsUpState.THUMBSUP);
                Integer sumThumbs = thumbsUpService.countThumbsBySharedId(shared.getSharedId());
                sharedApiBean.setSumThumbs(sumThumbs);
                //查询该用户是否有收藏本分享
                FavoriteShared favoriteShared = favoritesService.findFavSharedById(memberId,shared.getSharedId());
                if(null == favoriteShared || favoriteShared.getColSharedIdState() == FavState.CANCELCOLSHARED)
                    sharedApiBean.setMemberIsFavShared(FavState.CANCELCOLSHARED);
                else
                    sharedApiBean.setMemberIsFavShared(FavState.COLSHARED);
                list.add(sharedApiBean);
            }
            jsonObj.put("result", 1);
            jsonObj.put("msg", "获取成功");
            jsonObj.put("data",
                    JSONArray.fromObject(list, JsonUtils.getJsonConfig()));
        } catch (Exception e) {
            e.printStackTrace();
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
            jsonObj.put("data", "[]");
        }
        return jsonObj;
    }


    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        try {
           response.sendRedirect("http://sso.vdian.net");
        } catch (Exception e) {
        }
    }


}
