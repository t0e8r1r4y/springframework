package com.redisapply.redis.domain;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redisapply.redis.domain.department.DepartmentsRepository;
import com.redisapply.redis.domain.manager.ManagersRepository;
import com.redisapply.redis.domain.records.Records;
import com.redisapply.redis.domain.records.RecordsRepository;
import com.redisapply.redis.domain.users.RequestUserInfo;
import com.redisapply.redis.domain.users.UsersRepository;
import com.redisapply.redis.dto.Managers.ManagersResponseDto;
import com.redisapply.redis.dto.records.RecordsResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class MysqlBasicRead {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ManagersRepository managersRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @Autowired
    RecordsRepository recordsRepository;

    @Test
    @Transactional(readOnly = true)
    public void test1()
    {
        RequestUserInfo requestUserInfo = new RequestUserInfo(110420L,"Oscar","Ghazalie");
        List<ManagersResponseDto> list = managersRepository.selectJPQLByUserInfo(requestUserInfo)
                .map(ManagersResponseDto::new)
                .collect(Collectors.toList());

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getEmployeeId());
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void 레코드조회테스트() throws IOException {
        List<RecordsResponseDto> list = recordsRepository.selectJPQLFromRecordByEmployeeId(110033L)
                .map(RecordsResponseDto::new)
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getEmployeeId());
            sb.append(" ");
            sb.append(list.get(i).getId());
            sb.append(" ");
            sb.append(list.get(i).getTime());
            sb.append(" ");
            sb.append(list.get(i).getRecordSymbol());
            sb.append(" ");
            System.out.println(sb.toString());
            sb.setLength(0);
        }

        ObjectMapper mapper = new ObjectMapper();

// object to json

        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        String t = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        System.out.println(t);
//        mapper.writeValue(new File("test.json"), list);
    }

}
