# Concurrency

> java/spring framework에서 동시성 제어 이슈를 다뤄봅니다.  

<br/>

## Java에서 제공하는 동시성 제어
- Java에서는 Synchronize를 사용해서 동시성을 제어할 수 있습니다.
- 다만 위 방식의 단점은 하나의 서버 어플리케이션 내부에서만 동기화가 맞춰지기 때문에 분산 시스템에서는 동기화가 이뤄지지 않을 수 있음.

## Database 를 활용하여 레이스컨디션 해결해보기
1. Optimistic Lock  
   - lock 을 걸지않고 문제가 발생할 때 처리합니다.
   - 대표적으로 version column 을 만들어서 해결하는 방법이 있습니다. 
2. Pessimistic Lock (exclusive lock)  
   - 다른 트랜잭션이 특정 row 의 lock 을 얻는것을 방지합니다.
   - A 트랜잭션이 끝날때까지 기다렸다가 B 트랜잭션이 lock 을 획득합니다.
   - 특정 row 를 update 하거나 delete 할 수 있습니다.
   - 일반 select 는 별다른 lock 이 없기때문에 조회는 가능합니다.
3. named Lock 활용하기
   - 이름과 함께 lock 을획득합니다.
   - 해당 lock 은 다른세션에서 획득 및 해제가 불가능합니다.


## Redis를 사용한 동시성 제어
1. Lettuce
   - setnx 명령어를 활용하여 분산락 구현
   - sprin lock 방식
   - 구현이 간단하다
   - spring data redis 를 이용하면 lettuce 가 기본이기때문에 별도의 라이브러리를 사용하지 않아도 된다.
   - spin lock 방식이기때문에 동시에 많은 스레드가 lock 획득 대기 상태라면 redis 에 부하가 갈 수 있다.

2. Redission
   - pub-sub 기반으로 lock 구현 제공
   - 락 획득 재시도를 기본으로 제공한다.
   -  pub-sub 방식으로 구현이 되어있기 때문에 lettuce 와 비교했을 때 redis 에 부하가 덜 간다.
   -  별도의 라이브러리를 사용해야한다.
   -  lock 을 라이브러리 차원에서 제공해주기 떄문에 사용법을 공부해야 한다.

3. 실무에서는
   - 재시도가 필요하지 않은 Lock은 lettuce 활용
   - 재시도가 필요한 경우에는 redission 활용


## MySQL VS Redis

#### Mysql
- 이미 Mysql 을 사용하고 있다면 별도의 비용없이 사용가능하다.
- 어느정도의 트래픽까지는 문제없이 활용이 가능하다.
- Redis 보다는 성능이 좋지않다.

#### Redis
- 활용중인 Redis 가 없다면 별도의 구축비용과 인프라 관리비용이 발생한다.
- Mysql 보다 성능이 좋다.

<br/>

## 참고자료
- [git](https://github.com/sangyongchoi/stock-example)
- [doc](https://docs.google.com/document/d/1Nax0Q0dQpE0dlGFdro65kRZE_7wkmBbxrgCmcdhCJ0o/edit)
- https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_exclusive_lock
- https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html
- https://dev.mysql.com/doc/refman/8.0/en/locking-functions.html
- [줌 인터넷 기술 블로그](https://github.com/hgs-study/redis-sorted-set-practice)
