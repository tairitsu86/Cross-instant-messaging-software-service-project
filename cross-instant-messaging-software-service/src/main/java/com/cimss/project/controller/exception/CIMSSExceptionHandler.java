package com.cimss.project.controller.exception;

import com.cimss.project.apibody.MessageBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CIMSSExceptionHandler {
    @ExceptionHandler(NoPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageBean unauthorizedExceptionHandler(NoPermissionException e) {
        return MessageBean.CreateMessageBean(e.getErrorMessage());
    }
    @ExceptionHandler(RequestNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageBean requestNotFoundExceptionHandler(RequestNotFoundException e) {
        return MessageBean.CreateMessageBean(e.RequestValue());
    }
    @ExceptionHandler(WrongFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageBean wrongFormatExceptionHandler(WrongFormatException e) {
        return MessageBean.CreateMessageBean("Wrong format!");
    }
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageBean dataNotFoundExceptionHandler(DataNotFoundException e) {
        return MessageBean.CreateMessageBean(e.getErrorMessage());
    }
}
