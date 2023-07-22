package com.todoapp.backend_application.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private long taskId;

    private String taskName;

    private boolean status;

    private String description;


}
