package com.mikaelson.taskManager.dto.response;

import com.mikaelson.taskManager.entity.Status;

public record TaskCreateResponse(Long id,
                                 String title,
                                 Status status) {
}
