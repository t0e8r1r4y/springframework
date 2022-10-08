package com.redisapply.redis.dto.Users;

import com.redisapply.redis.domain.users.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UsersResponseDto {
    private Long id;
    private String birth;
    private String lastName;
    private String firstName;
    private char sex;
    private String joinDate;

    public UsersResponseDto(Users entity)
    {
        id = entity.getId();
        birth = entity.getBirth();
        lastName = entity.getLastName();
        firstName = entity.getFirstName();
        sex = entity.getSex();
        joinDate = entity.getJoinDate();
    }
}
