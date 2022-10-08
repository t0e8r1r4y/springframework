package com.redisapply.redis.domain.records;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "record")
@Entity
public class Records {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "time")
    private java.util.Date time;

    @Column(name = "record_symbol")
    private char recordSymbol;

    @Column(name = "door")
    private char door;

    @Column(name = "region")
    private char region;

    @Builder
    public Records(Long id, Long employeeId, java.util.Date time, char recordSymbol, char door, char region)
    {
        this.id = id;
        this.employeeId = employeeId;
        this.time = time;
        this.recordSymbol = recordSymbol;
        this.door =door;
        this.region = region;
    }


}
