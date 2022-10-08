package com.redisapply.redis.domain.department;

import com.redisapply.redis.domain.manager.Managers;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table( name = "department")
@Entity
@Setter
public class Departments {

    @Id
    @Column(name = "id")
    private String departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "note")
    private String note;

    @Builder
    public Departments(String departmentId, String departmentName, String note)
    {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.note = note;
    }
}
