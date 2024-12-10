package com.example.demo.dto;

import com.example.demo.entity.Duty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeAddRequestDto {
	private String classification;
	private String memberId;
	private Duty duty;
}