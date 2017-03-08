package com.sherlochao.dao;

import java.util.List;

import com.sherlochao.model.Book;

public interface BookDao {
	public void save(Book book);

	public Book findBookById(Integer bookId);

	public void update(Book book);

	public List<Book> findBookByBookClass(Integer bookClass);

	public List<Book> findBookByClassAndType(Integer bookClass, String searchType);

	public List<Book> findBookByKeyword(Integer bookClass, String keyword);

	public List<Book> findBookByKeywordAndSearchType(Integer bookClass,
			String keyword, String searchType);

	public List<Book> findBookByIsFav(Integer bookClass, Integer isFav,
			Integer memberId);
	
	public List<Book> findBookByBookClassAndMemberId(Integer bookClass,
			Integer memberId);
}
