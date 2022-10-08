package com.redisapply.redis.domain.loginInfo;

import com.redisapply.redis.dto.Users.UsersResponseDto;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

import java.util.List;

// 사용하지 않음
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@RedisHash(value = "userList", timeToLive = 300)
public class UserListCache {
    @Id
    private Long managerEmployeeId;
    private String departmentId;
    List<UsersResponseDto> usersResponseDtoList;

    @Builder
    public UserListCache(Long managerEmployeeId, String departmentId)
    {
        this.managerEmployeeId = managerEmployeeId;
        this.departmentId = departmentId;
        this.usersResponseDtoList = null;
    }

    @Builder
    public UserListCache(Long managerEmployeeId, String departmentId, List<UsersResponseDto> usersResponseDtoList)
    {
        this.managerEmployeeId = managerEmployeeId;
        this.departmentId = departmentId;
        this.usersResponseDtoList = usersResponseDtoList;
    }
}
