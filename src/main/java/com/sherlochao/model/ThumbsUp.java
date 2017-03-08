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
@Table(name = "thumbsup")
public class ThumbsUp implements Serializable{

    private static final long serialVersionUID = 3793119936483360359L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbsup_id")
    private Integer thumbsupId;

    @Column(name = "shared_id")
    private Integer sharedId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "member_nickname")
    private String memberNickname;

    @Column(name = "thumbsup_createtime")
    private String thumbsupCreatetime;

    @Column(name = "thumbsup_state")
    private Integer thumbsupState; //1点赞 2取消点赞
}
