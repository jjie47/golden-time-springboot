package com.example.demo.service;


import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberProfileResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.domain.MemberDTO;
import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberProfileResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mMapper;
	
	// 로그인
	public boolean login(String memberId, String password) {
		MemberDTO member = mMapper.getMemberByMemberId(memberId);
		if(member != null) {
			if(member.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	// 아이디 중복 확인
	public boolean checkId(String memberId) {
		MemberDTO member = mMapper.getMemberByMemberId(memberId);
		return member == null;
	}
	
	// 랜덤 번호 생성
	public String getCertificationNumber() {
	    StringBuilder certificationNumber = new StringBuilder();

	    for (int count = 0; count < 6; count++) {
	        certificationNumber.append((int)(Math.random() * 10));
	    }
	    
	    return certificationNumber.toString();
	}
	
	// 회원가입
	public boolean join(MemberDTO member) {
		return mMapper.insertMember(member) == 1;
	}

	// 아이디 찾기 & 비밀번호 찾기
	public MemberDTO checkPhoneAndMail(MemberDTO memberData) {
		MemberDTO member = mMapper.getMemberByPhoneAndMail(memberData);
		return member;
	}

	// 비밀번호 변경
	public boolean updatePwByMemberId(MemberDTO memberData) {
		return mMapper.updatePwByMemberId(memberData) == 1;
	}

}

