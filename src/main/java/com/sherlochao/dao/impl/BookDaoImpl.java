package com.sherlochao.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sherlochao.dao.BookDao;
import com.sherlochao.model.Book;

@Repository("bookDao")
public class BookDaoImpl implements BookDao{
	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Book book) {
		this.getSession().save(book);
	}

	@Override
	public Book findBookById(Integer bookId) {
		return (Book)this.getSession().get(Book.class, bookId);
	}

	@Override
	public void update(Book book) {
		this.getSession().saveOrUpdate(book);
	}

	@Override
	public List<Book> findBookByBookClass(Integer bookClass) {
		String hql = "from Book where bookClass = ? and isdel = ? and isSussess = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, bookClass);
		query.setInteger(1, 0);
		query.setInteger(2, 0);
		return query.list();
	}

	@Override
	public List<Book> findBookByClassAndType(Integer bookClass,
			String searchType) {
		String hql = "from Book where bookClass = ? and isdel = ? and isSussess = ? and bookType = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, bookClass);
		query.setInteger(1, 0);
		query.setInteger(2, 0);
		query.setString(3, searchType);
		return query.list();
	}

	@Override
	public List<Book> findBookByKeyword(Integer bookClass, String keyword) {
		String hql = "from Book where bookClass = ? and isdel = ? and isSussess = ? and bookName like ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, bookClass);
		query.setInteger(1, 0);
		query.setInteger(2, 0);
		query.setString(3, "%" + keyword + "%");
		return query.list();
	}

	@Override
	public List<Book> findBookByKeywordAndSearchType(Integer bookClass,
			String keyword, String searchType) {
		String hql = "from Book where bookClass = ? and isdel = ? and isSussess = ? and bookName like ? and bookType = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, bookClass);
		query.setInteger(1, 0);
		query.setInteger(2, 0);
		query.setString(3, "%" + keyword + "%");
		query.setString(4, searchType);
		return query.list();
	}

	@Override
	public List<Book> findBookByIsFav(Integer bookClass, Integer isFav,
			Integer memberId) {
		String hql = "from Book as b,Favorites as f where b.bookClass = ? and b.isdel = ? and b.isSussess = ? and b.memberId = f.memberId";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, bookClass);
		query.setInteger(1, 0);
		query.setInteger(2, 0);
		return query.list();
	}

	@Override
	public List<Book> findBookByBookClassAndMemberId(Integer bookClass,
			Integer memberId) {
		String hql = "from Book where bookClass = ? and isdel = ? and memberId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, bookClass);
		query.setInteger(1, 0);
		query.setInteger(2, memberId);
		return query.list();
	}

}
