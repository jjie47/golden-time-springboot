package com.example.demo.service;

import com.example.demo.domain.MemberInfoResponseDto;
import com.example.demo.domain.MemberProfileResponseDto;
import com.example.demo.domain.MemberUpdateRequestDto;

public interface MemberService {
	MemberInfoResponseDto getInfo(String memberId);
	MemberProfileResponseDto getProfile(String memberId);
	boolean update(MemberUpdateRequestDto member);
	boolean delete(String memberId);
}
