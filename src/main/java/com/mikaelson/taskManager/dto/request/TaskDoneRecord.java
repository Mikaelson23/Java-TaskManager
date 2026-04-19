package com.mikaelson.taskManager.dto.request;

import com.mikaelson.taskManager.entity.Priority;
import com.mikaelson.taskManager.entity.Status;

import java.time.LocalDate;

public record TaskDoneRecord(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        LocalDate limitDate,
        LocalDate completeDate
){}

