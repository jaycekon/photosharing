package com.sherlochao.common;

import java.io.Serializable;

import com.sherlochao.model.Member;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CacheUser implements Serializable{

    private static final long serialVersionUID = -5238744888313423560L;
    /**
     *会员
     */
    private Member member;

}
