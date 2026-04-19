package com.mikaelson.taskManager.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mikaelson.taskManager.entity.Priority;
import com.mikaelson.taskManager.entity.Status;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskResponseRecord(
        Long id,
        String title,
        String descricao,
        Status status,
        Priority priority,
        LocalDate limitDate
)  {
}
