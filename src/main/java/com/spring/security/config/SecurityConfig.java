package com.spring.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final SuccessHandler successHandler;
	private final FailureHandler failureHandler;


	private static final String[] WHITE_LIST = {
			"/error", "/login*"

	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(request -> request
						.requestMatchers(WHITE_LIST).permitAll()
								.anyRequest().authenticated()
//						.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
//						.requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
//						.anyRequest().authenticated()
				)
				.formLogin(form -> form
//						.loginPage("/login")            // 기본 로그인 페이지를 사용자 정의
//						.loginProcessingUrl("/login-process")    // 사용자 이름과 비밀번호를 확인하는 URL
//						.usernameParameter("username")
//						.passwordParameter("password")
						.successHandler(successHandler)
						.failureHandler(failureHandler)
				)
				.logout(logout -> logout
						.logoutUrl("/logout")	// 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
						.invalidateHttpSession(true) // // 로그아웃 이후 세션 전체 삭제 여부
//						.logoutSuccessUrl("/login") // 로그아웃 성공 후 이동페이지
						.deleteCookies("JSESSIONID") 	// 로그아웃 후 쿠키 삭제
						.deleteCookies("remember-me")
						.logoutSuccessUrl("/login" + "?logout")
				)
				.sessionManagement(session -> session
//						.invalidSessionUrl("/login?invalid")    // 잘못된 세션이 감지 될 때 리디렉션 할 URL
						.maximumSessions(1)        // 단일 사용자가 동시에 가질 수있는 활성 세션 수를 제한 (-1 이면 무제한)
						.maxSessionsPreventsLogin(true) //  동시 로그인 차단, false인 경우 기존 세션 만료 (default: false)
						.expiredUrl("/login?expired")   // 세션이 만료된 경우 이동 할 페이지
//						invalidSessionUrl, expiredUrl 두 개가 정의가 된다면 invalidSessionUrl가 우선적용 된다.
				)
				.rememberMe(remember -> remember
						.rememberMeParameter("remember-me") // default: remember-me, checkbox 등의 이름과 맞춰야함
						.key("uniqueAndSecret")
						.alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
						.tokenValiditySeconds(3600)    // 쿠키의 만료시간 설정(초), default: 14일
						.userDetailsService(userDetailsService)    // 기능을 사용할 때 사용자 정보가 필요함. 반드시 이 설정 필요함.
				)
				.passwordManagement((management) -> management
						.changePasswordPage("/update-password")
				)
				/* http to https redirect */
//				.requiresChannel(channel -> channel
//						.anyRequest().requiresSecure()
//				)
				.csrf(AbstractHttpConfigurer::disable) // 템플릿 엔진 사용 시 CSRF는 켜두는 게 보안상 좋다.
				.cors(AbstractHttpConfigurer::disable)
//				.cors(AbstractHttpConfigurer::disable) cors를 커스텀 하려면 disable 시킨 후 아래 Cors Custom 설정을 해야 한다.
				.getOrBuild();
	}




/*  //	주의사항 확인
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
*/

	//	주의사항 확인
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(List.of("https://example.com"));
//		configuration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.POST.name()));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
}