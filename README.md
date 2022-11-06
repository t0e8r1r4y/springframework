# Concurrency

## 참고자료
- [git](https://github.com/sangyongchoi/stock-example)
- [doc](https://docs.google.com/document/d/1Nax0Q0dQpE0dlGFdro65kRZE_7wkmBbxrgCmcdhCJ0o/edit)
- https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_exclusive_lock
- https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html
- https://dev.mysql.com/doc/refman/8.0/en/locking-functions.html


<br/>

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
2. Redission
   - pub-sub 기반으로 lock 구현 제공