package com.redisapply.redis.web;

import com.redisapply.redis.domain.loginInfo.ManagerLoginInfo;
import com.redisapply.redis.dto.Users.UsersResponseDto;
import com.redisapply.redis.service.cache.UserListService;
import com.redisapply.redis.service.managers.ManagerService;
import com.redisapply.redis.service.users.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class WebController {

    private ManagerService managerService;

    private UsersService usersService;

    private UserListService userListService;

    @GetMapping("/")
    public String main(Model model)
    {
        return "main";
    }

    @RequestMapping("/login")
    public String createLoginForm()
    {
        return "login";
    }

    /**
     * ( 편의상 로그인이라고 지칭합니다. )
     * input : 매니저의 로그인 정보( employeeId, lastName, firstName, departmentId )
     * processing : DB에서 로그인 정보를 기준으로 해당 데이터가 매니저인지, 등록된 사원인지, 입력한 부서번호가 맞는지 확인하고 부서에 속한 모든 직원 조회
     * output : 조회 결과를 view로 제공함
     *
     * Test Data : 110039 l:Vishwani f: Minakawa d001
     *
     * @param allParam
     * @param model
     * @return
     */
    @RequestMapping("/loginresult")
    public String getLoginInfo(@RequestParam Map<String, Object> allParam, Model model)
    {
        ManagerLoginInfo managerLoginInfo = new ManagerLoginInfo(
                                                    Long.parseLong(allParam.get("employeeId").toString()),
                                                    allParam.get("lastName").toString(),
                                                    allParam.get("firstName").toString(),
                                                    allParam.get("departmentId").toString()
                                                    );

        // 입력 받은 정보로 사원정보를 조회하여 정보가 없으면 그냥 로그인 창으로 뿌립니다.
        if(!usersService.findUserByEmployeeId(managerLoginInfo)) return "login";

        // 입력 받은 정보 중 사원번호를 입력하여 해당 담당자가 매니저가 맞는지? 그리고 담당 부서가 일치하는지 확인합니다. 만약 일치하지 않으면(anonymous) login창을 그대로 return 합니다.
        Optional<String> stringOptional = Optional.ofNullable(managerService.findManagerByEmployeeId(managerLoginInfo));
        String departmendId = stringOptional.orElse("anonymous");
        if(departmendId.equals("anonymous")) return "login";
        // 매니저가 존재하는데 매니저 로그인 정보에 입력한 부서번호와 동일하지 않으면 로그인 실패. login 창을 그대로 return 합니다.
        if(!stringOptional.get().equals(managerLoginInfo.getDepartmentId())) return "login";

        // manager의 로그인 정보의 유효성 검증이 완료가 된다면, 자신이 담당하고 있는 부서의 직원 목록을 모두 뿌립니다.
        // 여기서는 캐싱을 사용합니다.
        log.info("Data load start");
        List<UsersResponseDto> usersResponseDtoList = null;
        usersResponseDtoList = userListService.hitCacheUserList(managerLoginInfo); // 캐시 조회
        if(usersResponseDtoList == null) // 캐시 데이터 not hit
        {
            // 데이터베이스에서 정보 조회
            usersResponseDtoList = usersService.findUserByEmployeeIdAndDepartmentId(managerLoginInfo);
            // 캐시에 저장
            userListService.saveCacheUserList(managerLoginInfo, usersResponseDtoList);
        }
        // 출력 로깅
        log.info("Totally, " + usersResponseDtoList.size() + " is loaded. display fornt end");
        model.addAttribute("UsersResponseDtoList", usersResponseDtoList);
        return "loginresult";
    }
}
