package com.sherlochao.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sherlochao.dao.BookDao;
import com.sherlochao.model.Book;
import com.sherlochao.service.BookService;


@Transactional
@Service("bookService")
public class BookServiceImpl implements BookService{
	@Resource
	private BookDao bookDao;

	@Override
	public void save(Book book) {
		bookDao.save(book);
	}

	@Override
	public Book findBookById(Integer bookId) {
		return bookDao.findBookById(bookId);
	}

	@Override
	public void update(Book book) {
		bookDao.update(book);
	}

	@Override
	public List<Book> findBookByBookClass(Integer bookClass) {
		return bookDao.findBookByBookClass(bookClass);
	}

	@Override
	public List<Book> findBookByClassAndType(Integer bookClass,
			String searchType) {
		return bookDao.findBookByClassAndType(bookClass, searchType);
	}

	@Override
	public List<Book> findBookByKeyword(Integer bookClass, String keyword) {
		return bookDao.findBookByKeyword(bookClass, keyword);
	}

	@Override
	public List<Book> findBookByKeywordAndSearchType(Integer bookClass,
			String keyword, String searchType) {
		return bookDao.findBookByKeywordAndSearchType(bookClass, keyword, searchType);
	}

	@Override
	public List<Book> findBookByIsFav(Integer bookClass, Integer isFav,
			Integer memberId) {
		return bookDao.findBookByIsFav(bookClass, isFav, memberId);
	}

	@Override
	public List<Book> findBookByBookClassAndMemberId(Integer bookClass,
			Integer memberId) {
		return bookDao.findBookByBookClassAndMemberId(bookClass,memberId);
	}

}
