package com.redisapply.redis.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redisapply.redis.domain.loginInfo.ManagerLoginInfo;
import com.redisapply.redis.domain.users.Users;
import com.redisapply.redis.dto.Users.UsersResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UserListService {

    private RedisTemplate redisTemplate;

    public List<UsersResponseDto> hitCacheUserList(ManagerLoginInfo managerLoginInfo)
    {
        // ObjectMapper mapper = new ObjectMapper() 를 선언하고 처리를 하려고했으나 역직렬화 로직에서 제대로 값을 처리하지 못함.
        // 직렬화를 Json format으로 처리했기 때문에 simple-json으로 역직렬화를하여 값을 리턴함
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String result = valueOperations.get(managerLoginInfo.getDepartmentId());
        if(result == null) return null;
        return jsonParser(result);
    }

    public void saveCacheUserList(ManagerLoginInfo managerLoginInfo, List<UsersResponseDto> usersResponseDtoList)
    {
        String result = "";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        try {
            result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(usersResponseDtoList);
            valueOperations.set(managerLoginInfo.getDepartmentId(), result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return;
    }

    private List<UsersResponseDto> jsonParser(String str)
    {
        List<UsersResponseDto> usersResponseDtoList = new LinkedList<>(); // 최종 반환 값
        JSONParser jsonParser = new JSONParser();
        Object obj;

        try {
            obj = jsonParser.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONArray jsonArray = (JSONArray) obj;
        Iterator<JSONObject> iter = jsonArray.iterator();

        while(iter.hasNext())
        {
            JSONObject jsonObject = iter.next();
            Users users = new Users( Long.parseLong(jsonObject.get("id").toString()),
                                    jsonObject.get("birth").toString(),
                                    jsonObject.get("lastName").toString(),
                                    jsonObject.get("firstName").toString(),
                                    jsonObject.get("sex").toString().charAt(0),
                                    jsonObject.get("joinDate").toString()
            );
            usersResponseDtoList.add(new UsersResponseDto(users));
        }

        return usersResponseDtoList;
    }
}
