package com.sherlochao.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Sherlock_chao on 2016/11/17.
 */
@Data
@ToString
public class ThumbUpApiBean implements Serializable {
    private static final long serialVersionUID = -2380569293804533231L;

    private Integer memberId;

    private String memberNickname;

    private String thumbUpCreatetime;
}
