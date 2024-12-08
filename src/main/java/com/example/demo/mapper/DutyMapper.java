package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.DutyDTO;

@Mapper
public interface DutyMapper {
	int insertDuty(DutyDTO dutyData);
}
