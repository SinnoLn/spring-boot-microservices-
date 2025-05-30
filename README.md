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
✅ 리소스는 복수형으로 표현하자!
- /users/1 ← 선호
- /user/1 ← 비권장

## HTTP 상태 코드 요약
👉 HTTP 상태 코드 전체 요약 (1xx~5xx)

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