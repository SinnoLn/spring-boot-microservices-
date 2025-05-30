# ☁️ Master Microservices with Spring Boot and Spring Cloud

이 저장소는 Udemy 강의 [Master Microservices with Spring Boot and Spring Cloud](https://www.udemy.com/course/microservices-with-spring-boot-and-spring-cloud/)의 실습을 기반으로 정리된 학습 노트 및 코드 저장소입니다.  
총 26시간 분량의 실습을 따라가며 REST API 설계부터 Docker/Kubernetes 기반 MSA 시스템 구성까지 전 과정을 기록합니다.

---

## 🧰 사용 기술 스택

### 🖥️ 백엔드 프레임워크
- Java 17+
- Spring Boot (REST API, Security, JPA)
- Spring Cloud (Eureka, Gateway, Config Server, Sleuth, Zipkin)
- Spring Boot Actuator (헬스 체크 및 모니터링)
- Feign (Microservice 간 통신)

### ☁️ 클라우드/운영 도구
- **Docker** (컨테이너화)
- **Kubernetes** (마이크로서비스 오케스트레이션)
- **Resilience4j** (회복 탄력성, Circuit Breaker)
- **Postman** (API 테스트)
- **Zipkin + Sleuth** (분산 트레이싱)
- **Maven** (의존성 관리)

### 🛠️ 개발 환경
- Eclipse IDE
- Docker Desktop / Podman Desktop
- Embedded Tomcat
- Git (Spring Cloud Config용 Local Repo)
- Swagger (API 문서 자동화)

---

## 📝 주요 학습 내용

### 📦 Part 1: RESTful Web Services
- Hello World API → User CRUD 개발
- HATEOAS, 필터링, 예외 처리
- API 버전 관리, Spring Security 인증
- Swagger(OpenAPI) 문서 자동화
- Spring Boot Actuator를 활용한 모니터링
- JPA 연동 및 관계 매핑

### 🧩 Part 2: Spring Cloud Microservices
- Microservice 구성: Limits, CurrencyExchange, CurrencyConversion
- Spring Cloud Config Server 구성
- Eureka Naming Server + Client 연결
- Feign Client를 통한 서비스 통신
- Spring Cloud Gateway 적용
- 분산 트레이싱 (Sleuth + Zipkin)
- Circuit Breaker, Retry, Rate Limiting (Resilience4j)

### 🐳 Part 3: Docker
- Microservice 컨테이너화
- Dockerfile 작성 및 빌드
- Docker Compose 사용

### ☸️ Part 4: Kubernetes
- Microservice 배포 자동화
- 서비스 간 디스커버리 및 로드밸런싱
- YAML 설정, ConfigMap, Scaling 등

---

## ⚙️ 요구 사항

- Java 및 Spring Boot 기본 지식
- Microservice, Docker, Kubernetes는 **처음 접해도 OK**
- 로컬 개발환경에서 Docker Desktop 또는 Podman Desktop 설치 가능해야 함

---

## 📌 강의 정보

- 강의명: **Master Microservices with Spring Boot and Spring Cloud**
- 강사: [in28minutes 공식 강의](https://www.udemy.com/user/in28minutes/)
- 총 수강생 수: 280,000명+
- 언어: 영어 (한글 자막 제공)
- 수강일: 2024년 12월 구매 / 2025년 기준 최신 내용 반영

---

## ✅ 기대 효과

- RESTful API 설계 및 보안 적용 능력
- Spring Cloud 기반 MSA 설계 및 구성 경험
- 실전 컨테이너 환경에서 MSA 배포 자동화 실습
- Spring 전반에 대한 고급 이해 + 실무 적용력 강화

---

> 🚀 이 강의를 통해 Spring 기반 백엔드 개발자는 MSA 기반 실무 수준까지 도약할 수 있습니다.
