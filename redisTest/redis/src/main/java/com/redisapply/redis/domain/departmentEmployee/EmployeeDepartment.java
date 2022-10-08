package com.redisapply.redis.domain.departmentEmployee;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table( name = "employee_department")
@Entity
@Setter
public class EmployeeDepartment {

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
    public EmployeeDepartment(Long employeeId, String departmentId, String startDate, String endDate)
    {
        this.employeeId  = employeeId;
        this.departmentId = departmentId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
