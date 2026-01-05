# 📝 Todo 리스트 관리 프로젝트 (practicePJT)

Spring Boot와 Mustache를 활용하여 제작한 간단한 할 일(Todo) 관리 웹 애플리케이션입니다. 할 일의 등록, 조회, 수정, 삭제(CRUD) 기능을 제공하며, 우선순위 및 상태 관리가 가능합니다.

## 🛠 사용 기술 (Tech Stack)

* **Backend**: Java 25, Spring Boot 4.0.1
* **Framework**: Spring Data JPA, Spring Web MVC
* **Database**: H2 (In-memory Database)
* **View Engine**: Mustache
* **Build Tool**: Gradle
* **Lombok**: 어노테이션 기반 코드 간소화

## ✨ 주요 기능 (Core Features)

1.  **할 일 관리 (CRUD)**
    * **등록**: 제목, 내용, 마감기한, 우선순위 등을 설정하여 새로운 할 일을 등록합니다.
    * **조회**: 전체 목록 조회 및 특정 할 일의 상세 정보를 확인할 수 있습니다.
    * **수정**: 기존 할 일의 내용, 상태, 우선순위 등을 변경합니다.
    * **삭제**: 완료되었거나 불필요한 할 일을 삭제합니다.
2.  **데이터 자동 관리 (JPA Auditing)**
    * `@CreatedDate`와 `@LastModifiedDate`를 사용하여 할 일이 생성된 시간과 마지막으로 수정된 시간을 자동으로 기록합니다.
3.  **상태값 관리**
    * 할 일 생성 시 상태(`status`)를 지정하지 않으면 기본적으로 "TODO"로 설정되도록 비즈니스 로직이 구현되어 있습니다.

## 🗂 ERD (데이터베이스 구조)

### Todo 테이블
| 컬럼명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `id` | Long (PK) | 자동 생성되는 고유 식별자 (Identity 전략) |
| `title` | String | 할 일 제목 |
| `content` | String | 상세 내용 |
| `deadline` | LocalDate | 마감 기한 |
| `priority` | String | 우선순위 (긴급, 보통, 나중 등) |
| `status` | String | 진행 상태 (TODO, IN_PROGRESS, DONE, HOLD) |
| `createdAt` | LocalDateTime | 생성 일시 (수정 불가) |
| `updatedAt` | LocalDateTime | 최종 수정 일시 |

## 📑 API 명세서 (API Specification)

| 기능 | Method | URI | 설명 |
| :--- | :--- | :--- | :--- |
| 홈 화면 | GET | `/todos/home` | 메인 홈 페이지 이동 |
| 등록 폼 | GET | `/todos/new` | 새 할 일 작성 페이지 이동 |
| 할 일 등록 | POST | `/todos/create` | 할 일 저장 후 상세 페이지로 리다이렉트 |
| 상세 조회 | GET | `/todos/{id}` | 특정 ID의 할 일 정보 조회 |
| 목록 조회 | GET | `/todos/list` | 전체 할 일 목록 조회 |
| 수정 폼 | GET | `/todos/{id}/edit` | 기존 데이터 수정을 위한 페이지 이동 |
| 할 일 수정 | POST | `/todos/update` | 데이터 수정 후 상세 페이지로 리다이렉트 |
| 할 일 삭제 | GET | `/todos/{id}/delete` | 할 일 삭제 후 목록 페이지로 리다이렉트 |

## 🖼 프로젝트 실행 화면

* **목록 및 상세 보기**: Mustache 템플릿 레이아웃(header, footer)을 적용하여 일관된 UI를 제공합니다.
* **수정 기능**: 수정 시 현재의 우선순위와 상태가 폼에 자동으로 반영되어 사용자 편의성을 높였습니다.
* **알림 메시지**: 삭제 완료 시 `RedirectAttributes`를 통해 사용자에게 삭제 성공 메시지를 전달합니다.

## 💡 개발 포인트

* **DTO 사용**: `TodoForm`을 통해 계층 간 데이터 전송을 분리하고, `toEntity()` 메서드를 통해 안전하게 엔티티로 변환합니다.
* **Repository 활용**: `CrudRepository`를 상속받아 간단하게 DB 연동을 구현했으며, `findAll()`을 오버라이딩하여 `List` 형태로 반환하도록 개선했습니다.
* **로그 관리**: `@Slf4j`를 활용하여 주요 서비스 흐름(데이터 수신, 변환, 저장 등)을 로그로 기록합니다.
