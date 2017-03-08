package com.sherlochao.service;

import java.util.List;

import com.sherlochao.model.Book;

public interface BookService {
	
	/**
	 * 保存图书
	 * @param book
	 */
	void save(Book book); 
	
	
	Book findBookById(Integer bookId);
	
	void update(Book book);
	
	List<Book> findBookByBookClass(Integer bookClass);
	
	List<Book> findBookByClassAndType(Integer bookClass,String searchType);
	
	List<Book> findBookByKeyword(Integer bookClass,String keyword);
	
	List<Book> findBookByKeywordAndSearchType(Integer bookClass,String keyword,String searchType);
	
	List<Book> findBookByIsFav(Integer bookClass,Integer isFav,Integer memberId);
	
	List<Book> findBookByBookClassAndMemberId(Integer bookClass,Integer memberId);
}
