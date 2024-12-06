package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	MemberDTO getMemberByMemberId(String memberId);
	int insertMember(MemberDTO member);
}