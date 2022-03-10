package com.daelim.witty.v1.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: 김정민
 * @Use: 입력값이 검증 실패시 호출하는 에러
 * */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "입력값 확인 필요")
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
