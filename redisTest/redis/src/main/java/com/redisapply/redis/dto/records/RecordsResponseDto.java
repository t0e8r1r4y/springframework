package com.redisapply.redis.dto.records;

import com.redisapply.redis.domain.records.Records;
import lombok.Getter;

@Getter
public class RecordsResponseDto {

    private Long id;
    private Long employeeId;
    private java.util.Date time;
    private char recordSymbol;
    private char door;
    private char region;

    public RecordsResponseDto(Records entity)
    {
        this.id = entity.getId();
        this.employeeId = entity.getEmployeeId();
        this.time = entity.getTime();
        this.recordSymbol = entity.getRecordSymbol();
        this.door  = entity.getDoor();
        this.region = entity.getRegion();
    }
}
