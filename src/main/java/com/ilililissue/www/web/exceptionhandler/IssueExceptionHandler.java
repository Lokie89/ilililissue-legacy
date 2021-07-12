package com.ilililissue.www.web.exceptionhandler;

import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import com.ilililissue.www.exception.comment.CanNotUpdateCommentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ilililissue.www.web")
public class IssueExceptionHandler {

    @ExceptionHandler(value = CanNotRegisterCommentException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected void handleCannotRegisterCommentException(RuntimeException ex) {

    }

    @ExceptionHandler(value = {CanNotUpdateCommentException.class, CanNotRemoveCommentException.class})
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    protected void handleCannotUpdateCommentException() {

    }
}
