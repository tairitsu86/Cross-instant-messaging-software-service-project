package com.cimss.project.controller;

import com.cimss.project.apibody.MessageBean;
import com.cimss.project.controller.exception.DataNotFoundException;
import com.cimss.project.controller.exception.RequestNotFoundException;
import com.cimss.project.controller.exception.UnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CIMSSExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageBean unauthorizedExceptionHandler(UnauthorizedException e) {
        return MessageBean.CreateMessageBean("Authorization error!");
    }
    @ExceptionHandler(RequestNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageBean requestNotFoundExceptionHandler(@NotNull RequestNotFoundException e) {
        return MessageBean.CreateMessageBean(e.RequestValue());
    }
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageBean dataNotFoundExceptionHandler(@NotNull DataNotFoundException e) {
        return MessageBean.CreateMessageBean(e.getErrorMessage());
    }
}