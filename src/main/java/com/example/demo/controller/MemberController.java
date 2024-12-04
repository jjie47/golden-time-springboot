package com.example.demo.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.ResponseCode;
import com.example.demo.service.MemberService;
import com.example.demo.service.MemberServiceImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/member/*")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
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
		MemberInfoResponseDto memberInfo = service.get(memberId);
		if(memberInfo!=null) {
			return ApiResponse.success(memberInfo, ResponseCode.MEMBER_GET_SUCCESS);		
		}
		return ApiResponse.fail(null, ResponseCode.MEMBER_NOT_FOUND);
	}
	
	@PutMapping("{memberId}")
	public ApiResponse<String> modify(@PathVariable String memberId, MemberUpdateRequestDto member, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		member.setMemberId(memberId);
		if(service.update(member)) {
			return ApiResponse.success("success", ResponseCode.MEMBER_UPDATE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("{memberId}")
	public ApiResponse<String> delete(@PathVariable String memberId, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		if(service.delete(memberId)) {
			return ApiResponse.success("success", ResponseCode.MEMBER_DELETE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
}
