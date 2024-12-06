package com.example.demo.domain;

import com.example.demo.entity.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberProfileResponseDto {
	String memberId;
	String nickname;
	String systemName;
	String originName;
	long reviewCnt;
	long likeCnt;
	
	public static MemberProfileResponseDto toDto(Member entity, long reviewCnt, long likeCnt) {
		return MemberProfileResponseDto.builder()
				.memberId(entity.getMemberId())
				.nickname(entity.getNickname())
				.systemName(entity.getSystemName())
				.originName(entity.getOriginName())
				.reviewCnt(reviewCnt)
				.likeCnt(likeCnt)
				.build();
	}
}
