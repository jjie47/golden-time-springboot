package com.example.demo.domain;

import lombok.Data;

@Data
public class PharmReviewDTO {
	private String memberId;
	private String nickname;
	private String createdAt;
	private int rating;
	private String content;
}
