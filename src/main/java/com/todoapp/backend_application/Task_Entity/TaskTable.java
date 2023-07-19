package com.todoapp.backend_application.Task_Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="taskTable")
public class TaskTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long taskId;

    private String taskName;

    private boolean status;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "usertable_id")
    private UserTable userTable;


}