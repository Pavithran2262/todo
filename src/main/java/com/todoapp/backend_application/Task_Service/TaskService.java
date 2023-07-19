package com.todoapp.backend_application.Task_Service;

import com.todoapp.backend_application.Task_Entity.TaskTable;
import com.todoapp.backend_application.Task_Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public TaskTable addTask(TaskTable taskTable) {
        return taskRepository.save(taskTable);
    }


    public List<TaskTable> getAll() {
        return taskRepository.findAll();
    }

    public TaskTable getById(long id) {
        return taskRepository.getByid(id);
    }
}
