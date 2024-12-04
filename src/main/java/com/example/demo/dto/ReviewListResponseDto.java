package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.entity.Member;
import com.example.demo.entity.Review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewListResponseDto {
	long reviewId;
	String content;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	int rating;
	String classification;
	String memberId;
	String nickname;
	Duty duty;
	
	public static ReviewListResponseDto toDto(Review review) {
		return ReviewListResponseDto.builder()
				.reviewId(review.getReviewId())
				.content(review.getContent())
				.createdAt(review.getCreatedAt())
				.updatedAt(review.getUpdatedAt())
				.rating(review.getRating())
				.classification(review.getClassification())
				.memberId(review.getMember().getMemberId())
				.nickname(review.getMember().getNickname())
				.duty(review.getDuty())
				.build();
	}
}