package com.example.demo.domain;

import lombok.Data;

@Data
public class ReviewDTO {
	private long reviewId;
	private String content;
	private String createdAt;
	private String updatedAt;
	private int rating;
	private String classification;
	private String memberId;
	private String dutyId;
}
