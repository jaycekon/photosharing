package com.sherlochao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

@ToString
@Data
@Entity
@Table(name = "book")
public class Book implements Serializable{

	/**
    *
    */
	private static final long serialVersionUID = 2906447779118672379L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Integer bookId;// id 

	@Column(name = "isbn")
	private String isbn; // isbn

	@Column(name = "book_createDate")
	private Long bookCreateDate; // 书籍发布日期

	@Column(name = "book_name")
	private String bookName; //书名

    /**
     * 书本默认封面图片
     */
	@Column(name = "book_image")
    private String bookImage;

    /**
     * 书本多图
     */
	@Column(name = "book_image_more")
    private String bookImageMore;

	@Column(name = "author")
	private String author; // 作者

	@Column(name = "publisher")
	private String publisher; // 出版社

	@Column(name = "book_type")
	private String bookType; // 书本分类

	@Column(name = "book_number")
	private String bookNumber; // 书本数量  zan shi wei yong

	@Column(name = "remark")
	private String remark;// 备注

	@Column(name = "book_class")
	private Integer bookClass; // 判断书本是 求购的还是出售的 1求购 2出售

	@Column(name = "member_id")
	private Integer memberId;
	
	@Column(name = "is_wanted")
	private String isWanted; //想换 或 我有 的书名
	
	@Column(name = "is_sussess")
	private Integer isSussess; //书本是否已交yi 0未交易 1已交易
	
	@Column(name = "is_del")
	private Integer isdel;//书本是否删除 0未删除 1删除
	
	@Column(name = "contact")
	private String contact; //联系方式
	
	
}
