package com.redisapply.redis.dto.Users;

import com.redisapply.redis.domain.users.RequestUserInfo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UsersRequestDto {
    private Long id;
    private String lastName;
    private String firstName;

    public RequestUserInfo toEntity()
    {
        return RequestUserInfo.builder()
                .id(id)
                .lastName(lastName)
                .firstName(firstName)
                .build();
    }

    @Builder
    public UsersRequestDto(Long id, String lastName, String firstName)
    {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
