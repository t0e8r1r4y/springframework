package com.redisapply.redis.domain;

import com.redisapply.redis.domain.users.RequestUserInfo;
import com.redisapply.redis.domain.users.RequestUserInfoRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class RedisBasicRest {
//    @Autowired
//    RedisTemplate<String, String> redisTemplate;
    @Autowired
    private  RedisTemplate<String, String> myRedisTemplate;

    @Autowired
    private RequestUserInfoRedisRepository requestUserInfoRedisRepository;

//    @Test
//    void redisConnectionTest() {
//        final String key = "a";
//        final String data = "1";
//
//        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set(key, data);
//
//        final String s = valueOperations.get(key);
//        Assertions.assertThat(s).isEqualTo(data);
//    }

    @Test
    void 레디스저장삭제테스트()
    {
        RequestUserInfo userInfo = new RequestUserInfo(1234L, "testt", "shi");
//        requestUserInfoRedisRepository.save(userInfo);
        System.out.println(requestUserInfoRedisRepository.findById(3456L));
        System.out.println(requestUserInfoRedisRepository.count());

    }

    @Test
    void 레디스여러객체저장테스트()
    {
        // given
        SetOperations<String, String> setOperations = myRedisTemplate.opsForSet();
        String key = "setKey";

        // when
        setOperations.add(key, "h", "e", "l", "l", "o");

        // then
        Set<String> members = setOperations.members(key);
        Long size = setOperations.size(key);

        assertThat(members).containsOnly("h", "e", "l", "o");
        assertThat(size).isEqualTo(4);
    }
}
