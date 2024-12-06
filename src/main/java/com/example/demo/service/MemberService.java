package com.example.demo.service;

import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberProfileResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;

public interface MemberService {
	MemberInfoResponseDto getInfo(String memberId);
	MemberProfileResponseDto getProfile(String memberId);
	boolean update(MemberUpdateRequestDto member);
	boolean delete(String memberId);
}
