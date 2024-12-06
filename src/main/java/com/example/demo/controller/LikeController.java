package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.LikeItemDto;
import com.example.demo.domain.LikeListRequestDto;
import com.example.demo.domain.LikeListResponseDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.ResponseCode;
import com.example.demo.service.LikeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/member/{memberId}/*")
public class LikeController {
	
	@Autowired LikeService service;
	
	@GetMapping("likes")
	public ApiResponse<LikeListResponseDto> getlist(@PathVariable String memberId, LikeListRequestDto likeReq, HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		LikeListResponseDto response = service.getList(likeReq);
		if(response!=null) {
			return ApiResponse.success(response, ResponseCode.LIKES_GET_SUCCESS);
		}
		return ApiResponse.fail(response, ResponseCode.LIKES_NOT_FOUND);
	}
	
//	@GetMapping("likes")
//	public ApiResponse<List<LikeItemDto>> getlist(@PathVariable String memberId, HttpServletRequest req) {
//		String loginMember = (String)req.getSession().getAttribute("loginMember");
//		if(loginMember==null) {
//			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
//		}
//		if(!loginMember.equals(memberId)) {
//			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
//		}
//		List<LikeItemDto> list = service.getList(loginMember);
//		if(list!=null) {
//			return ApiResponse.success(list, ResponseCode.LIKES_GET_SUCCESS);
//		}
//		return ApiResponse.fail(list, ResponseCode.LIKES_NOT_FOUND);
//	}
	
	@GetMapping("likes/limit")
	public ApiResponse<List<LikeItemDto>> getlist(@PathVariable String memberId, @RequestParam int limit, HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<LikeItemDto> list = service.getList(loginMember, limit);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.LIKES_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.LIKES_NOT_FOUND);
	}
	
	@GetMapping("likes/classification")
	public ApiResponse<List<LikeItemDto>> getlist(@PathVariable String memberId,
													@RequestParam String classification,
													HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<LikeItemDto> list = service.getList(loginMember, classification);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.LIKES_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.LIKES_NOT_FOUND);
	}
	
	@DeleteMapping("like/{likeId}")
	public ApiResponse<String> delete(@PathVariable String memberId,
											@PathVariable long likeId,
											HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		if(service.delete(likeId)) {
			return ApiResponse.success("success", ResponseCode.LIKES_DELETE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
}
