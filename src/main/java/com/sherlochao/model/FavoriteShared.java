package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */
@Data
@ToString
@Entity
@Table(name = "favorite_shared")
public class FavoriteShared {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_shared_id")
    private Integer favoriteSharedId;

    @Column(name = "col_shared_id")
    private Integer colSharedId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "favorite_shared_createtime")
    private String favoriteSharedCreatetime;

    @Column(name = "col_shared_id_state")
    private Integer colSharedIdState;

    @Column(name = "col_member_id")
    private Integer colMemberId; //发这个分享的用户id

}
