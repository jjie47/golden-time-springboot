package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.MemberDTO;
import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.PharmReviewDTO;
import com.example.demo.domain.ReviewDTO;
import com.example.demo.domain.ReviewWriteDTO;

import java.util.List;

@Mapper
public interface ReviewMapper {

	List<PharmListDTO> getPharmreviewcount(List<String> hpid);

	List<PharmReviewDTO> getPharmReview(String hpid);

	boolean checkpharm(String dutyId);

	void writeReview(ReviewWriteDTO rv);

	void insertPharm(ReviewWriteDTO rv);

	String getNickName(String memberid);
	
	int insertMember(MemberDTO member);

	// 병원 리뷰작성
	int insertReview(ReviewDTO reviewData);
	// 병원ID에 해당하는 리뷰 목록 조회
    List<ReviewDTO> getReviewsByDutyId(String dutyId);

	boolean existsByMemberIdAndDutyId(String memberId, String dutyId);

	boolean checklike(String dutyId, String memberId, String classification);

	void addlike(String dutyId, String memberId, String classification);

	void deletelike(String dutyId, String memberId, String classification);

	
	
}
