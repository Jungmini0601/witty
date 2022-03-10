package com.daelim.witty.v1.web.exhandler;

import com.daelim.witty.v1.web.exception.BadRequestException;
import com.daelim.witty.v1.web.exception.ForbbiddenException;
import com.daelim.witty.v1.web.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
//@RestControllerAdvice(basePackages = "com.daelim.witty.web.controller")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResult badRequestExHandler(BadRequestException e) {
        return new ErrorResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ErrorResult unAuthorizedExHandler(UnAuthorizedException e) {
        return new ErrorResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbbiddenException.class)
    public ErrorResult forbiddenExHandler(ForbbiddenException e) {
        return new ErrorResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResult methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ErrorResult("path 경로 값이 잘못 되었습니다");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult internalServerError(Exception e) {
        return new ErrorResult(e.getMessage());
    }
}
