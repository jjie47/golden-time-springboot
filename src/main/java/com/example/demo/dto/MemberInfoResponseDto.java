package com.example.demo.dto;

import com.example.demo.entity.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoResponseDto {
	String memberId;
	String nickname;
	String email;
	String phoneNumber;
	String systemName;
	String originName;
	Long reviewCnt;
	Long likeCnt;
	
	public static MemberInfoResponseDto toDto(Member entity, long reviewCnt, long likeCnt) {
		return MemberInfoResponseDto.builder()
				.memberId(entity.getMemberId())
				.nickname(entity.getNickname())
				.email(entity.getEmail())
				.phoneNumber(entity.getPhoneNumber())
				.systemName(entity.getSystemName())
				.originName(entity.getOriginName())
				.reviewCnt(reviewCnt)
				.likeCnt(likeCnt)
				.build();
	}
}
