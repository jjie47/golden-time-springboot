package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeAddAgainRequestDto {
	private long likeId;
	private String classification;
	private String memberId;
	private String dutyId;
}