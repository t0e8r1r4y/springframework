package com.redisapply.redis.domain.manager;

import com.redisapply.redis.domain.loginInfo.ManagerLoginInfo;
import com.redisapply.redis.domain.users.RequestUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ManagersRepository extends JpaRepository<Managers, Long> {
    // for test
    @Query(value = "select m from Managers m where exists " +
                        "(select u from Users u where " +
                        "u.id = m.employeeId and u.id = :#{#userinfo.id} " +
                        "and u.lastName = :#{#userinfo.lastName} and u.firstName = :#{#userinfo.firstName} )" +
                    "and m.endDate = '9999_01_01' " )
    Stream<Managers> selectJPQLByUserInfo(@Param(value = "userinfo") RequestUserInfo userInfo);

    @Query(value = "select m from Managers m where exists " +
            "(select u from Users u where " +
            "u.id = m.employeeId and u.id = :#{#userinfo.employeeId} " +
            "and u.lastName = :#{#userinfo.lastName} and u.firstName = :#{#userinfo.firstName} )" +
            "and m.endDate = '9999_01_01' " )
    Stream<Managers> selectJPQLByManagerLoginInfo(@Param(value = "userinfo") ManagerLoginInfo userInfo);
}
