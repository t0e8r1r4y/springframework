package com.redisapply.redis.domain.records;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface RecordsRepository extends JpaRepository<Records, Long> {
    @Query(value = "select r from Records r where exists " +
            "(select u from Users u where u.id = r.employeeId and r.recordSymbol = 'O') " +
            "and r.employeeId = :employeeId")
    Stream<Records> selectJPQLFromRecordByEmployeeId(@Param(value = "employeeId")Long EmployeeId);
}
