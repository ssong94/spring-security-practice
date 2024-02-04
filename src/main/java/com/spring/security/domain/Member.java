package com.spring.security.domain;

import com.spring.security.config.CustomUserDetails;
import com.spring.security.config.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Id
	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 120)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;


	public CustomUserDetails toCustomUserDetails() {
		return CustomUserDetails.builder()
				.password(password)
				.email(email)
				.role(role)
				.build();
	}


}
