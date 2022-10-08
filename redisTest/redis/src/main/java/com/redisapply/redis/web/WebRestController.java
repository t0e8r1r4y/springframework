package com.redisapply.redis.web;

import com.redisapply.redis.dto.Users.UsersRequestDto;
import com.redisapply.redis.dto.Users.UsersResponseDto;
import com.redisapply.redis.service.records.RecordService;
import com.redisapply.redis.service.users.UsersService;
import com.redisapply.redis.service.managers.ManagerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class WebRestController {
    private UsersService usersService;
    private ManagerService managerService;

    private RecordService recordService;

    /**
     * for test - 데이터 조회 테스트용으로 작성한 함수입니다.
     * @param dto
     */
    @PostMapping("/users")
    public void reqUser(@RequestBody UsersRequestDto dto){

        List<UsersResponseDto> list = usersService.findUser(dto);
        log.info(Integer.toString(list.size()));
        return;
    }

    /**
     * for test - 데이터 조회 테스트용으로 작성한 함수입니다.
     * @param dto
     */
    @PostMapping("/users2")
    public void reqUsers(@RequestBody UsersRequestDto dto)
    {
        List<UsersResponseDto> list = usersService.findUserName(dto);
        log.info(list.get(list.size()-1).getBirth());
        return;
    }

    /**
     * for test - 데이터 조회 테스트용으로 작성한 함수입니다.
     * @param dto
     * @return
     */
    @PostMapping("/findManager")
    public boolean reqManagerInfo(@RequestBody UsersRequestDto dto)
    {
        return managerService.findManager(dto);
    }

    /**
     * for test - 데이터 조회 테스트용으로 작성한 함수입니다.
     * @param id
     */
    @PostMapping("/records")
    public void reqRecordInfo(@RequestBody Long id)
    {
        recordService.findRecords(id);
        return;
    }
}
