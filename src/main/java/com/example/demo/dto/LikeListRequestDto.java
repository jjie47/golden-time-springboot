package com.example.demo.dto;

import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeListRequestDto {
	String memberId;
	String classification;
	int pageNo;
	int numOfRows;
}