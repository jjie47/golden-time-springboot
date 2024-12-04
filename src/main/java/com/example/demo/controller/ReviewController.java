package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LikeListResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;
import com.example.demo.dto.ReviewListResponseDto;
import com.example.demo.dto.ReviewUpdateRequestDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.ResponseCode;
import com.example.demo.service.LikeService;
import com.example.demo.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/member/{memberId}/*")
public class ReviewController {
	
	@Autowired ReviewService service;
	
	// 리뷰 보기, 한달전꺼보기, 리뷰 구분별 조회, 리뷰 수정, 리뷰 삭제
	
	@GetMapping("reviews")
	public ApiResponse<List<ReviewListResponseDto>> getlist(@PathVariable String memberId, HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<ReviewListResponseDto> list = service.getList(loginMember);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("reviews/month")
	public ApiResponse<List<ReviewListResponseDto>> getlist(@PathVariable String memberId, @RequestParam int months, HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<ReviewListResponseDto> list = service.getList(loginMember, months);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("reviews/classfication")
	public ApiResponse<List<ReviewListResponseDto>> getlist(@PathVariable String memberId,
													@RequestParam String classification,
													HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<ReviewListResponseDto> list = service.getList(loginMember, classification);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@PutMapping("review/{reviewId}")
	public ApiResponse<String> modify(@PathVariable String memberId, @PathVariable long reviewId, ReviewUpdateRequestDto review, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		review.setReviewId(reviewId);
		if(service.update(review)) {
			return ApiResponse.success("success", ResponseCode.REVIEWS_UPDATE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("review/{reviewId}")
	public ApiResponse<String> delete(@PathVariable String memberId,
											@PathVariable long reviewId,
											HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		if(service.delete(reviewId)) {
			return ApiResponse.success("success", ResponseCode.REVIEWS_DELETE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
}
