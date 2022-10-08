package com.redisapply.redis.domain.manager;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "manager")
@Entity
public class Managers {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Builder
    public Managers(Long employeeId, String departmentId,String startDate, String endDate)
    {
        this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
