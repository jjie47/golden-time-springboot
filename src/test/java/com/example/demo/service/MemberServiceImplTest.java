package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberServiceImplTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberService memberService;
	
//	@Test
	void get() {
		Member actual = new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234");
		memberRepository.save(actual);
		MemberInfoResponseDto expected = memberService.get("testId");
		assertThat(expected).usingRecursiveComparison()
							.ignoringFields("systemName")
							.ignoringFields("originName")
							.isEqualTo(actual);
	}
	
//	@Test
	void update() {
		Member actual = new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234");
		memberRepository.save(actual);
		MemberUpdateRequestDto member = new MemberUpdateRequestDto("testId", "1234", "change", "testEmail", "010-1234-1234");
		if(memberService.update(member)) {
			System.out.println("변경 성공");
			MemberInfoResponseDto expected = memberService.get("testId");
			assertThat(expected).usingRecursiveComparison()
			.ignoringFields("systemName")
			.ignoringFields("originName")
			.isEqualTo(member);
		}
		else {
			System.out.println("실패");
		}
		
	}
	
	@Test
	void delete() {
		Member actual = new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234");
		memberRepository.save(actual);
		if(memberService.delete("testId")) {
			System.out.println("삭제 성공");
			MemberInfoResponseDto expected = memberService.get("testId");
			assertThat(expected).isNull();
		}
		else {
			System.out.println("실패");
		}
	}
}
