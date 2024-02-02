package com.spring.security;

import com.spring.security.domain.Member;
import com.spring.security.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MemberRepoTest {

	@Autowired
	private MemberRepository repo;
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Test
	@DisplayName("회원 가입 테스트")
	public void createUser() {

		String encPassword = passwordEncoder.encode("1");

		Member email = Member.builder()
				.email("ssong")
				.password(encPassword)
				.build();

		repo.save(email);

		Member existUser = repo.findByEmail("ssong")
				.orElseThrow(() -> new RuntimeException("아이디가 없습니다."));


		assertThat(email.getEmail()).isEqualTo(existUser.getEmail());

	}

}
