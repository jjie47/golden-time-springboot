package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.FavoriteDTO;

@Mapper
public interface FavoriteMapper {
	boolean checkHospital(String dutyId);
	int checkFavorite(FavoriteDTO favoriteData);
	int insertLike(FavoriteDTO favoriteData);
	int deleteLike(FavoriteDTO favoriteData);
	int insertDuty(FavoriteDTO favoriteData);
	
}
