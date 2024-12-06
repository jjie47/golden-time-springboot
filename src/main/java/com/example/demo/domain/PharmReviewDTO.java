package com.example.demo.domain;

import lombok.Data;

@Data
public class PharmReviewDTO {
	private String memberid;
	private String nickname;
	private String createdAt;
	private int rating;
	private String content;
}
