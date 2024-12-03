package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberInfoResponseDto;

@Service
public interface MemberService {
	MemberInfoResponseDto get(String memberId);
}
