package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */

@Data
@ToString
@Entity
@Table(name = "comment")
public class Comment implements Serializable{


    private static final long serialVersionUID = -4431137823341240538L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "comment_content")
    private String commentContent;

    /**
     * 可根据其找出发布者的id
     */
    @Column(name = "shared_id")
    private Integer sharedId;

    /**
     * 评论者的id
     */
    @Column(name = "comment_from_member_id")
    private Integer commentFromMemberId;

    /**
     * 回复者的id 若其存在 则是回复评论者 若不存在 则评论者id是直接回复该分享
     */
    @Column(name = "comment_to_member_id")
    private Integer commentToMemberId;

    @Column(name = "from_member_nickname")
    private String fromMemberNickname;

    @Column(name = "from_member_avatar")
    private String fromMemberAvatar;

    @Column(name = "to_member_nickname")
    private String toMemberNickname;

    @Column(name = "to_member_avatar")
    private String toMemberAvatar;

    @Column(name = "comment_createtime")
    private String commentCreateTime;

    @Column(name = "comment_state")
    private Integer commentState; //1评论 2回复

}
