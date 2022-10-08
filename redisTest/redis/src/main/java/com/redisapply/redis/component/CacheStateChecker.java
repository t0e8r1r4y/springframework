package com.redisapply.redis.component;

import com.redisapply.redis.component.redisCheck.RedisCheckerTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class CacheStateChecker implements ApplicationRunner {

    private ExecutorService executorService;
    private RedisCheckerTask redisCheckerTask = new RedisCheckerTask();

    @Override
    public void run(ApplicationArguments args)
    {
        // single thread for check redis key
        executorService = Executors.newSingleThreadExecutor();
        // thread start
        executorService.submit(redisCheckerTask);
        // if thread task is ended, resource is returned
        executorService.shutdown();
    }
}
