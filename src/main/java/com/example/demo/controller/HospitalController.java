package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.FavoriteDTO;
import com.example.demo.service.HospitalService;

@RestController
@RequestMapping("/api/hospital/*")
public class HospitalController {

	@Autowired
	private HospitalService service;
	
	
	// 즐겨찾기 상태 확인
	@PostMapping("checkFavorite")
	public ResponseEntity<Map<String, Boolean>> checkFavorite(@RequestBody FavoriteDTO favoriteData) {
        System.out.println("Check FavoriteDTO: " + favoriteData);
        
        // 서비스 호출하여 즐겨찾기 여부 확인
        boolean isFavorite = service.checkFavorite(favoriteData);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isFavorite", isFavorite);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	
	// 즐겨찾기 추가
	@PostMapping("favorite")
    public ResponseEntity<String> insertfavorite(@RequestBody FavoriteDTO FavoriteData) {
        System.out.println("Insert FavoriteDTO: " + FavoriteData);
        
        if(service.insertFavorite(FavoriteData)) {
        	return new ResponseEntity<String>("O",HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>("X",HttpStatus.OK);
        }
    }
	
	//즐겨찾기 삭제
	@DeleteMapping("favorite")
	public ResponseEntity<String> deleteFavorite(@RequestBody FavoriteDTO favoriteData) {
	    System.out.println("Delete FavoriteDTO: " + favoriteData);
	    boolean result = service.deleteFavorite(favoriteData);

	    if (result) {
	        return new ResponseEntity<String>("O", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<String>("X", HttpStatus.OK);
	    }
	}
}
