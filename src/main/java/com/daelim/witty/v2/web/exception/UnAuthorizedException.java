package com.daelim.witty.v2.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: 김정민
 * @Use: 로그인을 해야 사용할 수 있는 기능인데 로그인을 안하고 사용하면 던지는 에러
 * */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "로그인 안된 유저")
public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String message) {
        super(message);
    }
}
