package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeListRequestDto {
	String memberId;
	String classification;
	int pageNo;
	int numOfRows;
}