package com.redisapply.redis.domain.users;

import org.springframework.data.repository.CrudRepository;

public interface RequestUserInfoRedisRepository extends CrudRepository<RequestUserInfo, Long> {
}
