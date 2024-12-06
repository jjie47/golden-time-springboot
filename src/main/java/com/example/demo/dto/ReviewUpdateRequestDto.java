package com.example.demo.dto;

import com.example.demo.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReviewUpdateRequestDto {
	long reviewId;
	String content;
	int rating;
	String memberId;
	
	public static ReviewUpdateRequestDto toDto(Review review) {
		return ReviewUpdateRequestDto.builder()
				.reviewId(review.getReviewId())
				.content(review.getContent())
				.rating(review.getRating())
				.memberId(review.getMember().getMemberId())
				.build();
	}
}