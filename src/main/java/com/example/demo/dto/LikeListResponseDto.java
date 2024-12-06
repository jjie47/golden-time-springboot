package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeListResponseDto {
	List<LikeItemDto> items;
	int pageNo;
	int numOfRows;
	long totalCount;
}