package com.sherlochao.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface GenericDao<T> {
	
	T get(String id);
	
	void save(T entity);
	
	void delete(String id);
	
	T load(String id);
	
	List<T> findAll();
	
	void persist(T entity);
	
	void saveOrUpdate(T entity);
	
	void flush();
	
	/**
	 * 根据条件返回实体集合
	 * @param whereHql			hql语句
	 * @param params			参数
	 * @param orderBy			对应排序方式
	 * @return					实体集合
	 */
	public List<T> findObjectsByConditionWithNoPage(String whereHql,
			Object[] params, LinkedHashMap<String, String> orderBy);

}
