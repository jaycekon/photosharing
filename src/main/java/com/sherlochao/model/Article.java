package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/8.
 */
@ToString
@Data
@Entity
@Table(name = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = -6419102587097277278L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private int articleId;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "topic")
    private String topic;
}
