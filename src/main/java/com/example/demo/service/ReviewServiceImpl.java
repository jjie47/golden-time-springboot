package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LikeListResponseDto;
import com.example.demo.dto.ReviewListResponseDto;
import com.example.demo.dto.ReviewUpdateRequestDto;
import com.example.demo.entity.Like;
import com.example.demo.entity.Member;
import com.example.demo.entity.Review;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<ReviewListResponseDto> getList(String memberId) {
		return reviewRepository.findAllByMember_MemberId(memberId).stream()
				.map(ReviewListResponseDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<ReviewListResponseDto> getList(String memberId, int months) {
		LocalDateTime date = LocalDateTime.now().minusMonths(months);
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
