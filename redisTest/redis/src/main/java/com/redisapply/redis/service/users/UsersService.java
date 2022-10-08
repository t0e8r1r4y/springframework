package com.redisapply.redis.service.users;

import com.redisapply.redis.domain.loginInfo.ManagerLoginInfo;
import com.redisapply.redis.domain.users.UsersRepository;
import com.redisapply.redis.dto.Users.UsersRequestDto;
import com.redisapply.redis.dto.Users.UsersResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class UsersService {
    private UsersRepository usersRepository;

    // for test
    public List<UsersResponseDto> findUser(UsersRequestDto userInfo)
    {
        return usersRepository.selectJPQLById(userInfo.toEntity().getId());
    }

    // for test
    public List<UsersResponseDto> findUserName(UsersRequestDto userInfo)
    {
        return usersRepository.selectJPQLByName(userInfo.toEntity());
    }

    /**
     * input : 로그인 화면에서 전달받은 정보
     * Processing : 데이터베이스에서 입력받은 사용자가 실제 사용자인지 확인
     * output : 사용자 정보가 DB에서 조회가 된다면 true, 그렇지 않다면 false
     *
     * @param managerLoginInfo
     * @return
     */
    public boolean findUserByEmployeeId(ManagerLoginInfo managerLoginInfo)
    {
        List<UsersResponseDto> list = usersRepository.selectJPQLByIdAndName(managerLoginInfo);
        // 데이터 상으로는 위 조건만으로도 중복이 없어서 아래 처리 로직으로 함 -> 데이터 변동이 있다면 별도의 처리가 필요함
        if(list.size() > 0) return true; // 중복이 없으므로 조회하고자 하는 대상이 있다면 크기가 1임
        log.info("Not Found Users in UsersService. " + Long.toString(managerLoginInfo.getEmployeeId()));
        return false;
    }

    /**
     *
     * input : 부서 번호
     * processing : 부서 번호를 기준으로 해당 부서에 포함 된 모든 사용자 출력
     * output : 조회 결과를 list에 담아서 반환
     *
     * @param managerLoginInfo
     * @return
     */
    @Transactional(readOnly = true)
    public List<UsersResponseDto> findUserByEmployeeIdAndDepartmentId(ManagerLoginInfo managerLoginInfo)
    {
        return usersRepository.selectJPQLFromEmployeeByDepartment(managerLoginInfo.getDepartmentId())
                .map(UsersResponseDto::new).collect(Collectors.toList());
    }

}
