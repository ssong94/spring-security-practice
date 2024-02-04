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
