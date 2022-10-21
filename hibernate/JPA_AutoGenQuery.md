# Java Persistence API 메소드 명명 규칙 정리

> Spring Data JPA는 Repository 인터페이스에 선언된 메소드를 그 이름에서 쿼리를 생성하고 자동으로 생성 해주는 편리한 기능이 있다.
> 개발의 편의성이 증가한다.
> 물론 쿼리 성능문제가 있을 수 있고, N+1 문제 등이 있기 때문에 필요시 적절히 쿼리 튜닝을해야 한다.


<br/>

## 메소드 생성 키워드

| Keyword |	Sample |	JPQL snippet |
| ----- | ----- | ----- |
| Distinct	| findDistinctByLastnameAndFirstname |	select distinct … where x.lastname = ?1 and x.firstname = ?2 |
| And |	findByLastnameAndFirstname |	… where x.lastname = ?1 and x.firstname = ?2 |
| Or	| findByLastnameOrFirstname	| … where x.lastname = ?1 or x.firstname = ?2 |
| Is、Equals	| findByFirstname, findByFirstnameIs, findByFirstnameEquals| 	… where x.firstname = ?1 |
| Between	| findByStartDateBetween| 	… where x.startDate between ?1 and ?2 |
| LessThan	| findByAgeLessThan| 	… where x.age < ?1 |
| LessThanEqual	| findByAgeLessThanEqual| 	… where x.age <= ?1 |
| GreaterThan	| findByAgeGreaterThan | 	… where x.age > ?1 |
| GreaterThanEqual| 	findByAgeGreaterThanEqual	| … where x.age >= ?1 |
| After| 	findByStartDateAfter	| … where x.startDate > ?1 |
| Before	| findByStartDateBefore	| … where x.startDate < ?1 |
| IsNull、Null	| findByAge(Is)Null	| … where x.age is null |
| IsNotNull、NotNull	| findByAge(Is)NotNull	| … where x.age not null |
| Like| 	findByFirstnameLike	| … where x.firstname like ?1 |
| NotLike	| findByFirstnameNotLike	| … where x.firstname not like ?1 |
| StartingWith	| findByFirstnameStartingWith	| … where x.firstname like ?1 ( %가 뒤에 추가된 매개 변수) |
| EndingWith	| findByFirstnameEndingWith | 	… where x.firstname like ?1 ( %가 앞에 추가된 매개 변수) |
| Containing	| findByFirstnameContaining	|  … where x.firstname like ?1 ( %가 래핑된 매개 변수) |
| OrderBy	| findByAgeOrderByLastnameDesc	| … where x.age = ?1 order by x.lastname desc |
| Not| 	findByLastnameNot | … where x.lastname <> ?1 |
| In	| findByAgeIn(Collection<Age> ages) | 	… where x.age in ?1 |
| NotIn	| findByAgeNotIn(Collection<Age> ages | 	… where x.age not in ?1 |
| True	| findByActiveTrue() | 	… where x.active = true |
| False	| findByActiveFalse()	| … where x.active = false |
| IgnoreCase | 	findByFirstnameIgnoreCase | 	… where UPPER(x.firstname) = UPPER(?1) |

  
<br/>

## 예제
- 내가 작성한 코드 - 레포 정리 중
  
#### Is / Equals / Not
```java
// SELECT e FROM Employee e
Employee findById(Long id);

// SELECT e FROM Employee e WHERE e.firstname = ?1 
List<Employee> findByFirstnameEquals(String firstname);

// SELECT e FROM Employee e WHERE e.age = ?1
List<Employee> findByAgeIs(int age);

// SELECT e FROM Employee e WHERE e.lastname != ?1
List<Employee> findByLastnameNot(String lastname);

// SELECT e FROM Employee e WHERE e.department = ?1
List<Employee> findByDepartment(Department department);
```
  
  
#### LessThan / GreaterThan
```java
// SELECT e FROM Employee e WHERE e.age < ?1
List<Employee> findByAgeLessThan(int age);

// SELECT e FROM Employee e WHERE e.age > ?1
List<Employee> findByAgeGreaterThan(int age);

// SELECT e FROM Employee e WHERE e.hired_at > ?1
List<Employee> findByHiredAtGreaterThan(Date date);
  
  
// SELECT e FROM Employee WHERE e.age <= ?1
List<Employee> findByAgeLessThanEqual(int age);

// SELECT e FROM Employee WHERE e.age >= ?1
List<Employee> findByAgeGreaterThanEqual(int age);

// SELECT e FROM Employee WHERE e.hired_at BETWEEN ?1 AND ?2
List<Employee> findByHiredAtBetween(Date since, Date until);
```
  
  
#### Like / NotLike / StartingWith / EndingWith / Containing
```java
// SELECT e FROM Employee WHERE e.firstname LIKE ?1
List<Employee> findByFirstnameLike(int age);

// SELECT e FROM Employee WHERE e.firstname NOT LIKE ?1
List<Employee> findByFirstnameNotLike(String firstname);

// SELECT e FROM Employee WHERE e.lastname LIKE ?1 (앞에 일치)
List<Employee> findByLastnameStartingWith(String lastname);

// SELECT e FROM Employee WHERE e.lastname LIKE ?1 (뒤에 일치)
List<Employee> findByLastnameEndingWith(String lastname);

// SELECT e FROM Employee WHERE e.lastname LIKE ?1 (모두 일치)
List<Employee> findByLastnameContaining(String lastname);
```
  
  
  
  

#### Between
```java
// SELECT e FROM Employee e WHERE e.age BETWEEN ?1 AND ?2
List<Employee> findByAgeBetween(int olderThan, int youngerThan);

// SELECT e FROM Employee e WHERE e.hiredAt BETWEEN ?1 AND ?2
List<Employee> findByHiredAtBetween(Date since, Date until);

```
  
  

#### IsNull / (Is)NotNull
```java
// SELECT e FROM Employee WHERE e.hiredAt IS NULL
List<Employee> findByHiredAtIsNull();

// SELECT e FROM Employee WHERE e.hiredAt IS NOT NULL
List<Employee> findByHiredAtIsNotNull();

// SELECT e FROM Employee WHERE e.hiredAt IS NOT NULL
List<Employee> findByHiredAtNotNull();
```
  
  

#### True / False
```java
// SELECT e FROM Employee WHERE e.active = true
List<Employee> findByActiveTrue();

// SELECT e FROM Employee WHERE e.active = false
List<Employee> findByActiveFalse();
```
  
  

#### IN
```java
// SELECT e FROM Employee WHERE e.lastname in ?1
List<Employee> findByLastnameIn(List<String> lastname);
```
  

#### After / Before
```java
// SELECT e FROM Employee WHERE e.lastname > ?1
List<Employee> findByHiredAtAfter(Date date);

// SELECT e FROM Employee WHERE e.lastname < ?1
List<Employee> findByHiredAtBefore(Date date);
```
  
  
  
#### OrderBy
```
// SELECT e FROM Employee e WHERE e.lastname = ?1 ORDER BY e.age
List<Employee> findByLastnameOrderByAge(String lastname);

// SELECT e FROM Employee e WHERE e.firstname = ?1 ORDER BY e.firstname ASC
List<Employee> findByFirstnameOrderByHiredAtAsc(String firstname);
  
// SELECT e FROM Employee e WHERE e.lastname = ?1 ORDER BY e.age ASC, e.firstname DESC
List<Employee> findByLastnameOrderByAgeAscFirstnameDesc(String lastname);
```
  
  
#### Top / First 
```
Employee findFirstByLastname(String lastname);

Employee findTopByLastname(String lastname);

List<Employee> findFirst3ByActiveTrueOrderByAgeDesc();

List<Employee> findTop5ByHiredAtIs(Date date);
```
  
  
#### 키워드 조합
```
// SELECT e FROM Employee e WHERE e.age = ?1, e.active = true
List<Employee> findByAgeIsAndActiveTrue(int age);

// SELECT e FROM Employee e WHERE e.lastname LIKE ?1 OR e.lastname LIKE ?2
List<Employee> findByLastnameStartingWithOrFirstnameEndingWith(String lastname, String firstname);

// SELECT e FROM Employee e WHERE e.age BETWEEN ?1 AND ?2 AND e.department = ?3
List<Employee> findByAgeBetweenAndDepartmentIs(int startAge, int endAge, Department department);
```
  
  
<br/>
  
  
## 출처
- [링크](https://www.devkuma.com/docs/jpa/%EC%9E%90%EB%8F%99-%EC%83%9D%EC%84%B1-%EC%BF%BC%EB%A6%AC-%EB%A9%94%EC%86%8C%EB%93%9C%EC%9D%98-%EB%AA%85%EB%AA%85-%EA%B7%9C%EC%B9%99/)
- [공식문서](https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#reference)
