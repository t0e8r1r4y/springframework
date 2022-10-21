# Hibernate/JPA에서 커스텀 ID를 부여하는 방법입니다.


<br/>


## 개요
- JPA를 사용하여 DB에 데이터를 저장하는 과정에서 일반적으로 ID 부여전략을 1,2,3 등을 자동으로 부여하는 전략을 사용하여 개발자가 직접 ID를 부여하지 않고 관리할 수 있는 방식을 많이 사용하였습니다.
- 이번 개발에서는 이 부분을 커스텀하게 사용하는 방법을 작성해 봅니다.


<br/>


## 💚 Why We Use?
- 보통 UUID를 사용하거나, 특별히 관리번호를 순차적으로 부여하는 등 별도의 상황이 필요할 때 사용하면 좋습니다.


<br/>

## How to Use?
- 먼저 CustomIdGenerator라고 명명한 클래스를 만듭니다. 여기서는 `IdentifierGenerator`인터페이스에서 configure과 generate를 오버라이드하여 필요한 ID를 생성합니다.

```java
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class CustomIdGenerator implements IdentifierGenerator {

    private String prefix;

    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {

        String query = String.format("select %s from %s",
                session.getEntityPersister(obj.getClass().getName(), obj)
                        .getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        Stream ids = session.createQuery(query).stream();

        Long max = ids.map(o -> o.toString().replace(prefix + "-", "" ))
                .mapToLong(num -> Long.parseLong((String) num))
                .max()
                .orElse(0L);

        return prefix + "-" + (max+1);
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }
}
```

- 적용할 엔터티에 아래와 같이 적용합니다.
```java
public class ReservesDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "detail-generator")
    @GenericGenerator(name = "detail-generator",
            parameters = @Parameter(name = "prefix", value = "d"),
            strategy = "com.robotTest.robotTest.reserve.domain.CustomIdGenerator")
    String id;
    
    ...
}
```

<br/>


## How to Operate?

정리중

<br/>

## ✔️ 참고자료
- [baeldung](https://www.baeldung.com/hibernate-identifiers)
