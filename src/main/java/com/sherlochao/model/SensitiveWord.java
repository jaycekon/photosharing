package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Sherlock_chao on 2016/12/1.
 */
@Data
@ToString
@Entity
@Table(name = "sensitiveWord")
public class SensitiveWord implements Serializable{

    private static final long serialVersionUID = -8974399475739479538L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensitive_word_id")
    private Integer sensitiveWordId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "sensitive_content")
    private String sensitiveContent;

}
