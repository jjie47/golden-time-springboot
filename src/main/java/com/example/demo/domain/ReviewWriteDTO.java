package com.example.demo.domain;

import lombok.Data;

@Data
public class ReviewWriteDTO {
	private String dutyId;
	private String dutyName;
	private String dutyTel;
	private String content;
	private String memberId;
	private int rating;
	private String classification;
}
