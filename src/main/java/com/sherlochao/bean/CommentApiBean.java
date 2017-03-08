package com.sherlochao.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Data
@ToString
public class CommentApiBean implements Serializable {

    private static final long serialVersionUID = -2938894701540028481L;

    private Integer commentFromMemberId;

    private String fromMemberNickname;

    private Integer commentToMemberId;

    private String toMemberNickname;

    private String commentContent;

    private String commentCreateTime;

    private String fromMemberAvatar;

    private String toMemberAvatar;

    private Integer commentId;


}
