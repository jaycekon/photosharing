package com.sherlochao.service;

import com.sherlochao.model.FavoriteMember;
import com.sherlochao.model.FavoriteShared;
import com.sherlochao.model.Favorites;

import java.util.List;

public interface FavoritesService {
	/**
	 * 判断查询是否重复
	 * @param id 图书id
	 * @param buyerId 用户id
	 * @return
	 */
	int findcountFav(Integer id,Integer buyerId);
	
	/**
	 * 添加收藏商品
	 * @param id 商品id
	 * @param memberId 用户id
	 * @return
	 */
	void saveFavBook(Integer bookId,Integer memberId);
	
	/**
	 * 取消收藏商品
	 * @param goodsId
	 * @param buyerId
	 */
	void deleteFavBook(Integer bookId,Integer memberId);
	
	/**
	 * 删除收藏信息
	 * @param favorites
	 * 删除时传值：memberId 会员id favId商品id
	 * @return
	 */
	int deleteAllFav(Favorites favorites);

	Integer saveFavMember(FavoriteMember favoriteMember);

	Integer saveFavShared(FavoriteShared favoriteShared);

	FavoriteMember findFavMemberById(Integer memberId, Integer colMemberId);

	FavoriteShared findFavSharedById(Integer memberId, Integer colSharedId);

	Integer updateFavMember(FavoriteMember favoriteMember);

	Integer updateFavShared(FavoriteShared favoriteShared);

	List<FavoriteMember> listFavMemberById(Integer memberId);

	List<FavoriteShared> listFavSharedById(Integer memberId);
}
