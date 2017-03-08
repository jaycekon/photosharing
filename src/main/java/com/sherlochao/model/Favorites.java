package com.sherlochao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "favorites")
public class Favorites {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "favorites_id")
	private Integer favoritesId;
	
	@Column(name = "member_id")
	private Integer memberId;
	
	@Column(name = "fav_id")
	private Integer favId;//收藏类型id
	
	@Column(name = "fav_time")
	private Long favTime;//收藏时间
}
