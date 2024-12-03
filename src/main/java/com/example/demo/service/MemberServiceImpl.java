package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepository;
	
	public MemberInfoResponseDto get(String memberId) {
		Optional<Member> member = memberRepository.findByMemberId(memberId);
		if(member.isEmpty()) {
			// TODO: 예외 처리
			return null;
		}
		return MemberInfoResponseDto.toDto(memberRepository.findByMemberId(memberId).get()); 
		
		// SELECT * FROM TABLE
		// memberRepository.findAll();
		
		// INSERT INTO
		// memberRepository.save();
		
		// DELETE
		// memberRepository.delete();
	}
}
