package com.redisapply.redis.component.redisCheck;

import com.redisapply.redis.domain.manager.ManagersRepository;
import com.redisapply.redis.domain.users.RequestUserInfoRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Timestamp;

/**
 * 이슈 : 상태패턴을 사용하여 주기적으로 캐싱 정보를 업데이트 하려고 했으나, while문에서는 캐시 DB에 접근하지 못함
 */
@Slf4j
public class RedisCheckerTask implements Runnable{

    @Autowired
    private RequestUserInfoRedisRepository requestUserInfoRedisRepository;
    @Autowired
    private ManagersRepository managersRepository;
    @Autowired
    private RedisTemplate<String, String> myRedisTemplate;

    private void test()
    {
        log.info(new Timestamp(System.currentTimeMillis()).toString() + "TTTT");
        return;
    }

    @Override
    public void run()
    {
        while(true)
        {
//            test();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info(new Timestamp(System.currentTimeMillis()).toString());
                throw new RuntimeException(e);
            }

        }

    }

}




