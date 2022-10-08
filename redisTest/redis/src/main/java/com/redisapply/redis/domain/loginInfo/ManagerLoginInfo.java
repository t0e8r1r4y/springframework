package com.redisapply.redis.domain.loginInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import org.springframework.data.annotation.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ManagerLoginInfo {

    @Id
    private Long employeeId;

    private String firstName;

    private String lastName;

    private String departmentId;

    @Builder
    public ManagerLoginInfo(Long employeeId, String firstName, String lastName, String departmentId)
    {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
    }

}
