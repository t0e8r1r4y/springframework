package com.redisapply.redis.service.records;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redisapply.redis.domain.records.RecordsRepository;
import com.redisapply.redis.domain.records.ResponseRecordString;
import com.redisapply.redis.domain.records.ResponseRecordStringRedisRepository;
import com.redisapply.redis.dto.records.RecordsResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class RecordService {

    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    ResponseRecordStringRedisRepository responseRecordStringRedisRepository;

    // for test ( cache )
    private ResponseRecordString hitCacheByKeyRecord(Long employeeId)
    {
        Optional<ResponseRecordString> optionalResponseRecordString = responseRecordStringRedisRepository
                                                                        .findById(employeeId);

        if(!optionalResponseRecordString.isPresent()) return null;
        log.info(Long.toString(optionalResponseRecordString.get().getEmployeeId()) + " is hit cache.!!");
        return optionalResponseRecordString.get();
    }

    // for test ( cache )
    private String recordList(Long employeeId)
    {
        String result = "";
        List<RecordsResponseDto> recordsResponseDtoList = recordsRepository.selectJPQLFromRecordByEmployeeId(employeeId)
                .map(RecordsResponseDto::new)
                .collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(recordsResponseDtoList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // for test ( cache )
    private void saveCache(ResponseRecordString responseRecordString)
    {
        responseRecordStringRedisRepository.save(responseRecordString);
    }

    // for test ( cache )
    @Transactional(readOnly = true)
    public boolean findRecords( Long employeeId )
    {
        log.info(Long.toString(employeeId));
        if( hitCacheByKeyRecord(employeeId) == null )
        {
            log.info("cache not hits");
            String jsonStringAsRecordList = recordList(employeeId);
            log.info(jsonStringAsRecordList);
            if(jsonStringAsRecordList.equals("[ ]")) return false;
            log.info("DB has data! cache save");
            saveCache(new ResponseRecordString(employeeId, jsonStringAsRecordList));
        }
        return true;
    }
}
