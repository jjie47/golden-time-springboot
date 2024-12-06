package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberProfileResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;
import com.example.demo.dto.ReviewListResponseDto;
import com.example.demo.dto.ReviewUpdateRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.ResponseCode;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/member/*")
public class MemberController {
	
	@Autowired
	private MemberService mService;
	@Autowired
	private ReviewService rService;
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody Member member, HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("loginMember", member.getMemberId());
		return new ResponseEntity<String>("O", HttpStatus.OK);
	}
	
	@GetMapping("session")
	public ApiResponse<String> get(HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember!=null) {
			return ApiResponse.success(loginMember, ResponseCode.SESSION_GET_SUCCESS);		
		}
		return ApiResponse.fail(null, ResponseCode.SESSION_NOT_FOUND);	
	}
	
	@GetMapping("{memberId}")
	public ApiResponse<MemberInfoResponseDto> get(@PathVariable String memberId, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		MemberInfoResponseDto memberInfo = mService.getInfo(memberId);
		if(memberInfo!=null) {
			return ApiResponse.success(memberInfo, ResponseCode.MEMBER_GET_SUCCESS);		
		}
		return ApiResponse.fail(null, ResponseCode.MEMBER_NOT_FOUND);
	}
	
	@GetMapping("{memberId}/profile")
	public ApiResponse<MemberProfileResponseDto> getProfile(@PathVariable String memberId, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		MemberProfileResponseDto memberInfo = mService.getProfile(memberId);
		if(memberInfo!=null) {
			return ApiResponse.success(memberInfo, ResponseCode.MEMBER_GET_SUCCESS);		
		}
		return ApiResponse.fail(null, ResponseCode.MEMBER_NOT_FOUND);
	}
	
	@PutMapping("{memberId}")
	public ApiResponse<String> modify(@PathVariable String memberId, @RequestBody MemberUpdateRequestDto member, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		member.setMemberId(memberId);
		if(mService.update(member)) {
			return ApiResponse.success("success", ResponseCode.MEMBER_UPDATE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("{memberId}")
	public ApiResponse<String> remove(@PathVariable String memberId, HttpServletRequest req){
		HttpSession session = req.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		if(mService.delete(memberId)) {
			session.removeAttribute("loginMember");
			return ApiResponse.success("success", ResponseCode.MEMBER_DELETE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("reviews")
	public ApiResponse<List<ReviewListResponseDto>> getlist(@PathVariable String memberId, HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<ReviewListResponseDto> list = rService.getList(loginMember);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("reviews/month")
	public ApiResponse<List<ReviewListResponseDto>> getlist(@PathVariable String memberId, @RequestParam int month, HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<ReviewListResponseDto> list = rService.getList(loginMember, month);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("reviews/classification")
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
		List<ReviewListResponseDto> list = rService.getList(loginMember, classification);
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
		if(rService.update(review)) {
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
		if(rService.delete(reviewId)) {
			return ApiResponse.success("success", ResponseCode.REVIEWS_DELETE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
}