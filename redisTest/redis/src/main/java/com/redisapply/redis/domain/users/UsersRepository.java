package com.redisapply.redis.domain.users;

import com.redisapply.redis.domain.loginInfo.ManagerLoginInfo;
import com.redisapply.redis.dto.Users.UsersResponseDto;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // for test
    @Query(value = "select u from Users u where u.id = :id")
    public List<UsersResponseDto> selectJPQLById(@Param(value = "id") Long id);

    // for test
    @Query(value = "select u from Users u where u.firstName = :#{#userName.firstName} and u.lastName = :#{#userName.lastName}")
    public List<UsersResponseDto> selectJPQLByName(@Param(value = "userName") RequestUserInfo name );

    // for query employee in same department - Employee is working in department that inputted param currently.
    // 'current' condition is '9999_01_01'
    @Query(value = "select u from Users u where exists " +
                        "(select ed from EmployeeDepartment ed where " +
                            "u.id = ed.employeeId and " +
                            "ed.departmentId = :departmentId and " +
                            "ed.endDate = '9999_01_01')")
    Stream<Users> selectJPQLFromEmployeeByDepartment(@Param(value = "departmentId")String departmentId);

    @Query(value = "select u from Users u where u.id = :#{#managerInfo.employeeId} and " +
            "u.firstName = :#{#managerInfo.firstName} and " +
            "u.lastName = :#{#managerInfo.lastName}")
    public List<UsersResponseDto> selectJPQLByIdAndName(@Param(value = "managerInfo")ManagerLoginInfo managerLoginInfo);
}
