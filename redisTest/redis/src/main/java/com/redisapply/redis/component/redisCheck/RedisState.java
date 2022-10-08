package com.redisapply.redis.component.redisCheck;

import com.redisapply.redis.domain.users.RequestUserInfo;

public interface RedisState {
    boolean checkRedisChange(RequestUserInfo requestUserInfo);
}
