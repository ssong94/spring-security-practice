package com.spring.security.config;


import com.spring.security.domain.Member;
import com.spring.security.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Member member = userRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " 아이디가 존재하지 않습니다."));

		return member.toCustomUserDetails();
	}
}
