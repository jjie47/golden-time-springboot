package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberServiceImplTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberServiceImpl memberService;
	
	@Test
	void get() {
		Member actual = new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234");
		memberRepository.save(actual);
		MemberInfoResponseDto expected = memberService.get("testId");
		assertThat(expected).usingRecursiveComparison()
							.ignoringFields("systemName")
							.ignoringFields("originName")
							.isEqualTo(actual);
	}

}
