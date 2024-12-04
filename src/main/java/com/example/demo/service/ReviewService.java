package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ReviewListResponseDto;
import com.example.demo.dto.ReviewUpdateRequestDto;

public interface ReviewService {
	List<ReviewListResponseDto> getList(String memberId);
	List<ReviewListResponseDto> getList(String memberId, int months);
	List<ReviewListResponseDto> getList(String memberId, String classification);
	boolean update(ReviewUpdateRequestDto review);
	boolean delete(long reviewId);
}
