package com.hodolog.hodollog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private Map<String, String> validation = new HashMap<>();

    public void addValidation(String field, String defaultMessage) {
        this.validation.put(field, defaultMessage);
    }
}
