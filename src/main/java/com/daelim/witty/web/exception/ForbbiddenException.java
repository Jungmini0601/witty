package com.daelim.witty.web.exception;

/**
 * @Author: 김정민
 * @Use: 권한 없는 사용자가 기능을 호출 할시 사용
 * */
public class ForbbiddenException extends RuntimeException{
    public ForbbiddenException() {
        super();
    }

    public ForbbiddenException(String message) {
        super(message);
    }
}
