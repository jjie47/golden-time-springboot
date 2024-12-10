package com.example.demo.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.DutyDTO;
import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.PharmReviewDTO;
import com.example.demo.domain.ReviewDTO;
import com.example.demo.domain.ReviewWriteDTO;
import com.example.demo.dto.ReviewListResponseDto;
import com.example.demo.dto.ReviewItemDto;
import com.example.demo.dto.ReviewUpdateRequestDto;
import com.example.demo.entity.Review;
import com.example.demo.mapper.FavoriteMapper;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.mapper.DutyMapper;
import com.example.demo.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@Service
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
	public String getNickName(String memberId) {
		String nick = reviewMapper.getNickName(memberId);
		return nick;
	}
		
	
	
		
		
		
	
	@Override
	public List<ReviewItemDto> getList(String memberId) {
		return reviewRepository.findAllByMember_MemberIdOrderByUpdatedAtDesc(memberId).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<ReviewItemDto> getList(String memberId, int month) {
		LocalDateTime date = (month > 0)?LocalDateTime.now().minusMonths(month):LocalDateTime.now().minusDays(1);
		return reviewRepository.findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualOrderByUpdatedAtDesc(memberId, date).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<ReviewItemDto> getList(String memberId, String classification) {
		return reviewRepository.findAllByMember_MemberIdAndClassificationOrderByUpdatedAtDesc(memberId, classification).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<ReviewItemDto> getList(String memberId, int month, String classification) {
		LocalDateTime date = (month > 0)?LocalDateTime.now().minusMonths(month):LocalDateTime.now().minusDays(1);
		return reviewRepository.findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualAndClassificationOrderByUpdatedAtDesc(memberId, date, classification).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public ReviewListResponseDto getList(String memberId, int pageNo, int numOfRows) {
		Pageable pageable = PageRequest.of(pageNo-1, numOfRows);
		List<ReviewItemDto> items = reviewRepository.findAllByMember_MemberIdOrderByUpdatedAtDesc(memberId, pageable).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
		long totalCount = reviewRepository.countByMember_MemberId(memberId);
		return new ReviewListResponseDto(items, pageNo, numOfRows, totalCount);
	}
	@Override
	public ReviewListResponseDto getList(String memberId, int month, int pageNo, int numOfRows) {
		Pageable pageable = PageRequest.of(pageNo-1, numOfRows);
		LocalDateTime date = (month > 0)?LocalDateTime.now().minusMonths(month):LocalDateTime.now().minusDays(1);
		List<ReviewItemDto> items = reviewRepository.findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualOrderByUpdatedAtDesc(memberId, date, pageable).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
		long totalCount = reviewRepository.countByMember_MemberIdAndUpdatedAtGreaterThanEqual(memberId, date);
		return new ReviewListResponseDto(items, pageNo, numOfRows, totalCount);
	}
	@Override
	public ReviewListResponseDto getList(String memberId, String classification, int pageNo, int numOfRows) {
		Pageable pageable = PageRequest.of(pageNo-1, numOfRows);
		List<ReviewItemDto> items = reviewRepository.findAllByMember_MemberIdAndClassificationOrderByUpdatedAtDesc(memberId, classification, pageable).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
		long totalCount = reviewRepository.countByMember_MemberIdAndClassification(memberId, classification);
		return new ReviewListResponseDto(items, pageNo, numOfRows, totalCount);
	}
	@Override
	public ReviewListResponseDto getList(String memberId, int month, String classification, int pageNo, int numOfRows) {
		Pageable pageable = PageRequest.of(pageNo-1, numOfRows);
		LocalDateTime date = (month > 0)?LocalDateTime.now().minusMonths(month):LocalDateTime.now().minusDays(1);
		List<ReviewItemDto> items = reviewRepository.findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualAndClassificationOrderByUpdatedAtDesc(memberId, date, classification, pageable).stream()
				.map(ReviewItemDto::toDto)
				.collect(Collectors.toList());
		long totalCount = reviewRepository.countByMember_MemberIdAndClassification(memberId, classification);
		return new ReviewListResponseDto(items, pageNo, numOfRows, totalCount);
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
	
	
	
	@Autowired
	private FavoriteMapper fMapper;
	@Autowired
	private ReviewMapper rMapper;
	@Autowired
	private DutyMapper dMapper;
	
	//병원 리뷰 등록
	@Override
	public boolean writeHospitalReview(Map<String, Object> sendData) {
		// JSON 데이터를 개별적으로 파싱
        ObjectMapper mapper = new ObjectMapper();

        ReviewDTO reviewData = mapper.convertValue(sendData.get("reviewData"), ReviewDTO.class);
        DutyDTO dutyData = mapper.convertValue(sendData.get("dutyData"), DutyDTO.class);
        
        System.out.println("reviewData:" + reviewData);
        System.out.println("dutyData:" + dutyData);
        System.out.println("dutyData.getDutyId():" + dutyData.getDutyId());
        
        
        boolean check = fMapper.checkHospital(dutyData.getDutyId());
		// 병원 존재여부 확인
		if(check) {
			rMapper.insertReview(reviewData);
		}
		else {
			dMapper.insertDuty(dutyData);
			rMapper.insertReview(reviewData);
		}
		return true;
	}
	
	// 병원ID에 해당하는 리뷰 목록 조회
	@Override
    public List<ReviewDTO> getReviewsByDutyId(String dutyId) {
        return rMapper.getReviewsByDutyId(dutyId);
    }

	@Override
	public List<Integer> getPharmlike(List<String> hpid, String memberId) {
		List<Integer> result = new ArrayList<>();

        for (String dutyId : hpid) {
            // DB에서 memberId와 dutyId로 `like` 테이블을 확인
            boolean exists = reviewMapper.existsByMemberIdAndDutyId(memberId, dutyId);
            
            // 존재하면 1, 없으면 2
            if (exists) {
                result.add(1);
            } else {
                result.add(2);
            }
        }
        
		
		return result;
	}

	@Override
	public int getPharmlikeone(String hpid, String memberId) {
		int result = 0;
		
		boolean exists = reviewMapper.existsByMemberIdAndDutyId(memberId, hpid);
        
        // 존재하면 1, 없으면 2
        if (exists) {
            result = 1;
        } else {
            result = 2;
        }
		return result;
	}

	@Override
	public void likeadd(ReviewWriteDTO rv) {
		boolean exist = reviewMapper.checkpharm(rv.getDutyId());
		
		//약국이 있다면
		if (exist) {
			
			boolean exist2 = reviewMapper.checklike(rv.getDutyId(),rv.getMemberId(),rv.getClassification());
				if (exist2) {
					System.out.println("즐겨찾기가 있습니다");
					reviewMapper.deletelike(rv.getDutyId(),rv.getMemberId(),rv.getClassification());
					System.err.println("즐겨찾기 삭제 성공");
				}
				else {
					System.out.println("즐겨찾기가 없습니다.");
					reviewMapper.addlike(rv.getDutyId(),rv.getMemberId(),rv.getClassification());
					System.out.println("즐겨찾기 등록 성공");
				}

		}

		else {
			//약국 정보 넣어주기
			reviewMapper.insertPharm(rv);
			System.out.println("약국 등록 성공");
			boolean exist2 = reviewMapper.checklike(rv.getDutyId(),rv.getMemberId(),rv.getClassification());
			if (exist2) {
				System.out.println("즐겨찾기가 있습니다");
				reviewMapper.deletelike(rv.getDutyId(),rv.getMemberId(),rv.getClassification());
				System.err.println("즐겨찾기 삭제 성공");
			}
			else {
				System.out.println("즐겨찾기가 없습니다.");
				reviewMapper.addlike(rv.getDutyId(),rv.getMemberId(),rv.getClassification());
				System.out.println("즐겨찾기 등록 성공");
			}
		}
	}
	
}
