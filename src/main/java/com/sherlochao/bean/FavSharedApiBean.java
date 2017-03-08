package com.sherlochao.bean;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/27.
 */
@Data
@ToString
public class FavSharedApiBean {

    private Integer favoriteSharedId;

    private Integer colSharedId;

    private Integer memberId;

    private String favoriteSharedCreatetime;

    private String sharedContent;

    private List photos = new ArrayList(); //图片

    private Integer sharedId;

    private Integer colmemberId; //发布该分享的人的id

    private String sharedCreatetime; //发布时间

    private String memberAvatar; //用户头像

    private String memberNickname; //用户昵称

    private Integer memberIsThumbs; //该登陆的用户是否点过该分享的赞 1点过 2没点过

    private Integer sumThumbs; //该分享的点赞总数

}
