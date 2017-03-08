package com.sherlochao.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.sherlochao.model.FavoriteMember;
import com.sherlochao.model.FavoriteShared;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sherlochao.dao.FavoritesDao;
import com.sherlochao.model.Favorites;
import com.sherlochao.model.User;

@Repository("favoritesDao")
public class FavoritesDaoImpl implements FavoritesDao{
	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Favorites> queryFavById(Favorites favorites) {
		String hql = "from Favorites where memberId = ? and favId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, favorites.getMemberId());
		query.setInteger(1, favorites.getFavId());
		return query.list();
	}

	@Override
	public void saveFav(Favorites favorites) {
		this.getSession().save(favorites);
	}

	@Override
	public void deleteAllFav(Favorites favorites) {
		String hql = "from Favorites where memberId = ? and favId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, favorites.getMemberId());
		query.setInteger(1, favorites.getFavId());
		this.getSession().delete(query.uniqueResult());
	}

	@Override
	public Integer saveFavMember(FavoriteMember favoriteMember) {
		this.getSession().save(favoriteMember);
		return favoriteMember.getFavoriteMemberId();
	}

	@Override
	public Integer saveFavShared(FavoriteShared favoriteShared) {
		this.getSession().save(favoriteShared);
		return favoriteShared.getFavoriteSharedId();
	}

	@Override
	public List<FavoriteMember> listFavMemberByMemberId(Integer memberId, Integer colMemberIdState) {
		String hql = "from FavoriteMember where memberId = ? and colMemberIdState = ? order by favoriteMemberCreatetime desc ";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, memberId);
		query.setInteger(1, colMemberIdState);
		return (List<FavoriteMember>)query.list();
	}

	@Override
	public List<FavoriteShared> listFavSharedByMemberId(Integer memberId, Integer colSharedIdState) {
		String hql = "from FavoriteShared where memberId = ? and colSharedIdState = ? order by favoriteSharedCreatetime desc ";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, memberId);
		query.setInteger(1, colSharedIdState);
		return (List<FavoriteShared>)query.list();
	}

	@Override
	public FavoriteMember findFavMemberById(Integer memberId, Integer colMemberId) {
		String hql = "from FavoriteMember where memberId = ? and colMemberId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, memberId);
		query.setInteger(1, colMemberId);
		return (FavoriteMember)query.uniqueResult();
	}

	@Override
	public FavoriteShared findFavSharedById(Integer memberId, Integer colSharedId) {
		String hql = "from FavoriteShared where memberId = ? and colSharedId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, memberId);
		query.setInteger(1, colSharedId);
		return (FavoriteShared)query.uniqueResult();
	}

	@Override
	public Integer updateFavMember(FavoriteMember favoriteMember) {
		this.getSession().saveOrUpdate(favoriteMember);
		return favoriteMember.getFavoriteMemberId();
	}

	@Override
	public Integer updateFavShared(FavoriteShared favoriteShared) {
		this.getSession().saveOrUpdate(favoriteShared);
		return favoriteShared.getFavoriteSharedId();
	}
}
