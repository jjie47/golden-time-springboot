package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.LikeListResponseDto;
import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;
import com.example.demo.dto.ReviewListResponseDto;
import com.example.demo.dto.ReviewUpdateRequestDto;
import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.entity.Member;
import com.example.demo.entity.Review;
import com.example.demo.repository.DutyRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReviewRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewServiceImplTest {
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private DutyRepository dutyRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReviewService reviewService;
	
	private List<Duty> dlist;
	private List<Like> llist;
	private List<Review> rlist;
	
	@BeforeEach
	public void setUp() {
		Member member = new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234");
		Member member2 = new Member("betaId", "1234", "nickname", "testEmail", "010-1234-1234");
		memberRepository.save(member);
		memberRepository.save(member2);
		
		dlist = new ArrayList<>();
		dlist.add(new Duty("a1234", "문선치과", "치과", "02-3023-0333"));
		dlist.add(new Duty("a1235", "문선내과", "내과", "02-3023-0335"));
		dlist.add(new Duty("a1236", "문선외과", "외과", "02-3023-0336"));
		dlist.add(new Duty("a1237", "문선정신과", "정신과", "02-3023-0337"));
		dlist.add(new Duty("a1238", "문선가정의학과", "가정의학과", "02-3023-0338"));
		dlist.add(new Duty("a1239", "문선약국", "약국", "02-3023-0338"));
		dlist.add(new Duty("a1240", "문선검진기관", "검진기관", "02-3023-0338"));
		dutyRepository.saveAll(dlist);
		
		llist = new ArrayList<>();
		llist.add(new Like("병원", member, new Duty("a1234", "문선치과", "치과", "02-3023-0333")));
		llist.add(new Like("병원", member, new Duty("a1235", "문선내과", "내과", "02-3023-0335")));
		llist.add(new Like("병원", member, new Duty("a1236", "문선외과", "외과", "02-3023-0336")));
		llist.add(new Like("병원", member, new Duty("a1237", "문선정신과", "정신과", "02-3023-0337")));
		llist.add(new Like("병원", member, new Duty("a1238", "문선가정의학과", "가정의학과", "02-3023-0338")));
		llist.add(new Like("약국", member, new Duty("a1239", "문선약국", "약국", "02-3023-0337")));
		llist.add(new Like("검진기관", member, new Duty("a1240", "문선검진기관", "검진기관", "02-3023-0338")));
		likeRepository.saveAll(llist);
		
		rlist = new ArrayList<>();
		rlist.add(new Review("내용1", 5, LocalDateTime.of(2024, 12, 4, 0, 0, 0), "병원", new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234"), new Duty("a1234", "문선치과", "치과", "02-3023-0333")));
		rlist.add(new Review("내용2", 5, LocalDateTime.of(2024, 11, 24, 0, 0, 0), "병원", new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234"), new Duty("a1234", "문선치과", "치과", "02-3023-0333")));
		rlist.add(new Review("내용3", 5, LocalDateTime.of(2024, 11, 14, 0, 0, 0), "병원", new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234"), new Duty("a1234", "문선치과", "치과", "02-3023-0333")));
		rlist.add(new Review("내용4", 5, LocalDateTime.of(2024, 11, 4, 0, 0, 0), "병원", new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234"), new Duty("a1234", "문선치과", "치과", "02-3023-0333")));
		rlist.add(new Review("내용5", 5, LocalDateTime.of(2024, 10, 24, 0, 0, 0), "병원", new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234"), new Duty("a1234", "문선치과", "치과", "02-3023-0333")));
		rlist.add(new Review("내용6", 5, LocalDateTime.of(2024, 10, 14, 0, 0, 0), "약국", new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234"), new Duty("a1234", "문선치과", "치과", "02-3023-0333")));
		reviewRepository.saveAll(rlist);
		
		List<ReviewListResponseDto> before = reviewService.getList("testId");
		for(ReviewListResponseDto data: before) {
			System.out.println(data);
		}
	}
	
//	@Test
	void getListByMemberId() {
		List<ReviewListResponseDto> expected = reviewService.getList("testId");
		for(ReviewListResponseDto data: expected) {
			System.out.println(data);
		}
	}
	
//	@Test
	void getListByMemberIdWithLimit() {
		List<ReviewListResponseDto> expected = reviewService.getList("testId", 1);
		for(ReviewListResponseDto data: expected) {
			System.out.println(data);
		}
	}
	
//	@Test
	void getListByMemberIdAndClassification() {
		List<ReviewListResponseDto> expected = reviewService.getList("testId", "약국");
		for(ReviewListResponseDto data: expected) {
			System.out.println(data);
		}
	}
	
//	@Test
	void update() {
		ReviewUpdateRequestDto review = new ReviewUpdateRequestDto(6, "1234", 3, "testId");
		if(reviewService.update(review)) {
			System.out.println("변경 성공");
			List<ReviewListResponseDto> expected = reviewService.getList("testId");
			for(ReviewListResponseDto data: expected) {
				System.out.println(data);
			}
		}
		else {
			System.out.println("실패");
		}
		
	}
	
	@Test
	void delete() {
		if(reviewService.delete(6)) {
			System.out.println("삭제 성공");
			List<ReviewListResponseDto> expected = reviewService.getList("testId");
			for(ReviewListResponseDto data: expected) {
				System.out.println(data);
			}
		}
		else {
			System.out.println("실패");
		}
	}

}
