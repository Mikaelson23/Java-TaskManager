package com.mikaelson.taskManager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotHasAuthorityException extends RuntimeException {
    public UserNotHasAuthorityException(String userName) {
        super("Usuario não autorizado: "+ userName);
    }
}
