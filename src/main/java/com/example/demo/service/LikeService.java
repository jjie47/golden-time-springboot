package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LikeAddAgainRequestDto;
import com.example.demo.dto.LikeAddRequestDto;
import com.example.demo.dto.LikeItemDto;
import com.example.demo.dto.LikeListRequestDto;
import com.example.demo.dto.LikeListResponseDto;

public interface LikeService {
	List<LikeItemDto> getList(String memberId);
	List<LikeItemDto> getList(String memberId, int count);
	List<LikeItemDto> getList(String memberId, String classification);
	LikeListResponseDto getList(LikeListRequestDto likeReq);
	LikeListResponseDto getListWithClassification(LikeListRequestDto likeReq);
	boolean insert(LikeAddRequestDto likeReq);
	boolean insert(LikeAddAgainRequestDto likeReq);
	boolean delete(long likeId);
}
