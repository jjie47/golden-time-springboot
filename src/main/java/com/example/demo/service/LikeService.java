package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LikeListRequestDto;
import com.example.demo.dto.LikeListResponseDto;
import com.example.demo.dto.LikeItemDto;

public interface LikeService {
	List<LikeItemDto> getList(String memberId);
	List<LikeItemDto> getList(String memberId, int count);
	List<LikeItemDto> getList(String memberId, String classification);
	LikeListResponseDto getList(LikeListRequestDto likeReq);
	boolean delete(long likeId);
}
