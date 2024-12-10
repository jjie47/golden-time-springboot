package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewListResponseDto {
	List<ReviewItemDto> items;
	int pageNo;
	int numOfRows;
	long totalCount;
}