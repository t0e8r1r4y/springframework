package com.redisapply.redis.dto.Managers;

import com.redisapply.redis.domain.manager.Managers;
import lombok.*;

@Getter
public class ManagersResponseDto {
    private Long employeeId;
    private String departmentId;
    private String startDate;
    private String endDate;

    public ManagersResponseDto(Managers entity)
    {
        this.employeeId = entity.getEmployeeId();
        this.departmentId = entity.getDepartmentId();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
    }


}
