package com.example.demo.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.PharmReviewDTO;
import com.example.demo.domain.ReviewListResponseDto;
import com.example.demo.domain.ReviewUpdateRequestDto;
import com.example.demo.domain.ReviewWriteDTO;
import com.example.demo.entity.Review;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	//목록에서 리뷰 갯수 받아옴
	@Override
	public List<PharmListDTO> getPharmreviewcount(List<String> hpid) {
		List<PharmListDTO> result = reviewMapper.getPharmreviewcount(hpid);
//		System.out.println("리절트:" + result);
		return result;
	}
	
	//약국 선택하여 리뷰 목록 받아오기
	@Override
	public List<PharmReviewDTO> getPharmReview(String hpid) {		
		//리뷰 결과
		List<PharmReviewDTO> result = reviewMapper.getPharmReview(hpid);
		
		//닉네임 받아오기
		for (PharmReviewDTO review : result) {
	        // 리뷰의 memberid를 통해 닉네임 가져오기
	        String nickname = reviewMapper.getNickName(review.getMemberid());
	        
	        // 가져온 닉네임을 PharmReviewDTO에 세팅
	        review.setNickname(nickname);
	    }
		
		//리뷰 결과 + 닉네임
		return result;
	}

	@Override
	public void writeReview(ReviewWriteDTO rv) {
		
		//약국 정보가 있는지 체크
		boolean exist = reviewMapper.checkpharm(rv.getDutyId());
		
		//약국이 있다면
		if (exist) {
//			System.out.println("약국이 존재합니다.");
			
			//리뷰 등록하기
			reviewMapper.writeReview(rv);
//			System.out.println("리뷰 등록 성공~");
		}
		//약국이 없다면
		else {
			//약국 정보 넣어주기
			reviewMapper.insertPharm(rv);
			
			//그리고 나서 리뷰 등록하기
			reviewMapper.writeReview(rv);
		}
	}
		
	
	
		
		
		
	
	@Override
	public List<ReviewListResponseDto> getList(String memberId) {
		return reviewRepository.findAllByMember_MemberId(memberId).stream()
				.map(ReviewListResponseDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<ReviewListResponseDto> getList(String memberId, int month) {
		LocalDateTime date = LocalDateTime.now().minusMonths(month);
		return reviewRepository.findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualMonths(memberId, date).stream()
				.map(ReviewListResponseDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<ReviewListResponseDto> getList(String memberId, String classification) {
		return reviewRepository.findAllByMember_MemberIdAndClassification(memberId, classification).stream()
				.map(ReviewListResponseDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public boolean update(ReviewUpdateRequestDto review) {
		Review data = em.find(Review.class, review.getReviewId());
		if(data==null) {
			return false;
		}
		data.setContent(review.getContent());
		data.setRating(review.getRating());
		return true;	
	}
	@Override
	public boolean delete(long reviewId) {
		Optional<Review> data = reviewRepository.findById(reviewId);
		if(data.isPresent()) {
			reviewRepository.deleteById(reviewId);
			data = reviewRepository.findById(reviewId);
			if(data.isEmpty()) {
				return true;
			}
			// TODO : 예외 처리(즐겨찾기 삭제 실패)
			return false;
		}
		// TODO : 예외 처리(존재하지 않는 즐겨찾기)
		return false;
	}
}
