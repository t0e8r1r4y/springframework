# Springboot와 Prometheus 연동에 대한 기본 뼈대입니다.

## 개발환경 및 실행환경

- VScode - spring boot install
- Docker - prometheus server
- VScode 설치나 Docker 설치에 대한 부분은 설명 생략하고 넘어감

## 1. spring boot에 prometheus 의존성 주입

<aside>
💡 spring boot의 상태정보를 수집해서 프로메테우스 서버로 전송하기 위한 행위
즉 프로메테우스 클라이언트에다가 상태정보를 수집할 수 있도록 세팅함

</aside>

### Spring boot test project 생성

![Untitled](https://user-images.githubusercontent.com/91730236/194707478-31c6a08e-67dc-4e16-888d-a2b25be597a5.png)

- java 11, jar로 세팅
- 롬북이나 웹 등 기본 의존성 주입
- 크롬에서 [localhost:8080](http://localhost:8080) 실행 시켰을 때 창이 뜨면 성공

### Actuator, Prometheus 의존성 주입

- Actuator란?
    - 스프링부터 어플리케이션에 대해 모니터링 할 수 있는 라이브러리
    - maven 적용 방법 : porm.xml에 의존성 추가 ( 아래 사이트에서 복붙 ) 
    - 의존성 추가 후 [localhost:8080/actuator](http://localhost:8080/actuator) 입력 시 json 형태로 정보가 표시되면 정상
        
        ![스크린샷_2021-10-31_오전_1 13 19](https://user-images.githubusercontent.com/91730236/194707456-5185c2c4-ec83-4390-b8f6-930c2c72cfe9.png)
        
        `{"_links":{"self":{"href":"http://localhost:8080/actuator","templated":false},"health":{"href":"http://localhost:8080/actuator/health","templated":false},"health-path":{"href":"http://localhost:8080/actuator/health/{*path}","templated":true},"info":{"href":"http://localhost:8080/actuator/info","templated":false}`
        
    - 만약에 표시가 되지 않는다면...
        - 코드에서 어노테이션 ComponentScan하고 Controller를 프로젝트 하위에 넣어줘야함. ( 스프링에 대해 깊은 이해가 없어 일단 요렇게 하면 나온다는 것 까지만... )
        
        [Spring Boot Whitelabel error page](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=whapkj303&logNo=221565938970)
        
        ```java
        package com.prometheus.prometheus;
        
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.context.annotation.ComponentScan;
        
        @ComponentScan(basePackages = {"com.prometheus.controller"})
        @SpringBootApplication
        public class PrometheusApplication {
        
        	public static void main(String[] args) {
        		SpringApplication.run(PrometheusApplication.class, args);
        	}
        
        }
        ```
        
- 프로메테우스 의존성 주입
    - Actuator와 동일하게 porm.xml에서 의존성주입
    
    [](https://mvnrepository.com/artifact/io.micrometer/micrometer-registry-prometheus/1.7.5)
    
    - 실행 결과
        
        `{"_links":{"self":{"href":"http://localhost:8080/actuator","templated":false},"health":{"href":"http://localhost:8080/actuator/health","templated":false},"health-path":{"href":"http://localhost:8080/actuator/health/{*path}","templated":true},"info":{"href":"http://localhost:8080/actuator/info","templated":false},"prometheus":{"href":"http://localhost:8080/actuator/prometheus","templated":false}}}`
        
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
        	<modelVersion>4.0.0</modelVersion>
        	<parent>
        		<groupId>org.springframework.boot</groupId>
        		<artifactId>spring-boot-starter-parent</artifactId>
        		<version>2.5.6</version>
        		<relativePath/> <!-- lookup parent from repository -->
        	</parent>
        	<groupId>com.prometheus</groupId>
        	<artifactId>prometheus</artifactId>
        	<version>0.0.1-SNAPSHOT</version>
        	<name>prometheus</name>
        	<description>Demo project for Spring Boot</description>
        	<properties>
        		<java.version>11</java.version>
        	</properties>
        	<dependencies>
        		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator -->
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-starter-actuator</artifactId>
        			<version>2.5.5</version>
        		</dependency>
        
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-starter-web</artifactId>
        		</dependency>
        
        		<!-- https://mvnrepository.com/artifact/io.micrometer/micrometer-registry-prometheus -->
        		<dependency>
        			<groupId>io.micrometer</groupId>
        			<artifactId>micrometer-registry-prometheus</artifactId>
        			<version>1.7.5</version>
        		</dependency>
        
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-devtools</artifactId>
        			<scope>runtime</scope>
        			<optional>true</optional>
        		</dependency>
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-configuration-processor</artifactId>
        			<optional>true</optional>
        		</dependency>
        		<dependency>
        			<groupId>org.projectlombok</groupId>
        			<artifactId>lombok</artifactId>
        			<optional>true</optional>
        		</dependency>
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-starter-test</artifactId>
        			<scope>test</scope>
        		</dependency>
        	</dependencies>
        
        	<build>
        		<plugins>
        			<plugin>
        				<groupId>org.springframework.boot</groupId>
        				<artifactId>spring-boot-maven-plugin</artifactId>
        				<configuration>
        					<excludes>
        						<exclude>
        							<groupId>org.projectlombok</groupId>
        							<artifactId>lombok</artifactId>
        						</exclude>
        					</excludes>
        				</configuration>
        			</plugin>
        		</plugins>
        	</build>
        
        </project>
        ```
        

## 2. prometheus 서버 설치 ( docker image 활용 )

<aside>
💡 local에 설치를 하는 등의 방법으로 프로메테우스 서버를 구동 시킬 수 있음
여기서는 Application 실행 환경과 프로메테우스 서버 구동 환경을 구분하고자 docker Container를 사용하여 실행시켜 봄

</aside>

### 서버 기동 준비

- docker image 가져오기
    
    [Installation | Prometheus](https://prometheus.io/docs/prometheus/latest/installation/)
    
    [Docker Hub](https://hub.docker.com/r/prom/prometheus)
    
- config file인 yml 파일 작성하기 ( 가장 기본적인 스타팅 예제로 작성 함)
    - 여기서 target은 프로메테우스가 폴링해서 데이터를 가져올 cli의 주소를 적어주면 됨
    - ( 여기서는 내 host PC의 IP를 적어 두었고, spring boot 기본 포트 8080으로 작성 )
    
    ```yaml
    global:
      scrape_interval: 5s
    scrape_configs:
      - job_name: 'spring-boot-test'
        metrics_path: '/actuator/prometheus'
        static_configs:
          - targets: ['XXX.XXX.XXX.XXX:8080']
    ```
    

### 도커 컨테이너 RUN

- 명령어
    
    docker run -p 9090:9090 -v /Users/terryakishin/prometheus_spring/prometheus.yml:/etc/prometheus/prometheus.yml --name prometheus -d bitnami/prometheus --config.file=/etc/prometheus/prometheus.yml
    
- 여기서 하나 문제가 있었던 부분! 컨테이너 접속
    - 컨테이너 접속을 하기 위해 docker exec -it <컨테이너ID> /bin/bash 명령어 입력 시 에러 발생
    - 에러 내용은 starting container process caused "exec: \"/bin/bash\"
    - 에러 원인 : Docker Image가 [Alpine](https://ko.wikipedia.org/wiki/%EC%95%8C%ED%8C%8C%EC%9D%B8_%EB%A6%AC%EB%88%85%EC%8A%A4)이라면 **/bin/bash**를 지원하지 않을 수 있다.
    - 이럴 때는 /bin/sh로 접속이 필요함

### 실행확인

- localhost:9090으로 접속하여 target tab에서 확인
    
    ![Untitled 1](https://user-images.githubusercontent.com/91730236/194707459-4c8a5031-e2a7-4232-8ffa-c2d44bd0f351.png)
    
- Last Scrape 시간이 설정한 시간을 주기로 계속 업데이트 됨을 확인 가능
- 조회 하고 싶은 지표에 대해서는 프로메테우스 그래프에서도 가능
    
    ![Untitled 2](https://user-images.githubusercontent.com/91730236/194707467-3c79727d-fb4a-4a0b-8519-76eda56a1ed8.png)
    

## (번외)  그라파나로 시각화 하기

### 그라파나 도커 이미지 pull

[Docker Hub](https://hub.docker.com/r/grafana/grafana)

### 프로메테우스와 연결하여 데이터 가져오기

- docker image Hub를 선택 후
- docker run -d --name=grafana -p 3000:3000 grafana/grafana 입력
- [localhost:3030](http://localhost:3030)으로 그라파나 접속
- 최초 로그인시 admin admin으로 접속 가능
- 접속 후 Configuration에서 Data Source 클릭
- 프로메테우스 선택
- 현재 특별한 부분이 없으니 URL만 프로메테우스 서버와 포트 입력
    
    ![Untitled 3](https://user-images.githubusercontent.com/91730236/194707470-846cba19-648b-40fe-b7c5-417ebb13bb34.png)
    

### 대쉬보드 사용하기

- 다시 시작 페이지에서 create dashboard
- 필요한 메트릭 정보들 쿼리로 조회

![Untitled 4](https://user-images.githubusercontent.com/91730236/194707472-a98291ac-76a0-4e59-b720-9e16542e55d1.png)

![Untitled 5](https://user-images.githubusercontent.com/91730236/194707475-bad15693-4b76-4492-a422-ddf4d18fbce6.png)


<aside>
💡 그라파나 사용에 대해서는 별도로 다시한번 다뤄야 할 만큼 내용이 많아서 일단 요런 구성으로 돌릴 수 있다는 것 까지만 설명함

</aside>

> 그래서 무엇을 모니터링 해야되는가?
> 
- 기본적으로 CPU, Memory, Disk, Network, File System, System Daemon etc
- 세션에 대한 정보, 프로토콜 데이터 단위 등 필요한 것들

[사용자 지정 PromQL 쿼리로 SMF 통화 흐름 문제 해결](https://www.cisco.com/c/ko_kr/support/docs/wireless/ultra-cloud-core-session-management-function/215830-troubleshoot-smf-call-flow-issues-with-c.html#anc6)

[Monitoring 01. 프로메테우스를 이용하여 프로젝트 매트릭 수집하기](https://lob-dev.tistory.com/entry/Monitoring-01-%ED%94%84%EB%A1%9C%EB%A9%94%ED%85%8C%EC%9A%B0%EC%8A%A4%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%A9%94%ED%8A%B8%EB%A6%AD-%EC%88%98%EC%A7%91%ED%95%98%EA%B8%B0)
