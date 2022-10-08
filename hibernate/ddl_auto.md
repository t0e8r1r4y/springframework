## spring.jap.hibernate.ddl-auto 설정 관련 옵션
- 간단하지만 중요하다고 생각하는 부분
- ddl-auto 옵션 종류
     - create : 기존 테이블 삭제 후 다시 생성 ( drop 후 create 동작 )
     - create-drop : 기존 테이블 삭제 후 다시 생성 ( 프로세스 종료 시점에서 모두 drop 후, 프로세스 재 시작 시점에서 create 동작 )
     - update : 변경분만 반영
     - validate : 엔티티와 테이블이 정상 매핑되었는지 확인
     - none : 사용하지 않음 ( 사실상 없는 값 )
     
  
- 예시
```
spring:
  profiles:
    active: local # 기본 환경 선택
  jpa:
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    open-in-view: false
    generate-ddl: true
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
```


- 사용하는 경우
    - [주의] 운영 장비에서는 절대 create, create-drop, update 사용하면 안된다.
    - 스테이징과 운영 서버는 validate or none
    - 개발 초기 단계에는 create 또는 update
    - 테스트 서버는 update 또는 Validate
