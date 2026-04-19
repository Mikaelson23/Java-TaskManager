package com.mikaelson.taskManager.dto.request;

import com.mikaelson.taskManager.entity.Priority;

import java.time.LocalDate;

public record TaskCreateRecord(
        Long id,
        String title,
        String description,
        Priority priority,
        LocalDate limitDate) {
}
