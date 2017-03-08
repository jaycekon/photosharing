package com.sherlochao.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Data
@ToString
public class SharedApiBean implements Serializable{


    private static final long serialVersionUID = -8873308483509447280L;

    private Integer sharedId;

    private String sharedContent;

    private String sharedCreatetime;

    private Date sharedCreateTime;

    private Integer sharedView;

    private Integer memberId;

    private String memberAvatar;

    private String memberNickname;

    private List thumbUps = new ArrayList();

    private List comments = new ArrayList();

    private List photos = new ArrayList(); //图片

    public void setThumbUps(ThumbUpApiBean thumbUpApiBean){
        thumbUps.add(thumbUpApiBean);
    }

    public void setComments(CommentApiBean commentApiBean){
        comments.add(commentApiBean);
    }

    private Integer sumThumbs; //该分享的点赞总数

    private Integer memberIsThumbs; //该登陆的用户是否点过该分享的赞 1点过 2没点过

    private Integer memberIsFavShared; //该登陆的用户是否收藏过该分享 1已收藏 2未收藏


}
