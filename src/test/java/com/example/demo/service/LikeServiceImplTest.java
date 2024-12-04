package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.entity.Member;
import com.example.demo.repository.DutyRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikeServiceImplTest {
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private DutyRepository dutyRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private LikeService likeService;
	
	private List<Duty> dlist;
	private List<Like> llist;
	
	@BeforeEach
	public void setUp() {
		Member member = new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234");
		memberRepository.save(member);
		
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
	}
	
//	@Test
	void getListByMemberId() {
		List<LikeListResponseDto> expected = likeService.getList("testId");
		for(LikeListResponseDto data: expected) {
			System.out.println(data);
		}
	}
	
//	@Test
	void getListByMemberIdWithLimit() {
		List<LikeListResponseDto> expected = likeService.getList("testId", 3);
		for(LikeListResponseDto data: expected) {
			System.out.println(data);
		}
	}
	
//	@Test
	void getListByMemberIdAndClassification() {
		List<LikeListResponseDto> expected = likeService.getList("testId", "약국");
		for(LikeListResponseDto data: expected) {
			System.out.println(data);
		}
	}
	
	@Test
	void delete() {
		if(likeService.delete(6)) {
			System.out.println("삭제 성공");
			List<LikeListResponseDto> expected = likeService.getList("testId");
			for(LikeListResponseDto data: expected) {
				System.out.println(data);
			}
		}
		else {
			System.out.println("실패");
		}
	}

}
