package com.sherlochao.controller;

import com.sherlochao.bean.SharedApiBean;
import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.constant.FavState;
import com.sherlochao.constant.SharedState;
import com.sherlochao.constant.ThumbsUpState;
import com.sherlochao.model.FavoriteShared;
import com.sherlochao.model.Member;
import com.sherlochao.model.Shared;
import com.sherlochao.model.ThumbsUp;
import com.sherlochao.service.FavoritesService;
import com.sherlochao.service.MemberService;
import com.sherlochao.service.SharedService;
import com.sherlochao.service.ThumbsUpService;
import com.sherlochao.util.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 点赞接口
 * Created by Sherlock_chao on 2016/11/17.
 */
@Slf4j
@Controller
@RequestMapping("/thumbsupapi")
public class ThumbsUpApi {

    @Resource
    private ThumbsUpService thumbsUpService;

    @Resource
    private SharedService sharedService;

    @Resource
    private MemberService memberService;

    @Resource
    private FavoritesService favoritesService;

    public static final Logger logger = LoggerFactory.getLogger(ThumbsUpApi.class);


    /**
     * 点赞／取消点赞
     * @param request
     * @return
     */
    @RequestMapping("/thumbsUp")
    @ResponseBody
    public JSONObject thumbsUp(HttpServletRequest request,
                               @RequestParam(value = "choice") Integer choice){
        JSONObject jsonObj = new JSONObject();
        try {
            Integer memberId = ParamsUtils.getInt(request
                    .getParameter("memberId"));
            Integer sharedId = ParamsUtils.getInt(request
                    .getParameter("sharedId"));
            String memberNickname = ParamsUtils.getString(request.getParameter("memberNickname"));

            //如果为游客则返回不可点赞
            if(null == memberId){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "请先登陆");
                if (log.isInfoEnabled()){
                    log.info("游客不可点赞");
                }
                return jsonObj;
            }
            Member member = memberService.findByMemberId(memberId);
            if(null == member){
                jsonObj.put("result", 0);
                jsonObj.put("msg", "无此用户");
                return jsonObj;
            }

            if(choice == 1){ //点赞
                ThumbsUp thumbsUp1 = thumbsUpService.findThumbsUpByShareIdAndMemberId(sharedId, memberId); //判断数据库里面有没有这玩意
                if (null == thumbsUp1){
                    ThumbsUp thumbsUp = new ThumbsUp();
                    thumbsUp.setSharedId(sharedId);
                    thumbsUp.setMemberId(memberId);
                    thumbsUp.setMemberNickname(memberNickname);
                    thumbsUpService.saveThumbsUp(thumbsUp);
                }else{
                    thumbsUp1.setThumbsupState(ThumbsUpState.THUMBSUP);
                    thumbsUpService.updateThumbsUp(thumbsUp1);
                }
                jsonObj.put("result", 1);
                jsonObj.put("msg", "点赞成功");
            }else{
                ThumbsUp thumbsUp = thumbsUpService.findThumbsUpByShareIdAndMemberId(sharedId, memberId);
                thumbsUp.setThumbsupState(ThumbsUpState.CANCELTHUMBSUP);
                thumbsUpService.updateThumbsUp(thumbsUp);
                jsonObj.put("result", 1);
                jsonObj.put("msg", "取消点赞成功");
            }
        } catch (Exception e) {
            log.error("点赞／取消点赞API出错", e);
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器异常");
        }

        return jsonObj;
    }


    /**
     * 列出点赞榜
     * 首先根据时间统计某段时间内 该分享所获得的点赞总数
     * 需要返回 分享  以及 该分享点赞的总数
     * @param choice
     * 1 1月 2 一周 3 今天
     * @return
     */
    @RequestMapping("/listThumbs")
    @ResponseBody
    public JSONObject listThumbs(@RequestParam(value = "choice") Integer choice,
                                 @RequestParam(required = false, value = "memberId" ,defaultValue = "0")
                                         Integer memberId){
        JSONObject jsonObj = new JSONObject();
        try {
            String endtime = DateUtils.getDate1();
            Date date1= DateUtils.toDate(endtime, null);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            String begintime = "";
            if(choice == 1){
                calendar.add(Calendar.MONTH,-1);
                begintime = DateUtils.getDateStr1(calendar.getTime());
            }else if(choice == 2){
                calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-7);
                begintime = DateUtils.getDateStr1(calendar.getTime());
            }else{
                calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
                begintime = DateUtils.getDateStr1(calendar.getTime());
            }
            List<Shared> sharedList = sharedService.listSharedByTime(begintime, endtime);
            List<SharedApiBean> list = new ArrayList<>();
            for (Shared shared : sharedList){
                Integer sumThumbs = thumbsUpService.countThumbsBySharedId(shared.getSharedId());
                SharedApiBean sharedApiBean = new SharedApiBean();
                Member member = memberService.findByMemberId(shared.getMemberId());
                sharedApiBean.setMemberNickname(member.getMemberNickname());
                sharedApiBean.setMemberAvatar(member.getMemberAvatar());
                MyBeanUtils.copyBeanNotNull2Bean(shared, sharedApiBean);
                if(null != shared.getSharedPhoto())
                    sharedApiBean.setPhotos(StringUtils.removeComma(shared.getSharedPhoto()));
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
                list.add(sharedApiBean);
            }

            //sort
            Comparator<SharedApiBean> comparator = new Comparator<SharedApiBean>(){
                public int compare(SharedApiBean s1, SharedApiBean s2) {
                    //先根据点赞数排序
                    if(s1.getSumThumbs()!=s2.getSumThumbs()){
                        return s2.getSumThumbs()-s1.getSumThumbs();
                    }
                    else{
                        //点赞数相同根据时间排序
                        return s1.getSharedCreatetime().compareTo(s2.getSharedCreatetime());
                    }
                }
            };
            Collections.sort(list,comparator);
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
}
