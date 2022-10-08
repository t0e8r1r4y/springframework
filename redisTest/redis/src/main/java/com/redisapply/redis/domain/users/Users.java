package com.redisapply.redis.domain.users;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "employee")
@Entity
public class Users {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "birth")
    private String birth;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "sex")
    private char sex;

    @Column(name = "join_date")
    private String joinDate;

    @Builder
    public Users(Long id, String birth, String lastName, String firstNamem, char sex, String joinDate)
    {
        this.id = id;
        this.birth = birth;
        this.lastName = lastName;
        this.firstName = firstNamem;
        this.sex = sex;
        this.joinDate = joinDate;
    }

}
