# Spring scheduler 사용에 관한 내용입니다.

- @Componet 어노테이션을 @Scheduled를 사용할 클래스에 사용합니다.
- 해당 클래스 내 @Scheduled를 메서드에 사용합니다.
- [주의] 프로젝트 main class에는 반드시 @EnableScheduling을 붙여줍니다.
- 해당 실행을 비동기로 처리하고자 할 경우 @Async 어노테이션을 붙여 사용할 수 있습니다.

```
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
	}
}
```

## 옵션
- fixedDelay
    - 이전 작업이 종료된 후 설정시간(miliseconds) 이후에 다시 시작
    - 이전 작업이 완료될 때까지 대기
- fixedRate
    - 고정 시작 간격으로 시작
    - 이전 작업이 완료될 때까지 다음 작업이 진행되지 않음
- fixedDelay + fixedRate
    - initialDelay 값 기준으로 처음 실행
    - fixedDelay 값에 따라 계속 실행
    ```
    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    ```
- Cron ( Zone 설정 포함 )
    - 작업 예약으로 실행
    ```
    @Scheduled(cron = "0 15 10 15 * ?") // 매월 15일 오전 10시 15분에 실행
    @Scheduled(cron = "0 15 10 15 11 ?") // 11월 15일 오전 10시 15분에 실행
    @Scheduled(cron = "${cron.expression}")
    @Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris") // timezone 설정
    ```


## 더 찾아보기
- Quants?


## 참고자료

| 제목 | 링크 | 연관 내용 |
| --- | --- | --- |
| @Scheduled | [springio](https://spring.io/guides/gs/scheduling-tasks/) | [git example](https://github.com/spring-guides/gs-scheduling-tasks/blob/main/complete/src/main/java/com/example/schedulingtasks/ScheduledTasks.java) |
| 크롭 옵션 | [Allonsy님 블로그](https://allonsyit.tistory.com/43) | |
