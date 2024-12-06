package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.PharmReviewDTO;
import com.example.demo.domain.ReviewListResponseDto;
import com.example.demo.domain.ReviewUpdateRequestDto;
import com.example.demo.domain.ReviewWriteDTO;


public interface ReviewService {

	List<PharmListDTO> getPharmreviewcount(List<String> hpid);

	List<PharmReviewDTO> getPharmReview(String hpid);

	void writeReview(ReviewWriteDTO rv);

	List<ReviewListResponseDto> getList(String memberId);
	List<ReviewListResponseDto> getList(String memberId, int month);
	List<ReviewListResponseDto> getList(String memberId, String classification);
	boolean update(ReviewUpdateRequestDto review);
	boolean delete(long reviewId);
}
