package com.sherlochao.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import com.sherlochao.constant.FavState;
import com.sherlochao.model.FavoriteMember;
import com.sherlochao.model.FavoriteShared;
import com.sherlochao.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sherlochao.dao.FavoritesDao;
import com.sherlochao.model.Book;
import com.sherlochao.model.Favorites;
import com.sherlochao.service.FavoritesService;

@Transactional
@Service("favoritesService")
@Slf4j
public class FavoritesServiceImpl implements FavoritesService {

	@Resource
	private FavoritesDao favoritesDao;

	@Override
	public int findcountFav(Integer id, Integer buyerId) {
		int result = 0;
		Favorites favorites = new Favorites();
		favorites.setFavId(id);
		favorites.setMemberId(buyerId);
		List<Favorites> list = favoritesDao.queryFavById(favorites);
		result = list.size();
		favorites = null;// 释放内存
		return result;
	}


	@Override
	public void saveFavBook(Integer bookId, Integer memberId) {
		Favorites favorites = new Favorites();
		favorites.setFavId(bookId);
		favorites.setMemberId(memberId);
		favorites.setFavTime(System.currentTimeMillis());// 收藏时间
		favoritesDao.saveFav(favorites);
	}

	@Override
	public void deleteFavBook(Integer bookId, Integer memberId) {
		Favorites favorites = new Favorites();
		favorites.setMemberId(memberId);
		favorites.setFavId(bookId);
		favoritesDao.deleteAllFav(favorites);
	}


	@Override
	public int deleteAllFav(Favorites favorites) {
		int result = 0;
		try {
			favoritesDao.deleteAllFav(favorites);
			result = 1;
		} catch (Exception e) {
			result = 0;
			log.error("删除失败！"+e.getMessage());
		}
		return result;
	}

	@Override
	public Integer saveFavMember(FavoriteMember favoriteMember) {
		String time = DateUtils.getDate1();
		favoriteMember.setFavoriteMemberCreatetime(time);
		favoriteMember.setColMemberIdState(FavState.COLMEMBER);
		return favoritesDao.saveFavMember(favoriteMember);
	}

	@Override
	public Integer saveFavShared(FavoriteShared favoriteShared) {
		String time = DateUtils.getDate1();
		favoriteShared.setFavoriteSharedCreatetime(time);
		favoriteShared.setColSharedIdState(FavState.COLSHARED);
		return favoritesDao.saveFavShared(favoriteShared);
	}

	@Override
	public FavoriteMember findFavMemberById(Integer memberId, Integer colMemberId) {
		return favoritesDao.findFavMemberById(memberId, colMemberId);
	}

	@Override
	public FavoriteShared findFavSharedById(Integer memberId, Integer colSharedId) {

		return favoritesDao.findFavSharedById(memberId, colSharedId);
	}

	@Override
	public Integer updateFavMember(FavoriteMember favoriteMember) {
		String time = DateUtils.getDate1();
		favoriteMember.setFavoriteMemberCreatetime(time);
		return favoritesDao.updateFavMember(favoriteMember);
	}

	@Override
	public Integer updateFavShared(FavoriteShared favoriteShared) {
		String time = DateUtils.getDate1();
		favoriteShared.setFavoriteSharedCreatetime(time);
		return favoritesDao.updateFavShared(favoriteShared);
	}

	@Override
	public List<FavoriteMember> listFavMemberById(Integer memberId) {
		Integer colMemberIdState = FavState.COLMEMBER;
		return favoritesDao.listFavMemberByMemberId(memberId, colMemberIdState);
	}

	@Override
	public List<FavoriteShared> listFavSharedById(Integer memberId) {
		//如果管理员禁止了该用户 则其收藏的分享应该是看不到的 or 用户屏蔽了之后 也应该是看不到的
		Integer colSharedIdState = FavState.COLSHARED;
		return favoritesDao.listFavSharedByMemberId(memberId, colSharedIdState);
	}

}
