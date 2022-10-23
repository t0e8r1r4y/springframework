package com.hodolog.hodollog.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 이 메시지를 적용하는 것은 회사마다 다르다. 다만 보통
 * {
 *     "Code" : "400",
 *     "message" : "잘못된 요청입니다.",
 *     "validation": {
 *         "title":"값을 입력해주세요.",
 *     }
 * }
 */
@Getter
public class ErrorResponse {
    private final String code;
    private final String message;

    //TODO : 가급적 Map을 직접사용하지 않는다. 꼭 사용한다면 일급 객체를 사용한다. -> 이건 일급 객체로 개선해봐야 함.
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String > validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(String field, String defaultMessage) {
        this.validation.put(field, defaultMessage);
    }
}
