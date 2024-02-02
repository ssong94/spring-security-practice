package com.spring.security.config;

import com.spring.security.domain.Member;
import com.spring.security.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepo;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String id = authentication.getName();
		String password = (String) authentication.getCredentials();

		createMember(id);
		CustomUserDetails savedMember = (CustomUserDetails) userDetailsService.loadUserByUsername(id);

		validateMemberAuth(savedMember, password);


		return new UsernamePasswordAuthenticationToken(
				savedMember,
				savedMember.getPassword(),
				savedMember.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private boolean isNotMatches(String password, String encodePassword) {
		return !passwordEncoder.matches(password, encodePassword);
	}

	private void validateMemberAuth(CustomUserDetails savedMember, String inputPassword) {

		if (isNotMatches(inputPassword, savedMember.getPassword())) {
			throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
		}

		// 이하 여러 유효성 검사....

	}

	/**
	 * @deprecated
	 */
	private void createMember(String username) {
		String encPassword = passwordEncoder.encode("1");

		Member email = Member.builder()
				.email(username)
				.password(encPassword)
				.build();

		memberRepo.save(email);
	}
}
