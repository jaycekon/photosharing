package com.sherlochao.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookApiBean {
	/**
    *
    */
	private static final long serialVersionUID = 2906447779118672379L;

	private String bookId;// id 

	private String isbn; // isbn

	private Long bookCreateDate; // 书籍发布日期

	private String bookName; //书名

    /**
     * 书本默认封面图片
     */
    private String bookImage;

    /**
     * 书本多图
     */
    private String bookImageMore;

	private String author; // 作者

	private String publisher; // 出版社

	private String bookType; // 书本分类

	private String bookNumber; // 书本数量  zan shi wei yong

	private String remark;// 备注

	private Integer bookClass; // 判断书本是 求购的还是出售的 1求购 2出售

	private Integer memberId;
	
	private String isWanted; //想换 或 我有 的书名
	
	private Integer isSussess; //书本是否已交yi 0未交易 1已交易
	
	private Integer isdel;//书本是否删除 0未删除 1删除
	
	private String contact; //联系方式
	
	private Integer isFav;//是否收藏 0未收藏  大于0收藏
}
