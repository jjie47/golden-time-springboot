package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.MemberDTO;

@Mapper
public interface MemberMapper {
	MemberDTO getMemberByMemberId(String memberId);
	int insertMember(MemberDTO member);
	MemberDTO getMemberByPhoneAndMail(MemberDTO member);
	int updatePwByMemberId(MemberDTO member);
}