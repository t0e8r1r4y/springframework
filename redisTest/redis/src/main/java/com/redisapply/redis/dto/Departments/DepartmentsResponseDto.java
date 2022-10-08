package com.redisapply.redis.dto.Departments;

import com.redisapply.redis.domain.department.Departments;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentsResponseDto {

    private String departmentId;
    private String departmentName;
    private String note;


    @Builder
    public DepartmentsResponseDto(Departments entity)
    {
        this.departmentId = entity.getDepartmentId();
        this.departmentName = entity.getDepartmentName();
        this.note = entity.getNote();
    }
}
