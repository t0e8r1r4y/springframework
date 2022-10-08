# redisTest
레디스로 Caceh Aside 패턴이라는 캐싱전략을 구현한 springboot 프로젝트 구성입니다.자세한 설명은 [링크](https://medium.com/@tas.com/cache-aside-%ED%8C%A8%ED%84%B4-%EA%B5%AC%ED%98%84%EC%9D%84-%ED%86%B5%ED%95%9C-springboot-redis-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-6416039b96a5)에 작성하였습니다.


---

[ 구성 ] 


해당 어플리케이션과 저장소의 배치 구성은 아래와 같습니다.


![cacheAside drawio](https://user-images.githubusercontent.com/91730236/182029051-ba48f9b2-7cde-437f-9fbe-44a052f5a054.png)


- springboot 2.7.2  
- gradle 7.4.2  
- redis 7.0.4  
- mysql 5.7.32  


---
[ 데이터 셋 ]



![1_RPJHrVW4OLG-T2qOajIDQw](https://user-images.githubusercontent.com/91730236/182029206-55be47e4-0be7-49f8-a9ab-3fc4364b6c49.png)


- employee table에는 300024 row 데이터가 존재  
- manager table에는 24 row 데이터가 존재  
- employee_department table에는 331603 row 데이터가 존재  
- 조회가 될 데이터는 약 14300건입니다.

-- yml 파일은 제외하고 올립니다. 차후에 외부 경로에서 읽어 오도록 수정 예정입니다.



---
[ 구현 시나리오 ]


- 회사 시스템이라 가정한다. 매니저는 본인의 이름(last, first 구분), 사원번호, 부서번호를 입력하면 해당 부서에 소속 된 직원들의 정보를 화면에 출력한다.
- 여기서 직원들은 공채나 상시채용을 하지만, 생각보다 자주 바뀌는 정보는 아니다. 그리고 매니저는 직원 관리를 위해서 직원들을 자주 조회해야 된다.
- 그래서 최초 로그인 시에만 DB에서 데이터를 조회하고 30초 이내로 재접속하면 캐시에 저장 된 값을 가져온다.


---
[ 캐시 적용결과 ]

- 적용 전 : 시작 -> 00:30:32:18306 / 종료 -> 00:30:36:21712 약 4초
- 적용 후 : 시작 -> 00:30:54:40252 / 종료 -> 00:30:55:40799 약 1초
- DB에서 조회하면 약 14300 row를 조회하는데 4초가 걸린다. 이 내용을 캐시에서 동일하게 조회하는데 약 1초가 걸린다.










---
### 프로젝트의 주요 관심사

- 코드 컨벤션 : 배달의 민족 code Style 준수
- redis 사용방법 정리 및 동작 코드 저장





---
### 개선사항

- 현재 redis의 일부기능만을 구현하여 저장. redis에서 제공하는 데이터 타입의 다양한 사용 및 기능 구현 추가 필요
- 코드 리팩토링 필요
