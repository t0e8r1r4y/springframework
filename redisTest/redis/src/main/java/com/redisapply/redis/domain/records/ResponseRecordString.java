package com.redisapply.redis.domain.records;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import org.springframework.data.annotation.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash(value = "record", timeToLive = 180)
public class ResponseRecordString {
    @Id
    private Long employeeId;

    private String records;

    @Builder
    public ResponseRecordString(Long employeeId, String records)
    {
        this.employeeId = employeeId;
        this.records = records;
    }
}
