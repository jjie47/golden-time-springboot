package com.example.demo.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.PharmReviewDTO;
import com.example.demo.domain.ReviewWriteDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

	List<PharmListDTO> getPharmreviewcount(List<String> hpid);

	List<PharmReviewDTO> getPharmReview(String hpid);

	boolean checkpharm(String dutyId);

	void writeReview(ReviewWriteDTO rv);

	void insertPharm(ReviewWriteDTO rv);

	String getNickName(String memberid);
	
}
