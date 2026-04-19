package com.mikaelson.taskManager.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mikaelson.taskManager.entity.Status;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskStatusResponse(Long id, Status status, LocalDate dateStarted, LocalDate dateComplete) {
}
