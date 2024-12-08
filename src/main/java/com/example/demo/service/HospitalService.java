package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.FavoriteDTO;
import com.example.demo.mapper.FavoriteMapper;

@Service
public class HospitalService {

	@Autowired
	private FavoriteMapper fMapper;
	
	// 즐겨찾기 존재여부 확인
	public boolean checkFavorite(FavoriteDTO favoriteData) {
        int count = fMapper.checkFavorite(favoriteData);
        return count > 0;
    }
    
    // 즐겨찾기 추가
	public boolean insertFavorite(FavoriteDTO favoriteData) {
		// 병원 존재여부 확인
		boolean check = fMapper.checkHospital(favoriteData.getDutyId());
		if(check) {
//			System.out.println("병원있음(즐겨찾기추가)");
			fMapper.insertLike(favoriteData);
		}
		else {
//			System.out.println("병원없음(기관추가,즐겨찾기추가)");
			fMapper.insertDuty(favoriteData);
			fMapper.insertLike(favoriteData);
		}
		return true;
	}

	// 즐겨찾기 삭제
	public boolean deleteFavorite(FavoriteDTO favoriteData) {
		return fMapper.deleteLike(favoriteData) == 1 ;
	}

}
