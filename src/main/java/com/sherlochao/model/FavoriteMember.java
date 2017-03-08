package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */
@Data
@Entity
@ToString
@Table(name = "favorite_member")
public class FavoriteMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_member_id")
    private Integer favoriteMemberId;

    @Column(name = "col_member_id")
    private Integer colMemberId; //bei收藏的用户

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "favorite_member_createtime")
    private String favoriteMemberCreatetime;

    @Column(name = "col_member_id_state")
    private Integer colMemberIdState;
}
