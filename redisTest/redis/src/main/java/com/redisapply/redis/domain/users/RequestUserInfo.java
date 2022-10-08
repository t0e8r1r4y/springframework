package com.redisapply.redis.domain.users;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

// for test
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash(value = "manager", timeToLive = 60)
public class RequestUserInfo {
    @Id
    private Long id;
    private String lastName;
    private String firstName;

    @Builder
    public RequestUserInfo(Long id, String lastName, String firstName)
    {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
