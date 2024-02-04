# spring-security-practice
스프링 시큐리티 6 연습

---
### ⚙️ 환경
- Jdk21
- Spring Boot 3.2.*
- Spring Data Jpa
- Gradle
- Yaml
- H2 DB


---
###  ✅ Todo
- [x] security6 config
- [x] AuthenticationProvider
- [x] UserDetailsService
- [x] Handler

---
### 📝 주의 사항
- Spring Security  6.1 버전 이후로 기존 방식이 `@Deprecated` 되어서 Lambda DSL을 권장한다
- 몇몇 블로그를 보면 resource 경로를 ignore 시키고 있다. 과거에는 성능이슈로 인해 쓰기도 했지만, 버전이 올라가면서 해결이 됐다고 한다. [여기](https://docs.spring.io/spring-security/reference/6.1-SNAPSHOT/servlet/authorization/authorize-http-requests.html#favor-permitall)에서 확인
- 패스워드 인코딩에 대한 코드를 아래와 같이 하는 것은 현재 권장하지 않는다. 이에 대한 정보는 [여기](https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html#authentication-password-storage-dpe)에서 확인
```java 
  @Bean 
  public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
  ```

- 더이상 SHA-256과 같은 단방향 해시는 안전하지 않다. [여기](https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html)에서 확인

---
### 📚 참고
[공식 문서](https://docs.spring.io/spring-security/reference/6.1-SNAPSHOT/index.html)