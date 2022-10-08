package com.redisapply.redis.domain.loginInfo;

import org.springframework.data.repository.CrudRepository;

public interface UserListRedisRepository extends CrudRepository<UserListCache, Long> {
}
