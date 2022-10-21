<h1 align="center">
  <br>
  <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcuSahr_aLdlNT-wkMm8wd06qPnlLK-8uI1Q&usqp=CAU"  width=600"></a>
  <br>
  <br>
     스프링 프
  <br>
  <br>
</h1>


# springframework
                                                                         
## 소개
- spring framework와 관련 내용을 정리합니다.
- 여러개의 프로젝트를 하나로 관리하는 과정에서 설정파일에 개인정보 관리가 귀찮아서 application.properties와 yml 파일은 반드시 `local`에서 잘 관리

## :trident: 공식 도큐먼트
                                                                                                                                               
| TITLE | LINK |
| ------ | ------ |
| 스프링 공식문서 | [link][스프링공식] |
| 토리맘의 한글 번역 | [link][스프링공식번역] |
| Spring Initializr | [link][스프링프로젝트생성] |
| 스프링 프레임워크에서 제공하는 의존성과 관련 예시 | [link][스프링의존성예시] |
                                                                         
## :crown: 스프링에 대한 모든것 ( 필요한 내용 하나씩 번역하고 정리 )

| 구분 | 종류 |
| ------ | ------ |
| Overview | history, design philosophy, feedback, getting started. |
| Core | IoC Container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP. |
| Testing | Mock Objects, TestContext Framework, Spring MVC Test, WebTestClient. |
| [Data Access][데이터엑세스] | [Transactions][트랜잭션], DAO Support, JDBC, R2DBC, [O/R Mapping][ORM매핑], XML Marshalling. |
| Web Servlet | Spring MVC, WebSocket, SockJS, STOMP Messaging. |
| Web Reactive | Spring WebFlux, WebClient, WebSocket, RSocket. |
| Integration | Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Caching. |
| Languages | Kotlin, Groovy, Dynamic Languages |
| Appendix | Spring properties. |
| Wiki | What’s New, Upgrade Notes, Supported Versions, and other cross-version information. |
                                                                         
                                                                         
                                                                         
                                                                         
                                                                         
## :heavy_plus_sign: 설정파일 관리하기


##  :custard: 데이터베이스 연결 및 설정, 사용 관련 팁
- [hibernate auto-ddl 설정관리](https://github.com/t0e8r1r4y/springframewordk/blob/main/hibernate/ddl_auto.md)
- [h2 데이터베이스에서 테이블 데이터 export 명령어](https://github.com/t0e8r1r4y/springframewordk/blob/main/hibernate/h2-data-export.md)
- [커스텀 엔티티 ID 부여하는 방법](https://github.com/t0e8r1r4y/springframewordk/blob/main/hibernate/Identifiers.md)
- [JPA 자동 생성 쿼리 메소드 명명 규칙](https://github.com/t0e8r1r4y/springframewordk/blob/main/hibernate/JPA_AutoGenQuery.md)


## :green_apple: 어노테이션 정리
- @Transactional
- [@Async](https://brunch.co.kr/@springboot/401)
- [@scheduled](https://github.com/t0e8r1r4y/springframewordk/blob/main/annotation/schedule.md)

## :strawberry: 빌드 도구
- Maven
- Gradle

## :banana: 오픈소스 연계
- [spring  + 프로메테우스로 모니터링 하기](https://github.com/t0e8r1r4y/springframewordk/tree/main/prometheus_spring)

##  :purse: 기타
- [16 Best Practices in Spring Boot Production](https://medium.com/@hubian/16-best-practices-in-spring-boot-production-62c065a6145c)


---
**Thanks!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
   [스프링공식]: <https://docs.spring.io/spring-framework/docs/current/reference/html/>
   [스프링공식번역]: <https://godekdls.github.io/>
   [스프링프로젝트생성]: <https://start.spring.io>
   [스프링의존성예시]: <https://github.com/spring-projects/spring-kafka/tree/main/samples>
   [데이터엑세스]: <https://github.com/t0e8r1r4y/springframewordk/blob/main/OfficialDoc/DATAACCESS.md>
   [트랜잭션]: <https://github.com/t0e8r1r4y/springframewordk/blob/main/OfficialDoc/DataAccess/TransactionManagement.md>
   [ORM매핑]: <https://github.com/t0e8r1r4y/springframewordk/blob/main/OfficialDoc/DataAccess/ORMMapping.md>
