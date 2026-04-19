package com.mikaelson.taskManager.dto.request;

public record UserLoginRequest(
        String userLogin,
        String userPassword
) {
}
