# Spring Boot Microservices 강의 정리

## 왜 스프링 부트를 써야 하는가?

스프링 부트 없이도 REST API를 만들 수 있습니다. 하지만 스프링 부트를 사용하면 **효율성과 생산성** 면에서 큰 장점을 얻을 수 있습니다. 아래에서 스프링 부트의 주요 이점과 동작 방식을 자세히 살펴보겠습니다.

---

## 1. REST API와 소비자 관점

REST API를 개발할 때는 항상 **소비자(사용자)**의 입장에서 생각해야 합니다.

예를 들어, 사용자가 `GET /users/123` 요청을 보낸다면, 이 요청이 어떻게 처리되고 응답으로 JSON 형식의 데이터가 반환되는지 이해해야 합니다.

---

## 2. 요청은 어떻게 처리되는가?

### DispatcherServlet의 역할

스프링 부트에서는 모든 HTTP 요청이 **DispatcherServlet**으로 전달됩니다.

- **Front Controller 패턴**의 핵심
- 모든 요청을 중앙에서 처리 후 적절한 컨트롤러로 분배

### 왜 DispatcherServlet이 먼저 호출될까?

DispatcherServlet은 `/` 경로에 매핑되어 있기 때문에, 모든 요청이 가장 먼저 이곳에 도달합니다.

> 예시  
> `http://localhost:8080/hello-world` → DispatcherServlet → HelloWorldController

### DispatcherServlet의 자동 구성

스프링 부트는 **AutoConfiguration** 기능을 통해 DispatcherServlet을 자동 구성합니다.

---

## 3. JSON 응답은 어떻게 만들어지는가?

### 핵심 구성 요소

- `@ResponseBody`: 반환된 객체를 HTTP 응답 본문으로 직렬화
- Jackson 라이브러리: 객체를 JSON으로 변환
- Message Converter: HTTP 요청/응답 변환 자동 처리


> 예시 코드

```java
@RestController
public class HelloWorldController {
    @GetMapping("/hello-world")
    public HelloWorldBean helloWorld() {
        return new HelloWorldBean("Hello, World!");
    }
}
```

> 응답 예시

``` json
{
  "message": "Hello, World!"
}
```

### 자동 구성
Jackson 설정: JacksonHttpMessageConvertersConfiguration 통해 자동 적용

## 4. 오류 처리: 화이트라벨 에러 페이지
- 잘못된 URL 요청 시, 기본적으로 화이트라벨 에러 페이지가 표시됩니다.

- 이는 ErrorMvcAutoConfiguration에 의해 자동 생성됩니다.

- 컨트롤러를 찾지 못할 때 DispatcherServlet이 에러 페이지를 반환

## 5. 의존성 관리는 어떻게 되는가?
### Spring Boot Starter Web
> REST API를 구축하는 데 필요한 라이브러리 포함:
- spring-webmvc: MVC 패턴
- spring-boot-starter-tomcat: 내장 웹 서버
- spring-boot-starter-json: JSON 처리 (Jackson)

> 예시

- spring-boot-starter-web 추가 시:
    - Tomcat 자동 실행
    - DispatcherServlet 자동 등록
    - JSON 자동 변환 지원

## 6. 스프링 부트의 최대 장점
- 내장 서버 실행
- DispatcherServlet 자동 설정
- 객체-JSON 변환 자동화
- 에러 처리 자동화

> 요약
스프링 부트는 REST API 개발에 필요한 복잡한 설정을 자동화하여, 생산성과 유지보수성을 크게 향상시킵니다.

## REST API 설계 팁
리소스는 복수형으로 표현하자!
- /users/1 ← 선호
- /user/1 ← 비권장

## HTTP 상태 코드 요약
HTTP 상태 코드 전체 요약 (1xx~5xx)

---
## 리소스 생성 후 응답 규칙
### REST 원칙
- POST 요청으로 리소스 생성 시:
    - 응답 상태 코드: 201 Created
    - Location 헤더: 생성된 리소스 URI 반환

### 예시
#### 요청
``` http
POST /users HTTP/1.1
Content-Type: application/json

{
  "name": "John Doe",
  "birthDate": "1990-01-01"
}
```
#### 응답
```http
HTTP/1.1 201 Created
Location: http://localhost:8080/users/10
```
- 클라이언트는 /users/10 URI를 통해 리소스를 조회하거나 수정할 수 있음

## 실생활 비유: 주문번호 발급
- 주문 → 서버는 주문번호 발급 (#12345)
- 이후 주문 상태 확인 시 필수
- → REST에서도 마찬가지로, 생성된 리소스의 URI를 반드시 알려줘야 함

## 7. 국제화(i18n)와 Accept-Language 헤더

### 국제화란?
국제화(Internationalization, i18n)는 애플리케이션이 **여러 언어와 지역을 지원할 수 있도록 준비하는 과정**입니다. 스프링 부트는 이를 위한 자동 설정과 메시지 리소스 지원을 제공합니다.

---

### Accept-Language 헤더란?

클라이언트(브라우저 또는 앱)는 서버에 요청을 보낼 때 `Accept-Language` HTTP 헤더를 포함시켜 **선호하는 언어 목록**을 전달.

#### 예시
```http
Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
```
| 언어 코드 | 설명               | 우선순위 (q) |
|-----------|--------------------|--------------|
| `ko-KR`   | 한국어 (대한민국)  | 1.0 (기본)   |
| `ko`      | 일반 한국어        | 0.9          |
| `en-US`   | 미국 영어          | 0.8          |
| `en`      | 일반 영어          | 0.7          |

서버는 이 정보를 바탕으로 가장 적절한 언어 리소스를 선택한다.

#### 스프링 부트에서의 설정
```java
@Bean
public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
    resolver.setDefaultLocale(Locale.ENGLISH); // 기본 언어 설정
    return resolver;
}
```
- AcceptHeaderLocaleResolver: HTTP 요청의 Accept-Language 값을 기반으로 Locale을 설정
- Locale: 언어 및 국가 정보를 표현하는 객체

#### 메시지 리소스 구성
- 스프링 부트는 messages.properties 파일을 통해 다국어 메시지를 관리.

```matlab
src/main/resources/
 ├─ messages.properties
 ├─ messages_ko.properties
 └─ messages_fr.properties
 ```
#### 예시
```properties
# messages.properties
greeting=Hello

# messages_ko.properties
greeting=안녕하세요

# messages_fr.properties
greeting=Bonjour
```
#### 메시지 사용 예시
```java
@Autowired
private MessageSource messageSource;

@GetMapping("/greet")
public String greet(Locale locale) {
    return messageSource.getMessage("greeting", null, locale);
}
```
- 클라이언트가 Accept-Language: ko-KR로 요청하면 → "안녕하세요"

- fr이면 → "Bonjour"

#### 실생활 비유
- 온라인 쇼핑몰 접속 시 자동으로 한글로 보이는 이유?
  - → 브라우저가 Accept-Language: ko-KR 헤더를 서버에 전달했기 때문
- 즉, 브라우저가 "나는 한국어 설명서를 선호합니다"라고 요청한 것과 동일

---

## 7. API 버전 관리
> Rest API를 만들고 100명의 손님을 끌었다.  
> 하지만 원래 만들던 api에 변화를 줘야한다.
- 풀로 받던 이름을 성과 이름으로 나누기
#### 해결방법
##### API 버전 관리 
- ex) twitter
- 기존 API는 그대로 유지하고, 새로운 버전을 추가!!
- 예: `/api/v1/users` → `/api/v2/users`

##### 파라메터 추가
- ex) Amazon
- 기존 API에 새로운 파라메터를 추가
- 예: `/api/users?firstName=John&lastName=Doe`

##### 요청 헤더 사용
- ex) Microsoft
- 클라이언트가 요청 헤더에 버전 정보를 포함
- 예: `Accept: application/vnd.myapp.v2+json`

##### Media Type 버전 관리
- ex) GitHub
- 요청의 Content-Type 또는 Accept 헤더에 버전 정보를 포함
- 예: `application/vnd.myapp.v2+json`

###  API 버전 관리 방식 정리 및 고려할 점

| 방식         | 예시                                | 장점                                       | 단점 및 고려사항 |
|------------|-----------------------------------|--------------------------------------------|------------------|
| URL        | `/api/v1/users`, `/api/v2/users`  | - 명확한 버전 구분<br>- 쉬운 라우팅         | - URL 오염<br>- 자원마다 중복 가능성 |
| 파라미터       | `/users?version=2`, `/users?firstName=John`| - 기존 구조 유지<br>- 빠른 수정 반영 가능  | - query string이 길어질 수 있음 |
| 헤더 기반      | `X-API-VERSION: 2`                | - URL 깨끗함<br>- 내부 구조 분리 가능       | - 브라우저 테스트 어려움<br>- 캐싱 불리 |
| Media Type | `Accept: application/vnd.myapp.v2+json` | - REST 철학에 부합<br>- 문서화에 유리       | - 헤더 설정 복잡<br>- 캐싱 어려움 |

---

### 헤더/미디어 타입 방식의 주요 고려사항

- **장점**
  - URL이 깔끔함 → RESTful API 설계에 부합
  - Swagger 등 API 문서화 도구에서 유연하게 표현 가능

-  **단점**
  - **브라우저 주소창에서 직접 테스트 불가**
    - 브라우저는 직접 헤더를 설정할 수 없기 때문에 curl/Postman 사용 필요
  - **HTTP 캐싱 비효율**
    - URL이 동일하면 브라우저/프록시 캐시가 재사용될 수 없음
    - 즉, 헤더 기반 요청은 캐시 구분이 어려움
  - **Swagger/OpenAPI 문서화 도구와 호환 이슈**
    - 도구가 헤더 조건을 자동 인식하지 못할 수도 있음 → 추가 설정 필요

---

### 결론

- **내부 시스템** 또는 **모바일/서버 간 통신** 중심이라면 → `헤더 기반` 또는 `미디어 타입 기반` 추천
- **브라우저나 외부 공개 API** 위주라면 → `URL 버전` 또는 `쿼리 파라미터` 방식이 더 실용적

> 버전 관리 방식은 일관성 있게 선택해야 하며, API 사용자(클라이언트)의 특성과 테스트 환경도 함께 고려해야 함.   
> 즉 완벽한 방법이란 없다!!
---
## 8. HATEOAS와 API 문서화
- HATEOAS(Hypermedia as the Engine of Application State)는 REST API의 핵심 원칙 중 하나로, 클라이언트가 API를 탐색할 수 있도록 **링크를 제공**하는 방식.

#### 어떻게 소비자에게 링크를 제공할까?
- HATEOAS

1. 응답방식과 구현을 커스텀 한다
   - 유지하기 어려움
2. 스탠다드 라이브러리를 사용한다
   - Spring HATEOAS 라이브러리 사용

---

# 9. Rest API Responses 커스터마이징 – **정적 필터링 vs 동적 필터링**

스프링 부트에서는 **Jackson** 직렬화 모듈이 기본으로 탑재되어 있어, 객체를 JSON으로 변환할 때 보낼 필드(속성)를 자유롭게 조절할 수 있습니다.  
필드 선택 방식은 크게 **정적 필터링(static filtering)** 과 **동적 필터링(dynamic filtering)** 두 가지로 나뉩니다.

| 구분 | 적용 시점 | 대표 애너테이션 / API | 특징 | 주요 사용처 |
|------|-----------|-----------------------|-------|------------|
| **정적 필터링** | **컴파일 시점**<br>(클래스 정의 단계) | `@JsonIgnore`<br>`@JsonIgnoreProperties`<br>`@JsonInclude` | ⬥ 항상 동일한 규칙 적용<br>⬥ 간단·빠름 | ① 민감정보 상시 마스킹<br>② 요청마다 규칙이 바뀌지 않는 필드 |
| **동적 필터링** | **런타임 시점**<br>(컨트롤러·서비스 계층) | `@JsonFilter` + `MappingJacksonValue`<br>`@JsonView` | ⬥ 요청별로 노출 필드 가변<br>⬥ 로직은 조금 복잡 | ① 한 엔드포인트에서 여러 뷰 제공<br>② 모바일/웹 등 클라이언트 맞춤 응답 |

---

## 9‑1. 정적 필터링 (Static Filtering)

> **“항상 같은 필드를 감출 때는 클래스에 직접 ‘라벨’을 붙여라.”**

### ① `@JsonIgnore` ‑ 필드 단위로 숨기기
```java
public class UserDto {
    private Long id;
    private String username;

    @JsonIgnore          // 항상 제외
    private String password;
}
```
- **장점**: 선언 한 줄이면 끝 → **성능 영향 無**, 유지보수 단순
- **단점**: 모든 엔드포인트에서 **항상** 숨겨짐. 특정 요청에서만 보여주고 싶어도 불가.

### ② `@JsonIgnoreProperties` ‑ 여러 필드 한꺼번에
```java
@JsonIgnoreProperties({"password", "ssn"})
public class UserDto { … }
```

### ③ `@JsonInclude` ‑ 조건부 직렬화
```java
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 제외
public class CommentResponse { … }
```
- null·빈 컬렉션·기본값만 제외 등 세밀한 옵션 제공.

---

## 9‑2. 동적 필터링 (Dynamic Filtering)

> **“같은 객체라도 호출 컨텍스트마다 다른 ‘가면’을 씌워라.”**

### ① `@JsonFilter` + `MappingJacksonValue`

1. **도메인/DTO에 이름 없는 필터를 선언**
   ```java
   @JsonFilter("UserDynamicFilter")
   public class UserDto { … }
   ```

2. **컨트롤러에서 노출할 필드 결정**
   ```java
   @GetMapping("/users/{id}")
   public MappingJacksonValue getUser(@PathVariable Long id) {
       UserDto user = userService.find(id);

       // ① 보여줄 필드만 남기거나
       SimpleBeanPropertyFilter filter =
            SimpleBeanPropertyFilter.filterOutAllExcept("id", "username", "email");

       // ② 특정 필드만 제외할 수도 있음
       // SimpleBeanPropertyFilter.serializeAllExcept("password")

       FilterProvider filters = new SimpleFilterProvider()
               .addFilter("UserDynamicFilter", filter);

       MappingJacksonValue wrapper = new MappingJacksonValue(user);
       wrapper.setFilters(filters);
       return wrapper;
   }
   ```

### ② `@JsonView` ‑ 뷰 계층화 방식
```java
public class Views {
    public static class Public {}
    public static class Internal extends Public {}
}

public class UserDto {
    @JsonView(Views.Public.class)
    private Long id;

    @JsonView(Views.Public.class)
    private String username;

    @JsonView(Views.Internal.class)
    private String ssn;
}

@GetMapping("/users/{id}")
@JsonView(Views.Public.class)     // 공개 뷰
public UserDto getPublic(@PathVariable Long id){ … }

@GetMapping("/internal/users/{id}")
@JsonView(Views.Internal.class)   // 내부 뷰
public UserDto getInternal(@PathVariable Long id){ … }
```
- **장점**: 필드 → 뷰 → 컨트롤러 간 역할이 분리되어 가독성 ↑
- **단점**: 뷰 클래스를 계속 관리해야 하며, 세밀한 조건 조합에는 `MappingJacksonValue`가 더 유연합니다.

---

## 9‑3. 언제 어떤 방식을 택할까?

| 선정 기준 | 추천 방식 | 이유 |
|-----------|----------|------|
| **항상 숨겨야 하는 민감 정보** (비밀번호, 내부 ID) | 정적(`@JsonIgnore`) | 실수 방지·성능 좋음 |
| **엔드포인트마다 다른 응답 스펙** (모바일 전용, 관리자용) | 동적 필터링 | 요청별 필드 선택 가능 |
| **동일 리소스를 뷰 관점으로 분리** (Public vs Internal) | `@JsonView` | 뷰 계층 구조로 직관적 |
| **Swagger/OpenAPI로 문서화 필요** | 정적 필터링 or `@JsonView` | 필드 구성이 정적이어야 문서 반영이 용이 |

---

## 9‑4. 실전 예시 & 성능 메모

| 시나리오 | 선택 | 이유 |
|----------|------|------|
| **회원 목록 API** – “아이디·닉네임만 보여줘” | `filterOutAllExcept` | 리스트라 성능 민감. 동적 필터로 필요 필드만 직렬화 ➜ 네트워크 절감 |
| **주문 상세 API** – “관리자만 가격 원가 열람” | `@JsonView` | Public·Admin 두 뷰가 명확히 구분 |
| **로그 저장 엔티티** – “IP 주소는 절대 노출 X” | `@JsonIgnore` | 실수 방지 가장 확실 |

> **벤치마크**   
> 일반적으로 `@JsonIgnore` → `@JsonView` → `MappingJacksonValue` 순으로 오버헤드가 증가하지만, 네트워크 전송량이 크게 줄어드는 경우 **전체 응답 시간은 오히려 빨라질 수 있으므로** **JMH · VisualVM** 등으로 실제 프로파일링을 권장합니다.

---

**요약**  
정적 필터링과 동적 필터링을 적절히 조합하면, “필요할 땐 가볍고, 복잡해질 땐 유연하게” ― API 응답을 **안전하면서도 확장 가능**하게 설계할 수 있습니다.
