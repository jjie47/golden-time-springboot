package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LikeListResponseDto;

public interface LikeService {
	List<LikeListResponseDto> getList(String memberId);
	List<LikeListResponseDto> getList(String memberId, int count);
	List<LikeListResponseDto> getList(String memberId, String classification);
	boolean delete(long likeId);
}
