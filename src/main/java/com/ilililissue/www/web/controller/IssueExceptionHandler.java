package com.ilililissue.www.web.controller;

import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ilililissue.www.web")
public class IssueExceptionHandler {

    @ExceptionHandler(value = NotAuthorizedManagerException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    protected void handleNotAuthorizedManagerException(RuntimeException ex) {

    }
}
