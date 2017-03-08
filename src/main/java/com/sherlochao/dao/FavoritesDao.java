package com.sherlochao.dao;

import java.util.List;

import com.sherlochao.model.FavoriteMember;
import com.sherlochao.model.FavoriteShared;
import com.sherlochao.model.Favorites;
import com.sherlochao.model.Member;

public interface FavoritesDao {
	
	List<Favorites> queryFavById(Favorites favorites);
	
	void saveFav(Favorites favorites);
	
	void deleteAllFav(Favorites favorites);

	Integer saveFavMember(FavoriteMember favoriteMember);

	Integer saveFavShared(FavoriteShared favoriteShared);

	List<FavoriteMember> listFavMemberByMemberId(Integer memberId, Integer colMemberIdState);

	List<FavoriteShared> listFavSharedByMemberId(Integer memberId, Integer colSharedIdState);

	FavoriteMember findFavMemberById(Integer memberId, Integer colMemberId);

	FavoriteShared findFavSharedById(Integer memberId, Integer colSharedId);

	Integer updateFavMember(FavoriteMember favoriteMember);

	Integer updateFavShared(FavoriteShared favoriteShared);
}
