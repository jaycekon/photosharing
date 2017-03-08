package com.sherlochao.bean;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Sherlock_chao on 2016/11/26.
 */
@Data
@ToString
public class FavMemberApiBean {

    private Integer colMemberId; //收藏的用户Id

    private Integer memberId; //用户ID

    private String favoriteMemberCreatetime;

    private String memberAvatar; //用户头像

    private String memberNickname; //用户昵称

    private Integer favoriteMemberId;

}
