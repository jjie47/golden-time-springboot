package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberInfoResponseDto;

import com.example.demo.dto.MemberUpdateRequestDto;

import jakarta.transaction.Transactional;

public interface MemberService {
	MemberInfoResponseDto get(String memberId);
	boolean update(MemberUpdateRequestDto member);
	boolean delete(String memberId);
}
