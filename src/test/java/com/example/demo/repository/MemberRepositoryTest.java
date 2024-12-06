package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.Member;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	void findByMemberId() {
		Member actual = new Member("testId", "1234", "nickname", "testEmail", "010-1234-1234");
		memberRepository.save(actual);
		Member expected = memberRepository.findByMemberId("testId").get();
		assertThat(expected).usingRecursiveComparison()
							.ignoringFields("systemName")
							.ignoringFields("originName")
							.isEqualTo(actual);
	}

}
