package com.redisapply.redis.service.managers;

import com.redisapply.redis.domain.loginInfo.ManagerLoginInfo;
import com.redisapply.redis.domain.manager.ManagersRepository;
import com.redisapply.redis.domain.users.RequestUserInfo;
import com.redisapply.redis.domain.users.RequestUserInfoRedisRepository;
import com.redisapply.redis.dto.Managers.ManagersResponseDto;
import com.redisapply.redis.dto.Users.UsersRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ManagerService {

    private ManagersRepository managersRepository;
    private RequestUserInfoRedisRepository requestUserInfoRedisRepository; // key is "manager" ref RequestUserInfo.class

    // for test
    private RequestUserInfo hitCacheByKeyManager(RequestUserInfo requestUserInfo)
    {
        // repository.findById method return Optional Object.
        Optional<RequestUserInfo> optionalRequestUserInfo = requestUserInfoRedisRepository
                                                            .findById(requestUserInfo.getId());
        if(!optionalRequestUserInfo.isPresent()) return null;
        log.info(Long.toString(requestUserInfo.getId()) + " is hit cache.!!");

        return optionalRequestUserInfo.get();
    }

    // for test
    private List<ManagersResponseDto> findManagerFromDB(RequestUserInfo requestUserInfo)
    {
        return managersRepository.selectJPQLByUserInfo(requestUserInfo)
                .map(ManagersResponseDto::new)
                .collect(Collectors.toList());
    }

    // for test
    private ManagersResponseDto findManagerFromList(List<ManagersResponseDto> managerList, RequestUserInfo userInfo)
    {
        // Not use for loop twice. indet is only one
        return managerList.get(managerList.size()-1);
    }

    // for test
    private void saveCache(RequestUserInfo requestUserInfo)
    {
        requestUserInfoRedisRepository.save(requestUserInfo);
        return;
    }

    // for test ( cache )
    @Transactional(readOnly = true)
    public boolean findManager(UsersRequestDto userInfo)
    {
        RequestUserInfo requestUserInfo = userInfo.toEntity(); // make entityå
        RequestUserInfo requestUserInfoFromCache = hitCacheByKeyManager(requestUserInfo); // cache 조회

        ManagersResponseDto managersResponseDto = null;

        // cache is not hit
        if(requestUserInfoFromCache == null)
        {
            log.info("Cache is not hitto!!!");
            // find manager info from database
            managersResponseDto = findManagerFromList( findManagerFromDB(requestUserInfo), requestUserInfo );
            // result is null means that data is none.
            if(managersResponseDto == null) return false;
            log.info("DB is saved Data!!!");
            // data is in db. so save cache. when save cache, sava 'requestUserInfo'
            saveCache(requestUserInfo);
        }
        // cache is hit!
        return true;
    }

    /**
     * input : view에서 입력 받은 로그인 정보
     * processing : DB에서 입력 정보를 가진 사람이 매니저인지 DB에서 조회
     * output : DB에서 조회한 정보 return
     *
     * 비고 : 캐시는 데이터 유실 가능성이 있기 때문에 테스트 로직과 달리 캐싱을 사용하지 않았습니다.
     *
     * @param managerLoginInfo
     * @return
     */
    @Transactional(readOnly = true)
    public String findManagerByEmployeeId(ManagerLoginInfo managerLoginInfo)
    {
        List<ManagersResponseDto> managersResponseDtoList = managersRepository
                                                            .selectJPQLByManagerLoginInfo(managerLoginInfo)
                                                            .map(ManagersResponseDto::new)
                                                            .collect(Collectors.toList());

        // 데이터 조회 결과는 중복이 없고 하나만 조회가 되는 형태입니다.
        if(managersResponseDtoList.size() > 0)
            return managersResponseDtoList.get(managersResponseDtoList.size()-1).getDepartmentId();

        log.info("Not found info in ManagerService");
        return null;
    }

}
