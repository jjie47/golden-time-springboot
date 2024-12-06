package com.example.demo.domain;

import java.time.format.DateTimeFormatter;

import com.example.demo.entity.Duty;
import com.example.demo.entity.Review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewListResponseDto {
	long reviewId;
	String content;
	String createdAt;
	String updatedAt;
	int rating;
	String classification;
	String memberId;
	String nickname;
	Duty duty;
	
	public static ReviewListResponseDto toDto(Review review) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		return ReviewListResponseDto.builder()
				.reviewId(review.getReviewId())
				.content(review.getContent())
				.createdAt(review.getCreatedAt().format(dtf))
				.updatedAt(review.getUpdatedAt().format(dtf))
				.rating(review.getRating())
				.classification(review.getClassification())
				.memberId(review.getMember().getMemberId())
				.nickname(review.getMember().getNickname())
				.duty(review.getDuty())
				.build();
	}
}